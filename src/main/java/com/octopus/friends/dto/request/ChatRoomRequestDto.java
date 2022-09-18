package com.octopus.friends.dto.request;

import com.octopus.friends.domain.ChatRoom;
import com.octopus.friends.domain.ChatRoomRelation;
import com.octopus.friends.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 패키지명 com.octopus.friends.dto.request
 * 클래스명 ChatRoomRequestDto
 * 클래스설명
 * 작성일 2022-09-18
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
public class ChatRoomRequestDto {
    private Long chatRoomIdx;
    private String userId;

    /**
     *
     * @param chatRoom 참여할 채팅방 엔티티
     * @param user 채팅방에 참여하는 user 엔티티
     * @return
     */

    public ChatRoomRelation toEntity(ChatRoom chatRoom, User user){
        return ChatRoomRelation.builder()
                .user(user)
                .chatRoom(chatRoom)
                .build();
    }
}
