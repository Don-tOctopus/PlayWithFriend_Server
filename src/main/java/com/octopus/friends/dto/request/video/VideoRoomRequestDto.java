package com.octopus.friends.dto.request.video;

import com.octopus.friends.domain.ChatRoom;
import com.octopus.friends.domain.ChatRoomRelation;
import com.octopus.friends.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 패키지명 com.octopus.friends.dto.request
 * 클래스명 VideoRoomRequestDto
 * 클래스설명
 * 작성일 2022-09-24
 *
 * @author 남유정
 * @version 1.0
 * [수정내용]
 * 예시) [2022-09-17] 주석추가 - 남유정
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VideoRoomRequestDto {
    private String sessionId;
    private String userId;

    /**
     * @param user     채팅방에 참여하는 user 엔티티
     * @return
     */
    public ChatRoomRelation toEntity(User user) {
        return ChatRoomRelation.builder()
                .user(user)
                .build();
    }
}