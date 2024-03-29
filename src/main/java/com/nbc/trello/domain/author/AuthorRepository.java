package com.nbc.trello.domain.author;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    Optional<List<Author>> findByCardId(Long userId);

    // Optional<Author> findByEmail(String email);

    Optional<Author> findByCardIdAndUserId(Long cardId, Long userId);

    Boolean existsByCardIdAndUserId(Long cardId, Long userId);

}
