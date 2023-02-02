package com.ohmyraid.rest.party;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ohmyraid.common.wrapper.ResultView;
import com.ohmyraid.dto.party.PartyInfoDto;
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
    public ResultView<Long> insertPartyInfo(@RequestBody PartyInfoDto partyInfoDto) throws JsonProcessingException {
        return new ResultView<>(partyInfoService.insertPartyInfo(partyInfoDto));
    }

    @PutMapping(value = "")
    @ApiOperation(value = "파티글 수정", notes = "레이드 파티 정보수정합니다.")
    public ResultView<Long> updatePartyInfo(@RequestBody PartyInfoDto partyInfoDto) throws JsonProcessingException {
        return new ResultView<>(partyInfoService.updatePartyInfo(partyInfoDto));
    }

    @GetMapping(value = "/list/{accountId}")
    @ApiOperation(value = "작성자의 모든 파티글 가져오기", notes = "작성자의 모든 파티 게시글을 가져옵니다.")
    public ResultView<List<PartyInfoDto>> getPartyInfoListByAccountId(@PathVariable long accountId) {
        return new ResultView<List<PartyInfoDto>>(partyInfoService.getPartyInfoListByAccountId(accountId));
    }

    @GetMapping(value = "/{partyId}")
    @ApiOperation(value = "특정 id값의 파티 글 가져오기", notes = "partyId에 해당하는 파티글을 가져옵니다.")
    public ResultView<PartyInfoDto> getPartyInfoByPartyId(@PathVariable long partyId) {
        return new ResultView<PartyInfoDto>(partyInfoService.getPartyInfoByPartyId(partyId));
    }

}
