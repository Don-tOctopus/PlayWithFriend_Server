package com.octopus.friends.common.exception;

import com.octopus.friends.common.domain.enums.Status;
/**
 * 패키지명 com.octopus.friends.common.exception
 * 클래스명 CustomerNotFoundException
 * 클래스설명 개발자 정의 exception 클래스
 * 작성일 2022-09-20
 *
 * @author 원지윤
 * @version 1.0
 * [수정내용]
 * 예시) [2022-09-17] 주석추가 - 원지윤
 */

public class CustomerNotFoundException extends RuntimeException{
    public CustomerNotFoundException() {
        super();
    }

    public CustomerNotFoundException(Status status){
        super(status.getMessage());
    }

    public CustomerNotFoundException(String message) {
        super(message);
    }

    public CustomerNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomerNotFoundException(Throwable cause) {
        super(cause);
    }
}