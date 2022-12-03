package com.sample.security.form;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author bumblebee
 */
@SpringBootTest
@AutoConfigureMockMvc
class SampleControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void index_anonymous() throws Exception {
        mockMvc.perform(get("/").with(anonymous()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    void index_anonymous_annotation() throws Exception {
        mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void index_user() throws Exception {
        mockMvc.perform(get("/").with(user("bumblebee").roles("USER")))
                // bumblebee 유저가 이미 로그인한 상태일 때를 가정해서 테스트 ,db에 저장되어 있다가 아니다
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithUser // 메타 애노테이션
    void index_user_annotation() throws Exception {
        mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void admin_user() throws Exception {
        mockMvc.perform(get("/admin").with(user("bumblebee").roles("USER")))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "bumblebee", roles = "USER")
    void admin_user_annotation() throws Exception {
        mockMvc.perform(get("/admin"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    void admin_admin() throws Exception {
        mockMvc.perform(get("/admin").with(user("bumblebee").roles("ADMIN")))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "bumblebee", roles = "ADMIN")
    void admin_admin_annotation() throws Exception {
        mockMvc.perform(get("/admin"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void login() throws Exception{
        mockMvc.perform(formLogin().user("bumblebee").password("123"))
                .andExpect(authenticated());
    }

}