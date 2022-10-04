package com.octopus.friends.dto.response.video;

import com.octopus.friends.domain.ChatRoom;
import com.octopus.friends.domain.ChatRoomRelation;
import com.octopus.friends.domain.VideoRoom;
import com.octopus.friends.domain.VideoRoomRelation;
import com.octopus.friends.dto.response.chat.CreateChatRoomResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 패키지명 com.octopus.friends.dto.response.video
 * 클래스명 CreateVideoRoomResponseDto
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
public class CreateVideoRoomResponseDto {
    private Long chatRoomIdx;
    private Long chatRoomRelationIdx;
    private String hostId;
    private String roomName;

    public static CreateVideoRoomResponseDto of(VideoRoom videoRoom, VideoRoomRelation videoRoomRelation){
        return new CreateVideoRoomResponseDto(
                videoRoom.getChatRoomIdx(),
                videoRoomRelation.getRoomRelationIdx(),
                videoRoomRelation.getUser().getUserId(),
                videoRoom.getRoomName()
        );
    }
}
