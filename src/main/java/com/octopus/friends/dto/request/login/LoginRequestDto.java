package com.octopus.friends.dto.request.login;

import lombok.Getter;
import lombok.Setter;

/**
 * 패키지명 com.octopus.friends.dto.request.login
 * 클래스명 LoginRequestDto
 * 클래스설명 로그인 요청 시 사용하는 Dto
 * 작성일 2022-10-06
 *
 * @author 원지윤
 * @version 1.0
 * [수정내용]
 * 예시) [2022-09-17] 주석추가 - 원지윤
 */
@Getter
@Setter
public class LoginRequestDto {
    private String email;
    private String password;
}
