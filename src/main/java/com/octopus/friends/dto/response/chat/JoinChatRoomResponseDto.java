package com.octopus.friends.dto.response.chat;

import com.octopus.friends.domain.ChatRoom;
import com.octopus.friends.domain.ChatRoomRelation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 패키지명 com.octopus.friends.dto.response
 * 클래스명 JoinChatRoomResponseDto
 * 클래스설명
 * 작성일 2022-09-18
 *
 * @author 원지윤
 * @version 1.0
 * [수정내용]
 * 예시) [2022-09-17] 주석추가 - 원지윤
 * [2022-09-27] userId -> userEmail로 수정 - 원지윤
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class JoinChatRoomResponseDto {
    private Long chatRoomIdx;
    private Long roomRelationIdx;
    private String userEmail;

    /**
     *
     * @param chatRoom 참여하는 채팅방 엔티티
     * @param chatRoomRelation 채팅방에 참여하는 유저의 채팅방 엔티티
     * @return
     */
    public static JoinChatRoomResponseDto of(ChatRoom chatRoom, ChatRoomRelation chatRoomRelation){
        return new JoinChatRoomResponseDto(
                chatRoom.getChatRoomIdx(),
                chatRoomRelation.getRoomRelationIdx(),
                chatRoomRelation.getUser().getEmail()
        );
    }
}
