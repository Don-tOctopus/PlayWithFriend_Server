package com.octopus.friends.dto.response.video;


import com.octopus.friends.domain.ChatRoom;
import com.octopus.friends.domain.ChatRoomRelation;
import com.octopus.friends.dto.response.chat.JoinChatRoomResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class JoinVideoRoomResponseDto {
    private Long videoRoomIdx;
    private Long roomRelationIdx;
    private String userEmail;

    /**
     *
     * @param chatRoom 참여하는 채팅방 엔티티
     * @param chatRoomRelation 채팅방에 참여하는 유저의 채팅방 엔티티
     * @return
     */
    public static JoinVideoRoomResponseDto of(ChatRoom chatRoom, ChatRoomRelation chatRoomRelation){
        return new JoinVideoRoomResponseDto(
                chatRoom.getChatRoomIdx(),
                chatRoomRelation.getRoomRelationIdx(),
                chatRoomRelation.getUser().getEmail()
        );
    }
}
