package com.octopus.friends.service;

import com.octopus.friends.domain.ChatRoom;
import com.octopus.friends.domain.ChatRoomRelation;
import com.octopus.friends.domain.User;
import com.octopus.friends.dto.request.CreateChatRoomRequestDto;
import com.octopus.friends.dto.request.JoinChatRoomRequestDto;
import com.octopus.friends.dto.response.ChatRoomRelationResponseDto;
import com.octopus.friends.dto.response.ChatRoomResponseDto;
import com.octopus.friends.dto.response.CreateChatRoomResponseDto;
import com.octopus.friends.dto.response.JoinChatRoomResponseDto;
import com.octopus.friends.repository.ChatRoomRelationRepository;
import com.octopus.friends.repository.ChatRoomRepository;
import com.octopus.friends.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 패키지명 com.octopus.friends.service
 * 클래스명 ChatRoomService
 * 클래스설명
 * 작성일 2022-09-17
 * 
 * @author 원지윤
 * @version 1.0
 * [수정내용] 
 * 예시) [2022-09-17] 주석추가 - 원지윤
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomRelationRepository chatRoomRelationRepository;
    private final UserRepository userRepository;

    // 채팅방(topic)에 발행되는 메시지를 처리할 Listner
    private final RedisMessageListenerContainer redisMessageListener;
    // 구독 처리 서비스
    private final RedisSubscriber redisSubscriber;
    // Redis
    private static final String CHAT_ROOMS = "CHAT_ROOM";
    private final RedisTemplate<String, Object> redisTemplate;
    private HashOperations<String, String, ChatRoom> opsHashChatRoom;
    // 채팅방의 대화 메시지를 발행하기 위한 redis topic 정보. 서버별로 채팅방에 매치되는 topic정보를 Map에 넣어 roomId로 찾을수 있도록 한다.
    private Map<String, ChannelTopic> topics;

    /**
     * 생성한 채팅방을 DB에 저장
     * @param userId 로그인한 유저의 id
     * @param request 생성하려는 채팅방 정보
     * @return
     */
    @Transactional
    public CreateChatRoomResponseDto save(final String userId, CreateChatRoomRequestDto request){
        User host = checkValidUser(userId, request.getHostId());
        ChatRoom chatRoom = chatRoomRepository.save(request.toEntity());

        List<ChatRoomRelation> chatRoomRelationList = new ArrayList<>();
        for(String invitedId: request.getUserList()){
            Optional<User> user = userRepository.findByUserIdEquals(invitedId);

            if(!user.isPresent()){
                continue;
            }

            ChatRoomRelation chatRoomRelation = ChatRoomRelation.builder()
                    .chatRoom(chatRoom)
                    .user(user.get())
                    .build();

            chatRoomRelationRepository.save(chatRoomRelation);
        }

        ChatRoomRelation hostRoom = chatRoomRelationRepository.findByUserAndChatRoom(host, chatRoom);

        CreateChatRoomResponseDto response = CreateChatRoomResponseDto.of(hostRoom.getChatRoom(), hostRoom);
        opsHashChatRoom.put(CHAT_ROOMS, chatRoom.getChatRoomIdx().toString(), chatRoom);
        return response;

    }

    /**
     * 채팅방을 생설할 때 생성하는 유저와 로그인한 유저가 일치하는 지 확인
     * @param userId 로그인한 유저의 id
     * @param hostId 채팅방을 생성하려는 유저의 id
     * @return 유저의 정보
     */
    private User checkValidUser(String userId, String hostId)  {
        if(!userId.equals(hostId)){
            throw new RuntimeException("옳지 않은 접근입니다");
        }
        return userRepository.findByUserIdEquals(userId)
                .orElseThrow(() -> new EntityNotFoundException("찾을 수 없는 사용자"));
    }

    /**
     * 기존 채팅방에 새로운 유저 추가
     * @param request 참여하려는 채팅방의 정보
     * @return
     */
    @Transactional
    public JoinChatRoomResponseDto joinChatRoom(JoinChatRoomRequestDto request){
        ChatRoom chatRoom = chatRoomRepository.findById(request.getChatRoomIdx())
                .orElseThrow(() -> new EntityNotFoundException("찾을 수 없는 채팅방"));
        User user = userRepository.findByUserIdEquals(request.getUserId())
                .orElseThrow(()-> new EntityNotFoundException("찾을 수 없는 사용자"));

        ChatRoomRelation chatRoomRelation = request.toEntity(chatRoom, user);
        chatRoom.join();
        return JoinChatRoomResponseDto.of(chatRoom,chatRoomRelationRepository.save(chatRoomRelation));
    }

    /**
     *
     * @param userId
     * @return
     */
    @Transactional
    public List<ChatRoomRelationResponseDto> findAllByUserId(final String userId){
        User user = userRepository.findByUserIdEquals(userId)
                .orElseThrow(() ->  new EntityNotFoundException("찾을 수 없는 사용자"));
        List<ChatRoomRelation> chatRoomRelationList = chatRoomRelationRepository.findAllByUser(user);
        List<ChatRoomRelationResponseDto> responses = new ArrayList<>();
        for(ChatRoomRelation chatRoomRelation: chatRoomRelationList){
            responses.add(ChatRoomRelationResponseDto.of(chatRoomRelation));
        }
        return responses;
    }

    /**
     * 전체 채팅방 조회
     * @return
     */
    public List<ChatRoomResponseDto> findAll(){
        List<ChatRoom> chatRoomRelationList = chatRoomRepository.findAll();
        List<ChatRoomResponseDto> responses = new ArrayList<>();
        for(ChatRoom chatRoom: chatRoomRelationList){
            responses.add(ChatRoomResponseDto.of(chatRoom));
        }
        return responses;
    }

    public ChannelTopic getTopic(String roomId) {
        return topics.get(roomId);
    }


}
