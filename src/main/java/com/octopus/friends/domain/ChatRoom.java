package com.octopus.friends.domain;

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

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long chatRoomIdx;

    @Column(nullable = false, columnDefinition = "varchar(30)")
    private String hostId;

    @Column(nullable = false, columnDefinition = "int")
    private int uCnt;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Column(nullable = false, columnDefinition = "varchar(5)")
    private ChatRoomType chatRoomType;

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
        this.uCnt = uCnt;
    }

    public void leave(){
        this.uCnt--;
    }

    public void join(){
        this.uCnt++;
    }
}
