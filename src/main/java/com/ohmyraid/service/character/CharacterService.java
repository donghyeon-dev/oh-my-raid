package com.ohmyraid.service.character;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ohmyraid.domain.character.CharacterEntity;
import com.ohmyraid.domain.character.CharacterRaidInfEntity;
import com.ohmyraid.feign.RaiderClient;
import com.ohmyraid.repository.account.AccountRepository;
import com.ohmyraid.repository.character.CharacterRaidInfRespository;
import com.ohmyraid.repository.character.CharacterRespository;
import com.ohmyraid.utils.RedisUtils;
import com.ohmyraid.utils.StringUtils;
import com.ohmyraid.utils.ThreadLocalUtils;
import com.ohmyraid.dto.character.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CharacterService {

    private final CharacterRespository characterRespository;

    private final AccountRepository accountRepository;

    private final CharacterRaidInfRespository characterRaidInfRespository;

    private final RaiderClient raiderClient;

    private final RedisUtils redisUtils;

    private final ObjectMapper mapper;

    String gear = "gear";
    String mpScore = "mythic_plus_scores_by_season:current";
    String raidPrg = "raid_progression";

    /**
     * 캐릭터의 기본정보, 템렙, 쐐기, 레이드진행정보를 저장한다.
     * @param inpVo
     * @return
     */
    @Transactional
    public Boolean getCharacterInf(CharacterFeignInpDto inpVo) throws JsonProcessingException {

        // token 가져오기
        String token = ThreadLocalUtils.getThreadInfo().getAccessToken();
        String email = redisUtils.getSession(token).getEmail();


        CharacterEntity targetEntity = characterRespository.findByNameAndRealm(inpVo.getName(), inpVo.getRealm());
        if(ObjectUtils.isEmpty(targetEntity)) {

            // FeignClient를 통해 유저의 아이템레벨 정보를 가져온다,
            Map<String, Object> gearResult
                    = raiderClient.getCharacterGear(inpVo.getRegion(),
                    inpVo.getRealm(), inpVo.getName(), gear,
                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 11_2_3) Apple" +
                            "WebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.90 Safari/537.36");
            GearDto gearDto = mapper.convertValue(gearResult.get("gear"), GearDto.class);

            // FeignClient를 통해 유저의 RaidProgression 정보를 가져온다,
            Map<String, Object> raidResult = raiderClient.getCharacterRaidProgress(inpVo.getRegion(),
                    inpVo.getRealm(), inpVo.getName(), raidPrg,
                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 11_2_3) Apple" +
                            "WebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.90 Safari/537.36");
            Map<String, String> rp = mapper.convertValue(raidResult.get("raid_progression"), Map.class);
            RaidPrgDto raidPrgDto = mapper.convertValue(rp.get("castle-nathria"), RaidPrgDto.class);

            // FeignClient를 통해 유저의 M+ 정보를 가져온다,
            Map<String, Object> mythicPlusResult = raiderClient.getCharacterMythicScore(inpVo.getRegion(),
                    inpVo.getRealm(), inpVo.getName(), mpScore,
                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 11_2_3) Apple" +
                            "WebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.90 Safari/537.36");
            List<Map<String, String>> mp = mapper.convertValue(mythicPlusResult.get("mythic_plus_scores_by_season"), ArrayList.class);
            MpScoreDto mpScoreDto = mapper.convertValue(mp.get(0), MpScoreDto.class);
            log.debug("GearVo is {}", gearDto);
            log.debug("Raid Progression is {}", raidPrgDto);
            log.debug("MythicPlusScore is {}", mpScoreDto);

            LocalDateTime time = ZonedDateTime.parse(StringUtils.objectToString(gearResult.get("last_crawled_at"))).toLocalDateTime();
            time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            //가져온 정보를 DB에 인서트
            characterRespository.save(CharacterEntity.builder()
                    .name(StringUtils.objectToString(gearResult.get("name")))
                    .race(StringUtils.objectToString(gearResult.get("race")))
                    .classes(StringUtils.objectToString(gearResult.get("class")))
                    .activeSpecName(StringUtils.objectToString(gearResult.get("active_spec_name")))
                    .activeSpecRole(StringUtils.objectToString(gearResult.get("active_spec_role")))
                    .gender(StringUtils.objectToString(gearResult.get("gender")))
                    .faction(StringUtils.objectToString(gearResult.get("faction")))
                    .archievementPoints(Integer.parseInt(StringUtils.objectToString(gearResult.get("achievement_points"))))
                    .honorableKills(StringUtils.objectToString(gearResult.get("honorable_kills")))
                    .region(StringUtils.objectToString(gearResult.get("region")))
                    .realm(StringUtils.objectToString(gearResult.get("realm")))
                    .lastCrawledAt(time)
                    .itemLevelEquipped(gearDto.getItemLevelEquipped())
                    .mythicPlusScoreBySeason(mpScoreDto.getScores().getAll())
                    .accountEntity(accountRepository.findAllByEmail(email))
                    .build()
            );
            // 생성한 엔티티 가져와서 Raid Progression Entity에 사용
            CharacterEntity characterEntity = characterRespository.findByNameAndRealm(StringUtils.objectToString(gearResult.get("name")), StringUtils.objectToString(gearResult.get("realm")));
            characterRaidInfRespository.save(CharacterRaidInfEntity.builder()
                    .characterEntity(characterEntity)
                    .normalBossesKilled(raidPrgDto.getNormalBossesKilled())
                    .heroicBossesKilled(raidPrgDto.getHeroicBossesKilled())
                    .mythicBossesKilled(raidPrgDto.getMythicBossesKilled())
                    .totalBosses(raidPrgDto.getTotalBosses())
                    .raidTier("castle-nathria") // Todo 이게 하드코딩이 맞는걸까...?
                    .summary(raidPrgDto.getSummary())
                    .build()
            );
            return true;
        } else {

            // FeignClient를 통해 유저의 아이템레벨 정보를 가져온다,
            Map<String, Object> gearResult
                    = raiderClient.getCharacterGear(inpVo.getRegion(),
                    inpVo.getRealm(), inpVo.getName(), gear,
                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 11_2_3) Apple" +
                            "WebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.90 Safari/537.36");
            GearDto gearDto = mapper.convertValue(gearResult.get("gear"), GearDto.class);

            // FeignClient를 통해 유저의 RaidProgression 정보를 가져온다,
            Map<String, Object> raidResult = raiderClient.getCharacterRaidProgress(inpVo.getRegion(),
                    inpVo.getRealm(), inpVo.getName(), raidPrg,
                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 11_2_3) Apple" +
                            "WebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.90 Safari/537.36");
            Map<String, String> rp = mapper.convertValue(raidResult.get("raid_progression"), Map.class);
            RaidPrgDto raidPrgDto = mapper.convertValue(rp.get("castle-nathria"), RaidPrgDto.class);

            // FeignClient를 통해 유저의 M+ 정보를 가져온다,
            Map<String, Object> mythicPlusResult = raiderClient.getCharacterMythicScore(inpVo.getRegion(),
                    inpVo.getRealm(), inpVo.getName(), mpScore,
                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 11_2_3) Apple" +
                            "WebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.90 Safari/537.36");
            List<Map<String, String>> mp = mapper.convertValue(mythicPlusResult.get("mythic_plus_scores_by_season"), ArrayList.class);
            MpScoreDto mpScoreDto = mapper.convertValue(mp.get(0), MpScoreDto.class);
            log.debug("GearVo is {}", gearDto);
            log.debug("Raid Progression is {}", raidPrgDto);
            log.debug("MythicPlusScore is {}", mpScoreDto);

            // 날짜포맷변환
            LocalDateTime time = ZonedDateTime.parse(StringUtils.objectToString(gearResult.get("last_crawled_at"))).toLocalDateTime();
            time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            characterRespository.save(CharacterEntity.builder()
                    .characterId(targetEntity.getCharacterId())
                    .name(StringUtils.objectToString(gearResult.get("name")))
                    .race(StringUtils.objectToString(gearResult.get("race")))
                    .classes(StringUtils.objectToString(gearResult.get("class")))
                    .activeSpecName(StringUtils.objectToString(gearResult.get("active_spec_name")))
                    .activeSpecRole(StringUtils.objectToString(gearResult.get("active_spec_role")))
                    .gender(StringUtils.objectToString(gearResult.get("gender")))
                    .faction(StringUtils.objectToString(gearResult.get("faction")))
                    .archievementPoints(Integer.parseInt(StringUtils.objectToString(gearResult.get("achievement_points"))))
                    .honorableKills(StringUtils.objectToString(gearResult.get("honorable_kills")))
                    .region(StringUtils.objectToString(gearResult.get("region")))
                    .realm(StringUtils.objectToString(gearResult.get("realm")))
                    .lastCrawledAt(time)
                    .itemLevelEquipped(gearDto.getItemLevelEquipped())
                    .mythicPlusScoreBySeason(mpScoreDto.getScores().getAll())
                    .accountEntity(accountRepository.findAllByEmail(email))
                    .build()
            );
            // 생성한 엔티티 가져와서 Raid Progression Entity에 사용
            CharacterEntity characterEntity = characterRespository.findByNameAndRealm(StringUtils.objectToString(gearResult.get("name")), StringUtils.objectToString(gearResult.get("realm")));
            CharacterRaidInfEntity raidInfEntity = characterRaidInfRespository.findByCharacterEntity_CharacterId(characterEntity.getCharacterId());
            characterRaidInfRespository.save(CharacterRaidInfEntity.builder()
                    .raidId(raidInfEntity.getRaidId())
                    .characterEntity(characterEntity)
                    .normalBossesKilled(raidPrgDto.getNormalBossesKilled())
                    .heroicBossesKilled(raidPrgDto.getHeroicBossesKilled())
                    .mythicBossesKilled(raidPrgDto.getMythicBossesKilled())
                    .totalBosses(raidPrgDto.getTotalBosses())
                    .raidTier("castle-nathria") // Todo 이게 하드코딩이 맞는걸까...?
                    .summary(raidPrgDto.getSummary())
                    .build()
            );
            return false;
        }
    }
}
