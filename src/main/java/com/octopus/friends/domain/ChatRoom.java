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
 * 클래스명 ChatRoom
 * 클래스설명
 * 작성일 2022-09-17
 * 
 * @author 원지윤
 * @version 1.0
 * [수정내용] 
 * 예시) [2022-09-17] 주석추가 - 원지윤
 * [2022-09-19] 스웨거 어노테이션 추가 - 원지윤
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class ChatRoom implements Serializable {
    private static final long serialVersionUID = 6494678977089006639L;

    public enum ChatRoomType {
        TEXT,
        VEDIO
    }

    @Schema(description = "채팅방 인덱스")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatRoomIdx;

    @Schema(description = "채팅방 생성 유저의 아이디")
    @Column(nullable = false, columnDefinition = "varchar(30)")
    private String hostId;

    @Schema(description = "채팅방안의 유저수")
    @Column(nullable = false, columnDefinition = "int")
    private int uCnt;

    @Schema(description = "채팅방이 만들어진 시간")
    @CreatedDate
    private LocalDateTime createdAt;

    @Schema(description = "채팅방이 마지막으로 수정된 시간")
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Schema(description = "채팅방의 타입", example = "TEXT", allowableValues = {"TEXT", "VEDIO"})
    @Column(nullable = false, columnDefinition = "varchar(5)")
    private ChatRoomType chatRoomType;

    @Schema(description = "채팅방에 속한 유저의 채팅방릴레이션 엔티티")
    @OneToMany(mappedBy = "chatRoom")
    private List<ChatRoomRelation> chatRoomRelationList = new ArrayList<>();

    /**
     * ChatRoom 엔티티의 builder
     * @param hostId 채팅방을 생성한 user의 아이디
     * @param chatRoomType 생성한 채팅방의 종류
     * @param uCnt 채팅방 생성 초기의 채팅방에 있는 유저의 숫자
     */
    @Builder
    public ChatRoom(String hostId,ChatRoomType chatRoomType, int uCnt){
        this.hostId = hostId;
        this.chatRoomType = chatRoomType;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.uCnt = uCnt;
    }

    /**
     * 채팅방에서 유저가 나갈 때 채팅방의 유저수 감소
     */
    public void leave(){
        this.uCnt--;
    }

    /**
     * 채팅방에 유저가 들어올 때 채팅방의 유저수 증가
     */
    public void join(){
        this.uCnt++;
    }
}
