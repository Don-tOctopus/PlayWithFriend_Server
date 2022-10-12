package com.octopus.friends.dto.response.login;

import com.octopus.friends.domain.TokenInfo;
import com.octopus.friends.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 패키지명 com.octopus.friends.dto.response.login
 * 클래스명 LoginResponseDto
 * 클래스설명
 * 작성일 2022-10-10
 *
 * @author 원지윤
 * @version 1.0
 * [수정내용]
 * 예시) [2022-09-17] 주석추가 - 원지윤
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDto {
    private TokenInfo tokenInfo;
    private String email;
    private String userName;

    public static LoginResponseDto of(User user, TokenInfo tokenInfo){
        return new LoginResponseDto(
                tokenInfo,
                user.getEmail(),
                user.getUserName()
        );
    }
}
