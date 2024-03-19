package com.nbc.trello.entity.board;

import com.nbc.trello.global.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BoardController {
  @PostMapping("/api/boards")
  public ResponseEntity<CommonResponse> createBoard() {
    return null;
  }
}
