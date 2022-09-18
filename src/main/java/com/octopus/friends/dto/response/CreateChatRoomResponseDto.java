package com.octopus.friends.dto.response;

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
 */

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateChatRoomResponseDto {

    private Long chatRoomIdx;
    private Long chatRoomRelationIdx;
    private String hostId;

    public static CreateChatRoomResponseDto of(ChatRoom chatRoom, ChatRoomRelation chatRoomRelation){
        return new CreateChatRoomResponseDto(
                chatRoom.getChatRoomIdx(),
                chatRoomRelation.getRoomRelationIdx(),
                chatRoomRelation.getUser().getUserId()
        );
    }

}
