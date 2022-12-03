package com.sample.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author bumblebee
 */
@Configuration
@EnableWebSecurity
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
