package com.nbc.trello.domain.todo;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoResponseDto createTodo(TodoRequestDto requestDto) {
        Todo todo = Todo.builder()
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
}
