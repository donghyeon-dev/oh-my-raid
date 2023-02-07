package com.ohmyraid.rest.login;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ohmyraid.dto.login.LoginInpDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class LoginControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void sholud_pass_with_status_200() throws Exception {
        LoginInpDto inpVo = new LoginInpDto();
        inpVo.setEmail("donghyeondev@gmail.com");
        inpVo.setPassword("qwe123123");

        mockMvc.perform(post("/login")
                .header("Origin", "http://localhost:8880")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inpVo))
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void InvalidPassword__should_pass_with_error_result() throws Exception {
        LoginInpDto inpVo = new LoginInpDto();
        inpVo.setEmail("donghyeondev@gmail.com");
        inpVo.setPassword("test123");

        mockMvc.perform(post("/login")
                .header("Origin", "http://localhost:8880")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inpVo))
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void NoId__sholud_pass_with_error_result() throws Exception {
        LoginInpDto inpVo = new LoginInpDto();
        inpVo.setEmail("ddddddd@gmail.com");
        inpVo.setPassword("test123");

        mockMvc.perform(post("/login")
                .header("Origin", "*", "X-AUTH")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inpVo))
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }
}