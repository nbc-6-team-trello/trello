package com.nbc.trello.domain.card;

<<<<<<< HEAD
import com.nbc.trello.domain.comment.Comment;
import com.nbc.trello.domain.timeStamped.TimeStamped;
=======
>>>>>>> 321b30fd6529b7c35e945bed912b77e3a75c5544
import com.nbc.trello.domain.todo.Todo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class) //자동으로 LocalDateTime 생성
public class Card extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String pic;

    @Column
    private String description;

    @Column
    private String color;

    @Column
    private LocalDateTime deadline;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "todo_id")
    private Todo todo;

<<<<<<< HEAD

=======
>>>>>>> 321b30fd6529b7c35e945bed912b77e3a75c5544
    public Card (CardRequestDto cardRequestDto){
        this.name = cardRequestDto.getName();
        this.pic = cardRequestDto.getPic();
        this.description = cardRequestDto.getDescription();
        this.color = cardRequestDto.getBackground();
        this.deadline = cardRequestDto.getDeadline();
    }

    public void CardUpdate(CardRequestDto cardRequestDto){
        this.name = cardRequestDto.getName();
        this.pic = cardRequestDto.getPic();
        this.description = cardRequestDto.getDescription();
        this.color = cardRequestDto.getBackground();
        this.deadline = cardRequestDto.getDeadline();
    }
<<<<<<< HEAD

=======
>>>>>>> 321b30fd6529b7c35e945bed912b77e3a75c5544
}
