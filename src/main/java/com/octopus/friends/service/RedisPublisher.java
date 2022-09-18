package com.octopus.friends.service;

import com.octopus.friends.domain.Chat;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

/**
 * 패키지명 com.octopus.friends.service
 * 클래스명 RedisPublisher
 * 클래스설명 채팅방에 입장하여 메시지를 작성하면 해당 메시지를 Redis Topic에 발행하는 기능의 서비스
 * 작성일 2022-09-19
 *
 * @author 원지윤
 * @version 1.0
 * [수정내용]
 * 예시) [2022-09-17] 주석추가 - 원지윤
 */
@RequiredArgsConstructor
@Service
public class RedisPublisher {
    private final RedisTemplate<String, Object> redisTemplate;
    public void publish(ChannelTopic topic, Chat chat) {
        redisTemplate.convertAndSend(topic.getTopic(), chat);
    }
}
