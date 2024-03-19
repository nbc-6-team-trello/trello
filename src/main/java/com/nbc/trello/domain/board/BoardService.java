package com.nbc.trello.domain.board;

import com.nbc.trello.domain.participants.Participants;
import com.nbc.trello.domain.participants.ParticipantsRepository;
import com.nbc.trello.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {

  private final BoardRepository boardRepository;

  private final ParticipantsRepository participantsRepository;

  //보드 생성
  public BoardResponseDto createBoard(BoardRequestDto requestDto, User user) {
    Board board = new Board(requestDto);
    boardRepository.save(board);

    Participants participants = new Participants(user, board);
    participantsRepository.save(participants);

    return participants.toDto();
  }
}
