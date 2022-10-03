package com.octopus.friends.service;

import com.octopus.friends.common.domain.enums.Status;
import com.octopus.friends.common.exception.CustomerNotFoundException;
import com.octopus.friends.domain.ChatRoom;
import com.octopus.friends.domain.ChatRoomRelation;
import com.octopus.friends.domain.User;
import com.octopus.friends.dto.request.chat.JoinChatRoomRequestDto;
import com.octopus.friends.dto.request.video.CreateVideoRoomRequestDto;
import com.octopus.friends.dto.request.video.JoinVideoRoomRequestDto;
import com.octopus.friends.dto.response.chat.ChatRoomResponseDto;
import com.octopus.friends.dto.response.chat.JoinChatRoomResponseDto;
import com.octopus.friends.dto.response.video.CreateVideoRoomResponseDto;
import com.octopus.friends.dto.response.video.JoinVideoRoomResponseDto;
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
    ChatRoomRepository chatRoomRepository;
    UserRepository userRepository;
    ChatRoomRelationRepository chatRoomRelationRepository;

    /**
     * 기존 채팅방에 새로운 유저 참여
     * @param request 참여하려는 user 및 채팅방 정보
     * @return
     */
    public JoinVideoRoomResponseDto joinVideoRoom(JoinVideoRoomRequestDto request) {

        ChatRoom chatRoom = chatRoomRepository.findByChatRoomIdEquals(request.getRoomIdx())
                .orElseThrow(() -> new CustomerNotFoundException(Status.NOT_SEARCHED_CHATROOM));

        User user = userRepository.findByUserIdEquals(request.getUserId())
                .orElseThrow(() -> new CustomerNotFoundException(Status.NOT_SEARCHED_USER));

        if(!passwordCheck(request.getRoomIdx(), request.getRoomPassword()))
            new CustomerNotFoundException(Status.NOT_COINCIDE_PASSWORD);

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
    public boolean passwordCheck(final Long roomIdx, final String password) {
        String roomPassword = chatRoomRepository.findRoomPasswordById(roomIdx);

        boolean response = password.equals(roomPassword);

        return response;
    }


    /**
     * 생성된 채팅방 DB에 저장
     * @param userEmail 로그인한 유저 Id (host)
     * @param request 생성하려는 채팅방 정보
     * @return
     */
//    @Transactional
//    public CreateVideoRoomResponseDto save(final String userEmail, CreateVideoRoomRequestDto request) {
        // user의 유효성 확인
//        User host = checkValidUser(userEmail, request.getHostId());
//
//        // 채팅방 정보 저장
//        ChatRoom chatRoom = chatRoomRepository.save(request.toEntity());
//
//        List<ChatRoomRelation> chatRoomRelationList = new ArrayList<>();


//    }

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
