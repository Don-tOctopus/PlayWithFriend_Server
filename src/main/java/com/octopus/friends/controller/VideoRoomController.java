package com.octopus.friends.controller;

import com.octopus.friends.dto.request.JoinChatRoomRequestDto;
import com.octopus.friends.utils.Constants;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
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
@Tag(name = "chatRoom", description = "채팅방 관리 관련 API")
@RequiredArgsConstructor
@CrossOrigin(origins = {Constants.API_URL, Constants.API_URL_DEV}, allowCredentials = "true")
public class VideoRoomController {

    // 세션 리스트
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

    @ResponseBody
    @GetMapping("/test")
    public String test(){

        log.info("qweqwe");
        return "qwe";
    }

    /**
     * caller들의 정보를 다른 callee에게 전송
     * @param ob callerId, calleeId, streamData
     * @return caller의 정보
     */
    @MessageMapping("/video/caller-info")
    @SendTo("/sub/video/caller-info")
    private Map<String, Object> caller(JSONObject ob) {

        log.info(ob.toJSONString());

        Map<String, Object> data = new HashMap<>();
        data.put("toCall", ob.get("toCall"));
        data.put("from", ob.get("from"));
        data.put("signal", ob.get("signal"));

        return data;
    }

    /**
     * caller에게 온 signalling Data 확인 후 caller에게 signaling answer
     * (caller와 callee의 signaling을 위해 callee 정보 전송)
     * @param ob callee의 정보
     * @return accepter의 정보
     */
    @MessageMapping("/video/callee-info")
    @SendTo("/sub/video/callee-info")
    private Map<String, Object> answerCall(JSONObject ob) {

        log.info(ob.toJSONString());

        Map<String, Object> data = new HashMap<>();
        data.put("signal", ob.get("signal"));
        data.put("from", ob.get("from"));
        data.put("to", ob.get("to"));

        return data;
    }

    /**
     * ////////////////////////////////////////////////////////////////////
     * @param map
     * @return
     * @throws ParseException
     */
    @MessageMapping("/video/audio-sentiment")
    @SendTo("/sub/video/audio-sentiment")
    public Map<String, Object> getAudioSentiment(@RequestBody Map<String, String> map) throws ParseException {

        HttpHeaders headers = new HttpHeaders();
        RestTemplate restTemplate = new RestTemplate();
        String resultMessage = restTemplate.postForObject(Constants.ML_API_URL + "/audio-sentiment", new HttpEntity<>(map, headers), String.class);

        JSONParser parser = new JSONParser();
        Object obj = parser.parse(resultMessage);
        JSONObject jsonObj = (JSONObject) obj;

        // {from : senderId, }
        Map<String, Object> returnData = new HashMap<>();
        returnData.put("from", map.get("from"));
        returnData.put("resultOfAudioSentiment", jsonObj);
        return returnData;
    }

    /**
     *
     * @param map
     * @return /////////////////////////////////////////////////////////////
     * @throws ParseException
     */
    @MessageMapping("/video/chat")
    @SendTo("/sub/video/chat")
    public Map<String, String> listenAndSendChat(@RequestBody Map<String, String> map) throws ParseException {

        return map;
    }

    @EventListener
    private void handleSessionConnected(SessionConnectEvent event) {

    }

    /**
     * close된 세션 id 전달
     * @param event close된 세션 id
     */
    @EventListener
    private void handleSessionDisconnect(SessionDisconnectEvent event) {

        String removedID = "";

        // close된 세션의 id 저장
        for (JoinChatRoomRequestDto session : chatRoomIdxList) {
            if (session.getSessionId().equals(event.getSessionId())) {
                removedID = session.getUserId();
                chatRoomIdxList.remove(session);
                break;
            }
        }

        // 종료 세션 id 전달
        template.convertAndSend("/sub/video/close-session", removedID);
    }
}