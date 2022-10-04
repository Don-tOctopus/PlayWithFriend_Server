package com.octopus.friends.service;

import com.octopus.friends.common.domain.enums.Status;
import com.octopus.friends.common.exception.CustomerNotFoundException;
import com.octopus.friends.domain.ChatRoom;
import com.octopus.friends.domain.ChatRoomRelation;
import com.octopus.friends.domain.User;
import com.octopus.friends.dto.request.chat.JoinChatRoomRequestDto;
import com.octopus.friends.dto.request.video.CreateVideoRoomRequestDto;
import com.octopus.friends.dto.request.video.JoinVideoRoomRequestDto;
import com.octopus.friends.dto.response.chat.ChatRoomRelationResponseDto;
import com.octopus.friends.dto.response.chat.ChatRoomResponseDto;
import com.octopus.friends.dto.response.chat.CreateChatRoomResponseDto;
import com.octopus.friends.dto.response.chat.JoinChatRoomResponseDto;
import com.octopus.friends.dto.response.video.CreateVideoRoomResponseDto;
import com.octopus.friends.dto.response.video.JoinVideoRoomResponseDto;
import com.octopus.friends.dto.response.video.VideoRoomRelationResponseDto;
import com.octopus.friends.dto.response.video.VideoRoomResponseDto;
import com.octopus.friends.repository.ChatRoomRelationRepository;
import com.octopus.friends.repository.ChatRoomRepository;
import com.octopus.friends.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 패키지명 com.octopus.friends.service
 * 클래스명 VideoRoomService
 * 클래스설명
 * 작성일 2022-10-01
 *
 * @author 남유정
 * @version 1.0
 * [수정내용]
 * 예시) [2022-09-17] 주석추가 - 남유정
 * [2022-10-03] 채팅방 비밀번호 일치 여부 확인 매서드 추가 - 남유정
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class VideoRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;
    private final ChatRoomRelationRepository chatRoomRelationRepository;

    /**
     * 기존 채팅방에 새로운 유저 참여
     * @param request 참여하려는 user 및 채팅방 정보
     * @return
     */
    public JoinVideoRoomResponseDto joinVideoRoom(JoinVideoRoomRequestDto request) {

        ChatRoom chatRoom = chatRoomRepository.findById(request.getRoomIdx())
                .orElseThrow(() -> new CustomerNotFoundException(Status.NOT_SEARCHED_CHATROOM));

        log.error(request.getUserId());
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new CustomerNotFoundException(Status.NOT_SEARCHED_USER));

//        if(!passwordCheck(request.getRoomIdx(), request.getRoomPassword()))
//            new CustomerNotFoundException(Status.NOT_COINCIDE_PASSWORD);

        ChatRoomRelation chatRoomRelation = request.toEntity(chatRoom, user);
        chatRoom.join();

        return JoinVideoRoomResponseDto.of(chatRoom, chatRoomRelationRepository.save(chatRoomRelation));
    }

    /**
     * 채팅방 비밀번호 일치 여부 확인
     * @param roomIdx 채팅방 인덱스
     * @param password 채팅방 비밀번호
     * @return 채팅방 비밀번호 일치 여부에 대한 True/False
     */
//    public boolean passwordCheck(final Long roomIdx, final String password) {
//        String roomPassword = chatRoomRepository.findRoomPasswordById(roomIdx);
//// table따로 만들면 findroompasswaordbyid findbyid로 변경
//        boolean response = password.equals(roomPassword);
//
//        return response;
//    }

    /**
     * user가 속한 모든 채팅방 조회
     * @param userid user id
     * @return user가 속한 모든 채팅방 조회
     */
    @Transactional
    public List<VideoRoomRelationResponseDto> findAllByUser(final String userid) {
        User user = userRepository.findById(userid)
                .orElseThrow(() ->  new CustomerNotFoundException(Status.NOT_SEARCHED_USER));

        // user가 사용중인 모든 realtion 들 호출
        List<ChatRoomRelation> chatRoomRelationList = chatRoomRelationRepository.findByUser(user);
        List<VideoRoomRelationResponseDto> responses = new ArrayList<>();

        for(ChatRoomRelation chatRoomRelation : chatRoomRelationList){
            ChatRoom chatRoom = chatRoomRepository.findById(chatRoomRelation.getChatRoom().getChatRoomIdx())
                    .orElseThrow(() -> new CustomerNotFoundException(Status.NOT_SEARCHED_CHATROOM));

            if(chatRoomRelation.isStatus())
                responses.add(VideoRoomRelationResponseDto.of(chatRoomRelation, chatRoom));
        }
        return responses;
    }

    /**
     * 생성된 채팅방 저장
     * @param userEmail 방 생성하는 hostEmail
     * @param request 방생성 시 필요한 채팅방 정보
     * @return
     */
    public CreateVideoRoomResponseDto save(final String userEmail, CreateVideoRoomRequestDto request){
        User host = checkValidUser(userEmail, request.getHostId());

        ChatRoom chatRoom = chatRoomRepository.save(request.toEntity());

        List<ChatRoomRelation> chatRoomRelationList = new ArrayList<>();

        for(String invitedUser : request.getUserList()) {
            Optional<User> user = userRepository.findByEmail(invitedUser);

            if(!user.isPresent())
                continue;

            ChatRoomRelation chatRoomRelation = ChatRoomRelation.builder()
                    .chatRoom(chatRoom)
                    .user(user.get())
                    .build();

            ChatRoomRelation saveResponse = chatRoomRelationRepository.save(chatRoomRelation);
        }

        ChatRoomRelation hostRoomRelation = chatRoomRelationRepository.findByUserAndChatRoom(host, chatRoom);
        CreateVideoRoomResponseDto response = CreateVideoRoomResponseDto.of(hostRoomRelation.getChatRoom(), hostRoomRelation);

        return response;
    }

    /**
     * 채팅방 생성시 생성하는 유저와 로그인한 유저의 일치 확인
     * @param userEmail 로그인한 유저 id
     * @param hostId 채팅방을 생성하려는 유저 id
     * @return 유저 정보
     */
    private User checkValidUser(String userEmail, String hostId)  {
        if(!userEmail.equals(hostId)){
            throw new CustomerNotFoundException(Status.BAD_REQUEST);
        }
        return userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CustomerNotFoundException(Status.NOT_SEARCHED_USER));
    }

}