package com.octopus.friends.dto.response.chat;

import com.octopus.friends.domain.ChatRoom;
import com.octopus.friends.domain.ChatRoomRelation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 패키지명 com.octopus.friends.dto.response
 * 클래스명 ChatRoomResponseDto
 * 클래스설명
 * 작성일 2022-09-17
 *
 * @author 원지윤
 * @version 1.0
 * [수정내용]
 * 예시) [2022-09-17] 주석추가 - 원지윤
 * [2022-09-23] 채팅 생성 시 채팅이름 반환 추가 - 원지윤
 */

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateChatRoomResponseDto {

    private Long chatRoomIdx;
    private Long chatRoomRelationIdx;
    private String hostId;
    private String roomName;

    public static CreateChatRoomResponseDto of(ChatRoom chatRoom, ChatRoomRelation chatRoomRelation){
        return new CreateChatRoomResponseDto(
                chatRoom.getChatRoomIdx(),
                chatRoomRelation.getRoomRelationIdx(),
                chatRoomRelation.getUser().getUserId(),
                chatRoom.getRoomName()
        );
    }

}
