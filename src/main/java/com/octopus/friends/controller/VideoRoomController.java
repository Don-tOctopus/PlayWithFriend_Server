package com.octopus.friends.controller;

import com.octopus.friends.dto.request.JoinChatRoomRequestDto;
import com.octopus.friends.utils.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.json.JSONParser;
import org.json.simple.JSONObject;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpEntity;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 패키지명 com.octopus.friends.controller
 * 클래스명 VideoRoomController
 * 클래스설명
 * 작성일 2022-09-19
 *
 * @author 남유정
 * @version 1.0
 * [수정내용]
 * 예시) [2022-09-17] 주석추가 - 남유정
 */

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = {Constants.API_URL, Constants.API_URL_DEV}, allowCredentials = "true")
public class VideoRoomController {

    /**
     * 세션 리스트
     */
    private final ArrayList<JoinChatRoomRequestDto> chatRoomIdxList;
    private final SimpMessagingTemplate template;

    /**
     * 실시간으로 들어온 세션 감지하여 전체 세션 리스트 반환
     * @param chatRoomIdx user가 입장한 방의 Idx
     * @param sessionId 입장한 user의 세션 Id
     * @param ob chatRoom user의 세션 정보
     * @return 전체 세션 정보 리스트
     */
    @MessageMapping("/video/joined-room-info")
    @SendTo("/sub/video/joined-room-info")
    private ArrayList<JoinChatRoomRequestDto> joinRoom(Long chatRoomIdx, @Header("simpSessionId") String sessionId, JSONObject ob) {

        // 현재 들어온 세션 저장
        chatRoomIdxList.add(new JoinChatRoomRequestDto(chatRoomIdx,(String) ob.get("from"), sessionId));

        return chatRoomIdxList;
    }


}