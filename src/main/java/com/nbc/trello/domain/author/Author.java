package com.nbc.trello.domain.author;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DialectOverride.Version;

@Entity
@Table(name = "authors")
@Getter
@NoArgsConstructor
public class Author {

    @Version(major = 0)
    private int version;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column
    private Long userId;

    @Column
    private Long cardId;


    public Author(Long user_id, Long card_id) {
        this.userId = user_id;
        this.cardId = card_id;
    }

    private void updateVersion(int version) {
        this.version = version;
    }
}
