package com.ohmyraid.service.login;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ohmyraid.dto.login.LoginInpDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

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
        LoginInpDto inpVo = new LoginInpDto();
        inpVo.setEmail("donghyeondev@gmail.com");
        inpVo.setPassword("test123");

        mockMvc.perform(post("/login")
                        .header("Origin", "*")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inpVo))
                )
                .andDo(print());
    }

    @Test
    void NoId__sholud_pass_with_error_result() throws Exception {
        LoginInpDto inpVo = new LoginInpDto();
        inpVo.setEmail("ddddddd@gmail.com");
        inpVo.setPassword("test123");

        mockMvc.perform(post("/login")
                        .header("Origin", "*")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inpVo))
                )
                .andDo(print());
    }
}
