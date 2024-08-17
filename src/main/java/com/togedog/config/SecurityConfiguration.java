package com.togedog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfiguration {

    /**
    * filterChain 옵션 설정
    *
    * @param  보안 설정 구성요소가 담긴 HttpSecurity 타입 객체
    * @return 구성된 SecurityFilterChain 객체
    * @throws 접근 허가가 없는 사용자가 접근 할 때 예외 발생
    * @author Tizesin(신민준)
    */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .headers().frameOptions().sameOrigin() //H2를 사용하기 위한 추가 문
                .and()
                .csrf().disable()        // 로컬에서 확인 시 사용 필요.
                .cors(withDefaults())    // (3) corsConfigurationSource 사용
                .formLogin().disable()   // (4)
                .httpBasic().disable()   // (5)
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().permitAll()                // (6)
                );
        return http.build();
    }

    /**
    * PasswordEncoder를 Spring  Security 설정에서 사용하지 위해 Bean 정의
    *
    * @author Tizesin(신민준)
    */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    /**
    * filterChain의 CORS 설정 값 정의
    *
    * @param source JSON 형식의 문자열
    * @return 반환값 내용
    * @throws 예외처리
    * @author Tizesin(신민준)
    */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));   // (8-1)
        configuration.setAllowedMethods(Arrays.asList("GET","POST", "PATCH", "DELETE"));  // (8-2)

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();   // (8-3)
        source.registerCorsConfiguration("/**", configuration);      // (8-4)
        return source;
    }
}

