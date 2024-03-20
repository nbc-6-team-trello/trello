package com.nbc.trello.domain.card;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardResponseDto {

  private String name;

  public CardResponseDto(Card card) {
    this.name = card.getName();
  }
}
