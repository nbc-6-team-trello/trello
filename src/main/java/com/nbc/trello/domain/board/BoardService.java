package com.nbc.trello.domain.board;

import com.nbc.trello.domain.card.CardRepository;
import com.nbc.trello.domain.participants.Participants;
import com.nbc.trello.domain.participants.ParticipantsRepository;
import com.nbc.trello.domain.todo.TodoRepository;
import com.nbc.trello.domain.user.User;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {

  private final BoardRepository boardRepository;

  private final TodoRepository todoRepository;

  private final CardRepository cardRepository;

  private final ParticipantsRepository participantsRepository;

  //보드 생성
  public BoardResponseDto createBoard(BoardRequestDto requestDto, User user) {
    Board board = new Board(requestDto);
    boardRepository.save(board);

    Participants participants = new Participants(user, board);
    participants.setGenerator(true);
    participantsRepository.save(participants);

    return board.toDto();
  }

  //보드 전체 조회
  public List<BoardResponseDto> getBoardList() {
    List<Board> boardList = boardRepository.findAll();
    if (boardList.isEmpty()) {
      throw new IllegalArgumentException("조회할 수 있는 보드가 없습니다.");
    }
    return boardList.stream().map(BoardResponseDto::new).toList();
  }

  //보드 수정
  @Transactional
  public BoardResponseDto updateBoard(Long boardId, BoardRequestDto requestDto, User user) {
    Board board = boardRepository.findById(boardId)
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 보드입니다."));
    Participants participants = participantsRepository.findByBoardIdAndUserIdAndGenerator(boardId,
        user.getId(), true).orElseThrow(() -> new IllegalArgumentException("보드 생성자가 아닙니다."));

    board.setName(requestDto.getName());
    board.setColor(requestDto.getColor());
    board.setDescription(requestDto.getDescription());

    return board.toDto();
  }

  //보드 삭제
  @Transactional
  public BoardResponseDto deleteBoard(Long boardId, BoardRequestDto requestDto, User user) {
    Board board = boardRepository.findById(boardId)
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 보드입니다."));
    Participants participants = participantsRepository.findByBoardIdAndUserIdAndGenerator(boardId,
        user.getId(), true).orElseThrow(() -> new IllegalArgumentException("보드 생성자가 아닙니다."));
    boardRepository.delete(board);

    List<Participants> participantsList = participantsRepository.findByBoardId(boardId)
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 참가자입니다."));
    participantsRepository.deleteAll(participantsList);

    return board.toDto();
  }
  //보드 삭제수정 -> 만든사람만 가능하게 할 수 없음

}