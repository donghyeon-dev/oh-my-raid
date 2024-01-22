package com.ohmyraid.service.party;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ohmyraid.common.result.CommonInvalidInputException;
import com.ohmyraid.common.result.CommonServiceException;
import com.ohmyraid.common.result.ErrorResult;
import com.ohmyraid.domain.party.PartyInfoEntity;
import com.ohmyraid.dto.login.UserSessionDto;
import com.ohmyraid.dto.party.PartyInfoDto;
import com.ohmyraid.mapper.PartyInfoMapper;
import com.ohmyraid.repository.user.UserRepository;
import com.ohmyraid.repository.character.CharacterRepository;
import com.ohmyraid.repository.party.PartyInfoRepository;
import com.ohmyraid.utils.RedisUtils;
import com.ohmyraid.utils.ThreadLocalUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PartyInfoService {

    private final PartyInfoRepository partyRepository;
    private final UserRepository userRepository;
    private final CharacterRepository characterRepository;

    private final RedisUtils redisUtils;

    /**
     * 파티 정보글을 등록한다.
     *
     * @param inpDto
     * @return
     */
    public long insertPartyInfo(PartyInfoDto inpDto) throws JsonProcessingException {

        String token = ThreadLocalUtils.getThreadInfo().getAccessToken();
        String email = redisUtils.getRedisValue(token, UserSessionDto.class).getEmail();

        // 엔티티 추가
        PartyInfoEntity partyInfoEntity = PartyInfoEntity.builder()
                .subject(inpDto.getSubject())
                .difficulty(inpDto.getDifficulty())
                .requiredMembers(inpDto.getRequiredMembers())
                .times(inpDto.getTimes())
                .instanceName(inpDto.getInstanceName())
                .memberCapacity(inpDto.getMemberCapacity())
                .contents(inpDto.getContents())
                .slug(inpDto.getSlug())
                .createUser(userRepository.findAllByEmail(email))
                .startAt(inpDto.getStartAt())
                .recruitUntil((inpDto.getRecruitUntil()))
                .build();
        return partyRepository.save(partyInfoEntity).getPartyId();


    }

    /**
     * 해당 계정의 전체 파티정보글을 가져온다.
     *
     * @param accountId
     * @return
     */
    public List<PartyInfoDto> getPartyInfoListByAccountId(long accountId) {
        List<PartyInfoEntity> partyInfoEntities = partyRepository.findPartyInfoEntitiesByCreateUser_UserId(accountId);

        return PartyInfoMapper.INSTANCE.entityListToDtoList(partyInfoEntities);
    }


    /**
     * 파티정보글을 수정한다
     * 작성자만 수정이 가능하다.
     *
     * @param partyInfoDto
     * @return
     */
    public long updatePartyInfo(PartyInfoDto partyInfoDto) throws JsonProcessingException {

        String token = ThreadLocalUtils.getThreadInfo().getAccessToken();
        long sessionAccountId = redisUtils.getRedisValue(token, UserSessionDto.class).getAccountId();

        //예외
        if (partyInfoDto.getCreateUserId() != sessionAccountId) {
            throw new CommonServiceException(ErrorResult.NO_URL_AUTH);
        }

        PartyInfoEntity partyInfoEntity = partyRepository.findPartyInfoEntityByPartyId(partyInfoDto.getPartyId());
        partyInfoEntity.update(partyInfoDto.getInstanceName(), partyInfoDto.getDifficulty(), partyInfoDto.getRequiredMembers(),
                partyInfoDto.getTimes(), partyInfoDto.getMemberCapacity(), partyInfoDto.getSubject(), partyInfoDto.getContents(),
                partyInfoDto.getSlug(), partyInfoDto.getStartAt(),
                partyInfoDto.getRecruitUntil());

        return partyRepository.save(partyInfoEntity).getPartyId();
    }

    /**
     * 특정 PartyId의 파티글을 반환한다.
     *
     * @param partyId
     * @return
     */
    public PartyInfoDto getPartyInfoByPartyId(long partyId) {
        if (ObjectUtils.isEmpty(partyId)) {
            throw new CommonInvalidInputException();
        }

        PartyInfoEntity partyInfoEntity = partyRepository.findPartyInfoEntityByPartyId(partyId);
        PartyInfoDto partyInfoDto = PartyInfoMapper.INSTANCE.entityToDto(partyInfoEntity);

        return partyInfoDto;
    }
}
