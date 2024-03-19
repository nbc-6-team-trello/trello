package com.nbc.trello.entity.user;

import com.nbc.trello.global.dto.request.SignupRequestDto;
import com.nbc.trello.global.response.CommonResponse;
import jakarta.validation.Valid;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j(topic = "사용자")
@RestController // @Controller + @ResponseBody
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<CommonResponse<Long>> createUser(
        @Valid @RequestBody SignupRequestDto userRequestDto,
        BindingResult bindingResult
    ) throws IOException
    {
        // Validation 예외처리
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        if(!fieldErrors.isEmpty()) {
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                log.error(fieldError.getField() + " 필드 : " + fieldError.getDefaultMessage());
            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(CommonResponse.<Long>builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .msg("회원가입 실패")
                .data(null)
                .build()
            );
        }

        log.info("회원가입 시도");
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(
            CommonResponse.<Long>builder()
                .statusCode(HttpStatus.CREATED.value())
                .msg("회원가입이 성공하였습니다.")
                .data(userService.signup(userRequestDto))
                .build()
        );
    }
}
