package com.octopus.friends.common.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 패키지명 com.octopus.friends.common.domain.enums
 * 클래스명 status
 * 클래스설명 response의 응답코드와 응답메세지에 대한 Enum
 * 작성일 2022-09-18
 *
 * @author 원지윤
 * @version 1.0
 * [수정내용]
 * 예시) [2022-09-17] 주석추가 - 원지윤
 */
@Getter
@AllArgsConstructor
public enum Status {

    SUCCESS(200,"성공"),
    FAIL(-999,"실패"),
    //200
    SUCCESS_JOINED_CHATROOM(200, "채팅방 입장 성공"),
    SUCCESS_SEARCHED_CHATROOM(200,"채팅방 전체 조회 성공"),
    SUCCESS_DELETED_CHATROOM(200,"채팅방 삭제 성공"),
    SUCCESS_ENTERED_CHATROOM(200,"채팅방 입장 성공"),

    //201
    SUCCESS_CREATED_CHATROOM(201, "채팅방 생성 성공"),

    //40x
    NOT_SEARCHED_USER(404, "찾을 수 없는 유저"),
    BAD_REQUEST(404,"옳지 않은 요청입니다."),
    NOT_SEARCHED_CHATROOM(404,"찾을 수 없는 채팅방입니다.");

    private final int code;
    private final String message;
}
