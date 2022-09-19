package com.octopus.friends.controller;

import com.octopus.friends.common.domain.SingleResponse;
import com.octopus.friends.common.domain.enums.Status;
import com.octopus.friends.common.service.ResponseService;
import com.octopus.friends.domain.ChatRoomRelation;
import com.octopus.friends.dto.request.CreateChatRoomRequestDto;
import com.octopus.friends.dto.request.JoinChatRoomRequestDto;
import com.octopus.friends.dto.response.ChatRoomRelationResponseDto;
import com.octopus.friends.dto.response.ChatRoomResponseDto;
import com.octopus.friends.dto.response.CreateChatRoomResponseDto;
import com.octopus.friends.dto.response.JoinChatRoomResponseDto;
import com.octopus.friends.service.ChatRoomService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 패키지명 com.octopus.friends.controller
 * 클래스명 ChatRoomController
 * 클래스설명
 * 작성일 2022-09-17
 *
 * @author 원지윤
 * @version 1.0
 * [수정내용]
 * 예시) [2022-09-17] 주석추가 - 원지윤
 */
@Slf4j
@Tag(name = "chatRoom", description = "채팅방 관리 관련 API")
@RestController
@AllArgsConstructor
@RequestMapping("/api/chatRoom")
public class ChatRoomController {
    private final ChatRoomService chatRoomService;
    private final ResponseService responseService;

    /**
     * user가 새로운 채팅방을 생성
     * @param userId 로그인한 user의 id로 header에 담겨옴
     * @param request 새로운 채팅방을 생성하기 위한 정보
     * @return 생성된 채팅방의 정보와 response 상태
     */
    @PostMapping
    public ResponseEntity<SingleResponse<CreateChatRoomResponseDto>> createChatRoom(@RequestHeader("USER-ID") String userId, @RequestBody CreateChatRoomRequestDto request){
        CreateChatRoomResponseDto chatRoom = chatRoomService.save(userId,request);
        SingleResponse<CreateChatRoomResponseDto> response = responseService.getSingleResponse(chatRoom,Status.SUCCESS_CREATED_CHATROOM);
        return ResponseEntity.ok().body(response);
    }

    /**
     * user가 기존의 채팅방에 입장
     * @param request 입장할 채팅방의 정보
     * @return 입장한 채팅방의 정보와 response상태
     */
    @PostMapping("/join")
    public ResponseEntity<SingleResponse<JoinChatRoomResponseDto>> joinChatRoom(@RequestBody JoinChatRoomRequestDto request){
        JoinChatRoomResponseDto joinChatRoom = chatRoomService.joinChatRoom(request);
        SingleResponse<JoinChatRoomResponseDto> response = responseService.getSingleResponse(joinChatRoom,Status.SUCCESS_JOINED_CHATROOM);
        return ResponseEntity.ok().body(response);
    }

    /**
     * user가 참여하고 있던 채팅방에서 나가기
     * @param userId 로그인한 유저의 id
     * @param roomIdx 나가기를 요청한 채팅방의 idx
     * @return 요청에 대한 응답
     */
    @PostMapping("/leave/{roomIdx}")
    public ResponseEntity<SingleResponse<ChatRoomRelationResponseDto>> leaveChatRoom(@RequestHeader("USER-ID") String userId, @PathVariable Long roomIdx){
        ChatRoomRelationResponseDto chatRoomRelation = chatRoomService.leaveChatRoom(userId, roomIdx);
        SingleResponse<ChatRoomRelationResponseDto> response = responseService.getSingleResponse(chatRoomRelation, Status.SUCCESS_DELETED_CHATROOM);
        return  ResponseEntity.ok().body(response);
    }

    /**
     * 로그인한 유저가 참여중인 모든 채팅방 조회
     * @param userId
     * @return
     */
    @GetMapping
    public ResponseEntity<SingleResponse<List<ChatRoomRelationResponseDto>>> findAllByUserId(@RequestHeader("USER-ID")String userId){
        List<ChatRoomRelationResponseDto> responses = chatRoomService.findAllByUserId(userId);
        SingleResponse<List<ChatRoomRelationResponseDto>> response = responseService.getSingleResponse(responses,Status.SUCCESS_SEARCHED_CHATROOM);
        return ResponseEntity.ok().body(response);
    }

    /**
     * 모든 채팅방 조회
     * @return
     */
    @GetMapping("/all")
    public ResponseEntity<SingleResponse<List<ChatRoomResponseDto>>> findAll(){
        List<ChatRoomResponseDto> responses = chatRoomService.findAll();
        SingleResponse<List<ChatRoomResponseDto>> response = responseService.getSingleResponse(responses,Status.SUCCESS_SEARCHED_CHATROOM);
        return ResponseEntity.ok().body(response);
    }


}
