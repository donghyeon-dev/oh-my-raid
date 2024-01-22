package com.ohmyraid.domain;

import com.ohmyraid.domain.character.CharacterEntity;
import com.ohmyraid.repository.user.UserRepository;
import com.ohmyraid.repository.character.CharacterRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CharacterEntityTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CharacterRepository characterRepository;

    @Test
    public void 엔티티_AttributeConverter_test() {

        String randomName = ("autocat" + new Random().nextInt(1000));
        // 엔티티 등록
        CharacterEntity characterEntity = CharacterEntity.builder()
                .userEntity(userRepository.findAllByEmail("donghyeondev@gmail.com"))
                .characterSeNumber(123456)
                .name(randomName)
                .level(60)
                .playableClass("전사") // "00"
                .specialization("분노")
                .race("타우렌")
                .gender("남성")
                .faction("호드")
                .equippedItemLevel(230)
                .averageItemLvel(231)
                .slug("아즈샤라")
                .lastCrawledAt(LocalDateTime.now())
                .build();
        characterRepository.save(characterEntity);

        // 컨버팅 된 코드값으로 로우 조회
        characterEntity = characterRepository.findByName(randomName);

        System.out.println("************* characterEntity\n" + characterEntity);

        // 검증, 값으로 조회할땐 코드가 아닌 "전사"로 나와야함
        String playbleClass = characterEntity.getPlayableClass();
        assertEquals(playbleClass, "전사");

        // 성공했으니 삭제
        characterRepository.delete(characterEntity);
    }


}
