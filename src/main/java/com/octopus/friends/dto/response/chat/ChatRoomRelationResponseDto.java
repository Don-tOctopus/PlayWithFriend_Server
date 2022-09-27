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
 * 작성일 2022-09-18
 *
 * @author 원지윤
 * @version 1.0
 * [수정내용]
 * 예시) [2022-09-17] 주석추가 - 원지윤
 * [2022-09-27] userId -> userEmail로 수정
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomRelationResponseDto {
    private Long chatRoomIdx;
    private Long roomRelationIdx;
    private int uCnt;
    private String userEmail;
    private String roomName;

    /**
     *
     * @param chatRoomRelation
     * @return
     */
    public static ChatRoomRelationResponseDto of(ChatRoomRelation chatRoomRelation, ChatRoom chatRoom){
        return new ChatRoomRelationResponseDto(
                chatRoomRelation.getChatRoom().getChatRoomIdx(),
                chatRoomRelation.getRoomRelationIdx(),
                chatRoomRelation.getChatRoom().getUCnt(),
                chatRoomRelation.getUser().getEmail(),
                chatRoom.getRoomName()

        );
    }
}
