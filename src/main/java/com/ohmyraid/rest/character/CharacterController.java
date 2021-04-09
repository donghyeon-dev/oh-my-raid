package com.ohmyraid.rest.character;

import com.ohmyraid.common.wrapper.ResultView;
import com.ohmyraid.service.character.CharacterService;
import com.ohmyraid.vo.character.CharacterFeignInpVo;
import com.ohmyraid.vo.character.CharacterVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/character")
@Api(tags = "CharacterController", value = "캐릭터 관련 API")
public class CharacterController {

    @Autowired
    CharacterService characterService;

    @GetMapping(value = "/inf")
    @ApiOperation(value = "캐릭터 정보 가져오기",notes = "지역, 서버, 이름, 필드를 입력하여 캐릭터 정보를 가져온다.")
    public ResultView<Map<String,String>> getCharacterInf(@RequestParam("region") String region,
                                                            @RequestParam("realm") String realm,
                                                            @RequestParam("name") String name,
                                                            @RequestParam(value = "fields",required = false) String fields){
        CharacterFeignInpVo inpVo = new CharacterFeignInpVo();
        inpVo.setRegion(region);
        inpVo.setRealm(realm);
        inpVo.setName(name);
        inpVo.setFields(fields);
        return new ResultView<>(characterService.getCharacterInf(inpVo));
    }
}
