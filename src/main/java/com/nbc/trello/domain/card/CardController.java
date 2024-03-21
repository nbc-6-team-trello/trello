package com.nbc.trello.domain.card;

import com.nbc.trello.domain.board.BoardResponseDto;
import com.nbc.trello.global.response.CommonResponse;
import com.nbc.trello.global.util.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Controller
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;

    @PostMapping("/boards/{board_id}/columns/{column_id}/cards")
    public ResponseEntity<CommonResponse<CardResponseDto>> createCard(
        @PathVariable("board_id") Long boardId,
        @PathVariable("column_id") Long todoId,
        @RequestBody CardRequestDto cardRequestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        CardResponseDto cardResponseDto = cardService.CardCreateService(boardId, todoId,
            cardRequestDto, userDetails.getUser());

        return ResponseEntity.ok(CommonResponse.<CardResponseDto>builder()
            .msg("카드 생성에 성공하였습니다.")
            .statusCode(HttpStatus.OK.value())
            .data(cardResponseDto)
            .build());
    }

    @GetMapping("/boards/{board_id}/columns/{column_id}/cards/{card_id}")
    public ResponseEntity<CommonResponse<CardCommentResponseDto>> getCard(
        @PathVariable("board_id") Long boardId,
        @PathVariable("column_id") Long todoId,
        @PathVariable("card_id") Long card_id,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        CardCommentResponseDto cardCommentResponseDto = cardService.CardGetService(boardId,
            todoId, card_id, userDetails);

        return ResponseEntity.ok(CommonResponse.<CardCommentResponseDto>builder()
            .msg("카드 조회에 성공하였습니다.")
            .statusCode(HttpStatus.OK.value())
            .data(cardCommentResponseDto)
            .build());
    }

    @DeleteMapping("/boards/{board_id}/columns/{column_id}/cards/{card_id}")
    public ResponseEntity<CommonResponse<CardResponseDto>> deleteCard(
        @PathVariable("board_id") Long boardId,
        @PathVariable("column_id") Long todoId,
        @PathVariable("card_id") Long card_id,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        CardResponseDto cardResponseDto = cardService.CardDeleteService(boardId, todoId, card_id, userDetails.getUser());

        return ResponseEntity.ok(CommonResponse.<CardResponseDto>builder()
            .msg("카드 삭제에 성공하였습니다.")
            .statusCode(HttpStatus.OK.value())
            .data(cardResponseDto)
            .build());
    }

    @PutMapping("/boards/{board_id}/columns/{column_id}/cards/{card_id}")
    public ResponseEntity<CommonResponse<CardResponseDto>> updateCard(
        @PathVariable("board_id") Long boardId,
        @PathVariable("column_id") Long todoId,
        @PathVariable("card_id") Long card_id,
        @RequestBody CardRequestDto cardRequestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        CardResponseDto cardResponseDto = cardService.CardUpdateService(boardId, todoId, card_id,
            cardRequestDto, userDetails.getUser());

        return ResponseEntity.ok(CommonResponse.<CardResponseDto>builder()
            .msg("카드 수정에 성공하였습니다.")
            .statusCode(HttpStatus.OK.value())
            .data(cardResponseDto)
            .build());
    }

    @PostMapping("/boards/{board_id}/columns/{column_id}/cards/{card_id}/inviteAuthor")
    public ResponseEntity<CommonResponse<CardResponseDto>> inviteAuthor(
        @PathVariable("board_id") Long boardId,
        @PathVariable("column_id") Long todoId,
        @PathVariable("card_id") Long card_id,
        @RequestBody CardRequestDto cardRequestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        CardResponseDto cardResponseDto = cardService.inviteAuthor(boardId,todoId,card_id,cardRequestDto,userDetails.getUser());

        return ResponseEntity.ok()
            .body(CommonResponse.<CardResponseDto>builder()
                .msg("카드 초대에 성공하였습니다.")
                .statusCode(HttpStatus.OK.value())
                .data(cardResponseDto)
                .build());
    }
}
