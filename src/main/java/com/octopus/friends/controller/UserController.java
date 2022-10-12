package com.octopus.friends.controller;

import com.octopus.friends.common.domain.SingleResponse;
import com.octopus.friends.common.domain.enums.Status;
import com.octopus.friends.common.service.ResponseService;
import com.octopus.friends.dto.request.user.CreateUserRequestDto;
import com.octopus.friends.dto.response.user.CreateUserResponseDto;
import com.octopus.friends.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 패키지명 com.octopus.friends.controller
 * 클래스명 UserController
 * 클래스설명
 * 작성일 2022-09-12
 * 
 * @author 원지윤
 * @version 1.0
 * [수정내용] 
 * 예시) [2022-09-17] 주석추가 - 원지윤
 */
@Slf4j
@Tag(name = "user", description = "사용자 관리 관련 API")
@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final ResponseService responseService;

    /**
     * 새로운 사용자를 생성
     * @param request 새로운 사용자를 생성하기 위한 정보
     * @return 생성된 사용자의 정보와 응답 상태
     */
    @PostMapping("/signup")
    public ResponseEntity<SingleResponse<CreateUserResponseDto>> createUser(@RequestBody CreateUserRequestDto request){
        CreateUserResponseDto user = userService.save(request);
        SingleResponse<CreateUserResponseDto> response =
                responseService.getSingleResponse(user, Status.SUCCESS_CREATED_USER);
        return ResponseEntity.ok().body(response);
    }
}
