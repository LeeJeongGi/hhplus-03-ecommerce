## 부하 테스트 대상 선정 및 목적, 시나리오 계획 작성

### 개요
- E-Commerce 시스템에서 부하 테스트를 실시하여 결과를 작성한다.
- 점진적으로 부하를 늘려가며 테스트를 한다.
  - 점진적으로 증가하는 부하를 정상적으로 처리 할 수 있는지 평가한다.
  - 일반적인 운영 상황에서 안정성을 평가한다.
- 일시적으로 부하를 늘려가며 테스트를 한다.
  - 일시적으로 높은 부하를 처리 할 수 있는지 평가한다.
  - `블랙프라이데이` 같은 특정한 날에 트래픽이 몰릴 수 있는 것을 가정하는 시나리오 테스트
- 테스트 결과 시스템이 안전한지 파악 한다.

### 부하 테스트 대상
- E-commerce 시스템에서 가장 많이 호출 될 4가지 대표 API에 대해 부하 테스트를 진행 한다.
  - 잔액 조회 API
  - 잔액 충전 API
  - 상품 주문 API
  - 상품 조회 API

### 목적
- 시스템이 예상되는 부하를 정상적으로 처리 할 수 있는지 평가
- 일시적인 높은 부하에서 시스템 성능 평가

### 테스트 도구
이번 부하 테스트시 사용할 도구는 K6이다. K6 오픈 소스 부하 테스트 도구이다.
주요 특징으로는 다음과 같다.
- javascript를 사용하여 테스트 스크립트 작성
- 클라우드 서비스 통합 지원
- 실시간 모니터링 및 결과 분석 가능

### 테스트 Common 스크립트
```js
export const options = {
    scenarios: {
        load_test: {
            executor: 'ramping-vus',
            startVUs: 0,
            stages: [
                { duration: '10s', target: 1000 },
                { duration: '30s', target: 1000 },
                { duration: '10s', target: 3000 },
                { duration: '30s', target: 3000 },
                { duration: '10s', target: 0 },
            ],
        },
        peak_test: {
            executor: 'ramping-arrival-rate',
            startRate: 10,
            timeUnit: '1s',
            preAllocatedVUs: 200,
            maxVUs: 500,
            stages: [
                { duration: '10s', target: 1000 },
                { duration: '20s', target: 5000 },
                { duration: '30s', target: 1000 },
                { duration: '10s', target: 5000 },
            ],
        },
    },
};
```

### 설정 상세
- executor
  - ramping-vus : 사용자 수를 점진적으로 증가하는 방법
  - ramping-arrival-rate : 초당 요청 수를 기반으로 부하를 생성하는 방법
- stages
  - 각각 [시간, 가상 사용자 수] 조합으로 되어 있다.
  - 첫 번째 [10s, 1000] 의미는 load_test의 경우 0~10초 동안 0명에서 1000명으로 가상 사용자를 증가 시킨다.
  - 반면 peak-test의 경우 초당 10개에서 1000개로 요청 수 증가 시킨다. 결국 동시에 초당 1000개의 요청이 들어온다는 뜻이다.

