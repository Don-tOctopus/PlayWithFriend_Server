package com.octopus.friends.dto.request.user;

import com.octopus.friends.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * 패키지명 com.octopus.friends.dto.request.user
 * 클래스명 CreateUserRequestDto
 * 클래스설명 사용자를 생성할 때 요청하는 Dto
 * 작성일 2022-10-05
 *
 * @author 원지윤
 * @version 1.0
 * [수정내용]
 * 예시) [2022-09-17] 주석추가 - 원지윤
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequestDto {
    private String email;
    private String userId;
    private String userName;
    private String password;
    private String birth;

    /**
     * request로 받아온 값으로 User엔티티를 만드는 메소드
     * @return User
     */
    public User toEntity(){
        return User.builder()
                .email(email)
                .userId(userId)
                .userName(userName)
                .password(password)
                .birth(birth)
                .build();
    }



}
