# E-Commerce

## Docs

1. [마일스톤](./docs/Milestone.md)
2. [이벤트 시퀀스 다이어그램](./docs/EventSequence.md)
3. [ERD](./docs/ERD.md)
4. [API 명세 문서](./docs/Api_Docs.md)
5. [Swagger 문서](./docs/Swagger.md)
6. [캐시 전략 보고서](./docs/Caching.md)
7. [인덱스 전략 보고서](./docs/Index.md)
8. [MSA 변환 계획서](./docs/MSA_Update.md)
9. [가상 장애 대응 보고서](./docs/system_performance_report.md)
---
## 기술 스택

### 언어 및 프레임워크
- Kotlin: 1.9.25
- Spring Boot: 3.3.4
- JPA: 1.9.25

### 데이터베이스 및 스토리지
- DBMS: H2, MySql
- Redis

### 빌드
- Gradle
- Docker Compose: 컨테이너화

### API 문서화
- API 명세서: SpringDoc OpenAPI
- Swagger UI

### 테스트 및 모니터링
- JUnit5: 단위 테스트
- Mockk: 목(Mock) 객체 활용 테스트
- K6: 부하 테스트
- Grafana: 모니터링 및 시각화

### 아키텍처 및 기타
- Saga Pattern: 분산 트랜잭션 관리
- Kafka: 비동기 메시지 처리 및 이벤트 기반 아키텍처

---
### 통합 테스트 시나리오
1. [잔액 충전 통합 테스트](./docs/BalanceIntegration.md)
2. [주문 통합 테스트](./docs/OrderIntegration.md)
3. [결제 통합 테스트](./docs/PaymentIntegration.md)

---

