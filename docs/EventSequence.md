# 잔액 충전 API

### 이벤트 시퀀스
```mermaid
sequenceDiagram
    participant Client
    participant API
    participant BalanceFacade
    participant UserService
    participant BalanceService
    participant DB

    Client ->> API:잔액 충전 요청
    alt 존재하는 유저
        API ->> BalanceFacade: 유저 인증(등록된 유저 인지 검증)
        BalanceFacade ->> UserService: 유저 인증 요청
        UserService ->> DB: 유저 인증 요청
        DB ->> UserService: 유저 인증 완료
        UserService ->> BalanceFacade: 유저 인증과 함께 보유 잔액 정보 넘겨준다.
        BalanceFacade ->> BalanceService: 잔액 충전 요청
        BalanceService ->> DB: 유저의 현재 잔액 조회
        DB ->> BalanceService: 현재 잔액
        BalanceService ->> DB: 유저의 잔액 업데이트
        DB ->> BalanceService: 잔액 업데이트 완료
        BalanceService ->> BalanceFacade: 유저의 충전된 잔액 정보
        BalanceFacade ->> API: 유저의 충전된 잔액 정보
        API ->> Client: 충전 성공 메세지, 충전 결과 정보
    else
        UserService ->> BalanceFacade: 잔액 충전 실패(존재하지 않는 유저)
        BalanceFacade ->> API: 잔액 충전 실패(존재하지 않는 유저)
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
    participant BalanceService
    participant DB

    Client ->> API:잔액 조회 요청
    alt 존재하는 유저
        API ->> BalanceService: 유저 인증(등록된 유저 인지 검증)
        BalanceService ->> DB: 유저의 존재와 현재 잔액 조회 요청
        DB ->> BalanceService: 현재 유저의 잔액 정보
        BalanceService ->> API: 유저의 잔액 정보
        API ->> Client: 성공 메세지, 유저의 잔액 정보 전달
    else
        BalanceService ->> API: 잔액 조회 실패(존재하지 않는 유저)
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
    participant ProductFacade
    participant ProductService
    participant DB

    Client ->> API: 상품 조회 요청
    alt 존재하는 상품
        API ->> ProductFacade: 상품 인증(등록된 상품 인지 검증)
        ProductFacade ->> ProductService: 상품 인증(등록된 상품 인지 검증)
        ProductService ->> DB: 상품 조회
        DB ->> ProductService: 상품 정보 (ID, 이름, 가격, 잔여수량)
        ProductService ->> ProductFacade: 상품 메타 정보 (ID, 이름, 가격)
        ProductFacade ->> ProductService: 상품 서브 정보 요청 (사이즈, 잔여 수량)
        ProductService ->> DB: 상품 서브 정보 요청
        DB ->> ProductService: 상품 서브 정보 전달
        ProductService ->> ProductFacade: 상품 서브 정보 전달
        ProductFacade ->> API: 상품 메타 정보 (ID, 이름, 가격, 잔여수량)
        API ->> Client: 성공 메세지, 상품 정보 전달
    else
        ProductService ->> API: 상품 조회 실패(존재하지 않는 상품)
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
    participant ProductService
    participant DB

    Client ->> API: 주간 TOP5 상품 조회 요청
    API ->> ProductService: 랭킹 TOP5 상품 조회 요청
    ProductService ->> DB: 랭킹 테이블에서 TOP5 상품 정보 조회
    DB ->> ProductService: 랭킹 테이블에서 TOP5 상품 정보 전달
    ProductService ->> API: 랭킹 TOP5 상품 정보 리스트로 전달
    API ->> Client: 성공 메세지, 상위 랭킹 상품 정보 리스트로 전달

```
<br>

---

# 주문 API

### 이벤트 시퀀스
```mermaid
sequenceDiagram
    participant Client
    participant API
    participant OrderFacade
    participant ProductService
    participant BalanceService
    participant OrderService
    participant DB
    
    Client ->> API: 상품 주문 요청
    alt 존재하는 상품
    
        API ->> OrderFacade: 상품 주문 요청
        OrderFacade ->> ProductService: 존재하는 상품인지 조회 요청 그리고 재고가 있는지 요청
        ProductService ->> DB: 상품 조회 및 재고 확인
        DB ->> ProductService: 상품 정보 (상품 ID, 상품 재고)
        ProductService ->> OrderFacade: 상품 정보 (상품 ID, 상품 재고)
    
        alt 존재하는 유저
            OrderFacade ->> BalanceService: 결제 요청한 회원 조회
            BalanceService ->> DB: 회원 존재 유무 그리고 회원의 보유 잔액 조회 요청
            DB ->> BalanceService: 회원 잔액과 정보를 전달
            BalanceService ->> OrderFacade: 유저 정보와 보유 잔액 정보
            
            OrderFacade ->> OrderService: 주문 정보 저장 요청
            OrderService ->> DB: 주문 정보 저장
            DB ->> OrderService: 저장 정보 전달
            OrderService ->> OrderFacade: 저장 정보 전달

            OrderFacade ->> ProductService: 재고 정보 업데이트 요청
            ProductService ->> DB: 재고 정보 업데이트 요청
            DB ->> ProductService: 재고 정보 업데이트 정보 전달
            ProductService ->> OrderFacade: 재고 정보 업데이트 정보 전달
            
        else
            BalanceService ->> OrderFacade: 잔액 조회 실패(존재하지 않는 유저 또는 잔액 부족)
            OrderFacade ->> API: 잔액 조회 실패(존재하지 않는 유저 또는 잔액 부족)
            API ->> Client: 오류 메시지 반환(존재하지 않는 유저 입니다. 또는 잔액 부족 입니다.)
        end
    else
        ProductService ->> OrderFacade: 상품 조회 실패(존재하지 않는 상품 또는 재고가 없음)
        OrderFacade ->> API: 상품 조회 실패(존재하지 않는 상품 또는 재고가 없음)
        API ->> Client: 오류 메시지 반환(존재하지 않는 상품 또는 재고가 없습니다.)
    end
```

