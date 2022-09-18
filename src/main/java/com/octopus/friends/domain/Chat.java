package com.octopus.friends.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

/**
 * 패키지명 com.octopus.friends.domain
 * 클래스명 Chat
 * 클래스설명
 * 작성일 2022-09-17
 * 
 * @author 원지윤
 * @version 1.0
 * [수정내용] 
 * 예시) [2022-09-17] 주석추가 - 원지윤
 */

@Getter
@Setter
@NoArgsConstructor
public class Chat {
    private String type;   // 메시지 타입
    private String roomIdx;      // 방번호
    private String senderId;      // 메시지 보낸 사람
    private String content;     // 메시지
    
    @CreatedDate
    private LocalDateTime createdAt;

    @Builder
    public Chat(String type,String roomIdx,String senderId,String content){
        this.type = type;
        this.roomIdx = roomIdx;
        this.senderId = senderId;
        this.content = content;
    }
}
