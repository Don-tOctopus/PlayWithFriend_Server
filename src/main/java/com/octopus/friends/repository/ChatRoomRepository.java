package com.octopus.friends.repository;

import com.octopus.friends.domain.ChatRoom;
import com.octopus.friends.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 패키지명 com.octopus.friends.repository
 * 클래스명 ChatRoomRepository
 * 클래스설명 ChatRoom의 Repository인터페이스이다.
 * 작성일 2022-09-17
 *
 * @author 원지윤
 * @version 1.0
 * [수정내용]
 * 예시) [2022-09-17] 주석추가 - 원지윤
 * [2022-10-03] 채팅방 인덱스로 채팅방 password 찾는 메소드 추가 - 남유정
 */
@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
}
