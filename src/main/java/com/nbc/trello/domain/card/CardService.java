package com.nbc.trello.domain.card;

import com.nbc.trello.domain.author.Author;
import com.nbc.trello.domain.author.AuthorRepository;
import com.nbc.trello.domain.board.Board;
import com.nbc.trello.domain.board.BoardResponseDto;
import com.nbc.trello.domain.comment.Comment;
import com.nbc.trello.domain.comment.CommentRepository;
import com.nbc.trello.domain.comment.CommentService;
import com.nbc.trello.domain.participants.Participants;
import com.nbc.trello.domain.participants.ParticipantsRepository;
import com.nbc.trello.domain.todo.Todo;
import com.nbc.trello.domain.todo.TodoRepository;
import com.nbc.trello.domain.todo.TodoSequenceRequestDto;
import com.nbc.trello.domain.user.User;
import com.nbc.trello.domain.user.UserRepository;
import com.nbc.trello.global.util.UserDetailsImpl;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;
    private final CommentRepository commentRepository;
    private final TodoRepository todoRepository;
    private final UserRepository userRepository;
    private final ParticipantsRepository participantsRepository;
    private final AuthorRepository authorRepository;

    //카드 등록
    public CardResponseDto CardCreateService(Long boardId, Long todoId,
        CardRequestDto cardRequestDto, User user) {
        //참여자 검증
        validateParticipants(boardId, user);

        Card card = new Card(cardRequestDto);

        //칼럽이 있는지 검증
        Todo todo = todoRepository.findById(todoId).orElseThrow(()->new IllegalArgumentException("todo에 등록할 todo를 찾을 수 없습니다."));
        card.setTodo(todo);
        Card save = cardRepository.save(card);

        //카드 생성할 때 레파지토리 작업자 리파지토리에 생성
        authorRepository.save(new Author(user.getId(),card.getId()));

        return new CardResponseDto(boardId, todo.getId(), save.getId());
    }

    //카드 단건조회
    public CardCommentResponseDto cardGetService(Long boardId, Long todoId, Long cardId,UserDetailsImpl userDetails) {
        //참여자 검증
        validateParticipants(boardId, userDetails.getUser());
        //투두 있는지
        validateTodoExistInBoard(boardId, todoId);

        //카드가 있으면 조회
        Card card = cardRepository.findById(cardId).orElseThrow(()->new IllegalArgumentException("조회할 수 있는 카드가 없습니다."));
        List<Comment> byCardId = commentRepository.findByCardId(cardId);
        CardCommentResponseDto cardCommentResponseDto = new CardCommentResponseDto(card);

        for (Comment com : byCardId) {
            cardCommentResponseDto.getGetCommentResponseDtoList().add(
                GetCommentResponseDto.builder().commentId(com.getId())
                    .comment(com.getContent()).build()
            );
        }

        return CardCommentResponseDto.builder()
            .cardId(card.getId())
            .boardId(card.getTodo().getBoard().getId())
            .name(card.getName())
            .description(card.getDescription())
            .deadline(card.getDeadline())
            .getCommentResponseDtoList(cardCommentResponseDto.getGetCommentResponseDtoList())
            .build();
    }

    //카드 삭제
    public CardResponseDto CardDeleteService(Long boardId, Long todoId, Long cardId, User user) {
        //참여자 검증
        validateParticipants(boardId,user);

        //todo 검증
        validateTodoExistInBoard(boardId,todoId);

        Card card = cardRepository.findById(cardId).orElseThrow(()->new IllegalArgumentException("삭제할 카드가 존재하지 않습니다."));

        //작업자 검증
        validateAuthor(cardId,user.getId());

        if (Objects.equals(todoId, card.getTodo().getId())) {
            cardRepository.delete(card);
            List<Author> authorList = authorRepository.findByCardId(cardId).orElseThrow(()->new IllegalArgumentException("존재하지 않는 작업자입니다."));

            //작업자 삭제
            authorRepository.deleteAll(authorList);
        }

        return new CardResponseDto(boardId, todoId, cardId);
    }

    //카드 수정
    public CardResponseDto CardUpdateService(Long boardId, Long todoId, Long cardId,
        CardRequestDto cardRequestDto, User user) {
        //참여자 검증
        validateParticipants(boardId,user);

        //todo 검증
        validateTodoExistInBoard(boardId,todoId);

        Card card = cardRepository.findById(cardId).orElseThrow(()->new IllegalArgumentException("수정할 카드가 존재하지 않습니다."));

        //작업자 검증
        validateAuthor(cardId,user.getId());

        if (Objects.equals(todoId, card.getTodo().getId())) {
            card.CardUpdate(cardRequestDto);
            Card save = cardRepository.save(card);
        }

        return new CardResponseDto(boardId, card.getTodo().getId(), card.getId());
    }

    @Transactional
    public void inviteUser(Long boardId, Long todoId, Long userId, Long cardId, User user) {
        // 유저 확인
        user = findUserBy(user.getEmail());
        // 참여자 확인
        validateParticipants(boardId, user);
        // 보드에 투두 들어있나 확인
        validateTodoExistInBoard(boardId, todoId);
        // 투두에 카드 들어있나 확인
        validateCardExistInTodo(todoId, cardId);

        userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("존재하는 유저가 아니여서 초대할 수 없습니다."));
        if (Objects.equals(userId, user.getId())) {
            throw new IllegalArgumentException("자기 자신은 초대할 수 없습니다.");
        }
        boolean present = authorRepository.findByCardIdAndUserId(cardId, userId)
            .isPresent();
        if (present) {
            throw new IllegalArgumentException("이미 작업중인 사용자입니다.");
        }
            validateParticipantsVerification(boardId, userId);

            Author author = new Author(userId, cardId);
            authorRepository.save(author);
    }

    public void MoveCard(Long boardId, Long todoId, Long cardId, User user) {
        // 유저 확인
        user = findUserBy(user.getEmail());
        // 참여자 확인
        validateParticipants(boardId, user);
        // 보드에 투두 들어있나 확인
        validateTodoExistInBoard(boardId, todoId);

        Card card = cardRepository.findById(cardId).
            orElseThrow(()->new IllegalArgumentException("카드가 존재하지 않습니다."));

        Todo todo = todoRepository.findById(todoId).
            orElseThrow(()->new IllegalArgumentException("투두가 존재하지 않습니다."));

        card.setTodo(todo);

        cardRepository.save(card);

    }

    @Transactional
    public void changeSequenceCard(Long boardId, Long todoId, Long cardId, CardSequenceRequestDto cardSequenceRequestDto,
        User user) {
        // 참여자 확인
        validateParticipants(boardId, user);
        // 보드에 투두 들어있나 확인
        validateTodoExistInBoard(boardId, todoId);

        Card card = cardRepository.findById(cardId).orElseThrow(()->new IllegalArgumentException("카드가 존재하지 않습니다."));

        List<Card> cardList = cardRepository.findAll(Sort.by(Direction.ASC, "sequence"));

        double sequence = cardList.get(cardSequenceRequestDto.getSequence()).getSequence();
        double preSequence = cardList.get(cardSequenceRequestDto.getSequence() - 1).getSequence();

        if (cardSequenceRequestDto.getSequence() == 1) {
            card.updateSequence(preSequence, 0D);
        } else {
            card.updateSequence(sequence, preSequence);
        }
    }


    private User findUserBy(String email) {
        return userRepository.findByEmail(email).orElseThrow(
            () -> new EntityNotFoundException("User 가 존재하지 않습니다."));
    }

    private void validateParticipants(Long boardId, User user) {
        if (!participantsRepository.existsByBoardIdAndUserId(boardId, user.getId())) {
            throw new EntityExistsException("참여자가 존재하지 않습니다.");
        }
    }

    private void validateCardExistInTodo(Long todoId, Long cardId) {
        if (!cardRepository.existsByIdAndTodoId(cardId, todoId)) {
            throw new EntityExistsException("Todo 에 Card 가 존재하지 않습니다.");
        }
    }

    private void validateTodoExistInBoard(Long boardId, Long todoId) {
        if (!todoRepository.existsByIdAndBoardId(todoId, boardId)) {
            throw new EntityExistsException("Board 에 Todo 가 존재하지 않습니다.");
        }
    }

    private void validateAuthor(Long cardId, Long userId) {
        if (!authorRepository.existsByCardIdAndUserId(cardId, userId)) {
            throw new IllegalArgumentException("작업자가 아닙니다.");
        }
    }

    private void validateParticipantsVerification(Long boardId, Long userId) {
        if (!participantsRepository.existsByBoardIdAndUserId(boardId, userId)) {
            throw new EntityExistsException("참여자가 아닙니다.");
        }
    }

}
