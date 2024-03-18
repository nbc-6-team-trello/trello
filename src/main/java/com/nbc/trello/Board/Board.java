package com.nbc.trello.Board;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
public class Board {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long board_Id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String color;

  @Column(nullable = false)
  private String description;

}
