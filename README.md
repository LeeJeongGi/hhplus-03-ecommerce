# E-Commerce

## Docs

1. [마일스톤](./docs/Milestone.md)
2. [이벤트 시퀀스 다이어그램](./docs/EventSequence.md)
3. [ERD](./docs/ERD.md)
4. [API 명세 문서](./docs/Api_Docs.md)

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
├── ECommerceApplication.kt
├── business
│   └── entity
├── common
│   └── error
│       ├── code
│       ├── exception
│       └── response
└── interfaces
    └── presentation
        ├── controller
        ├── request
        └── response
```

---