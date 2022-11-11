package com.ohmyraid.rest.character;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ohmyraid.common.wrapper.ResultView;
import com.ohmyraid.dto.character.CharacterRaidInfoDto;
import com.ohmyraid.dto.wow_account.ActualCharacterDto;
import com.ohmyraid.service.character.CharacterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/character")
@Api(tags = "CharacterController", value = "캐릭터 관련 API")
@RequiredArgsConstructor
public class CharacterController {

    private final CharacterService characterService;


    @GetMapping(value = "/{accountId}/characters")
    @ApiOperation(value = "계정의 전체 캐릭터 간략보기", notes = "내 계정의 모든 캐릭터의 정보를 조회한다.")
    public ResultView<List<ActualCharacterDto>> getMyCharacter(@PathVariable long accountId) throws JsonProcessingException {
        return new ResultView<List<ActualCharacterDto>>(characterService.getMyCharacter(accountId));
    }

    @GetMapping("/{accountId}/raid-encounter")
    @ApiOperation(value = "캐릭터의 레이드 정보 가져오기", notes = "사용자 계정에 저장된 캐릭터들의 레이드 정보를 저장한다.")
    public ResultView<Boolean> getRaidEncounter(@PathVariable long accountId) throws Exception {
        return new ResultView<>(characterService.getRaidEncounter(accountId));
    }

    @GetMapping("/{accountId}/{characterId}/raid-info")
    @ApiOperation(value = "본인계정의 특정 캐릭터 레이드 정보 가져오기", notes = "선택왼 캐릭터의 레이드 정보를 반환한다.")
    public ResultView<List<CharacterRaidInfoDto>> getSpecificCharacterRaidInfo(@PathVariable long characterId, @PathVariable long accountId) throws Exception {
        return new ResultView<List<CharacterRaidInfoDto>>(characterService.getSpecificCharacterRaidInfo(characterId, accountId));
    }
}
