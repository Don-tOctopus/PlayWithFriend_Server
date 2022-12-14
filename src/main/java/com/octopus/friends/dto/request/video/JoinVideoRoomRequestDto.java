package com.octopus.friends.dto.request.video;

import com.octopus.friends.domain.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 패키지명 com.octopus.friends.dto.request.video
 * 클래스명 JoinVideoRoomRequestDto
 * 클래스설명
 * 작성일 2022-10-01
 *
 * @author 남유정
 * @version 1.0
 * [수정내용]
 * 예시) [2022-09-17] 주석추가 - 남유정
 * [2022-10-03] 변수 변경 - 남유정
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JoinVideoRoomRequestDto {
    private Long roomIdx;
    private String userId;
    private String roomPassword;

    /**
     *
     * @param videoRoom 참여할 채팅방 엔티티
     * @param user 채팅방에 참여하는 user 엔티티
     * @return
     */
    public VideoRoomRelation toEntity(VideoRoom videoRoom, User user){
        return VideoRoomRelation.builder()
                .user(user)
                .videoRoom(videoRoom)
                .build();
    }
}
