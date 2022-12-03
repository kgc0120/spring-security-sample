package com.sample.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class SecurityApplication {

    // 비밀번호는 반드시 인코딩해서 저장
    // 단방향 알고리즘
    // spring에서 제공하는 PasswordEncoder는 특정한 포맷으로 동작함

    @Bean
    public PasswordEncoder passwordEncoder() {
//        return NoOpPasswordEncoder.getInstance(); // 비추 : 비밀번호가 평문 그대로 저장된다.
        return PasswordEncoderFactories.createDelegatingPasswordEncoder(); //권장
    }

    public static void main(String[] args) {
        SpringApplication.run(SecurityApplication.class, args);
    }

}
