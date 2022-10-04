package com.octopus.friends.dto.request.chat;

import com.octopus.friends.domain.ChatRoom;
import lombok.*;

import java.util.List;

/**
 * 패키지명 com.octopus.friends.dto.request
 * 클래스명 ChatRoomRequestDto
 * 클래스설명
 * 작성일 2022-09-17
 * 
 * @author 원지윤
 * @version 1.0
 * [수정내용] 
 * 예시) [2022-09-17] 주석추가 - 원지윤
 * [2022-10-04] ChatType 삭제 - 원지윤
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateChatRoomRequestDto {
    private String hostId;
    private String roomName;
    private List<String> userList;

    /**
     * 입력한 값을 ChatRoom 엔티티로 변경하여 반환하는 메소드
     * @return ChatRoom
     */
    public ChatRoom toEntity(){
        return ChatRoom.builder()
                .roomName(roomName)
                .hostId(hostId)
                .uCnt(userList.size())
                .build();
    }
}
