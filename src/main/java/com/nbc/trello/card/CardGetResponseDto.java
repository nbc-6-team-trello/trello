package com.nbc.trello.card;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class CardGetResponseDto {
    private String name;
    private String discription;
    private String background;
    private String pic;
    private LocalDateTime deadline;

    public CardGetResponseDto(Card card){
        this.name = card.getName();
        this.discription = card.getDescription();
        this.background = card.getColor();
        this.pic = card.getPic();
        this.deadline = card.getDeadline();
    }
}
