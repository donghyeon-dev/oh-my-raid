package com.ohmyraid.service.party;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ohmyraid.config.Constant;
import com.ohmyraid.dto.account.ThreadInfDto;
import com.ohmyraid.dto.login.RedisDto;
import com.ohmyraid.dto.party.PartyInpDto;
import com.ohmyraid.repository.party.PartyInfoRepository;
import com.ohmyraid.utils.DatetimeUtils;
import com.ohmyraid.utils.RedisUtils;
import com.ohmyraid.utils.ThreadLocalUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class PartyInfoServiceTest {

    @Autowired
    private PartyInfoService partyInfoService;

    @Autowired
    private PartyInfoRepository partyInfoRepository;

    @Autowired
    private DatetimeUtils datetimeUtils;

    @Autowired
    private RedisUtils redisUtils;

    @Test
    @Transactional
    void PartyInfo_Insert_테스트() throws JsonProcessingException {

        ThreadInfDto threadInfDto = new ThreadInfDto();
        // accessToken은 항상 수정해줘야함 => 뭔가 자동화를 시킬만한게 없을까?
        String token = "\"eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJvaG15cmFpZCIsImlhdCI6MTY3MzkxNzA5NiwiTG9naW5JZCI6ImRvbmdoeWVvbmRldkBnbWFpbC5jb20iLCJVc2VyTmFtZSI6ImF1dG9jYXQiLCJleHAiOjE2NzM5NTMwOTZ9.SSpdTQoxVtECwfC9vVfV2zkCAEZg7GC8tyLfihUH0z0\"";
        threadInfDto.setAccessToken(token);
        ThreadLocalUtils.add(Constant.THREAD_INF, threadInfDto);
        RedisDto redisDto = new RedisDto();
        redisDto.setEmail("donghyeondev@gmail.com");
        redisDto.setAccessToken(token);
        redisUtils.putSession(token, redisDto);

        PartyInpDto partyInpDto = new PartyInpDto();
        partyInpDto.setSubject("정공인원 모집 (목,금) 저녁 9시 3탐 진행");
        partyInpDto.setDifficulty("MYTHIC");
        partyInpDto.setMemberCapacity("확고");
        partyInpDto.setInstanceName("헌신자의 금고");
        partyInpDto.setRecruitUntil(("2023-02-26 13:00:00"));
        partyInpDto.setRequiredMembers(5);
        partyInpDto.setStartAt(("2023-03-01 19:00:00"));
        partyInpDto.setTimes(5);
        partyInpDto.setSlug("아즈샤라");
        partyInpDto.setContents("신세기 구르비 공대 인원 모집 합니다.\n" +
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

        partyInfoService.insertPartyInfo(partyInpDto);

        // partyInfoService.getPartyInfo(2);

    }

    @Test
    void getPartyInfoListByAccountId_Test() {
        List<PartyInpDto> partyInpDtoList = partyInfoService.getPartyInfoListByAccountId(1);
        System.out.println("partyInpDtoList => " + partyInpDtoList);
        assertEquals(partyInpDtoList.get(0).getSubject(), "구한다 제목ㅋ");
    }

}