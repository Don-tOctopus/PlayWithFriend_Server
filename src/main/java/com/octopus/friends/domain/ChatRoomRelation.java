package com.octopus.friends.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
/**
 * 패키지명 com.octopus.friends.domain
 * 클래스명 ChatRoomRelation
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
public class ChatRoomRelation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long roomRelationIdx;

    @ManyToOne
    @JoinColumn(name = "chatroom_idx")
    private ChatRoom chatRoom;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false, columnDefinition = "bool default 'true'")
    private boolean status;

    /**
     * ChatRoomRelation 엔티티의 builder
     * @param chatRoom 채팅방
     * @param user 채팅방에 참여하는 user
     */
    @Builder
    public ChatRoomRelation(ChatRoom chatRoom, User user){
        this.chatRoom = chatRoom;
        this.user = user;
        this.status = true;
    }

}
