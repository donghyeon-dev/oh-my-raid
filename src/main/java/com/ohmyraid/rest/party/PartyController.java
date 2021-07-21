package com.ohmyraid.rest.party;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ohmyraid.common.wrapper.ResultView;
import com.ohmyraid.dto.party.PartyInpDto;
import com.ohmyraid.service.party.PartyService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/party")
@RequiredArgsConstructor
public class PartyController {

    private final PartyService partyService;

    @PostMapping(value = "")
    @ApiOperation(value = "파티 등록", notes = "레이드 파티 정보를 입력하고 파티를 등록합니다.")
    public ResultView<Boolean> insertParty(@RequestBody PartyInpDto partyInpDto) throws JsonProcessingException {
        return new ResultView<>(partyService.insertParty(partyInpDto));
    }


}
