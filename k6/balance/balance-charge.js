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