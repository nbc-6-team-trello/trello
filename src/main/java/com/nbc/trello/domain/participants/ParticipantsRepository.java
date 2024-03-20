package com.nbc.trello.domain.participants;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipantsRepository extends JpaRepository<Participants, Long> {

  Optional<Participants> findByBoardIdAndUserIdAndGenerator (Long boardId, Long userId, Boolean create);

  Optional<List<Participants>> findByBoardId(Long boardId);
}
