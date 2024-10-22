## 주문 통합 테스트

### 코드 링크 -> []

---
### 통합 테스트 시나리오

재고가 50개 밖에 없을 때 동시에 주문 100개를 한다면 50개의 요청만 성공하고 <br>
나머지 50개 요청은 실패하는 통합 테스트 입니다.

```json
{
  "상품" : "아디다스 져지",
  "카테고리" : "상의",
  "가격" : "100,000원"
}

{
  "상품키" : "아디다스 져지",
  "사이즈" : "XL",
  "재고" : "3개"
}
```
상품 테이블 - 상품 상세 정보 테이블을 나누어 1:N 구조로 만들었습니다. <br>

재고에 대한 정확한 정합성이 보장되어야 하기 때문에 상품 상세 정보 테이블에서 상품에 <br>
대해 Lock 설정을 하여 재고보다 많은 요청이 와도 주문이 진행되지 않게 개발 하였습니다. 

```kotlin
@Lock(LockModeType.PESSIMISTIC_WRITE)
@Query("SELECT p FROM ProductStock p WHERE p.id = :productStockId")
fun findByIdWithLock(productStockId: Long): ProductStock?
```
