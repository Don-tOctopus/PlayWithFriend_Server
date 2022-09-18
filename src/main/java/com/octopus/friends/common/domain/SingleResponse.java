package com.octopus.friends.common.domain;

import com.octopus.friends.common.domain.enums.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 패키지명 com.octopus.friends.common.domain
 * 클래스명 SingleResponse
 * 클래스설명 단일데이터 response시 값을 반환하는 클래스
 * 작성일 2022-09-18
 *
 * @author 원지윤
 * @version 1.0
 * [수정내용]
 * 예시) [2022-09-17] 주석추가 - 원지윤
 */
@Getter
@Setter
@NoArgsConstructor
public class SingleResponse<T> extends CommonResponse {
    T data;

    public SingleResponse(T data, Status status){
        this.data = data;
        this.success = true;
        this.code = status.getCode();
        this.message = status.getMessage();
    }
}
