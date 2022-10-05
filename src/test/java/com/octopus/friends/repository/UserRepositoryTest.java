package com.octopus.friends.repository;

import com.octopus.friends.FriendsApplication;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@DataJpaTest
@RunWith(SpringJUnit4ClassRunner.class)
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    
    @Test
    @DisplayName("userRepository null 체크")
    public void UserRepositoryCheckNull () throws Exception {
        assertThat(userRepository).isNotNull();
    }
    

}