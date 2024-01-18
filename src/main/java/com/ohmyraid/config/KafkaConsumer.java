package com.ohmyraid.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ohmyraid.common.enums.RetryMethodType;
import com.ohmyraid.dto.client.WowClientRequestDto;
import com.ohmyraid.dto.kafka.KafkaStoreData;
import com.ohmyraid.dto.wow_raid.RaidDetailDto;
import com.ohmyraid.dto.wow_raid.RaidInfoDto;
import com.ohmyraid.feign.WowClientWrapper;
import com.ohmyraid.service.character.CharacterService;
import io.github.bucket4j.Bucket;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.naming.LimitExceededException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
@Profile("dev")
public class KafkaConsumer {

    private final CharacterService characterService;
    private final WowClientWrapper wowClientWrapper;
    private final ObjectMapper objectMapper;
    private final Bucket tokenBucket;

    @KafkaListener(topics = Constant.Kafka.TOPIC, groupId = Constant.Kafka.GROUP_ID)
    public void flightEventConsumer(KafkaStoreData<WowClientRequestDto> kafkaStoreData) throws LimitExceededException {
        log.info("Consumer consumed kafka message -> {}", kafkaStoreData.toString());
        RetryMethodType methodType = RetryMethodType.getTypeByMethodName(kafkaStoreData.getMethodName());
        if(methodType == RetryMethodType.GET_ACCOUNT_CHRACTERS){
            log.info("Retry get account characters");
            // Todo Some business logic get account characters info from blizzard api and update database with parsed data.

        } else if(methodType == RetryMethodType.GET_CHARACTER){
            log.info("Retry get character");
            // Todo Some business logic get account spec info from blizzard api and update database with parsed data.

        } else if(methodType == RetryMethodType.GET_CHARACTER_RAID_ENCOUNTER){
            // Todo TokenBucket 체크하고 보내
            if(tokenBucket.getAvailableTokens() < 1){
                log.warn("Exceed limit of request to Blizzard API");
                throw new LimitExceededException("Consumer 내 재호출 과정에서 API호출 횟수가 초과되었습니다.");
            }
            log.info("Retry get character raid encounter");
            if(ObjectUtils.isEmpty(kafkaStoreData)){

                throw new NullPointerException("KafkaStoreData is null");
            }
            WowClientRequestDto wowClientRequestDto = (WowClientRequestDto) objectMapper.convertValue(kafkaStoreData.getTargetParameter(),
                    kafkaStoreData.getParameterTargetClass());

            List<RaidInfoDto> raidInfoDtoList = new ArrayList<>();
            raidInfoDtoList.add(wowClientWrapper.fetchRaidEncounter(wowClientRequestDto));
            raidInfoDtoList.forEach(raidInfoDto -> {
                raidInfoDto.setCharacterId(wowClientRequestDto.getCharacterId());
            });
            List<RaidDetailDto> raidDetailDtoList = characterService.parseCharacterRaidInfosToRaidDetails(raidInfoDtoList);
            characterService.insertRaidDetail(raidDetailDtoList);

        }
    }
}
