package com.octopus.friends.common.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 패키지명 com.octopus.friends.common.domain
 * 클래스명 CommonResponse
 * 클래스설명 공통 response 속성을 제공하는 도메인
 * 작성일 2022-09-18
 *
 * @author 원지윤
 * @version 1.0
 * [수정내용]
 * 예시) [2022-09-17] 주석추가 - 원지윤
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommonResponse {
    boolean success;
    int code;
    String message;

}