---
## 1. 잔액
### 1.1 잔액 조회 API
```js
import http from 'k6/http';
import { check, sleep } from 'k6';
import { options, BASE_URL } from '../common/test-options.js';

export { options };

const TEST_USER_IDS = [9338, 9339, 9340, 9341];  // 테스트 데이터로 생성한 사용자 ID

export default function () {
    const userId = TEST_USER_IDS[Math.floor(Math.random() * TEST_USER_IDS.length)];

    sleep(1);

    const getBalanceRes = http.get(`${BASE_URL}/v1/users/${userId}/balance`);

    check(getBalanceRes, {
        'getBalance status is 200': (r) => r.status === 200,
    });

    sleep(1);
}
```
- 무작위로 사용자 4명의 잔액을 호출 한다.
```text
✓ getBalance status is 200

     checks.........................: 100.00% 91531 out of 91531
     data_received..................: 13 MB   138 kB/s
     data_sent......................: 9.2 MB  101 kB/s
     dropped_iterations.............: 168879  1842.065849/s
     http_req_blocked...............: avg=21.9µs   min=0s     med=3µs     max=35.92ms p(90)=9µs      p(95)=27µs    
     http_req_connecting............: avg=12.35µs  min=0s     med=0s      max=33.39ms p(90)=0s       p(95)=0s      
     http_req_duration..............: avg=155.91ms min=1.76ms med=35.36ms max=1.57s   p(90)=460.2ms  p(95)=720.98ms
       { expected_response:true }...: avg=155.91ms min=1.76ms med=35.36ms max=1.57s   p(90)=460.2ms  p(95)=720.98ms
     http_req_failed................: 0.00%   0 out of 91531
     http_req_receiving.............: avg=29.07µs  min=5µs    med=19µs    max=10ms    p(90)=57µs     p(95)=73µs    
     http_req_sending...............: avg=21.11µs  min=2µs    med=8µs     max=26.53ms p(90)=24µs     p(95)=37µs    
     http_req_tls_handshaking.......: avg=0s       min=0s     med=0s      max=0s      p(90)=0s       p(95)=0s      
     http_req_waiting...............: avg=155.86ms min=1.73ms med=35.28ms max=1.57s   p(90)=460.17ms p(95)=720.95ms
     http_reqs......................: 91531   998.384223/s
     iteration_duration.............: avg=2.15s    min=2s     med=2.03s   max=3.57s   p(90)=2.46s    p(95)=2.72s   
     iterations.....................: 91531   998.384223/s
     vus............................: 126     min=112            max=3500
     vus_max........................: 3500    min=3200           max=3500

```
### 분석
- 성공률 : 100%
- 처리량 : 초당 약 998개의 요청 처리, 총 91431개의 요청 처리
- 응답 시간 :
  - 평균 응답 시간 - 155.01ms
  - 중간값 응답 시간 - 35.36ms
  - 90번째 백분위 응답 시간 - 460.2ms
  - 95번째 백분위 응답 시간 - 720.98ms
- 네트워크 지연 :
  - 평균 요청 차단 시간 - 21.9µs
- 데이터 전송 :
  - 받은 데이터 - 13MB
  - 보낸 데이터 - 9.2MB

### 결론
- 잔액 조회 API는 높은 부하에서도 안정적으로 동작 하였다.
- 모든 요청이 성공했으며 평균 응답시간이 155.01ms로 아주 짧았다.

### 1.2 잔액 충전 API
```js
import http from 'k6/http';
import { check, sleep } from 'k6';
import { options, BASE_URL } from '../common/test-options.js';

export { options };

const TEST_USER_IDS = [9338, 9339, 9340, 9341];  // 테스트 데이터로 생성한 사용자 ID

export default function () {
    const userId = TEST_USER_IDS[Math.floor(Math.random() * TEST_USER_IDS.length)];

    const rechargePayload = JSON.stringify({
        amount: Math.floor(Math.random() * 10000) + 1000,
    });

    const rechargeRes = http.post(`${BASE_URL}/v1/users/${userId}/balance`, rechargePayload, {
        headers: { 'Content-Type': 'application/json' },
    });

    check(rechargeRes, {
        'recharge status is 200': (r) => r.status === 200,
    });

    sleep(1);
}
```
- 무작위로 사용자 잔액을 충전 한다.

