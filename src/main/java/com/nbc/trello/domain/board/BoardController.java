package com.nbc.trello.domain.board;

import com.nbc.trello.global.response.CommonResponse;
import com.nbc.trello.global.util.UserDetailsImpl;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BoardController {

  private final BoardService boardService;

  //보드 생성
  @PostMapping("/boards")
  public ResponseEntity<CommonResponse<BoardResponseDto>> createBoard(
      @RequestBody BoardRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
    try {
      BoardResponseDto responseDto = boardService.createBoard(requestDto, userDetails.getUser());
      return ResponseEntity.ok()
          .body(CommonResponse.<BoardResponseDto>builder()
              .msg("보드 생성이 성공하였습니다.")
              .statusCode(200)
              .data(responseDto)
              .build());
    } catch (Exception e) {
      return ResponseEntity.badRequest()
          .body(CommonResponse.<BoardResponseDto>builder()
              .msg(e.getMessage())
              .statusCode(400)
              .data(null)
              .build());
    }
  }
  //보드 전체 조회
  @GetMapping("/boards")
  public ResponseEntity<CommonResponse<List<BoardResponseDto>>> getBoardList() {
    List<BoardResponseDto> boardList = boardService.getBoardList();
    return ResponseEntity.ok()
        .body(CommonResponse.<List<BoardResponseDto>>builder()
            .msg("보드 생성이 성공하였습니다.")
            .statusCode(200)
            .data(boardList)
            .build());
  }
  //보드 수정

  //보드 삭제

  //보드 초대
}
