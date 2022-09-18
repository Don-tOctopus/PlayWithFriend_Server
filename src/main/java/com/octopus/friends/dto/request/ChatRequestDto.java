package com.octopus.friends.dto.request;

import com.octopus.friends.domain.Chat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
