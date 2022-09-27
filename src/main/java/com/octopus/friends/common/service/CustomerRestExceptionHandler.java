package com.octopus.friends.common.service;

import com.octopus.friends.common.domain.CustomerErrorResponse;
import com.octopus.friends.common.exception.CustomerNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 패키지명 com.octopus.friends.common.service
 * 클래스명 CustomerRestExceptionHandler
 * 클래스설명 개발자 정의 exceptionhandler 클래스
 * 작성일 2022-09-20
 *
 * @author 원지윤
 * @version 1.0
 * [수정내용]
 * 예시) [2022-09-17] 주석추가 - 원지윤
 * [2022-09-27] Status를 매개변수로 받는 생성자 추가 - 원지윤
 */

@RestControllerAdvice("com.octopus.friends.controller")
public class CustomerRestExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<CustomerErrorResponse> handleException(CustomerNotFoundException exc){
        CustomerErrorResponse error = new CustomerErrorResponse(
                exc.getStatus(),
                System.currentTimeMillis());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<CustomerErrorResponse> handleException(Exception exc){
        CustomerErrorResponse error = new CustomerErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                exc.getMessage(),
                System.currentTimeMillis());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
