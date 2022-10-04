package com.octopus.friends.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * 패키지명 com.octopus.friends.domain
 * 클래스명 VideoRoomRelation
 * 클래스설명
 * 작성일 2022-10-05
 * 
 * @author 남유정
 * @version 1.0
 * [수정내용] 
 * 예시) [2022-09-17] 주석추가 - 남유정
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class VideoRoomRelation {
    @Schema(description = "채팅방 관계 idx")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomRelationIdx;

    @Schema(description = "채팅방 idx")
    @ManyToOne
    @JoinColumn(name = "chatroom_idx")
    private VideoRoom videoRoom;

    @Schema(description = "채팅방에 속한 user의 email")
    @ManyToOne
    @JoinColumn(name = "user_email")
    private User user;

    @Schema(description = "채팅방 참여 상태", defaultValue = "true", allowableValues = {"true","false"})
    @Column(nullable = false, columnDefinition = "boolean")
    private boolean status;

    /**
     * VideoRoomRelation 엔티티의 builder
     * @param videoRoom 채팅방
     * @param user 채팅방에 참여하는 user
     */
    @Builder
    public VideoRoomRelation(VideoRoom videoRoom, User user){
        this.videoRoom = videoRoom;
        this.user = user;
        this.status = true;
    }
}
