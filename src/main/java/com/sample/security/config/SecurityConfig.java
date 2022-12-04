package com.sample.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author bumblebee
 */
@Configuration
//@EnableWebSecurity 빼도 무방하다 spring security가 자동으로 생성
@Order(Ordered.LOWEST_PRECEDENCE - 100) // filter가 여러개 등록될때 순서를 정한다. 낮을수록 더 먼저 실행
public class SecurityConfig extends WebSecurityConfigurerAdapter { // spring security web 설정파일이 생성된것


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .mvcMatchers("/", "/info", "/account/**").permitAll()
                .mvcMatchers("/admin").hasRole("ADMIN")
                .anyRequest().authenticated() // 기타등등
                .and()
            .formLogin()
                .and()
            .httpBasic();

    }
}
