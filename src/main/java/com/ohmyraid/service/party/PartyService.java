//package com.ohmyraid.service.party;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.ohmyraid.dto.party.PartyInpDto;
//import com.ohmyraid.repository.account.AccountRepository;
//import com.ohmyraid.repository.character.CharacterRespository;
//import com.ohmyraid.utils.RedisUtils;
//import com.ohmyraid.utils.StringUtils;
//import com.ohmyraid.utils.ThreadLocalUtils;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//
//@Service
//@Slf4j
//@RequiredArgsConstructor
//public class PartyService {
//
//    private final PartyRepository partyRepository;
//
//    private final AccountRepository accountRepository;
//
//    private final CharacterRespository characterRespository;
//
//    private final RedisUtils redisUtils;
//
//    /**
//     * 파티 정보를 등록한다.
//     * @param inpDto
//     * @return
//     */
//    public Boolean insertParty(PartyInpDto inpDto) throws JsonProcessingException {
//
//        // 쓰레드로컬에서 토큰 가져오기
//        String token = ThreadLocalUtils.getThreadInfo().getAccessToken();
//        // 레디스를 통해 email 가져오기
//        String email = redisUtils.getSession(token).getEmail();
//
//        // 엔티티 추가
//        PartyEntity partyEntity = PartyEntity.builder()
//                .subject(inpDto.getSubject())
//                .difficulty(inpDto.getDifficulty())
//                .requiredMembers(inpDto.getRequiredMembers())
//                .times(inpDto.getTimes())
//                .memberCapacity(inpDto.getMemberCapacity())
//                .contents(inpDto.getContents())
//                .creater(accountRepository.findAllByEmail(email))
//                .startAt(StringUtils.stringToLocalDateTime(inpDto.getStartAt()))
//                .recruitUntil(StringUtils.stringToLocalDateTime(inpDto.getRecruitUntil()))
//                .build();
//        partyRepository.save(partyEntity);
//
//
//        return true;
//    }
//
//}
