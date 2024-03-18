package com.nbc.trello.card;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Card {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private String description;
    @Column
    private String color;
    @Column
    private LocalDateTime Deadline;
    @Column
    private LocalDateTime CreatedAt;
    @Column
    private LocalDateTime ModifiedAt;

    public Card (String name, String description, String color){
        this.name = name;
        this.description = description;
        this.color = color;
    }

}
