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