package com.octopus.friends.controller;

import com.octopus.friends.common.domain.SingleResponse;
import com.octopus.friends.common.domain.enums.Status;
import com.octopus.friends.common.service.ResponseService;
import com.octopus.friends.dto.request.chat.CreateChatRoomRequestDto;
import com.octopus.friends.dto.request.chat.JoinChatRoomRequestDto;
import com.octopus.friends.dto.request.video.CreateVideoRoomRequestDto;
import com.octopus.friends.dto.request.video.JoinVideoRoomRequestDto;
import com.octopus.friends.dto.request.video.VideoRoomRequestDto;
import com.octopus.friends.dto.response.chat.ChatRoomRelationResponseDto;
import com.octopus.friends.dto.response.chat.CreateChatRoomResponseDto;
import com.octopus.friends.dto.response.video.CreateVideoRoomResponseDto;
import com.octopus.friends.dto.response.video.JoinVideoRoomResponseDto;
import com.octopus.friends.dto.response.video.VideoRoomRelationResponseDto;
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
import java.util.List;
import java.util.Map;

/**
 * ???????????? com.octopus.friends.controller
 * ???????????? VideoRoomController
 * ???????????????
 * ????????? 2022-09-19
 *
 * @author ?????????
 * @version 1.0
 * [????????????]
 * ??????) [2022-09-17] ???????????? - ?????????
 * [2022-10-01] swagger tag ?????? - ?????????
 * [2022-10-01] ?????? ????????? ?????? ????????? ?????? - ?????????
 */

@Slf4j
@RestController
@Tag(name = "videoRoom", description = "?????? ????????? ?????? ?????? API")
@AllArgsConstructor
//@CrossOrigin(origins = {Constants.API_URL, Constants.API_URL_DEV}, allowCredentials = "true")
@RequestMapping("/api/video/room")
public class VideoRoomController {

    // ?????? ?????????
    private final ArrayList<VideoRoomRequestDto> chatRoomIdxList;
    private final SimpMessagingTemplate template;

    private final VideoRoomService videoRoomService;
    private final ResponseService responseService;

    /**
     * ?????? ????????? ??????
     * @param request userId??? ????????? ??????
     * @return ????????? ???????????? ????????? response ??????
     */
//    @MessageMapping("/video/joined-room-info")
//    @SendTo("/sub/video/joined-room-info")
    @PostMapping("/enter")
    public ResponseEntity<SingleResponse<JoinVideoRoomResponseDto>> enterVideoRoom(@RequestBody JoinVideoRoomRequestDto
                                                                                                            request) {

        JoinVideoRoomResponseDto joinVideoRoom = videoRoomService.joinVideoRoom(request);
        SingleResponse<JoinVideoRoomResponseDto> response = responseService.getSingleResponse(joinVideoRoom,
                                                                                    Status.SUCCESS_ENTERED_CHATROOM);

        return ResponseEntity.ok().body(response);
    }

    /**
     * ???????????? user??? ???????????? ?????? ?????? ????????? ??????
     * @param
     * @return user??? ???????????? ?????? ?????? ????????? List
     */
    @GetMapping
    public ResponseEntity<SingleResponse<List<VideoRoomRelationResponseDto>>> findAllByUserId(final String userid) {
        List<VideoRoomRelationResponseDto> responses = videoRoomService.findAllByUser(userid);

        SingleResponse<List<VideoRoomRelationResponseDto>> response = responseService.getSingleResponse(responses, Status.SUCCESS_SEARCHED_CHATROOM);

        return ResponseEntity.ok().body(response);
    }

    /**
     * ????????? ??????
     * @param userEmail ????????? ??????????????? user
     * @param request
     * @return
     */
      @PostMapping("/create")
    public ResponseEntity<SingleResponse<CreateVideoRoomResponseDto>> createVideoRoom
                                                                    (@RequestHeader("USER-EMAIL") String userEmail,
                                                                    @RequestBody CreateVideoRoomRequestDto request) {

        CreateVideoRoomResponseDto chatRoom = videoRoomService.save(userEmail, request);
        SingleResponse<CreateVideoRoomResponseDto> response = responseService.getSingleResponse(chatRoom,
                                                                                    Status.SUCCESS_ENTERED_CHATROOM);

        return ResponseEntity.ok().body(response);
    }

    /**
     * caller?????? ????????? ?????? callee?????? ??????
     * @param ob callerId, calleeId, streamData
     * @return caller??? ??????
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
     * caller?????? ??? signalling Data ?????? ??? caller?????? signaling answer
     * (caller??? callee??? signaling??? ?????? callee ?????? ??????)
     * @param ob callee??? ??????
     * @return accepter??? ??????
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
     * close??? ?????? id ??????
     * @param event close??? ?????? id
     */
    @EventListener
    private void handleSessionDisconnect(SessionDisconnectEvent event) {

        String removedID = "";

        // close??? ????????? id ??????
        for (VideoRoomRequestDto session : chatRoomIdxList) {
            if (session.getSessionId().equals(event.getSessionId())) {
                removedID = session.getUserId();
                chatRoomIdxList.remove(session);
                break;
            }
        }

        // ?????? ?????? id ??????
        template.convertAndSend("/sub/video/close-session", removedID);
    }
}