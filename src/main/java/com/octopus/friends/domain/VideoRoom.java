package com.octopus.friends.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 패키지명 com.octopus.friends.domain
 * 클래스명 VideoRoom
 * 클래스설명
 * 작성일 2022-10-04
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
public class VideoRoom {
    @Schema(description = "채팅방 인덱스")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatRoomIdx;

    @Schema(description = "채팅방 생성유저 아아디")
    @Column(nullable = false, columnDefinition = "varchar(30)")
    private String hostId;

    @Schema(description = "채팅방 이름")
    @Column(nullable = false, columnDefinition = "varchar(30)")
    private String roomName;

    @Schema(description = "채팅방 참여 유저 수")
    @Column(nullable = false, columnDefinition = "int")
    private int uCnt;

    @Schema(description = "채팅방 비밀번호")
    @Column(nullable = false, columnDefinition ="varchar(6)")
    private String roomPassword;

    @Schema(description = "채팅방이 만들어진 시간")
    @CreatedDate
    private LocalDateTime createdAt;

    @Schema(description = "채팅방이 마지막으로 수정된 시간")
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Schema(description = "채팅방에 속한 유저의 채팅방릴레이션 엔티티")
    @OneToMany(mappedBy = "videoRoom")
    private List<VideoRoomRelation> videoRoomRelationList = new ArrayList<>();

    /**
     * VideoRoom 엔티티의 builder
     * @param hostId 채팅방을 생성한 user의 이메일
     * @param roomName 채팅방 이름
     * @param uCnt 채팅방 생성 초기의 채팅방에 있는 유저의 숫자
     */
    @Builder
    public VideoRoom(String hostId, String roomName, int uCnt, String roomPassword){
        this.hostId = hostId;
        this.roomName = roomName;
        this.uCnt = uCnt;
        this.roomPassword = roomPassword;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * 유저 나가기에 대한 채팅방 유저수 감소
     */
    public void leave() {
        this.uCnt--;
    }

    /**
     * 유저 입장에 대한 채팅방 유저수 증가
     */
    public void join() {
        this.uCnt++;
    }
}
