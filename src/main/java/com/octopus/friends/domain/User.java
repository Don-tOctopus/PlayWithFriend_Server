package com.octopus.friends.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 패키지명 com.octopus.friends.domain
 * 클래스명 User
 * 클래스설명 사용자 엔티티 클래스이다.
 * 작성일 2022-09-12
 *
 * @author 원지윤
 * @version 1.0
 * [수정내용]
 * 예시) [2022-09-17] 주석추가 - 원지윤
 * [2022-09-17] 내용 추가 - 원지윤
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User{
    @Schema(description = "사용자의 이메일")
    @Id
    @Column(nullable = false,columnDefinition = "varchar(30)")
    private String email;

    @Schema(description = "사용자의 아이디")
    @Column(nullable = false,columnDefinition = "varchar(20)")
    private String userId;


    @Schema(description = "사용자의 이름")
    @Column(nullable = false, columnDefinition = "varchar(20)")
    private String userName;

    @Schema(description = "사용자의 비밀번호")
    @Column(nullable = false, columnDefinition = "varchar(20)")
    private String password;

    @Schema(description = "사용자의 생년월일")
    @Column(nullable = false, columnDefinition = "varchar(10)")
    private String birth;

    @Schema(description = "사용자의 현재 상태(가입 true,탈퇴 false)")
    @Column(nullable = false, columnDefinition = "boolean default 'true'")
    private boolean status;

    @Schema(description = "사용자의 웹 버전")
    @Column(nullable = false, columnDefinition = "varchar(6)")
    private String version;

    @Schema(description = "사용자의 계정 최초 생성일자")
    @CreatedDate
    private LocalDateTime createdAt;

    @Schema(description = "사용자의 계정 마지막 수정일자")
    @LastModifiedDate
    private LocalDateTime updatedAt;
    
    @OneToMany(mappedBy = "user")
    private List<ChatRoomRelation> chatRoomRelationList = new ArrayList<>();

    /**
     * User엔티티의 builder
     * @param email user의 이메일
     * @param userId user의 아이디
     * @param userName uesr의 이름
     * @param password user의 비밀번호
     * @param birth user의 생년월일
     */
    @Builder
    public User(String email, String userId, String userName, String password,String birth){
        this.email = email;
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.birth = birth;
        this.status = true;
        this.version = "0.0.1";
    }
}
