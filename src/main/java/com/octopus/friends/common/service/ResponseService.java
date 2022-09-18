package com.octopus.friends.common.service;

import com.octopus.friends.common.domain.CommonResponse;
import com.octopus.friends.common.domain.ListResponse;
import com.octopus.friends.common.domain.SingleResponse;
import com.octopus.friends.common.domain.enums.Status;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 패키지명 com.octopus.friends.common.service
 * 클래스명 ResponseService
 * 클래스설명
 * 작성일 2022-09-18
 *
 * @author 원지윤
 * @version 1.0
 * [수정내용]
 * 예시) [2022-09-17] 주석추가 - 원지윤
 */
@Service
public class ResponseService {
    /**
     * 단일 데이터 response시 사용하는 함수
     * @param data 응답해줄 단일 데이터 값
     * @param status 요청에 대한 result code와 message를 담은 Enum
     * @param <T>
     * @return
     */
    public <T> SingleResponse<T> getSingleResponse(T data, Status status){
        SingleResponse<T> response = new SingleResponse<>(data,status);
        return response;
    }

    /**
     * 여러 데이터 response시 사용하는 함수
     * @param dataList 응답해줄 리스트 데이터
     * @param status 요청에 대한 result code와 message를 담은 Enum
     * @param <T>
     * @return
     */
    public<T>ListResponse<T> getListResponse(List<T> dataList, Status status){
        ListResponse<T> response = new ListResponse<>(dataList, status);
        return response;
    }

    /**
     * 무조건 성공 결과만 응답해주는 함수
     * @return
     */
    public CommonResponse getSuccessResponse(){
        CommonResponse response = new CommonResponse();
        setSuccessResponse(response);
        return response;
    }

    /**
     * 무조건 실패 결과만 응답해주는 함수
     * @return
     */
    public CommonResponse getFailResponse(){
        CommonResponse response = new CommonResponse();
        setFailResponse(response);
        return response;
    }

    /**
     * 성공에 대한 CommonResponse 도메인 설정해주는 함수
     * @param response
     */
    private void setSuccessResponse(CommonResponse response){
        response.setSuccess(true);
        response.setCode(Status.SUCCESS.getCode());
        response.setMessage(Status.SUCCESS.getMessage());
    }

    /**
     * 실패에 대한 CommonResponse 도메인 설정해주는 함수
     * @param response
     */
    private void setFailResponse(CommonResponse response){
        response.setSuccess(false);
        response.setCode(Status.FAIL.getCode());
        response.setMessage(Status.FAIL.getMessage());
    }
}
