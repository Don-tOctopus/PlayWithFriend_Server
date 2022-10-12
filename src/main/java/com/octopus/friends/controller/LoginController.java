package com.octopus.friends.controller;

import com.octopus.friends.common.domain.SingleResponse;
import com.octopus.friends.common.domain.enums.Status;
import com.octopus.friends.common.service.ResponseService;
import com.octopus.friends.domain.TokenInfo;
import com.octopus.friends.dto.request.login.LoginRequestDto;
import com.octopus.friends.dto.response.login.LoginResponseDto;
import com.octopus.friends.service.LoginService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * 패키지명 com.octopus.friends.controller
 * 클래스명 LoginController
 * 클래스설명 로그인컨트롤러
 * 작성일 2022-10-09
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
public class LoginController {
    private final LoginService loginService;
    private final ResponseService responseService;

    @PostMapping("/login")
    public ResponseEntity<SingleResponse<LoginResponseDto>> login(@RequestBody LoginRequestDto request){
        LoginResponseDto login = loginService.login(request);

        // 1. Response Header에 token 값을 넣어준다.
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + login.getTokenInfo().getAccessToken());

        // 2. Response Body에 token 값을 넣어준다.
        SingleResponse<LoginResponseDto> response = responseService.getSingleResponse(login, Status.SUCCESS);

        return ResponseEntity.ok().headers(httpHeaders).body(response);
    }
}
