package com.octopus.friends.repository;

import com.octopus.friends.domain.User;
import com.octopus.friends.domain.VideoRoom;
import com.octopus.friends.domain.VideoRoomRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 패키지명 com.octopus.friends.repository
 * 클래스명 VideoRoomRelationRepository
 * 클래스설명
 * 작성일 2022-10-05
 *
 * @author 남유정
 * @version 1.0
 * [수정내용]
 * 예시) [2022-09-17] 주석추가 - 남유정
 */
@Repository
public interface VideoRoomRelationRepository extends JpaRepository<VideoRoomRelation, Long> {
    public List<VideoRoomRelation> findByUser(User user);
    public VideoRoomRelation findByUserAndChatRoom(User user, VideoRoom videoRoom);
}