package com.octopus.friends.dto.response.video;

import com.octopus.friends.domain.ChatRoom;
import com.octopus.friends.domain.ChatRoomRelation;
import com.octopus.friends.dto.response.chat.ChatRoomRelationResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 패키지명 com.octopus.friends.dto.response.video
 * 클래스명 VideoRoomRelationResponseDto
 * 클래스설명
 * 작성일 2022-10-04
 *
 * @author 남유정
 * @version 1.0
 * [수정내용]
 * 예시) [2022-09-17] 주석추가 - 남유정
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class VideoRoomRelationResponseDto {
    private Long chatRoomIdx;
    private int uCnt;
    private String roomName;

    public static VideoRoomRelationResponseDto of(ChatRoomRelation chatRoomRelation, ChatRoom chatRoom){
        return new VideoRoomRelationResponseDto(
                chatRoomRelation.getChatRoom().getChatRoomIdx(),
                chatRoomRelation.getChatRoom().getUCnt(),
                chatRoom.getRoomName()
        );
    }
}
