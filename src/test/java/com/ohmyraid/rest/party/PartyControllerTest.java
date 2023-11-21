package com.ohmyraid.rest.party;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ohmyraid.config.Constant;
import com.ohmyraid.dto.account.ThreadInfDto;
import com.ohmyraid.dto.login.UserSessionDto;
import com.ohmyraid.dto.party.PartyInfoDto;
import com.ohmyraid.utils.RedisUtils;
import com.ohmyraid.utils.ThreadLocalUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class PartyControllerTest {

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;


    @Test
    void insertPartyInfo() throws Exception {

        ThreadInfDto threadInfDto = new ThreadInfDto();
        // accessToken은 항상 수정해줘야함 => 뭔가 자동화를 시킬만한게 없을까?
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJvaG15cmFpZCIsImlhdCI6MTY3MzkxNzA5NiwiTG9naW5JZCI6ImRvbmdoeWVvbmRldkBnbWFpbC5jb20iLCJVc2VyTmFtZSI6ImF1dG9jYXQiLCJleHAiOjE2NzM5NTMwOTZ9.SSpdTQoxVtECwfC9vVfV2zkCAEZg7GC8tyLfihUH0z0";
        threadInfDto.setAccessToken(token);
        ThreadLocalUtils.add(Constant.THREAD_INF, threadInfDto);
        UserSessionDto userSessionDto = new UserSessionDto();
        userSessionDto.setEmail("donghyeondev@gmail.com");
        userSessionDto.setAccessToken(token);
        redisUtils.storeObjectToRedis(token, userSessionDto);

        PartyInfoDto partyInfoDto = new PartyInfoDto();
        partyInfoDto.setSubject("정공인원 모집 (목,금) 저녁 9시 3탐 진행");
        partyInfoDto.setDifficulty("MYTHIC");
        partyInfoDto.setMemberCapacity("확고");
        partyInfoDto.setInstanceName("헌신자의 금고");
        partyInfoDto.setRecruitUntil(LocalDateTime.of(2023, 2, 26, 13, 0, 0));
        partyInfoDto.setRequiredMembers(5);
        partyInfoDto.setStartAt(LocalDateTime.of(2023, 3, 1, 19, 0, 0));
        partyInfoDto.setTimes(5);
        partyInfoDto.setSlug("아즈샤라");
        partyInfoDto.setContents("신세기 구르비 공대 인원 모집 합니다.\n" +
                "\n" +
                "\n" +
                "현재 영웅 7넴 킬\n" +
                "목요일 영웅,  금요일 신화 트라이 로 진행예정\n" +
                "(일정파밍 후엔// 신화만 목,금 진행)\n" +
                "대신에 평일 여러번 영웅팟 막공 운영 예정입니다.\n" +
                "(단톡방 공지)\n" +
                "\n" +
                "\n" +
                "근딜 (풍운, 딜죽)\n" +
                "원딜 (법사,기원사)\n" +
                "힐러 (신기, 복술, 신사, 기원사) \n" +
                "\n" +
                "\n" +
                " 언제든 즐겁게 스트레스 안 받으시고 \n" +
                "목, 금 공대일정과 , 그외 막공일정\n" +
                "평일 쐐기 위주로 같이 즐기실분 모십니다~\n" +
                "\n" +
                "\n" +
                "저희는 라이트 합니다! \n" +
                "\n" +
                "\n" +
                " 간혹  참가 하지 못 하시더라도 ~ 괜찮습니다.\n" +
                "길드원, 공대원과 함께 즐기실 분 모십니다!!\n" +
                "\n" +
                "\n" +
                "*바다이야기 길드원도 모집합니다 ^^*");

        ResultActions resultActions = mockMvc.perform(post("/party")
                        .header("Origin", "http://localhost:8880")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(partyInfoDto))
                )
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}