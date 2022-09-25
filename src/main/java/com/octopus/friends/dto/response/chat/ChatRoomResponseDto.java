package com.octopus.friends.dto.response.chat;

import com.octopus.friends.domain.ChatRoom;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
/**
 * 패키지명 com.octopus.friends.dto.response
 * 클래스명 ChatRoomResponseDto
 * 클래스설명
 * 작성일 2022-09-19
 *
 * @author 원지윤
 * @version 1.0
 * [수정내용]
 * 예시) [2022-09-17] 주석추가 - 원지윤
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomResponseDto {
    private Long chatRoomIdx;
    private int uCnt;
    private String roomName;
    private String hostId;

    public static ChatRoomResponseDto of(ChatRoom chatRoom){
        return new ChatRoomResponseDto(
                chatRoom.getChatRoomIdx(),
                chatRoom.getUCnt(),
                chatRoom.getRoomName(),
                chatRoom.getHostId()
        );
    }
}
