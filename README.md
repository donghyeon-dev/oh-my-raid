# OhMyRaid
프로젝트를 생각하게 된 계기
- 와우 레이드의 구인은 인벤과 게임내에서 이뤄지는게 전부

문제점
- 직장인들에겐 인게임 내에서 레이드파티를 구하는것은 상당히 제한됨
- 인벤 구인글은 인벤 쪽지로 상호작용
- 어떤포지션 어떤 직업특이  필요한지 알 수 없음

---

사용한 API
- Battle.net API((https://develop.battle.net/)

사용기술
- JPA
- MapStruct
- JUnit
- Feign
- Swagger
- Lombok

### 중첩된 For문을 사용하는 이유
![레이드정보계층](doc/RaidDtoUML.png)*블리자드에서 제공하는 레이드 API의 depth*
![캐릭터정보계층](doc/CharacterDtoUML.png)*블리자드에서 제공하는 캐릭터 API의 depth*