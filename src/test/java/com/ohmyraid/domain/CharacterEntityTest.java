package com.ohmyraid.domain;

import com.ohmyraid.dto.character.ActualCharacterDto;
import com.ohmyraid.enums.PlayableClass;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CharacterEntityTest {

    @Test
    void enum_column_테스트 (){
        String playbleClass = "사제";

        ActualCharacterDto characterDto = new ActualCharacterDto();

        characterDto.setPlaybleClass(PlayableClass.valueOf(playbleClass));

        System.out.println("################" + characterDto);

        assertEquals(characterDto.getPlaybleClass(), PlayableClass.사제);
    }
}
