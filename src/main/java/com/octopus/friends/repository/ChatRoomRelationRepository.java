package com.octopus.friends.repository;

import com.octopus.friends.domain.ChatRoom;
import com.octopus.friends.domain.ChatRoomRelation;
import com.octopus.friends.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 패키지명 com.octopus.friends.repository
 * 클래스명 ChatRoomRelationRepository
 * 클래스설명
 * 작성일 2022-09-17
 * 
 * @author 원지윤
 * @version 1.0
 * [수정내용] 
 * 예시) [2022-09-17] 주석추가 - 원지윤
 */

@Repository
public interface ChatRoomRelationRepository extends JpaRepository<ChatRoomRelation, Long> {
    public ChatRoomRelation findByUserAndChatRoom(User user, ChatRoom chatRoom);

    public List<ChatRoomRelation> findAllByUser(User user);
}
