package com.nbc.trello.card;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CardService {
    private final CardRepository cardRepository;

    //카드 등록
    public CardResponseDto CardCreateService(Long boardId, Long columnId, Long cardId, CardRequestDto cardRequestDto){
        Card card = new Card(cardRequestDto);
        cardRepository.save(card);

        return new CardResponseDto(boardId, columnId, cardId);
    }

    //카드 단건조회
    public CardGetResponseDto CardGetService(Long boardId, Long columnId, Long cardId){

        Card card = cardRepository.findById(cardId).get();

        return new CardGetResponseDto(card);
    }

    //카드 삭제
    public CardResponseDto CardDeleteService(Long boardId, Long columnId, Long cardId){

        Card card = cardRepository.findById(cardId).get();

        cardRepository.delete(card);

        return new CardResponseDto(boardId, columnId, cardId);
    }

    //카드 수정
    public CardResponseDto CardUpdateService(Long boardId, Long columnId, Long cardId, CardRequestDto cardRequestDto){

        Card card = cardRepository.findById(cardId).get();

        card.CardUpdate(cardRequestDto);
        Card save = cardRepository.save(card);

        return new CardResponseDto(boardId, columnId, cardId);
    }
}
