package com.nbc.trello.domain.todo;

import com.nbc.trello.global.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @PostMapping("/todos")
    ResponseEntity<CommonResponse<TodoResponseDto>> createTodo(
        @RequestBody TodoRequestDto requestDto) {
        TodoResponseDto responseDto = todoService.createTodo(requestDto);

        return ResponseEntity.status(HttpStatus.CREATED.value()).body(
            CommonResponse.<TodoResponseDto>builder()
                .msg("컬럼 생성 완료")
                .statusCode(HttpStatus.CREATED.value())
                .data(responseDto)
                .build()
        );
    }
}
