package com.ohmyraid.config;

import com.ohmyraid.common.enums.RetryMethodType;
import com.ohmyraid.dto.client.WowClientRequestDto;
import com.ohmyraid.dto.kafka.KafkaStoreData;
import com.ohmyraid.dto.wow_raid.RaidDetailDto;
import com.ohmyraid.dto.wow_raid.RaidInfoDto;
import com.ohmyraid.feign.WowClientWrapper;
import com.ohmyraid.service.character.CharacterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaConsumer {

    private final CharacterService characterService;
    private final WowClientWrapper wowClientWrapper;

    @KafkaListener(topics = Constant.Kafka.TOPIC, groupId = Constant.Kafka.GROUP_ID)
    public void flightEventConsumer(KafkaStoreData<WowClientRequestDto> kafkaStoreData) {
        log.info("Consumer consumed kafka message -> {}", kafkaStoreData.toString());
        RetryMethodType methodType = RetryMethodType.getTypeByMethodName(kafkaStoreData.getMethodName());
        if(methodType == RetryMethodType.GET_ACCOUNT_CHRACTERS){
            log.info("Retry get account characters");
            // Todo Some business logic get account characters info from blizzard api and update database with parsed data.

        } else if(methodType == RetryMethodType.GET_CHARACTER){
            log.info("Retry get character");
            // Todo Some business logic get account spec info from blizzard api and update database with parsed data.

        } else if(methodType == RetryMethodType.GET_CHARACTER_RAID_ENCOUNTER){
            log.info("Retry get character raid encounter");
            // Todo Some business logic getcharacter raid info from blizzard api and update database with parsed data.
            if(ObjectUtils.isEmpty(kafkaStoreData)){

                throw new NullPointerException("KafkaStoreData is null");
            }
            List<RaidInfoDto> raidInfoDtoList = new ArrayList<>();
            raidInfoDtoList.add(wowClientWrapper.getRaidEncounter(kafkaStoreData.getTargetParameter()));
            List<RaidDetailDto> raidDetailDtoList = characterService.parseCharacterRaidInfosToRaidDetails(raidInfoDtoList);
            characterService.insertRaidDetail(raidDetailDtoList);

        }
        log.info("Some business logic consum kafka messages");
    }
}
