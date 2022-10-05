package com.octopus.friends.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
/**
 * 패키지명 com.octopus.friends.config
 * 클래스명 WebSecurityConfig
 * 클래스설명 spring security 설정 파일
 * 작성일 2022-10-04
 * 
 * @author 원지윤
 * @version 1.0
 * [수정내용] 
 * 예시) [2022-09-17] 주석추가 - 원지윤
 * [2022-10-05] cors 처리 - 원지윤
 */

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Bean
    public BCryptPasswordEncoder encoderPassword() {
        return new BCryptPasswordEncoder();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //csfr 사용안함
        http.csrf().disable();

        http.cors().configurationSource(corsConfigurationSource());
        //URL 인증여부 설정.
        http.authorizeRequests()
                .antMatchers( "/user/signup", "/api/**", "/user/login", "/css/**").permitAll()
                //@Secured("ROLE_ADMIN")으로 대체   .antMatchers("/api/admin").hasRole("ADMIN")
                //stomp 권한 허용
                .antMatchers("/ws-stomp/**").permitAll()
                .anyRequest().authenticated();

        //로그인 관련 설정.
//        http.formLogin()
//                .loginPage("/user/login")
//                .loginProcessingUrl("/user/login") //Post 요청
//                .defaultSuccessUrl("/")
//                .failureUrl("/user/login?error")
//                .permitAll();
//
//        //로그아웃 설정
//        http.logout()
//                .logoutUrl("/user/logout")
//                .logoutSuccessUrl("/");

        //비인가자 요청시 보낼 Api URI
//        http.exceptionHandling().accessDeniedPage("/forbidden");
    }

    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.addAllowedOrigin("http://localhost:3000");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}