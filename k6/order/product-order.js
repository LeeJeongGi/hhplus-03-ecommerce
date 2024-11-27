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
            { productStockId: 4, quantity: 1 },
        ],
    });

    const rechargeRes = http.post(`${BASE_URL}/v1/orders`, orderPayload, {
        headers: { 'Content-Type': 'application/json' },
    });

    check(rechargeRes, {
        'orders status is 200': (r) => r.status === 200,
    });

    sleep(1);
}