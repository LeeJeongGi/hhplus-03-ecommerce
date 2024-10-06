# 잔액 충전 API

### 이벤트 시퀀스
```mermaid
sequenceDiagram
    participant Client
    participant API
    participant UserService
    participant BalanceManager
    participant DB

    Client ->> API:잔액 충전 요청
    alt 존재하는 유저
        API ->> UserService: 유저 인증(등록된 유저 인지 검증)
        UserService ->> BalanceManager: 잔액 충전 요청
        BalanceManager ->> DB: 유저의 현재 잔액 조회
        DB ->> BalanceManager: 현재 잔액
        BalanceManager ->> DB: 유저의 잔액 업데이트
        DB ->> BalanceManager: 잔액 업데이트 완료
        BalanceManager ->> API: 유저의 충전된 잔액 정보
        API ->> Client: 충전 성공 메세지, 충전 결과 정보
    else
        UserService ->> API: 잔액 충전 실패(존재하지 않는 유저)
        API ->> Client: 오류 메시지 반환(존재하지 않는 유저 입니다.)
    end
```
<br>

---
# 잔액 조회 API

### 이벤트 시퀀스
```mermaid
sequenceDiagram
    participant Client
    participant API
    participant UserService
    participant BalanceManager
    participant DB

    Client ->> API:잔액 조회 요청
    alt 존재하는 유저
        API ->> UserService: 유저 인증(등록된 유저 인지 검증)
        UserService ->> BalanceManager: 잔액 조회 요청
        BalanceManager ->> DB: 유저의 현재 잔액 조회
        DB ->> BalanceManager: 현재 잔액 정보
        BalanceManager ->> API: 유저의 잔액 정보
        API ->> Client: 성공 메세지, 유저의 잔액 정보 전달
    else
        UserService ->> API: 잔액 조회 실패(존재하지 않는 유저)
        API ->> Client: 오류 메시지 반환(존재하지 않는 유저 입니다.)
    end
```
<br>

---

# 상품 조회 API

### 이벤트 시퀀스
```mermaid
sequenceDiagram
    participant Client
    participant API
    participant ProductManager
    participant DB

    Client ->> API: 상품 조회 요청
    alt 존재하는 상품
        API ->> ProductManager: 상품 인증(등록된 상품 인지 검증)
        ProductManager ->> DB: 상품 조회
        DB ->> ProductManager: 상품 정보 (ID, 이름, 가격, 잔여수량)
        ProductManager ->> API: 상품 메타 정보 (ID, 이름, 가격, 잔여수량)
        API ->> Client: 성공 메세지, 상품 정보 전달
    else
        ProductManager ->> API: 상품 조회 실패(존재하지 않는 상품)
        API ->> Client: 오류 메시지 반환(존재하지 않는 상품 입니다.)
    end
```
<br>

---

# 상위 TOP5 상품 조회 API

### 이벤트 시퀀스
```mermaid
sequenceDiagram
    participant Client
    participant API
    participant ProductManager
    participant DB

    Client ->> API: 주간 TOP5 상품 조회 요청
    API ->> ProductManager: 랭킹 TOP5 상품 조회 요청
    ProductManager ->> DB: 랭킹 테이블에서 TOP5 상품 정보 조회
    DB ->> ProductManager: 랭킹 테이블에서 TOP5 상품 정보 전달
    ProductManager ->> API: 랭킹 TOP5 상품 정보 리스트로 전달
    API ->> Client: 성공 메세지, 상위 랭킹 상품 정보 리스트로 전달

```
<br>

---

# 주문/결제 API

### 이벤트 시퀀스
```mermaid
sequenceDiagram
    participant Client
    participant API
    participant ProductManager
    participant UserService
    participant BalanceManager
    participant DB
    
    Client ->> API: 상품 결제 요청
    alt 존재하는 상품
    
        API ->> ProductManager: 존재하는 상품인지 조회 요청
        ProductManager ->> DB: 상품 조회
        DB ->> ProductManager: 상품 정보 (상품 ID, 상품 재고)
    
        alt 존재하는 유저
            API ->> UserService: 결제 요청한 회원 조회
            UserService ->> API: 유저 정보와 보유 잔액 정보
            
            alt 결제 요청
                API ->> BalanceManager: 결제 금액만큼 포인트 차감 요청
                BalanceManager ->> DB: 차감된 유저의 포인트 업데이트 요청
                DB ->> PointManger: 결제 후 유저의 포인트 정보
                BalanceManager ->> API: 최종 유저의 포인트 정보
                API ->> Client: 성공 메세지, 결제 완료한 회원 정보 전달
            else
                BalanceManager ->> API: 결제 실패(금액이 부족합니다.)
                API ->> Client: 오류 메시지 반환(잔액이 부족합니다.)
            end
            
        else
            UserService ->> API: 잔액 조회 실패(존재하지 않는 유저)
            API ->> Client: 오류 메시지 반환(존재하지 않는 유저 입니다.)
        end
    else
        ProductManager ->> API: 상품 조회 실패(존재하지 않는 상품 또는 재고가 없음)
        API ->> Client: 오류 메시지 반환(존재하지 않는 상품 또는 재고가 없습니다.)
    end
    
```
<br>

---

# 장바구니 상품 조회 API

### 이벤트 시퀀스
```mermaid
sequenceDiagram
    participant Client
    participant API
    participant UserService
    participant BasketManager
    participant DB

    Client ->> API:유저의 장바구니 목록 조회 요청
    alt 존재하는 유저
        API ->> UserService: 유저 인증(등록된 유저 인지 검증)
        UserService ->> BasketManager: 유저 장바구니 상품 목록 조회 요청
        BasketManager ->> DB: 유저 장바구니 상품 목록 조회
        DB ->> BasketManager: 유저 장바구니 상품 정보
        BasketManager ->> API: 유저 장바구니 상품 정보 리스트 형식으로 전달
        API ->> Client: 성공 메세지, 유저 장바구니 상품 정보 리스트 정보 전달
    else
        UserService ->> API: 유저의 장바구니 목록 조회 실패(존재하지 않는 유저)
        API ->> Client: 오류 메시지 반환(존재하지 않는 유저 입니다.)
    end
```
<br>

---

# 장바구니 상품 추가/삭제 API

### 이벤트 시퀀스
```mermaid
sequenceDiagram
    participant Client
    participant API
    participant UserService
    participant BasketManager
    participant DB

    Client ->> API:유저의 장바구니 추가/삭제 요청
    alt 존재하는 유저
        API ->> UserService: 유저 인증(등록된 유저 인지 검증)
        UserService ->> BasketManager: 유저 장바구니 상품 추가/삭제 요청
        BasketManager ->> DB: 현재 유저 장바구니 목록 조회
        DB ->> BasketManager: 현재 유저 장바구니 정보
        BasketManager ->> DB: 유저 장바구니 목록 업데이트 요청
        DB ->> BasketManager: 업데이트 한 유저의 장바구니 정보
        BasketManager ->> API: 업데이트 된 유저 장바구니 정보 리스트 형식으로 전달
        API ->> Client: 성공 메세지, 유저 장바구니 정보 리스트 정보 전달
    else
        UserService ->> API: 유저의 장바구니 추가/삭제 실패(존재하지 않는 유저)
        API ->> Client: 오류 메시지 반환(존재하지 않는 유저 입니다.)
    end
```
<br><br>