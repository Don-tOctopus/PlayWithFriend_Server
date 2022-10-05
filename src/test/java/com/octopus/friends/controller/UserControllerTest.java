package com.octopus.friends.controller;

import com.octopus.friends.domain.User;
import com.octopus.friends.dto.request.user.CreateUserRequestDto;
import com.octopus.friends.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("사용자 생성 테스트")
    public void createUser(){
        //given
        final User user = User.builder()
                .email("test1@test.com")
                .userId("test")
                .userName("테스트씨")
                .password("1234")
                .birth("2022.10.05")
                .build();

        //when
        final User result = userRepository.save(user);

        //then
        assertThat(result.getEmail()).isEqualTo("test1@test.com");
        assertThat(result.getUserId()).isEqualTo("test");
        assertThat(result.getUserName()).isEqualTo("테스트씨");
        assertThat(result.getPassword()).isEqualTo("1234");
        assertThat(result.getBirth()).isEqualTo("2022.10.05");

    }

}