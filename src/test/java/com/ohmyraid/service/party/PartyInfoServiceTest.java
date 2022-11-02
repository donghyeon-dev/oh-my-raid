package com.ohmyraid.service.party;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ohmyraid.dto.party.PartyInpDto;
import com.ohmyraid.repository.party.PartyInfoRepository;
import com.ohmyraid.utils.DatetimeUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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

    @Test
    void PartInfo_Insert_테스트() throws JsonProcessingException {
        PartyInpDto partyInpDto = new PartyInpDto();
        partyInpDto.setSubject("용군단 아얼 미나리 길드 정규 공격대 인원 모집합니다!");
        partyInpDto.setDifficulty("MYTHIC");
        partyInpDto.setMemberCapacity("확고");
        partyInpDto.setRecruitUntil(datetimeUtils.stringToLocalDateTime("2022-11-02 13:00:00"));
        partyInpDto.setRequiredMembers(5);
        partyInpDto.setStartAt(datetimeUtils.stringToLocalDateTime("2022-11-27 19:00:00"));
        partyInpDto.setTimes(5);
        partyInpDto.setContents("안녕하세요!\n" +
                "아즈샤라 얼라에서 기사/죽기 하고 있는 나리라구/나리님 입니다.\n" +
                "용군단 대비 공대원 모집합니다.\n" +
                "\n" +
                "■ 레이드 일정 : 일 월 20:00~00:00 4탐 /  연장 따윈 일체 없음. / 공휴일 무조건 챙김.\n" +
                "템룰- 골팟 올분\n" +
                "\n" +
                "■ 구인 클래스\n" +
                "\n" +
                "원딜 - 조드 흑마 암사\n" +
                "힐러 - 복술\n" +
                "\n" +
                "■ 길드 기반 최정예 노리는 공대입니다. 따라서 길드 가입이 필수입니다.\n" +
                "\n" +
                "■ 정말 화목하고 실력있는 공장님 계셔서 공대 분위기 너무 좋습니다.\n" +
                " \n" +
                "■ 아즈샤라 얼라이언스에서 재미있게 하실분들 환영합니다. 길드 차원에서 영약/음식/반투스등 모든 것 다 지원하고 물약, 룬만 챙기시면 됩니다.\n" +
                "\n" +
                "■ 실수적고 1인분 이상 가능 하신분 이시면 좋겠습니다.\n" +
                " \n" +
                "■ 문의는 인게임 우편이나 인벤 우편으로 해주시고 본캐닉/지원캐릭터 닉 적어주시면 감사하겠습니다. 부담없이 지원해주세요!\n" +
                "\n" +
                "차후 소비용품 가격이 안정화되면 길드차원에서 각종 소비용품 지원 가능합니다. (기름, 마부, 보석 등등) " +
                "디코, 인스타 디엠도 가능합니다\n" +
                "디코 : 나리#0369\n" +
                "인스타 : nari_twitch\n" +
                "\n" +
                "많은 지원 부탁드릴게요! \uD83D\uDE0A");

//        PartyInfoMapperImpl mapper = new PartyInfoMapperImpl();
//        PartyInfoEntity partyInfoEntity = mapper.partInfoDtoToEntity(partyInpDto);
        partyInfoService.insertPartyInfo(partyInpDto);

    }


}