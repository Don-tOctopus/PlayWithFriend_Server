package com.octopus.friends.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
/**
 * 패키지명 com.octopus.friends.domain
 * 클래스명 TokenInfo
 * 클래스설명 클라이언트에 JWT토큰을 보내기 위한 도메인
 * 작성일 2022-10-09
 *
 * @author 원지윤
 * @version 1.0
 * [수정내용]
 * 예시) [2022-09-17] 주석추가 - 원지윤
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
public class TokenInfo {
    private String grantType; //grantType은 JWT 대한 인증 타입, 여기서는 Bearer
    private String accessToken;
    private String refreshToken;
}

