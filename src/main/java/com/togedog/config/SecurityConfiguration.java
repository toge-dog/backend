package com.togedog.config;

import com.togedog.auth.filter.JwtAuthenticationFilter;
import com.togedog.auth.filter.JwtVerificationFilter;
import com.togedog.auth.handler.MemberAuthenticationFailureHandler;
import com.togedog.auth.handler.MemberAuthenticationSuccessHandler;
import com.togedog.auth.jwt.JwtTokenizer;
import com.togedog.auth.utils.CustomAuthorityUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * authenticationEntryPoint와 accessDeniedHandler 추가
 */
@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfiguration {
    private final JwtTokenizer jwtTokenizer;
    private final CustomAuthorityUtils authorityUtils; // 추가

    public SecurityConfiguration(JwtTokenizer jwtTokenizer,
                                 CustomAuthorityUtils authorityUtils) {
        this.jwtTokenizer = jwtTokenizer;
        this.authorityUtils = authorityUtils;
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
//                .authenticationEntryPoint(new MemberAuthenticationEntryPoint())  // 추가
//                .accessDeniedHandler(new MemberAccessDeniedHandler())            // 추가
                .and()
                .apply(new CustomFilterConfigurer())   // (1)
                .and()
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().permitAll()
                );
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
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST", "PATCH", "DELETE"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


    public class CustomFilterConfigurer extends AbstractHttpConfigurer<CustomFilterConfigurer, HttpSecurity> {
        @Override
        public void configure(HttpSecurity builder) throws Exception {
            AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);

            JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager, jwtTokenizer);
            jwtAuthenticationFilter.setFilterProcessesUrl("/login");
            jwtAuthenticationFilter.setAuthenticationSuccessHandler(new MemberAuthenticationSuccessHandler());
            jwtAuthenticationFilter.setAuthenticationFailureHandler(new MemberAuthenticationFailureHandler());

            JwtVerificationFilter jwtVerificationFilter = new JwtVerificationFilter(jwtTokenizer, authorityUtils);


            builder
                    .addFilter(jwtAuthenticationFilter)
                    .addFilterAfter(jwtVerificationFilter, JwtAuthenticationFilter.class);
        }
    }
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class).build();
    }
}
