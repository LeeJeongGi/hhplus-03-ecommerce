## 결제 통합 테스트

### 코드 링크 -> [테스트 코드](https://github.com/LeeJeongGi/hhplus-03-ecommerce/blob/feature/step10/src/test/kotlin/com/hhplus/e_commerce/business/facade/PaymentFacadeTest.kt)

---
### 통합 테스트 시나리오

1. 외부 API 결제 정보 전달이 실패하더라고 결제 정보는 저장되는지 검증 테스트
2. 잔액이 천원 있는 유저가 동시에 600원 요청을 두번 했을 때 한번 성공하고 한번 실패 <br> 하는지 검증 테스트

두 가지 시나리오로 통합 테스트를 진행 했습니다.


첫 번째 결제 정보를 외부 API로 전달하는 방법으로 Spring Event를 발행하여 비동기적으로 <br>
호출하는 방식으로 개발을 하였습니다. Event 기능을 통해 외부 API에 호출이 실패 하더라도 <br>
결제 정보를 저장하는 부분까지 영향이 가지 않도록 개발을 했습니다.

```kotlin
@Component
class OrderEventPublisher(
    private val publisher: ApplicationEventPublisher,
) {

    fun publishDataPlatformEvent(paymentSaveResultDto: PaymentSaveResultDto) {
        val event = OrderCompletedEvent(paymentSaveResultDto)
        publisher.publishEvent(event)
    }
}
```

```kotlin
@Component
class OrderEventListener {

    @Async
    @EventListener
    fun listen(event: OrderCompletedEvent) {
        try {
            logOrderCompletion(event.paymentSaveResultDto)
        } catch (e: Exception) {
            println("주문 완료 이벤트 처리 중 오류 발생: ${e.message}")
        }
    }

    private fun logOrderCompletion(paymentSaveResultDto: PaymentSaveResultDto) {

        /**
         * 외부 API 연동시 실패했을 때 결제 정보 정상적으로 저장되는지 확인하기 위한 예외 처리.
         * 단일 테스트 실행시 orderId 1L 이기 때문에 실패해도 정상적으로 저장되는지 검증 확인
         * 전체 테스트 실행시 orderId는 1L 아니기 때문에 성공해도 정상적으로 저장되는지 검증 확인
         */
        if (paymentSaveResultDto.orderId == 1L) {
            throw BusinessException.BadRequest(ErrorCode.Common.BAD_REQUEST)
        }

        println("주문 완료 이벤트 수신: " +
                "[userId = ${paymentSaveResultDto.userId}, " +
                "orderId = ${paymentSaveResultDto.orderId}, " +
                "결제 일자 = ${paymentSaveResultDto.paymentDate}]")
    }
}
```
두 번째 통합 테스트는 잔액이 1000원 있는 유저가 600원 상품을 두 번 구매하는 경우 <br>
한 번의 요청만 성공하도록 통합 테스트를 작성 했습니다.

```kotlin
@Lock(LockModeType.PESSIMISTIC_WRITE)
@Query("select o from Order o where o.id = :orderId")
fun findByOrderIdWithLock(orderId: Long): Order?
```
가장 먼저 Order - Payment 1:1로 처리 되어야 합니다. 즉 주문 id는 한 번의 결제만 <br>
진행되어야 하기 때문에 해당 주문을 조회 할 때 Lock 설정을 하여 동시에 여러 요청이 오더라도 <br>
진행되지 않게 개발을 하였습니다.




