package com.octopus.friends.service;

import com.octopus.friends.common.domain.enums.Status;
import com.octopus.friends.common.exception.CustomerNotFoundException;
import com.octopus.friends.domain.User;
import com.octopus.friends.dto.request.user.CreateUserRequestDto;
import com.octopus.friends.dto.response.user.CreateUserResponseDto;
import com.octopus.friends.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 패키지명 com.octopus.friends.service
 * 클래스명 UserService
 * 클래스설명
 * 작성일 2022-09-17
 *
 * @author 원지윤
 * @version 1.0
 * [수정내용]
 * 예시) [2022-09-17] 주석추가 - 원지윤
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public CreateUserResponseDto save(CreateUserRequestDto request){

        Optional<User> findUser = userRepository.findByEmail(request.getEmail());

        if(findUser.isPresent()){
            throw new CustomerNotFoundException(Status.DUPLIECATED_USER);
        }

        String password = request.getPassword();
        request.setPassword(passwordEncoder.encode(password));

        User user = userRepository.save(request.toEntity());
        CreateUserResponseDto response = CreateUserResponseDto.of(user);

        return response;
    }



}