```text
     ✓ recharge status is 200

     checks.........................: 100.00% 29908 out of 29908
     data_received..................: 4.2 MB  45 kB/s
     data_sent......................: 5.1 MB  54 kB/s
     dropped_iterations.............: 178422  1896.515247/s
     http_req_blocked...............: avg=39.28µs min=1µs    med=4µs   max=21.62ms p(90)=224µs  p(95)=267µs 
     http_req_connecting............: avg=29.28µs min=0s     med=0s    max=11.11ms p(90)=191µs  p(95)=228µs 
     http_req_duration..............: avg=5.95s   min=8.07ms med=5.09s max=11.28s  p(90)=9.49s  p(95)=10.09s
       { expected_response:true }...: avg=5.95s   min=8.07ms med=5.09s max=11.28s  p(90)=9.49s  p(95)=10.09s
     http_req_failed................: 0.00%   0 out of 29908
     http_req_receiving.............: avg=48.76µs min=6µs    med=39µs  max=5.02ms  p(90)=83µs   p(95)=101µs 
     http_req_sending...............: avg=18.53µs min=3µs    med=14µs  max=7.81ms  p(90)=28µs   p(95)=38µs  
     http_req_tls_handshaking.......: avg=0s      min=0s     med=0s    max=0s      p(90)=0s     p(95)=0s    
     http_req_waiting...............: avg=5.95s   min=8.02ms med=5.09s max=11.28s  p(90)=9.49s  p(95)=10.09s
     http_reqs......................: 29908   317.903498/s
     iteration_duration.............: avg=6.95s   min=1s     med=6.09s max=12.28s  p(90)=10.49s p(95)=11.09s
     iterations.....................: 29908   317.903498/s
     vus............................: 110     min=110            max=3500
     vus_max........................: 3500    min=3200           max=3500
```
### 분석
- 성공률 : 100%
- 처리량 : 초당 약 300개의 요청 처리, 총 29908 요청 처리
- 응답 시간 :
    - 평균 응답 시간 - 5.95s
    - 중간값 응답 시간 - 5.09s
    - 90번째 백분위 응답 시간 - 9.49s
    - 95번째 백분위 응답 시간 - 10.09s
- 네트워크 지연 :
    - 평균 요청 차단 시간 - 39.28µs
- 데이터 전송 :
    - 받은 데이터 - 4.2MB
    - 보낸 데이터 - 5.1MB

### 결론
- 최대 3,500명의 가상 사용자가 동시에 테스트를 수행했다.
- 모든 요청이 성공했으나 평균 응답 시간이 잔액 조회에 비해 상당히 오래 걸리는 것을 볼 수 있다.
- 충전 작업이 데이터베이스에 쓰기 작업을 포함하기 때문으로 보인다.
- 잔액과 관련된 API이기 때문에 100% 성공한 점은 긍정적이나 성능이 너무 느린것으로 판단 된다.

---

## 2. 주문
### 2.1 상품 주문 API
```js
import http from 'k6/http';
import { check, sleep } from 'k6';
import { options, BASE_URL } from '../common/test-options.js';

export { options };

const TEST_USER_IDS = [9338, 9339, 9340, 9341];  // 테스트 데이터로 생성한 사용자 ID

export default function () {
  const userId = TEST_USER_IDS[Math.floor(Math.random() * TEST_USER_IDS.length)];

  const orderPayload = JSON.stringify({
    userId: userId,
    totalAmount: 600,
    products: [
      { productId: 4, quantity: 1 },
    ],
  });

  const rechargeRes = http.post(`${BASE_URL}/v1/orders`, orderPayload, {
    headers: { 'Content-Type': 'application/json' },
  });

  check(rechargeRes, {
    'recharge status is 200': (r) => r.status === 200,
  });

  sleep(1);
}
```
- 무작위로 사용자가 특정 상품을 주문 한다.
```text
✓ orders status is 200

     checks.........................: 100.00% 17639 out of 17639
     data_received..................: 2.7 MB  27 kB/s
     data_sent......................: 3.9 MB  39 kB/s
     dropped_iterations.............: 181051  1781.002931/s
     http_req_blocked...............: avg=63.27µs min=1µs     med=4µs   max=8.99ms  p(90)=259µs  p(95)=298µs 
     http_req_connecting............: avg=50.92µs min=0s      med=0s    max=8.86ms  p(90)=222µs  p(95)=251µs 
     http_req_duration..............: avg=11.59s  min=11.44ms med=8.91s max=21.06s  p(90)=19.68s p(95)=19.96s
       { expected_response:true }...: avg=11.59s  min=11.44ms med=8.91s max=21.06s  p(90)=19.68s p(95)=19.96s
     http_req_failed................: 0.00%   0 out of 17639
     http_req_receiving.............: avg=53.54µs min=9µs     med=45µs  max=12.36ms p(90)=84µs   p(95)=105µs 
     http_req_sending...............: avg=22.4µs  min=3µs     med=16µs  max=7.38ms  p(90)=34µs   p(95)=47µs  
     http_req_tls_handshaking.......: avg=0s      min=0s      med=0s    max=0s      p(90)=0s     p(95)=0s    
     http_req_waiting...............: avg=11.59s  min=11.38ms med=8.91s max=21.06s  p(90)=19.68s p(95)=19.96s
     http_reqs......................: 17639   173.515256/s
     iteration_duration.............: avg=12.59s  min=1.01s   med=9.91s max=22.06s  p(90)=20.68s p(95)=20.97s
     iterations.....................: 17639   173.515256/s
     vus............................: 168     min=116            max=3500
     vus_max........................: 3500    min=3200           max=3500

```
### 분석
- 성공률 : 100%
- 처리량 : 초당 약 173 요청 처리, 총 17639 요청 처리
- 응답 시간 :
  - 평균 응답 시간 - 11.59s
  - 중간값 응답 시간 - 8.91s
  - 90번째 백분위 응답 시간 - 19.68s
  - 95번째 백분위 응답 시간 - 19.96s
