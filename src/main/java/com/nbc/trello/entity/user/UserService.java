package com.nbc.trello.entity.user;

import com.nbc.trello.global.dto.request.SignupRequestDto;

public interface UserService {
    /**
     * 회원가입
     * // @param SignupRequestDto 회원가입 요청 정보
     */

    Long signup(SignupRequestDto userRequestDto);
}
