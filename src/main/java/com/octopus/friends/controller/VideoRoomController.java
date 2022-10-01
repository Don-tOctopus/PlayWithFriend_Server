package com.octopus.friends.controller;

import com.octopus.friends.common.domain.SingleResponse;
import com.octopus.friends.common.domain.enums.Status;
import com.octopus.friends.common.service.ResponseService;
import com.octopus.friends.dto.request.chat.CreateChatRoomRequestDto;
import com.octopus.friends.dto.request.chat.JoinChatRoomRequestDto;
import com.octopus.friends.dto.request.video.JoinVideoRoomRequestDto;
import com.octopus.friends.dto.request.video.VideoRoomRequestDto;
import com.octopus.friends.dto.response.chat.CreateChatRoomResponseDto;
import com.octopus.friends.dto.response.video.CreateVideoRoomResponseDto;
import com.octopus.friends.dto.response.video.JoinVideoRoomResponseDto;
import com.octopus.friends.dto.response.video.VideoRoomResponseDto;
import com.octopus.friends.service.VideoRoomService;
import com.octopus.friends.utils.Constants;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
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
 * [2022-10-01] swagger tag 수정 - 남유정
 * [2022-10-01] 기존 채팅방 입장 메소드 수정 - 남유정
 */

@Slf4j
@RestController
@Tag(name = "videoRoom", description = "화상 채팅방 관리 관련 API")
@AllArgsConstructor
//@CrossOrigin(origins = {Constants.API_URL, Constants.API_URL_DEV}, allowCredentials = "true")
@RequestMapping("/api/video/room")
public class VideoRoomController {

    // 세션 리스트
    private final ArrayList<VideoRoomRequestDto> chatRoomIdxList;
    private final SimpMessagingTemplate template;

    private final VideoRoomService videoRoomService;
    private final ResponseService responseService;

    /**
     * 기본 채팅방 입장
     * @param request
     * @return 입장한 채팅방의 정보와 response 상태
     */
//    @MessageMapping("/video/joined-room-info")
//    @SendTo("/sub/video/joined-room-info")
    @PostMapping("/enter/{roomIdx}")
    public ResponseEntity<SingleResponse<JoinVideoRoomResponseDto>> enterVideoRoom(@RequestBody JoinVideoRoomRequestDto
                                                                                                            request) {
        // 현재 들어온 세션 저장
//        chatRoomIdxList.add(new VideoRoomRequestDto((String) ob.get("from"), sessionId));
//        return chatRoomIdxList;
// 채팅방이름으로 들어오는거 IDX 변환 필요 (Long roomIdx = videoRoomService.findRoomIdxByRoomName(roomName))
        log.error("heyhey");
        JoinVideoRoomResponseDto joinVideoRoom = videoRoomService.joinVideoRoom(request);
        SingleResponse<JoinVideoRoomResponseDto> response = responseService.getSingleResponse(joinVideoRoom,
                                                                                    Status.SUCCESS_ENTERED_CHATROOM);

        return ResponseEntity.ok().body(response);
    }

//    @PostMapping("/")
//    public void hello(){
//        log.error("hihihihi");
//    }
//    public ResponseEntity<SingleResponse<CreateVideoRoomResponseDto>> createVieoRoom(@RequestHeader("USER-EMAIL") String userEmail,
//                                                                                      @RequestBody CreateChatRoomRequestDto request) {
//        CreateChatRoomResponseDto chatRoom = videoRoomService.save(userEmail, request);
//        singleResponse<CreateVideoRoomResponseDto> response = ;
//
//        return ResponseEntity.ok().body(response);
//    }

    /**
     * caller들의 정보를 다른 callee에게 전송
     * @param ob callerId, calleeId, streamData
     * @return caller의 정보
     */
    @MessageMapping("/video/caller-info")
    @SendTo("/sub/video/caller-info")
    private Map<String, Object> caller(JSONObject ob) {

//        log.info(ob.toJSONString());

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
        for (VideoRoomRequestDto session : chatRoomIdxList) {
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