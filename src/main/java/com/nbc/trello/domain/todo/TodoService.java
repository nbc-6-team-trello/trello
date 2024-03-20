package com.nbc.trello.domain.todo;

import com.nbc.trello.domain.board.Board;
import com.nbc.trello.domain.board.BoardRepository;
import com.nbc.trello.domain.participants.ParticipantsRepository;
import com.nbc.trello.domain.user.User;
import com.nbc.trello.domain.user.UserRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final ParticipantsRepository participantsRepository;

    public TodoResponseDto createTodo(Long boardId, TodoRequestDto requestDto, User user) {
        // 유저 확인
        user = findUserBy(user.getEmail());
        // 참여자 확인
        validateParticipants(boardId, user.getId());
        // 보드 확인
        Board board = findBoard(boardId);

        Todo todo = Todo.builder()
            .board(board)
            .user(user)
            .title(requestDto.getTitle())
            .build();

        return new TodoResponseDto(todoRepository.save(todo));
    }

    public List<TodoResponseDto> getTodos() {
        List<Todo> todoList = todoRepository.findAll(Sort.by(Direction.DESC, "createdAt"));

        return todoList.stream()
            .map(TodoResponseDto::new)
            .toList();
    }

    @Transactional
    public void updateTodo(Long todoId, TodoRequestDto requestDto) {
        Todo todo = findTodo(todoId);

        todo.update(requestDto);
    }

    @Transactional
    public void deleteTodo(Long todoId) {
        Todo todo = findTodo(todoId);

        todoRepository.delete(todo);
    }

    private User findUserBy(String email) {
        return userRepository.findByEmail(email).orElseThrow(
            () -> new EntityNotFoundException("회원이 존재하지 않습니다."));
    }

    private void validateParticipants(Long boardId, Long userId) {
        if (!participantsRepository.existsByBoardIdAndUserId(boardId, userId)) {
            throw new EntityExistsException("참여자가 아닙니다.");
        }
    }

    private Board findBoard(Long boardId) {
        return boardRepository.findById(boardId)
            .orElseThrow(() -> new EntityNotFoundException("해당 보드가 존재하지 않습니다."));
    }

    private Todo findTodo(Long todoId) {
        return todoRepository.findById(todoId)
            .orElseThrow(() -> new EntityExistsException("해당 컬럼이 존재하지 않습니다."));
    }
}
