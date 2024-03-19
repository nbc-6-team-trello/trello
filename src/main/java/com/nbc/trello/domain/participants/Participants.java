package com.nbc.trello.domain.participants;

import com.nbc.trello.domain.board.Board;
import com.nbc.trello.domain.board.BoardResponseDto;
import com.nbc.trello.domain.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class Participants {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long user_id;

    @Column
    private Long board_id;

    public Participants(User user, Board board) {
        this.user_id = user.getId();
        this.board_id = board.getId();
    }

    public BoardResponseDto toDto() {
        BoardResponseDto responseDto = new BoardResponseDto();
        responseDto.setBoard_id(this.board_id);
        return responseDto;
    }
}