package com.ohmyraid.rest.character;

import com.ohmyraid.common.wrapper.ResultView;
import com.ohmyraid.dto.character.CharacterRaidInfoDto;
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


    @GetMapping("/{accountId}/{characterId}/raid-info")
    @ApiOperation(value = "본인계정의 특정 캐릭터 레이드 정보 가져오기", notes = "선택왼 캐릭터의 레이드 정보를 반환한다.")
    public ResultView<List<CharacterRaidInfoDto>> getSpecificCharacterRaidInfo(@PathVariable long characterId, @PathVariable long accountId) throws Exception {
        return new ResultView<List<CharacterRaidInfoDto>>(characterService.getSpecificCharacterRaidInfo(characterId, accountId));
    }
}
