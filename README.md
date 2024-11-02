# E-Commerce

## Docs

1. [마일스톤](./docs/Milestone.md)
2. [이벤트 시퀀스 다이어그램](./docs/EventSequence.md)
3. [ERD](./docs/ERD.md)
4. [API 명세 문서](./docs/Api_Docs.md)
5. [Swagger 문서](./docs/Swagger.md)

## 기술 스택

### 프로젝트 구성 스택
- Kotlin: 1.9.25
- Spring Boot: 3.3.4
- JPA: 1.9.25
- DBMS: H2


### API 문서화
- API 명세서: SpringDoc OpenAPI
- Swagger UI: 1.6.15


### 패키지 구조

```code
src/main/kotlin/com/hhplus/e_commerce
├── business
│   ├── dto
│   ├── entity
│   │   └── type
│   ├── facade
│   │   └── dto
│   ├── repository
│   └── service
├── common
│   ├── config
│   └── error
│       ├── code
│       ├── exception
│       └── response
├── infra
│   ├── impl
│   └── jpa
└── interfaces
    └── presentation
        ├── controller
        ├── request
        └── response
```

---

### 통합 테스트 시나리오
1. [잔액 충전 통합 테스트](./docs/BalanceIntegration.md)
2. [주문 통합 테스트](./docs/OrderIntegration.md)
3. [결제 통합 테스트](./docs/PaymentIntegration.md)

-> [Chapter2 회고록](https://leejeonggi.tistory.com/entry/%EB%AA%A9%ED%91%9C%EC%9D%98-%EC%A4%91%EA%B0%84-%EC%A7%80%EC%A0%90%EC%97%90%EC%84%9C-%EB%8F%8C%EC%95%84%EB%B3%B4%EB%A9%B0)

---

### -> [동시성 처리 보고서](./docs/Concurrency_Lock.md)
