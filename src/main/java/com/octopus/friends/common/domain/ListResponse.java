package com.octopus.friends.common.domain;

import com.octopus.friends.common.domain.enums.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * 패키지명 com.octopus.friends.common.domain
 * 클래스명 ListReponse
 * 클래스설명 list데이터 reponse시 값을 반환하는 클래스
 * 작성일 2022-09-18
 *
 * @author 원지윤
 * @version 1.0
 * [수정내용]
 * 예시) [2022-09-17] 주석추가 - 원지윤
 */
@Getter
@Setter
public class ListResponse<T> extends CommonResponse{
    List<T> dataList;

    public ListResponse(List<T> dataList, Status status){
        this.dataList = dataList;
        this.code = status.getCode();
        this.message = status.getMessage();
        this.success = true;
    }
}
