package com.ohmyraid.rest.party;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ohmyraid.common.wrapper.ResultView;
import com.ohmyraid.dto.party.PartyInpDto;
import com.ohmyraid.service.party.PartyInfoService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/party")
@RequiredArgsConstructor
public class PartyController {

    private final PartyInfoService partyInfoService;

    @PostMapping(value = "")
    @ApiOperation(value = "파티글 등록", notes = "레이드 파티 정보를 입력하고 파티를 등록합니다.")
    public ResultView<Long> insertPartyInfo(@RequestBody PartyInpDto partyInpDto) throws JsonProcessingException {
        return new ResultView<>(partyInfoService.insertPartyInfo(partyInpDto));
    }

    @GetMapping(value = "/{accountId}")
    @ApiOperation(value = "작성자의 모든 파티글 가져오기", notes = "작성자의 모든 파티 게시글을 가져옵니다.")
    public ResultView<List<PartyInpDto>> getPartyInfoListByAccountId(@PathVariable long accountId) {
        return new ResultView<List<PartyInpDto>>(partyInfoService.getPartyInfoListByAccountId(accountId));
    }

}
