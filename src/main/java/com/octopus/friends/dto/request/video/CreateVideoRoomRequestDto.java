package com.octopus.friends.dto.request.video;

import com.octopus.friends.domain.ChatRoom;
import com.octopus.friends.domain.VideoRoom;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * 패키지명 com.octopus.friends.dto.request.video
 * 클래스명 CreateVideoRoomRequestDto
 * 클래스설명
 * 작성일 2022-10-01
 * 
 * @author 남유정
 * @version 1.0
 * [수정내용] 
 * 예시) [2022-09-17] 주석추가 - 남유정
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateVideoRoomRequestDto {

    private String hostId;
    private String roomName;
    private List<String> userList;

    /**
     * 입력된 값을 ChatRoom 엔티티로 변경하여 반환하는 메소드
     * @return ChatRoom
     */
    public VideoRoom toEntity(){
        return VideoRoom.builder()
                .roomName(roomName)
                .uCnt(userList.size())
                .build();
    }
}
