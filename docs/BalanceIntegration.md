## 잔액 충전 통합 테스트

### 코드 링크 -> []

---
### 통합 테스트 시나리오

동시에 500원, 600원 충전 요청에 대해 정상적으로 1100원이 충전 되는지 검증하는 통합 테스트 <br>

```kotlin
@Lock(LockModeType.PESSIMISTIC_WRITE)
@Query("select b from Balance b where b.user.id = :userId")
fun findByUserIdWithLock(userId: Long): Balance? 
```
동시성 처리를 위해 유저의 잔액을 관리하는 테이블에 락을 걸어 동시에 충전 요청이 와도 <br>
하나의 작업식 처리되도록 했습니다.

기존에는 User 테이블에서 잔액을 관리하려고 했지만, User 테이블에 락을 설정하게 되면 <br>
잔액 수정과는 무관한 유저의 개인 정보 변경에 대해서도 처리가 안되기 때문에 테이블을 분리 했습니다. <br>

따라서 User - Balance 1:1 테이블 구조로 설계하였고 Lock 설정은 Balance 테이블에 설정해서 <br>
충전에 대한 동시 요청에 대해서도 충전된 잔액의 정합성을 보장 할 수 있습니다.
