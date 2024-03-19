package com.nbc.trello.entity.board;

import com.nbc.trello.entity.user.User;
import com.nbc.trello.global.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BoardController {

  private final BoardService boardService;

  @PostMapping("/api/boards")
  public ResponseEntity<CommonResponse<BoardResponseDto>> createBoard(
      @RequestBody BoardRequestDto requestDto, @AuthenticationPrincipal User user) {
    BoardResponseDto responseDto = boardService.createBoard(requestDto, user);

    return ResponseEntity.ok()
        .body(CommonResponse.<BoardResponseDto>builder()
            .msg("보드 생성이 성공하였습니다.")
            .statusCode(200)
            .data(responseDto)
            .build());

  }
}