- 네트워크 지연 :
  - 평균 요청 차단 시간 - 63.27µs
- 데이터 전송 :
  - 받은 데이터 - 2.7 MB
  - 보낸 데이터 - 3.9 MB

### 결론
- 상품 주문 API 역시 성공률은 100%지만 평균 응답 시간이 8.91s로 아주 오랜 시간이 걸린다.
- 가장 처리가 빠른 시간을 보면 11.44ms 정도인데 평균 응답 시간이 11.59s 인걸로 봐서 어느 지점에서
병목 현상이 발생하는 것 같다.
- 원인 분석
  - 분산 락 사용으로 인한 응답 시간 증가
    - @DistributedRLock 어노테이션을 사용해 주문 요청에 대해 분산 락이 적용되어 있습니다.
      락은 userId와 products 조합으로 생성된 키를 기반으로 동작하며, 동시성 제어를 위해 waitTime과 leaseTime을 설정하고 있습니다.
      이로 인해 다수의 동시 요청이 들어올 경우 락 대기 시간이 길어져 전체 응답 시간이 증가하는 현상이 발생한 것으로 보입니다.
  - 요청 처리 시간이 최소 11.44ms로 보이는 점을 고려할 때, 락이 없는 상태에서는 상대적으로 빠른 처리가 가능하다는 점을 알 수 있습니다.

### 2.2 상품 조회 API
```js
import http from 'k6/http';
import { check, sleep } from 'k6';
import { options, BASE_URL } from '../common/test-options.js';

export { options };

// 테스트 대상 상품 ID
const TEST_PRODUCT_IDS = [1, 2, 3, 4];

export default function () {
    // 랜덤으로 상품 ID를 선택
    const productId = TEST_PRODUCT_IDS[Math.floor(Math.random() * TEST_PRODUCT_IDS.length)];

    // 상품 조회 요청

    const response = http.get(`${BASE_URL}/v1/products/${productId}`);

    // 응답 상태와 내용 확인
    check(response, {
        'getProduct status is 200': (r) => r.status === 200,
    });

    sleep(1); // 요청 간 1초 대기
}
```

- 임의로 등록된 4개의 상품을 무작위로 조회 한다.

