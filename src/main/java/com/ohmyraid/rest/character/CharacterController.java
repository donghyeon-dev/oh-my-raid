package com.ohmyraid.rest.character;

import com.ohmyraid.common.wrapper.ResultView;
import com.ohmyraid.service.character.CharacterService;
import com.ohmyraid.vo.character.CharacterFeignInpVo;
import com.ohmyraid.vo.character.CharacterVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/character")
public class CharacterController {

    @Autowired
    CharacterService characterService;

    @GetMapping(value = "/inf")
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
