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