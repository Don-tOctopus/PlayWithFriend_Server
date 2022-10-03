package com.octopus.friends.dto.response.video;


import com.octopus.friends.domain.ChatRoom;
import com.octopus.friends.dto.response.chat.ChatRoomResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
/**
 * 패키지명 com.octopus.friends.dto.response.video
 * 클래스명 VideoRoomResponseDto
 * 클래스설명
 * 작성일 2022-10-01
 * 
 * @author 남유정
 * @version 1.0
 * [수정내용] 
 * 예시) [2022-09-17] 주석추가 - 남유정
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class VideoRoomResponseDto {
    private Long chatRoomIdx;
    private int uCnt;
    private String roomName;
    private String hostId;

    public static VideoRoomResponseDto of(ChatRoom chatRoom){
        return new VideoRoomResponseDto(
                chatRoom.getChatRoomIdx(),
                chatRoom.getUCnt(),
                chatRoom.getRoomName(),
                chatRoom.getHostId()
        );
    }
}
