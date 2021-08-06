package com.ohmyraid.domain;

import com.ohmyraid.domain.character.CharacterEntity;
import com.ohmyraid.repository.account.AccountRepository;
import com.ohmyraid.repository.character.CharacterRespository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CharacterEntityTest {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    CharacterRespository characterRespository;

    @Transactional
    @Test
    void 엔티티_AttributeConverter_test() {

        // 엔티티 등록
        CharacterEntity characterEntity = CharacterEntity.builder()
                .accountEntity(accountRepository.findAllByEmail("donghyeondev@gmail.com"))
                .characterSeNumber(123456)
                .name("autocat22")
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
                .expansionOption("나이트페이")
                .expansionOptionLevel(50)
                .build();
        characterRespository.save(characterEntity);

        // 컨버팅 된 코드값으로 로우 조회
        characterEntity = characterRespository.findByName("autocat22");

        System.out.println("************* characterEntity\n" + characterEntity);

        // 검증, 값으로 조회할땐 코드가 아닌 "전사"로 나와야함
        String playbleClass = characterEntity.getPlayableClass();
        assertEquals(playbleClass, "전사");
    }
}
