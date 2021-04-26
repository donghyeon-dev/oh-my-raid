package com.ohmyraid.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ohmyraid.vo.login.LoginInpVo;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;



@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LoginServiceTest {

    private static final Logger log = LoggerFactory.getLogger(LoginServiceTest.class);

    @Autowired
     MockMvc mockMvc;

    @Autowired
     ObjectMapper objectMapper;

    @Test
    void InvalidPassword__sholud_pass_with_error_result() throws Exception {
        LoginInpVo inpVo = new LoginInpVo();
        inpVo.setEmail("donghyeondev@gmail.com");
        inpVo.setPassword("test123");

        mockMvc.perform(post("/login")
                .header("Origin","*")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inpVo))
                )
                .andDo(print());
    }

    @Test
    void NoId__sholud_pass_with_error_result() throws Exception {
        LoginInpVo inpVo = new LoginInpVo();
        inpVo.setEmail("ddddddd@gmail.com");
        inpVo.setPassword("test123");

        mockMvc.perform(post("/login")
                .header("Origin","*")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inpVo))
        )
                .andDo(print());
    }
}
