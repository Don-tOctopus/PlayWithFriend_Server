package com.octopus.friends.dto.response.user;

import com.octopus.friends.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
/**
 * 패키지명 com.octopus.friends.dto.response.user
 * 클래스명 CreateUserResponseDto
 * 클래스설명
 * 작성일 2022-10-06
 *
 * @author 원지윤
 * @version 1.0
 * [수정내용]
 * 예시) [2022-09-17] 주석추가 - 원지윤
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserResponseDto {
    private String email;
    private String userId;
    private String userName;
    private String birth;

    public static CreateUserResponseDto of(User user){
        return new CreateUserResponseDto(
                user.getEmail(),
                user.getUserId(),
                user.getUserName(),
                user.getBirth()
        );
    }
}
