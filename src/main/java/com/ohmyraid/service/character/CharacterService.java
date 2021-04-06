package com.ohmyraid.service.character;

import com.ohmyraid.common.wrapper.ResultView;
import com.ohmyraid.domain.account.AccountEntity;
import com.ohmyraid.domain.character.CharacterEntity;
import com.ohmyraid.feign.RaiderClient;
import com.ohmyraid.repository.character.CharacterRespository;
import com.ohmyraid.utils.CryptoUtils;
import com.ohmyraid.vo.character.CharacterFeignInpVo;
import com.ohmyraid.vo.character.CharacterVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class CharacterService {

    @Autowired
    CharacterRespository characterRespository;

    @Autowired
    RaiderClient raiderClient;

    public Map<String,String> getCharacterInf(CharacterFeignInpVo inpVo){
        // FeignClient를 통해 유저의 정보를 가져온다,
        Map<String,String> result = raiderClient.getCharacterInf(inpVo.getRegion(),
                inpVo.getRealm(), inpVo.getName(), inpVo.getFields(),
                "Mozilla/5.0 (Macintosh; Intel Mac OS X 11_2_3) Appl" +
                        "WebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.90 Safari/537.36");

        //가져온 정보를 DB에 인서트
        characterRespository.save(CharacterEntity.builder()
                .name(result.get("name"))
                .race(result.get("race"))
                .classes(result.get("class"))
                .activeSpecName(result.get("activeSpecName"))
                .activeSpecRole(result.get("activeSpecRole"))
                .gender(result.get("gender"))
                .faction(result.get("faction"))
                .archievementPoints(Integer.parseInt(result.get("archievementPoints")))
                .honorableKills(result.get("honorableKills"))
                .region(result.get("region"))
                .realm(result.get("realm"))
                .lastCrawledAt(LocalDateTime.parse(result.get("lastCrawledAt")))
                .build()
                );




        return result;

    }
}