<br>

---

# 결제 API

### 이벤트 시퀀스
```mermaid
sequenceDiagram
    participant 외부 API
    participant Client
    participant API
    participant PaymentFacade
    participant OrderService
    participant UserService
    participant BalanceService
    participant DB

    Client ->> API: 상품 결제 요청
    API ->> PaymentFacade: 상품 결제 요청
    PaymentFacade ->> OrderService: 주문 정보 요청
    alt 주문 정보가 완료
        OrderService ->> DB: 주문 정보 요청
        DB ->> OrderService: 주문 정보 전달
        OrderService ->> PaymentFacade: 주문 정보 전달

        PaymentFacade ->> UserService: 유저 잔액 조회
        UserService ->> DB: 유저 잔액 조회
        DB ->> UserService: 유저 보유 잔액 전달
        UserService ->> PaymentFacade: 유저 보유 잔액 전달
        
        PaymentFacade ->> BalanceService: 주문한 상품 가격만큼 잔액 차감 요청
        BalanceService ->> DB: 주문한 상품 가격만큼 잔액 차감 요청
        DB ->> BalanceService: 잔액 업데이트 완료
        BalanceService ->> PaymentFacade: 잔액 업데이트 완료
        
        PaymentFacade ->> OrderService: 주문 정보에 결제 상태 업데이트 요청
        OrderService ->> DB: 주문 정보에 결제 상태 업데이트 요청
        DB ->> OrderService: 주문 정보에 결제 상태 업데이트 결과 전달
        OrderService ->> PaymentFacade: 주문 정보에 결제 상태 업데이트 결과 전달

        Note over PaymentFacade, 외부 API: 주문 정보를 데이터 플랫폼에 전송(외부)

    else
        OrderService ->> PaymentFacade: 주문 정보 오류(주문이 취소 되었거나, 주문 정보가 없다)
        PaymentFacade ->> API: 주문 정보 오류(주문이 취소 되었거나, 주문 정보가 없다)
        API ->> Client: 오류 메시지 반환(주문이 취소 되었거나, 주문 정보가 없습니다.)
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
    participant CartsService
    participant DB

    Client ->> API:유저의 장바구니 목록 조회 요청
    API ->> CartsService: 유저의 장바구니 목록 조회 요청
    CartsService ->> DB: 저의 장바구니 목록 조회 요청
    DB ->> CartsService: 유저 장바구니 상품 정보
    CartsService ->> API: 유저 장바구니 상품 정보 리스트 형식으로 전달
    API ->> Client: 성공 메세지, 유저 장바구니 상품 정보 리스트 정보 전달
 
```
<br>

---

# 장바구니 상품 추가 API

### 이벤트 시퀀스
```mermaid
sequenceDiagram
    participant Client
    participant API
    participant CartsService
    participant DB

    Client ->> API: 유저의 장바구니 추가 요청
    API ->> CartsService: 유저의 장바구니 추가 요청
    CartsService ->> DB: 장바구니에 상품이 저장되어 있는지 확인
    DB ->> CartsService: 장바구니에 저장된 상품 인증
    CartsService ->> DB: 저장된 장바구니 상품 저장
    DB ->> CartsService: 저장된 장바구니 상품 정보 전달
    CartsService ->> API: 저장된 장바구니 상품 정보 전달
    API ->> Client: 저장된 장바구니 상품 정보 출력
    
```
<br><br>

---

# 장바구니 상품 삭제 API


### 이벤트 시퀀스
```mermaid
sequenceDiagram
    participant Client
    participant API
    participant CartsService
    participant DB

    Client ->> API: 유저의 장바구니 삭제 요청
    API ->> CartsService: 유저의 장바구니 삭제 요청
    CartsService ->> DB: 장바구니에 상품이 저장되어 있는지 확인
    DB ->> CartsService: 장바구니에 저장된 상품 인증
    CartsService ->> DB: 저장된 장바구니 상품 삭제 요청
    DB ->> CartsService: 저장된 장바구니 상품 삭제
    CartsService ->> API: 삭제된 상품 전달
    API ->> Client: 삭제된 상품 출력
    
```
<br><br>