```text
 ✓ getProduct status is 200

     checks.........................: 100.00% 162299 out of 162299
     data_received..................: 32 MB   350 kB/s
     data_sent......................: 15 MB   166 kB/s
     dropped_iterations.............: 155473  1711.061267/s
     http_req_blocked...............: avg=10.79µs  min=0s     med=2µs     max=6.13ms  p(90)=6µs      p(95)=9µs     
     http_req_connecting............: avg=6µs      min=0s     med=0s      max=6.07ms  p(90)=0s       p(95)=0s      
     http_req_duration..............: avg=203.02ms min=1.26ms med=87.77ms max=1.26s   p(90)=537.18ms p(95)=684.38ms
       { expected_response:true }...: avg=203.02ms min=1.26ms med=87.77ms max=1.26s   p(90)=537.18ms p(95)=684.38ms
     http_req_failed................: 0.00%   0 out of 162299
     http_req_receiving.............: avg=25.53µs  min=4µs    med=15µs    max=14.29ms p(90)=50µs     p(95)=66µs    
     http_req_sending...............: avg=13.73µs  min=1µs    med=6µs     max=15.72ms p(90)=20µs     p(95)=34µs    
     http_req_tls_handshaking.......: avg=0s       min=0s     med=0s      max=0s      p(90)=0s       p(95)=0s      
     http_req_waiting...............: avg=202.99ms min=1.25ms med=87.72ms max=1.26s   p(90)=537.1ms  p(95)=684.35ms
     http_reqs......................: 162299  1786.184949/s
     iteration_duration.............: avg=1.2s     min=1s     med=1.08s   max=2.26s   p(90)=1.53s    p(95)=1.68s   
     iterations.....................: 162299  1786.184949/s
     vus............................: 4       min=4                max=3500
     vus_max........................: 3500    min=3200             max=3500

```
### 분석
- 성공률 : 100%
- 처리량 : 초당 약 1786 요청 처리, 총 162299 요청 처리
- 응답 시간 :
  - 평균 응답 시간 - 203.02ms
  - 중간값 응답 시간 - 87.77ms
  - 90번째 백분위 응답 시간 - 537.18ms
  - 95번째 백분위 응답 시간 - 684.38ms
- 네트워크 지연 :
  - 평균 요청 차단 시간 - 10.79µs
- 데이터 전송 :
  - 받은 데이터 - 32 MB
  - 보낸 데이터 - 15 MB

### 결론
- 100% 성공률과 높은 초당 처리량은 서버가 안정적이며 테스트 시나리오에서 설정한 부하 조건을 잘 처리했음을 나타낸다.
- 평균 응답 시간이 200ms대로, API 요청 처리 성능이 매우 우수하다고 판단된다.
- 중간값 응답 시간과 90/95번째 백분위 응답 시간의 차이를 보면, 일부 요청만 느리게 처리되었으며 대부분의 요청은 빠르게 응답했다.

---

## 개선 방향성
- 조회 API에 비해 성능이 안나오는 충전과 상품 주문 API에 대해 개선점을 적어 보려고 합니다.

### 상품 조회, 잔액 충전 API
- 문제점 :
  - 상품 조회, 잔액 충전 모두 분산락, 심플락이 설정되어 있음 
  - 최저 처리 시간이 8.07ms, 평균 처리 시간이 5.95s로 차이가 크며, 요청이 몰릴 경우 지연이 더욱 커짐.
  - waitTime = 5, leaseTime = 10으로 설정되어 있으나, 설정 값 조정만으로는 큰 성능 개선이 어려움
  - 테스트에서 약 5명의 사용자(userId)에 대해 약 3만 건의 충전 요청을 처리하다 보니, 특정 사용자에 대한 락 경쟁이 심화되며 병목 현상이 발생.
  - 상품 주문 마찬가지로 특정 상품에 대해서만 요청을 보내다 보니 처리 시간이 오래 걸림
- 해결 방법 :
  - 트랜 잭션 범위 최소화 : 트랜잭션이 작게 나눈다고 했지만, 여전히 작업 단위가 크다고 생각한다. 범위를 더 작게 줄여서 성능 개선을 할 수 있을 것 같다.
  - 분산 처리 :
    - 분산 환경에서 Redis 대신 Kafka와 같은 메시지 큐를 활용해서 비동기적으로 처리한다면 개선될 것이라 생각 한다.

### 결론
- 현재 충전 API의 높은 안정성과 100% 성공률은 긍정적이나, 응답 속도를 개선하지 않으면 운영 환경에서 병목 현상이 발생할 수 있습니다.
- 위 개선 방안을 적용한 후 성능을 재평가하면 API의 처리 능력과 사용자 경험 모두 크게 향상될 것입니다.

---
