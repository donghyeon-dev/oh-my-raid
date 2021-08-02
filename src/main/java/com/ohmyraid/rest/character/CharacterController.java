package com.ohmyraid.rest.character;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ohmyraid.common.wrapper.ResultView;
import com.ohmyraid.service.character.CharacterService;
import com.ohmyraid.dto.character.CharacterFeignInpDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/character")
@Api(tags = "CharacterController", value = "캐릭터 관련 API")
@RequiredArgsConstructor
public class CharacterController {

    private final CharacterService characterService;

    @GetMapping(value = "/total-summary")
    @ApiOperation(value = "/profile/user/wow?",notes = "캐릭터의 요약 정보를 가져온다.")
    public ResultView<Boolean> getTotalSummary() throws JsonProcessingException {
        return new ResultView<>(characterService.getTotalSummary());
    }
}
