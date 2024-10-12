## 1. 잔액 충전 API

### Request

- **Method** : POST
- **Endpoint** : `/v1/users/{userId}/balance`
- **Description** : 유저의 잔액을 충전하고 충전 결과를 반환 합니다.
- **Path Parameter** : `userId` - 충전할 유저의 고유 ID

### Request Body
```json
{
  "amount" : 1000
}
```

### Response Body
```json
{
  "userId" : 1,
  "balance" : 5000
}
```


### Error
```json
{
  "code": 404,
  "message": "user not found"
}
```


---
## 2. 잔액 조회 API

### Request

- **Method** : GET
- **Endpoint** : `/v1/users/{userId}/balance`
- **Description** : 유저 잔액을 반환 한다.
- **Path Parameter** : `userId` - 충전할 유저의 고유 ID

### Response Body
```json
{
  "userId" : 1,
  "balance" : 2000
}
```
### Error
```json
{
  "code": 404,
  "message": "user not found"
}
```

---
## 3. 상품 조회 API

### Request

- **Method** : GET
- **Endpoint** : `/v1/products/{productId}`
- **Description** : 상품의 정보를 반환 한다.
- **Path Parameter** : `productId` - (필수) 조회할 상품의 ID 입니다.

### Response Body
```json
{
  "productId": 123,
  "name": "상품명",
  "category": "카테고리",
  "price": 20000,
  "stockQuantity": 10
}
```

### Error
```json
{
  "code": 404,
  "message": "product not found"
}
```

---
## 4. 상위 TOP5 상품 조회 API

### Request

- **Method** : GET
- **Endpoint** : `/v1/products/top`
- **Description** : 지정된 기간 동안 가장 많이 판매된 상위 N개의 상품을 조회합니다.
- **Query Parameter** :
  - limit (필수): 조회할 상품의 개수, 예를 들어 limit=5로 설정 시 상위 5개 조회
  - days (선택): 조회할 기간의 일 수, 기본값은 최근 3일


### Response Body (limit 수만큼 상품 정보 반복)
```json
{
  "topProducts": [
    {
      "productId": 123,
      "name": "상품명1",
      "category": "카테고리",
      "price": 15000,
      "salesCount": 100
    },
    {
      "productId": 456,
      "name": "상품명2",
      "category": "카테고리",
      "price": 12000,
      "salesCount": 85
    }
  ]
}
```

### Error
```json
{
  "code": 400,
  "message": "invalid parameters"
}
```


---
## 5. 주문 결제 API

### Request

- **Method** : POST
- **Endpoint** : `/v1/orders`
- **Description** : 사용자가 선택한 상품을 주문하고, 보유 잔액에서 결제 금액을 차감하여 결제를 완료합니다.

### Request Body
```json
{
  "userId": 1,
  "products": [
    {
      "productId": 123,
      "quantity": 2
    },
    {
      "productId": 456,
      "quantity": 1
    }
  ]
}
```

### Response Body
```json
{
  "balance": 20000,
  "orderId": 789,
  "products": [
    {
      "productId": 123,
      "quantity": 2,
      "price": 30000,
      "totalAmount": 60000
    },
    {
      "productId": 456,
      "quantity": 1,
      "price": 15000,
      "totalAmount": 15000
    }
  ]
}
```
### Error
```json
{
  "code": 404,
  "message": "product not found"
}
```
### Error
```json
{
  "code": 404,
  "message": "user not found"
}
```
### Error
```json
{
  "code": 500,
  "message": "user not money"
}
```
### Error
```json
{
  "code": 404,
  "message": "order not found"
}
```


---
## 6. 장바구니 상품 조회 API

### Request

- **Method** : GET
- **Endpoint** : `/v1/users/{userId}/basket`
- **Description** : 유저의 장바구니에 담겨 있는 상품을 조회 합니다.
- **Path Parameter** : `userId` - 충전할 유저의 고유 ID

### Response Body
```json
{
  "basketItems": [
    {
      "productId": 123,
      "quantity": 2
    },
    {
      "productId": 456,
      "quantity": 1
    }
  ]
}
```

### Error
```json
{
  "code": 404,
  "message": "user not found"
}
```


## 7. 장바구니 상품 추가/삭제 API

### Request

- **Method** : POST
- **Endpoint** : `/v1/users/{userId}/basket`
- **Description** : 유저의 장바구니에 상품을 추가/삭제를 한 후 업데이트 된 유저의
장바구니 목록 정보를 돌려준다.
- **Path Parameter** : `userId` - 충전할 유저의 고유 ID

### Request Body
```json
{
  "userId": 1,
  "productId": 123,
  "quantity": 2,
  "action": "ADD 또는 REMOVE "
}
```

### Response Body
```json
{
  "basketItems": [
    {
      "productId": 123,
      "quantity": 2
    },
    {
      "productId": 456,
      "quantity": 1
    }
  ]
}
```

### Error
```json
{
  "code": 404,
  "message": "user not found"
}
```