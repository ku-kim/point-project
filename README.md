- [회원 포인트 서비스](#회원-포인트-서비스)
  - [프로젝트 구조](#프로젝트-구조)
  - [아키텍처 \& Data](#아키텍처--data)
    - [아키텍처](#아키텍처)
    - [Data](#data)
  - [API 기능 상세](#api-기능-상세)
  - [테스트](#테스트)
    - [단위 테스트](#단위-테스트)
    - [E2E 테스트](#e2e-테스트)
  - [실행 방법](#실행-방법)
  - [추가 사용 기술 설명](#추가-사용-기술-설명)
  - [개선할 점](#개선할-점)

---

# 회원 포인트 서비스

간단히 회원 포인트 서비스를 구현합니다.  
로깅/모니터링, CI/CD, 인증/권한은 고려하지 않았습니다.

## 프로젝트 구조
```bash
.
├── ... # 생략
├── clients # Multi Module: AWS-SQS Client (모듈)
├── point-api # Multi Module: point-api (서비스)
├── point-batch # Multi Module: point-batch (서비스(미구현))
├── point-core # Multi Module: point-core (모듈)
├── point-worker # Multi Module: point-worker (서비스, 메세지 큐 Consumer)
└── httpclient # HTTP Client를 활용한 E2E 테스트

```

## 아키텍처 & Data

### 아키텍처

멀티 모듈 서비스와 메세지 큐를 사용하여 구현합니다.   
외부 의존성은 모두 embedded를 사용합니다. (h2, redis cluster, AWS SQS)  

- point-api
  - REST API 서버
  - AWS SQS에 메세지 전달 (producer 역할)
  - 외부 의존성 Embedded 설정과 실행

- point-worker
  - AWS SQS 메세지 수신 (Consumer 역할)
  - 메세지 수신 후 로직 실행
  - worker를 기능마다 구분할 수 있으나 현재는 하나의 worker 서비스에 구현


![아키텍처](https://github.com/ku-kim/point-project/assets/57086195/d6cc4646-f135-41c4-a057-23ff18a1714d.jpeg)

### Data

포인트 서비스의 적립 후 사용 / 사용 취소 로직은 단순하지 않았습니다.  
예를 들어 유저 A가 포인트 적립이 100 / 200 / 800 순서로 총 1100원이 적립되었습니다.  
만약 포인트 사용이 -700원 이라면, 단순히 잔액 400원이 남는것을 포함해 적립된 순서로 적립금을 사용해야합니다. 또한 취소도 마찬가지로 사용의 반대로 적립금을 돌려줘야 합니다.  
이를 위해서 `Point` 와 `PointHistory` 스키마 두 개를 만들었습니다. `Point` 에서는 전체 포인트 적립, 사용, 사용취소 정보만 들어있고 `PointHistory`에서 어떤 Point가 사용되었는지 디테일하게 관리합니다.  

적립된 포인트 만큼 사용 / 사용 취소 예

![Login](https://github.com/ku-kim/point-project/assets/57086195/9d74929b-951b-4168-9cdd-1cd9a4a6e2b1)


![ERD](https://github.com/ku-kim/point-project/assets/57086195/c37cc24e-fc34-4378-9e51-140f54c9f9b8)


## API 기능 상세


<details>
<summary> 📑 READ: 1. 잔여 포인트 조회 | `GET /api/v1/members/points/balance</summary>
<div markdown="1">

- redis를 활용하여 회원의 잔여 포인트를 조회합니다.
- 포인트 적립 / 사용 / 사용 취소 api 호출 시 잔여 포인트도 함께 업데이트 됩니다.

<details>
<summary> ✅ API Request / Response</summary>
<div markdown="1">

Request

```
GET {{point-api-host}}/api/v1/members/points/balance
Content-Type: application/json
X_MEMBER_ID: 10
```

Response OK

```
HTTP/1.1 200 
Content-Type: application/json
Transfer-Encoding: chunked
Date: Mon, 19 Jun 2023 12:18:18 GMT
Keep-Alive: timeout=60
Connection: keep-alive

{
  "code": "000",
  "message": "정상 처리",
  "data": {
    "memberId": 10,
    "point": 100.00
  }
}
```

</div>
</details>

</div>
</details>

<details>
<summary> 📑 READ: 2. 포인트 적립/사용 내역 조회 | GET /api/v1/members/points </summary>
<div markdown="1">

- 회원의 포인트 적립 / 사용 내역을 조회합니다.
- 단, 포인트 사용 취소 시 취소 내역은 조회하지 않고, 이전에 사용된 사용 내역도 취소되었기에 조회되지 않습니다.  
- 페이징 기능을 지원합니다.  

<details>
<summary> ✅ API Request / Response</summary>
<div markdown="1">

Request

```
# 페이징 가능
GET {{point-api-host}}/api/v1/members/points?page=2&size=2
Content-Type: application/json
X_MEMBER_ID: 10
```

Response OK

```
HTTP/1.1 200 
Content-Type: application/json
Transfer-Encoding: chunked
Date: Mon, 19 Jun 2023 12:22:53 GMT
Keep-Alive: timeout=60
Connection: keep-alive

{
  "code": "000",
  "message": "정상 처리",
  "data": {
    "content": [
      {
        "searchId": "p-20230619212245106-e4056be6",
        "tradeId": "d851e365-a8c5-4758-b729-ec96ac4ea169",
        "messageId": "20230619212244998-1b5defc4",
        "eventType": "SAVE",
        "eventDetailType": "SAVE_EVENT",
        "point": 100.00,
        "description": "로그인 적립",
        "memberId": 10,
        "expirationDate": "2024-06-19T21:22:45.10524",
        "createdDate": "2023-06-19T21:22:45.133214"
      },
      {
        "searchId": "p-20230619210420041-d47257c1",
        "tradeId": "fe11c694-6162-4057-8f36-211f3495363a",
        "messageId": "20230619210419842-f8821a74",
        "eventType": "SAVE",
        "eventDetailType": "SAVE_EVENT",
        "point": 100.00,
        "description": "로그인 적립",
        "memberId": 10,
        "expirationDate": "2024-06-19T21:04:20.040752",
        "createdDate": "2023-06-19T21:04:20.119929"
      }
    ],
    "pageable": {
      "sort": {
        "empty": true,
        "unsorted": true,
        "sorted": false
      },
      "offset": 2,
      "pageSize": 2,
      "pageNumber": 1,
      "unpaged": false,
      "paged": true
    },
    "totalPages": 2,
    "totalElements": 4,
    "last": true,
    "size": 2,
    "number": 1,
    "sort": {
      "empty": true,
      "unsorted": true,
      "sorted": false
    },
    "numberOfElements": 2,
    "first": false,
    "empty": false
  }
}
```

Response 400
```
HTTP/1.1 400 
Content-Type: application/json
Transfer-Encoding: chunked
Date: Mon, 19 Jun 2023 12:21:53 GMT
Connection: close

{
  "code": "S101",
  "message": "정렬을 허용하지 않습니다.",
  "data": null
}
```
</div>
</details>


</div>
</details>

<details>
<summary> 📑 INSERT: 3. 포인트 적립 | POST /api/v1/points/earn </summary>
<div markdown="1">

1. point-api가 포인트 적립 API 요청을 받고 AWS SQS에 메세지를 전송합니다.
2. point-worker가 해당 메세지를 수신합니다.
3. point-worker가 `point`, `point-history` 스키마를 업데이트 합니다.


<details>
<summary> ✅ API Request / Response</summary>
<div markdown="1">

Request

```
POST {{point-api-host}}/api/v1/points/earn
Content-Type: application/json
X_MEMBER_ID: 10

{
  "tradeId": "{{$uuid}}",
  "eventType": "SAVE",
  "eventDetailType": "SAVE_BUY",
  "earnPoint": 100,
  "description": "A 제품 구입"
}
```

Response OK

```
HTTP/1.1 200 
Content-Type: application/json
Transfer-Encoding: chunked
Date: Mon, 19 Jun 2023 12:04:20 GMT
Keep-Alive: timeout=60
Connection: keep-alive

{
  "code": "000",
  "message": "정상 처리",
  "data": {
    "messageId": "20230619210419842-f8821a74",
    "tradeNo": "fe11c694-6162-4057-8f36-211f3495363a",
    "eventType": "SAVE",
    "eventDetailType": "SAVE_BUY",
    "point": 100,
    "memberId": 10
  }
}
```

Response 400

```
HTTP/1.1 400 
Content-Type: application/json
Transfer-Encoding: chunked
Date: Mon, 19 Jun 2023 12:15:33 GMT
Connection: close

{
  "code": "S001",
  "message": "요청 값 확인 필요",
  "data": null
}
```

</div>
</details>

</div>
</details>

<details>
<summary> 📑 INSERT: 4. 포인트 사용 | POST /api/v1/points/redeem</summary>
<div markdown="1">

1. point-api가 포인트 사용 API 요청을 받습니다.
2. point-api가 포인트 사용 유효성 검증 후 `point` 스키마를 업데이트합니다.
3. point-api가 AWS SQS에 메세지를 전송합니다.
4. point-worker가 해당 메세지를 수신합니다.
5. point-worker가 적립된 순서대로 `point-history` 스키마에 업데이트를 하여 포인트를 사용합니다.


<details>
<summary> ✅ API Request / Response</summary>
<div markdown="1">

Request

```
POST {{point-api-host}}/api/v1/points/redeem
Content-Type: application/json
X_MEMBER_ID: 119
```

Reponse OK

```
HTTP/1.1 200 
Content-Type: application/json
Transfer-Encoding: chunked
Date: Mon, 19 Jun 2023 12:23:45 GMT
Keep-Alive: timeout=60
Connection: keep-alive

{
  "code": "000",
  "message": "정상 처리",
  "data": {
    "messageId": "20230619212345295-e479d1f1",
    "tradeNo": "custom-trade-id-2",
    "eventType": "USE",
    "eventDetailType": "USE_BUY",
    "point": -700,
    "memberId": 119
  }
}
```

</div>
</details>

</div>
</details>

<details>
<summary> 📑 INSERT: 5. 포인트 사용 취소 | POST /api/v1/points/redeem/cancel</summary>
<div markdown="1">

1. point-api가 포인트 사용 취소 API 요청을 받습니다.
2. point-api가 포인트 사용 취소 유효성 검증 후 `point` 스키마를 업데이트합니다.
3. point-api가 AWS SQS에 메세지를 전송합니다.
4. point-worker가 해당 메세지를 수신합니다.
5. point-worker가 사용된 포인트 내역들을 `point-history` 스키마에 업데이트를 하여 포인트 사용을 취소합니다.

<details>
<summary> ✅ API Request / Response</summary>
<div markdown="1">

Request

```
POST {{point-api-host}}/api/v1/points/redeem/cancel
Content-Type: application/json
X_MEMBER_ID: 119
```

Response OK

```
HTTP/1.1 200 
Content-Type: application/json
Transfer-Encoding: chunked
Date: Mon, 19 Jun 2023 12:24:21 GMT
Keep-Alive: timeout=60
Connection: keep-alive

{
  "code": "000",
  "message": "정상 처리",
  "data": {
    "messageId": "20230619212420992-13ce8da4",
    "originSearchId": "p-20230619212345295-ceac795e",
    "tradeNo": "custom-trade-id-2",
    "eventType": "CANCEL",
    "eventDetailType": "USE_CANCEL",
    "point": 700.00,
    "memberId": 119
  }
}
```
</div>
</details>

</div>
</details>



## 테스트

### 단위 테스트

![test](https://github.com/ku-kim/point-project/assets/57086195/53cf22ed-4934-4985-8922-dcd05e876657)

### E2E 테스트

서버를 모두 띄운 후 httpclient 폴더 하위에 .http를 실행하여 E2E 테스트 가능

![e2e-1](https://github.com/ku-kim/point-project/assets/57086195/4e26ed8b-d1de-4dd3-a464-1213b51eb730)

![e2e-2](https://github.com/ku-kim/point-project/assets/57086195/5c71c57e-3273-4662-acb6-0c1ea97159ed)



## 실행 방법

외부 의존성 서비스(h2, redis , AWS SQS)는 모두 embedded로 실행되기 때문에 서비스 실행 순서를 지켜주세요.

```bash
# 실행 환경: mac

# 1. point-api 
./gradlew :point-point:bootRun

# 2. point-worker (반드시 point-api 실행 후 실행)
./gradlew :point-worker:bootRun
```

✅ point-api 실행 시 embeddded redis cluster 환경을 위해 network 연결을 Allow 해야합니다.

![redis](https://github.com/ku-kim/point-project/assets/57086195/6d3e13bb-d83c-43cf-9a83-21833693f141)


실행 결과

![run](https://github.com/ku-kim/point-project/assets/57086195/fbeec931-5d9c-45be-b7c6-a353ee0143f0)


## 추가 사용 기술 설명

- Embedded Redis Cluster: "com.github.tarossi:embedded-redis" 
  - point-api 서비스의 [config/EmbeddedRedisConfig](https://github.com/ku-kim/point-project/blob/main/point-api/src/main/java/com/github/kukim/point/api/config/EmbeddedRedisConfig.java) 에서 Embedded Redis Cluster 를 on 합니다.

- Embedded AWS SQS: "com.github.jojoldu.spring-boot-aws-mock:mock-sqs-spring-boot-starter"  
    - point-api 서비스는 :clients:aws-sqs 모듈을 의존하고 있습니다. aws-sqs 모듈의 [application-aws-sqs.yml](https://github.com/ku-kim/point-project/blob/main/clients/aws-sqs/src/main/resources/application-aws-sqs.yml)에서 embedded AWS SQS 에서 사용할 큐를 설정하고 있습니다.

## 개선할 점

- API Docs 추가
- AWS SQS 메세지 실패 시 복구 로직 추가 (Dead Letter Queue 추가)
- 부하 테스트와 성능 개선
- point-worker 도메인 별 분리
- Application 단에 통합 테스트 추가
- ...
