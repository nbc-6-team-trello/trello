package com.nbc.trello.entity.board;

import com.nbc.trello.entity.participants.Participants;
import com.nbc.trello.entity.participants.ParticipantsRepository;
import com.nbc.trello.entity.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {

  private final BoardRepository boardRepository;

  private final ParticipantsRepository participantsRepository;

  public BoardResponseDto createBoard(BoardRequestDto requestDto, User user) {
    Board board = new Board(requestDto);
    boardRepository.save(board);
    Participants participants = new Participants(user, board);
    participantsRepository.save(participants);
    return null;
  }
}
