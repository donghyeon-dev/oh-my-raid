package com.ohmyraid.rest.character;

import com.ohmyraid.common.wrapper.ResultView;
import com.ohmyraid.dto.character.CharacterRaidInfoDto;
import com.ohmyraid.dto.character.CharacterRaidInfoRequest;
import com.ohmyraid.dto.character.CharacterSpecRequest;
import com.ohmyraid.service.character.CharacterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
    @ApiOperation(value = "특정 캐릭터의 레이드정보 가져오기", notes = "선택된 캐릭터의 필터링된 레이드 정보를 반환한다.")
    public ResultView<List<CharacterRaidInfoDto>> getSpecificCharacterRaidDetailInfo(//@PathVariable(name="characterId") Integer characterId,
            @ModelAttribute CharacterRaidInfoRequest characterRaidInfoRequest) {
        return new ResultView<List<CharacterRaidInfoDto>>(characterService.getSpecificCharacterRaidDetailInfo(characterRaidInfoRequest));
    }

    @PostMapping("/get-chracter-spec")
    @ApiOperation(value = "캐릭터 스펙정보 가져오기", notes = "블리자드 연동 여부와 상관없이 선택된 캐릭터의 스펙정보를 반환한다.")
    public ResultView<CharacterRaidInfoDto> getCharacterSpec(CharacterSpecRequest characterSpecRequest) {
        return new ResultView<CharacterRaidInfoDto>(characterService.getCharacterSpec(characterSpecRequest));
    }

}
