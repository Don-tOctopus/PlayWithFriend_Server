package com.octopus.friends.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * 패키지명 com.octopus.friends.config
 * 클래스명 EmbeddedRedisConfig
 * 클래스설명 채팅 서버가 실행될때 Embedded Redis 서버도 동시에 실행
 * 작성일 2022-09-19
 *
 * @author 원지윤
 * @version 1.0
 * [수정내용]
 * 예시) [2022-09-17] 주석추가 - 원지윤
 * [2022-10-01] redisServer 상태 확인 후 redisSart하도록 변경 - 원지윤
 */
@Profile("local")
@Configuration
public class EmbeddedRedisConfig {
    @Value("${spring.redis.port}")
    private int redisPort;
    private RedisServer redisServer = null;

    @PostConstruct
    public void redisServer() {
        if(redisServer == null || !redisServer.isActive()) {
            redisServer = RedisServer.builder()
                    .port(redisPort)
                    .setting("maxmemory 128M")
                    .build();
            redisServer.start();
        }
    }
    @PreDestroy
    public void stopRedis() {
        if (redisServer != null) {
            redisServer.stop();
        }
    }
}
