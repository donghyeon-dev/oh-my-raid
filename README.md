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

## 진행상황
1. Battle.net API와 통신(계정Token 발급)
2. 계정의 모든 캐릭터(50레벨 이상, 성약을 결정한)정보 저장 및 출력
4. `JWT` 토큰 만료시간 10시간으로 늘림 나중에 꼭 줄여야함
5. Raid진행정보를 어떻게 저장해야하는지 매우 고민중...
