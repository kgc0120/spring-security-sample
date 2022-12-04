package com.sample.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author bumblebee
 */
@Configuration
//@EnableWebSecurity 빼도 무방하다 spring security가 자동으로 생성
@Order(Ordered.LOWEST_PRECEDENCE - 150)
public class AnotherSecurityConfig extends WebSecurityConfigurerAdapter { // spring security web 설정파일이 생성된것


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .mvcMatchers("/account/**").permitAll()
                .mvcMatchers("/admin").hasRole("ADMIN")
           ;

    }
}
