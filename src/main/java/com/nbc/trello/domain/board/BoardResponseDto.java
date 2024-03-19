package com.nbc.trello.domain.board;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BoardResponseDto {

  private Long board_id;

  private String name;

  private String color;

  private String description;

  public BoardResponseDto(Board board) {
    this.board_id = board.getId();
    this.name = board.getName();
    this.color = board.getColor();
    this.description = board.getDescription();
  }
}
