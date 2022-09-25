package com.octopus.friends.dto.request.chat;

import com.octopus.friends.domain.Chat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * 패키지명 com.octopus.friends.dto.request.chat
 * 클래스명 ChatRequestDto
 * 클래스설명
 * 작성일 2022-09-18
 * 
 * @author 원지윤
 * @version 1.0
 * [수정내용] 
 * 예시) [2022-09-17] 주석추가 - 원지윤
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatRequestDto {
    public enum ChatType {
        ENTER,
        TALK,
        INVITE
    }
    private ChatType chatType;
    private String roomIdx;
    private String senderId;
    private String content;

    public Chat toEntity(){
        return Chat.builder()
                .type(chatType.toString())
                .roomIdx(roomIdx)
                .senderId(senderId)
                .content(content)
                .build();
    }

}
