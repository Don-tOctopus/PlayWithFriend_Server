package com.octopus.friends.controller;

import com.octopus.friends.domain.Chat;
import com.octopus.friends.dto.request.ChatRequestDto;
import com.octopus.friends.service.ChatRoomService;
import com.octopus.friends.service.RedisPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

/**
 * 패키지명 com.octopus.friends.domain
 * 클래스명 ChatController
 * 클래스설명
 * 작성일 2022-09-18
 *
 * @author 원지윤
 * @version 1.0
 * [수정내용]
 * 예시) [2022-09-17] 주석추가 - 원지윤
 */
@RequiredArgsConstructor
@Controller
public class ChatController {

    private final RedisPublisher redisPublisher;
    private final SimpMessageSendingOperations messagingTemplate;
    private final ChatRoomService chatRoomService;

    /**
     * /pub/chat/message로 들어온 chat을 /sub/chat/room/{roomIdx}로 전송
     * @param request 입력한 채팅
     */
    @MessageMapping("/chat/message")
    public void message(ChatRequestDto request) {
        if(ChatRequestDto.ChatType.ENTER.equals(request.getChatType()))
            request.setContent(request.getSenderId()+"님이 입장하셨습니다.");
        // Websocket에 발행된 메시지를 redis로 발행한다(publish)
        Chat chat = Chat.builder()
                .roomIdx(request.getRoomIdx())
                .senderId(request.getSenderId())
                .content(request.getContent())
                .type(request.getChatType().toString())
                .build();

        redisPublisher.publish(chatRoomService.getTopic(request.getRoomIdx()), chat);
        //messagingTemplate.convertAndSend("/sub/chat/room/"+request.getRoomIdx());
    }
}