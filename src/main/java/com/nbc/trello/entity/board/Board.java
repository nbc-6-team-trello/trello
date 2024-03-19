package com.nbc.trello.entity.board;

import com.nbc.trello.entity.todo.Todo;
import com.nbc.trello.entity.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
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
  private String discription;

  @CreatedDate
  @Temporal(TemporalType.TIMESTAMP)
  private LocalDateTime createAt;

  @LastModifiedDate
  @Temporal(TemporalType.TIMESTAMP)
  private LocalDateTime modifiedAt;

  @OneToMany(mappedBy = "board")
  private List<Todo> todoList = new ArrayList<>();

  public Board(BoardRequestDto requestDto) {
    this.name = requestDto.getName();
    this.color = requestDto.getColor();
    this.discription = requestDto.getDiscription();
  }
}
