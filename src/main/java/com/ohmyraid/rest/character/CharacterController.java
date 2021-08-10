package com.ohmyraid.rest.character;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ohmyraid.common.wrapper.ResultView;
import com.ohmyraid.dto.wow_account.ActualCharacterDto;
import com.ohmyraid.service.character.CharacterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/character")
@Api(tags = "CharacterController", value = "캐릭터 관련 API")
@RequiredArgsConstructor
public class CharacterController {

    private final CharacterService characterService;

    @GetMapping(value = "/total-summary")
    @ApiOperation(value = "/profile/user/wow?", notes = "계정의 캐릭터 정보를 동기화한다.")
    public ResultView<Boolean> getTotalSummary() throws JsonProcessingException, InterruptedException {
        return new ResultView<>(characterService.getTotalSummary());
    }

    @GetMapping(value = "/my-character")
    @ApiOperation(value = "모든 내 캐릭터 보기", notes = "내 계정의 모든 캐릭터를 조회한다.")
    public ResultView<List<ActualCharacterDto>> getMyCharacter() throws JsonProcessingException {
        return new ResultView<List<ActualCharacterDto>>(characterService.getMyCharacter());
    }
}
