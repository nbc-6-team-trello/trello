package com.nbc.trello.domain.todo;

import lombok.RequiredArgsConstructor;
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
}
