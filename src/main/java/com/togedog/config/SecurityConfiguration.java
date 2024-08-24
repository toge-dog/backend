package com.togedog.config;

import com.togedog.auth.filter.JwtAuthenticationFilter;
import com.togedog.auth.filter.JwtVerificationFilter;
import com.togedog.auth.handler.*;
import com.togedog.auth.jwt.JwtTokenizer;
import com.togedog.auth.utils.CustomAuthorityUtils;
import com.togedog.member.repository.MemberRepository;
import com.togedog.member.service.MemberService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * authenticationEntryPoint와 accessDeniedHandler 추가
 */
@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfiguration {
    @Value("${spring.security.oauth2.client.registration.google.clientId}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.google.clientSecret}")
    private String clientSecret;

    private final JwtTokenizer jwtTokenizer;
    private final CustomAuthorityUtils authorityUtils; // 추가
    private final MemberRepository memberRepository; // 추가
    private final RedisTemplate<String, Object> redisTemplate; // 추가

    public SecurityConfiguration(JwtTokenizer jwtTokenizer,
                                 CustomAuthorityUtils authorityUtils, MemberRepository memberRepository, RedisTemplate<String, Object> redisTemplate) {
        this.jwtTokenizer = jwtTokenizer;
        this.authorityUtils = authorityUtils;
        this.memberRepository = memberRepository;
        this.redisTemplate = redisTemplate;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .headers().frameOptions().sameOrigin() //H2를 사용하기 위한 추가 문
                .and()
                .csrf().disable()        // 로컬에서 확인 시 사용 필요.
                .cors(withDefaults())    // corsConfigurationSource 사용
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin().disable()
                .httpBasic().disable()
                .exceptionHandling()
                .authenticationEntryPoint(new MemberAuthenticationEntryPoint())  // 추가
                .accessDeniedHandler(new MemberAccessDeniedHandler())            // 추가
                .and()
                .apply(new CustomFilterConfigurer())   // (1)
                .and()
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().permitAll()
                )
                .oauth2Login(oauth2 -> oauth2
                        .successHandler(new Oauth2MemberSuccessHandler(jwtTokenizer, authorityUtils, memberRepository)));
        return http.build();
    }
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//            .headers().frameOptions().sameOrigin()
//            .and()
//            .csrf().disable()
//            .cors(withDefaults())
//            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//            .and()
//            .formLogin().disable()
//            .httpBasic().disable()
//            .exceptionHandling()
//            .authenticationEntryPoint(new MemberAuthenticationEntryPoint())  // 추가
//            .accessDeniedHandler(new MemberAccessDeniedHandler())            // 추가
//            .and()
//            .apply(new CustomFilterConfigurer())
//            .and()
//            .authorizeHttpRequests(authorize -> authorize
//                    .antMatchers(HttpMethod.POST, "/*/members").permitAll()
//                    .antMatchers(HttpMethod.PATCH, "/*/members/**").hasRole("USER")
//                    .antMatchers(HttpMethod.GET, "/*/members").hasRole("ADMIN")
////                    .mvcMatchers(HttpMethod.GET, "/*/members").hasRole("ADMIN")
//                    .antMatchers(HttpMethod.GET, "/*/members/**").hasAnyRole("USER", "ADMIN")
//                    .antMatchers(HttpMethod.DELETE, "/*/members/**").hasRole("USER")
//                    .antMatchers(HttpMethod.POST, "/*/coffees").hasRole("ADMIN")
//                    .antMatchers(HttpMethod.PATCH, "/*/coffees/**").hasRole("ADMIN")
//                    .antMatchers(HttpMethod.GET, "/*/coffees/**").hasAnyRole("USER", "ADMIN")
//                    .antMatchers(HttpMethod.GET, "/*/coffees").permitAll()
//                    .antMatchers(HttpMethod.DELETE, "/*/coffees").hasRole("ADMIN")
//                    .antMatchers(HttpMethod.POST, "/*/orders").hasRole("USER")
//                    .antMatchers(HttpMethod.PATCH, "/*/orders").hasAnyRole("USER", "ADMIN")
//                    .antMatchers(HttpMethod.GET, "/*/orders/**").hasAnyRole("USER", "ADMIN")
//                    .antMatchers(HttpMethod.DELETE, "/*/orders").hasRole("USER")
//                    .anyRequest().permitAll()
//            );
//        return http.build();
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Collections.singletonList("*"));  // 모든 오리진 허용
        configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));  // 허용되는 HTTP 메서드
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "X-Requested-With", "Accept"));  // 허용되는 헤더
        configuration.setExposedHeaders(Arrays.asList("Authorization", "memberId", "Location"));  // 노출할 헤더 추가
        configuration.setAllowCredentials(true);  // 인증 관련 정보를 허용

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


    public class CustomFilterConfigurer extends AbstractHttpConfigurer<CustomFilterConfigurer, HttpSecurity> {
        @Override
        public void configure(HttpSecurity builder) throws Exception {
            AuthenticationManager authenticationManager =
                    builder.getSharedObject(AuthenticationManager.class);

            JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager, jwtTokenizer);
            jwtAuthenticationFilter.setFilterProcessesUrl("/login");
            jwtAuthenticationFilter.setAuthenticationSuccessHandler(new MemberAuthenticationSuccessHandler(memberRepository));
            jwtAuthenticationFilter.setAuthenticationFailureHandler(new MemberAuthenticationFailureHandler());

            JwtVerificationFilter jwtVerificationFilter = new JwtVerificationFilter(jwtTokenizer, authorityUtils, redisTemplate); // 추가


            builder
                    .addFilter(jwtAuthenticationFilter)
                    .addFilterAfter(jwtVerificationFilter, JwtAuthenticationFilter.class)
                    .addFilterAfter(jwtVerificationFilter, OAuth2LoginAuthenticationFilter.class);

        }

    }
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class).build();
    }

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        var clientRegistration = clientRegistration();

        return new InMemoryClientRegistrationRepository(clientRegistration);
    }

    private ClientRegistration clientRegistration() {
        return CommonOAuth2Provider
                .GOOGLE
                .getBuilder("google")
                .clientId(clientId)
                .clientSecret(clientSecret)
                .build();
    }
}
