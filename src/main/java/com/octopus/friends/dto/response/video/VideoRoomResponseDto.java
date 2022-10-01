package com.octopus.friends.dto.response.video;


import com.octopus.friends.domain.ChatRoom;
import com.octopus.friends.dto.response.chat.ChatRoomResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
