package com.octopus.friends.service;

import com.octopus.friends.common.domain.enums.Status;
import com.octopus.friends.common.exception.CustomerNotFoundException;
import com.octopus.friends.domain.ChatRoom;
import com.octopus.friends.domain.ChatRoomRelation;
import com.octopus.friends.domain.User;
import com.octopus.friends.dto.request.chat.CreateChatRoomRequestDto;
import com.octopus.friends.dto.request.chat.JoinChatRoomRequestDto;
import com.octopus.friends.dto.response.chat.ChatRoomRelationResponseDto;
import com.octopus.friends.dto.response.chat.ChatRoomResponseDto;
import com.octopus.friends.dto.response.chat.CreateChatRoomResponseDto;
import com.octopus.friends.dto.response.chat.JoinChatRoomResponseDto;
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

import javax.annotation.PostConstruct;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.*;

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
 * [2022-09-21] 채팅방 입장 시 토픽을 생성할 수 있도록 수정 - 원지윤
 * [2022-09-27] userId -> userEmail로 수정 - 원지윤
 * [2022-09-27] findByUserAndChatRoom -> findByEmail 수정 - 원지윤
 * [2022-09-27] exception 매개변수 status Enum으로 변경 - 원지윤
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

    @PostConstruct
    private void init() {
        opsHashChatRoom = redisTemplate.opsForHash();
        topics = new HashMap<>();
    }

    /**
     * 생성한 채팅방을 DB에 저장
     * @param userEmail 로그인한 유저의 id
     * @param request 생성하려는 채팅방 정보
     * @return
     */
    @Transactional
    public CreateChatRoomResponseDto save(final String userEmail, CreateChatRoomRequestDto request){
        User host = checkValidUser(userEmail, request.getHostId());

        ChatRoom chatRoom = chatRoomRepository.save(request.toEntity());

        List<ChatRoomRelation> chatRoomRelationList = new ArrayList<>();
        for(String invitedEmail: request.getUserList()){
            Optional<User> user = userRepository.findByEmail(invitedEmail);

            if(!user.isPresent()){
                continue;
            }

            ChatRoomRelation chatRoomRelation = ChatRoomRelation.builder()
                    .chatRoom(chatRoom)
                    .user(user.get())
                    .build();

            ChatRoomRelation re = chatRoomRelationRepository.save(chatRoomRelation);
        }

        ChatRoomRelation hostRoom = chatRoomRelationRepository.findByUserAndChatRoom(host, chatRoom);

        CreateChatRoomResponseDto response = CreateChatRoomResponseDto.of(hostRoom.getChatRoom(), hostRoom);

        //서버간 채팅방 공유를 위해 redis hash에 저장
        opsHashChatRoom.put(CHAT_ROOMS, chatRoom.getChatRoomIdx().toString(), chatRoom);
        return response;

    }

    /**
     * 채팅방을 생설할 때 생성하는 유저와 로그인한 유저가 일치하는 지 확인
     * @param userEmail 로그인한 유저의 id
     * @param hostId 채팅방을 생성하려는 유저의 id
     * @return 유저의 정보
     */
    private User checkValidUser(String userEmail, String hostId)  {

        if(!userEmail.equals(hostId)){
            throw new CustomerNotFoundException(Status.BAD_REQUEST);
        }
        return userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CustomerNotFoundException(Status.NOT_SEARCHED_USER));
    }

    /**
     * 기존 채팅방에 새로운 유저 추가
     * @param request 참여하려는 채팅방의 정보
     * @return
     */
    @Transactional
    public JoinChatRoomResponseDto joinChatRoom(JoinChatRoomRequestDto request){
        ChatRoom chatRoom = chatRoomRepository.findById(request.getChatRoomIdx())
                .orElseThrow(() -> new CustomerNotFoundException(Status.NOT_SEARCHED_CHATROOM));
        User user = userRepository.findByEmail(request.getUserEmail())
                .orElseThrow(()-> new CustomerNotFoundException(Status.NOT_SEARCHED_USER));

        ChatRoomRelation chatRoomRelation = request.toEntity(chatRoom, user);
        chatRoom.join();
        return JoinChatRoomResponseDto.of(chatRoom,chatRoomRelationRepository.save(chatRoomRelation));
    }

    /**
     * 로그인한 유저가 속한 모든 채팅방 조회
     * @param userEmail 로그인한 user의 email
     * @return
     */
    @Transactional
    public List<ChatRoomRelationResponseDto> findAllByUserId(final String userEmail){
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() ->  new CustomerNotFoundException(Status.NOT_SEARCHED_USER));
        List<ChatRoomRelation> chatRoomRelationList = chatRoomRelationRepository.findAllByUser(user);
        List<ChatRoomRelationResponseDto> responses = new ArrayList<>();
        for(ChatRoomRelation chatRoomRelation: chatRoomRelationList){
            ChatRoom chatRoom = chatRoomRepository.findById(chatRoomRelation.getChatRoom().getChatRoomIdx())
                    .orElseThrow(() -> new CustomerNotFoundException(Status.NOT_SEARCHED_CHATROOM));
            if(chatRoomRelation.isStatus())
                responses.add(ChatRoomRelationResponseDto.of(chatRoomRelation,chatRoom));
        }
        return responses;
    }

    /**
     * 채팅방의 idx를 통해 채팅방을 조회
     * @param userEmail 로그인하고 있는 user의 email
     * @param roomIdx 채팅방의 idx
     * @return
     */
    @Transactional
    public ChatRoomResponseDto findRoomByRoomIdx(final String userEmail, final Long roomIdx){
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() ->  new CustomerNotFoundException(Status.NOT_SEARCHED_USER));
        ChatRoom chatRoom = chatRoomRepository.findById(roomIdx)
                .orElseThrow(() -> new CustomerNotFoundException(Status.NOT_SEARCHED_CHATROOM));
        ChatRoomResponseDto response = ChatRoomResponseDto.of(chatRoom);
        return response;
    }
    /**
     * 참여하고 있던 채팅방 나가기
     * @param userEmail 로그인한 유저
     * @param roomIdx 나가고 싶은 채팅방의 idx
     * @return ChatRoomRelationResponseDto
     */
    @Transactional
    public ChatRoomRelationResponseDto leaveChatRoom(final String userEmail, final Long roomIdx){
        ChatRoom chatRoom = chatRoomRepository.findById(roomIdx)
                .orElseThrow(()-> new CustomerNotFoundException(Status.NOT_SEARCHED_CHATROOM));
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CustomerNotFoundException(Status.NOT_SEARCHED_USER));

        ChatRoomRelation chatRoomRelation = chatRoomRelationRepository.findByUserAndChatRoom(user, chatRoom);

        chatRoomRelation.setStatus(false);
        chatRoom.leave();

        //채팅방의 인원이 모두 나간경우 redis에서 삭제
        if(chatRoom.getUCnt()<1){
            opsHashChatRoom.delete(CHAT_ROOMS, chatRoom.getChatRoomIdx().toString());
        }

        ChatRoomRelationResponseDto response = ChatRoomRelationResponseDto.of(chatRoomRelation,chatRoom);
        return response;
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

    /**
     * 채팅방에 들어갈때 topic생성
     * @param roomId 입장할 채팅방의 idx
     */
    public void enterChatRoom(String roomId) {
        //roomId를 key로 가진 topic을 가져옴
        ChannelTopic topic = topics.get(roomId);
        //가져온 topic이 null인 경우
        if (topic == null) {
            //roomId를 key로 새로운 topic생성
            topic = new ChannelTopic(roomId);
            //
            redisMessageListener.addMessageListener(redisSubscriber, topic);
            topics.put(roomId, topic);
        }
    }

    public ChannelTopic getTopic(String roomId) {
        return topics.get(roomId);
    }


}
