package com.ohmyraid.service.party;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ohmyraid.domain.party.PartyInfoEntity;
import com.ohmyraid.dto.party.PartyInpDto;
import com.ohmyraid.repository.account.AccountRepository;
import com.ohmyraid.repository.character.CharacterRespository;
import com.ohmyraid.repository.party.PartyInfoRepository;
import com.ohmyraid.utils.DatetimeUtils;
import com.ohmyraid.utils.RedisUtils;
import com.ohmyraid.utils.ThreadLocalUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PartyInfoService {

    private final PartyInfoRepository partyRepository;
    private final AccountRepository accountRepository;

    private final CharacterRespository characterRespository;

    private final DatetimeUtils datetimeUtils;

    private final RedisUtils redisUtils;

    /**
     * 파티 정보를 등록한다.
     *
     * @param inpDto
     * @return
     */
    public Boolean insertPartyInfo(PartyInpDto inpDto) throws JsonProcessingException {

        // 쓰레드로컬에서 토큰 가져오기
        String token = ThreadLocalUtils.getThreadInfo().getAccessToken();
        // 레디스를 통해 email 가져오기
        String email = redisUtils.getSession(token).getEmail();

        // 엔티티 추가
        PartyInfoEntity partyInfoEntity = PartyInfoEntity.builder()
                .subject(inpDto.getSubject())
                .difficulty(inpDto.getDifficulty())
                .requiredMembers(inpDto.getRequiredMembers())
                .times(inpDto.getTimes())
                .instanceName(inpDto.getInstanceName())
                .memberCapacity(inpDto.getMemberCapacity())
                .contents(inpDto.getContents())
                .createAccountId(accountRepository.findAllByEmail(email))
                .startAt(datetimeUtils.stringToLocalDateTime(inpDto.getStartAt()))
                .recruitUntil((datetimeUtils.stringToLocalDateTime(inpDto.getRecruitUntil())))
                .build();
        partyRepository.save(partyInfoEntity);


        return true;
    }

}
