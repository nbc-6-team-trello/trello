package com.nbc.trello.card;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Card {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column
    String name;
    @Column
    String description;
    @Column
    String Color;
    @Column
    LocalDateTime Deadline;
    @Column
    LocalDateTime CreatedAt;
    @Column
    LocalDateTime ModifiedAt;

}
