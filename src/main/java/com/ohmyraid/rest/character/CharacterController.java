package com.ohmyraid.rest.character;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ohmyraid.common.wrapper.ResultView;
import com.ohmyraid.dto.character.CharacterRaidInfoDto;
import com.ohmyraid.dto.character.CharacterRaidInfoRequest;
import com.ohmyraid.dto.character.CharacterSpecRequest;
import com.ohmyraid.dto.wow_account.CharacterDto;
import com.ohmyraid.service.character.CharacterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/characters")
@Api(tags = "CharacterController", value = "캐릭터 관련 API")
@RequiredArgsConstructor
public class CharacterController {

    private final CharacterService characterService;

    @GetMapping("/{characterId}/raids")
    @ApiOperation(value = "특정 캐릭터의 레이드정보 조회", notes = "캐릭터의 저장된 레이드 정보를 반환한다.")
    public ResultView<List<CharacterRaidInfoDto>> getSpecificCharacterRaidDetailInfo(
            @ModelAttribute CharacterRaidInfoRequest characterRaidInfoRequest) {
        return new ResultView<List<CharacterRaidInfoDto>>(characterService.getSpecificCharacterRaidDetailInfo(characterRaidInfoRequest));
    }

    @GetMapping("/get-chracter")
    @ApiOperation(value = "캐릭터 요약정보 조회", notes = "선택된 캐릭터의 요약정보를 반환한다.")
    public ResultView<CharacterDto> getCharacterProfile(CharacterSpecRequest characterSpecRequest) throws JsonProcessingException {
        return new ResultView<CharacterDto>(characterService.getCharacterProfile(characterSpecRequest));
    }



}
