package com.octopus.friends.common.exception;

import com.octopus.friends.common.domain.CustomerErrorResponse;
import com.octopus.friends.common.domain.enums.Status;
import lombok.Getter;

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
 * [2022-09-27] status를 매개변수로 받아 exception 처리 할 수 있도록 수정 - 원지윤
 */
@Getter
public class CustomerNotFoundException extends RuntimeException{
    private Status status;

    public CustomerNotFoundException() {
        super();
    }

    public CustomerNotFoundException(Status status){
        super(status.getMessage());
        this.status = status;
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