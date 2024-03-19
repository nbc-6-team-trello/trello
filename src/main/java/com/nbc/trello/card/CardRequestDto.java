package com.nbc.trello.card;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter
@NoArgsConstructor
public class CardRequestDto {
    private Long boardId;
    private Long columnId;
    private Long cardId;
    private String name;
    private String discription;
    private String background;
    private String pic;
    private LocalDateTime deadline;

    public CardRequestDto (String name, String discription, String background, String pic, LocalDateTime deadline){
        this.name = name;
        this.discription = discription;
        this.background = background;
        this.pic = pic;
        this.deadline = deadline;
    }

//    public CardRequestDto(Card card){
//        this.name = card.getName();
//        this.discription = card.getDescription();
//        this.background = card.getColor();
//        this.pic = card.getPic();
//        this.deadline = card.getDeadline();
//    }
}
