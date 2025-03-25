# scheduler


![ERD](https://github.com/classseoha/scheduler/blob/main/ERD.png)

## 프로젝트 개요

* 일정 관리 앱 서버 만들기
* 간단한 일정 관리 REST API
* 사용자가 할 일을 등록하고, 수정 및 삭제할 수 있도록 지원
* 특정 작성자나 날짜를 기준으로 일정 조회 가능

## 주요 기능

**1. 일정 생성 (Create)**

* 할일, 작성자명, 비밀번호, 작성일/수정일을 포함하여 일정 생성
* 일정 ID는 자동으로 생성됨
* 최초 작성 시 작성일과 수정일은 동일한 값으로 저장됨

**2. 전체 일정 조회 (Read All)**

* 작성자명 또는 수정일을 기준으로 일정 조회 가능
* 조건이 없을 경우 모든 일정을 조회
* 수정일 기준 내림차순 정렬

**3. 특정 일정 조회 (Read One)**

* 일정의 고유 ID를 이용하여 단일 일정 정보 조회

**4. 일정 수정 (Update)**

* 할일, 작성자명만 수정 가능
* 수정 요청 시 비밀번호가 필요함
* 작성일은 변경할 수 없으며, 수정일은 수정 요청 시 갱신됨

**5. 일정 삭제 (Delete)**

* 일정 삭제 시 비밀번호가 필요함

## API 명세

### 📌 POST / 일정 생성 API
* Endpoint : POST /schedules
* 설명 : 새로운 일정을 생성
* 요청 예시 (JSON)

{

"name": "이름",

"todo": "할일",

"password": "1234"

}

* 응답 예시(JSON)

{

"id": 1,

"name": "이름",

"todo": "할일",

"createdAt": "2025-03-21T10:00:00",

"updatedAt": "2025-03-21T10:00:00"

}

* 성공 응답 (Error - 200 OK)
* 실패 응답 (Error - 400 Bad Request)

### 📌 GET / 검색 일정 조회 API
* Endpoint : GET /schedules
* 설명 : 이름, 수정일자를 검색하여 등록된 일정 목록 조회
* 요청 예시 (JSON)

{

"name": "이름",

"userUpdatedAt": "YYYY-MM-DD",

}

* 응답 예시 (JSON)

[

{

"id": 1,

"name": "이름",

"todo": "할일",

"createdAt": "2025-03-21T10:00:00",

"updatedAt": "2025-03-21T10:00:00"

},

{

"id": 2,

"name": "이름",

"todo": "할일",

"createdAt": "2025-03-21T10:00:00",

"updatedAt": "2025-03-21T10:00:00"

}

]

* 성공 응답 (Error - 200 OK)
* 실패 응답 (Error - 400 Bad Request, 404 NOT FOUND)

### 📌 GET / 선택 일정 조최 API
* Endpoint : GET /schedules/{id}
* 설명 : 특정 일정 상세 조회
* 요청 예시 (param)

GET /schedules/1

* 응답 예시 (JSON)

{

"id": 1,

"name": "이름",

"todo": "할일",

"createdAt": "2025-03-21T10:00:00",

"updatedAt": "2025-03-21T10:00:00"

}

* 성공 응답 (Error - 200 OK)
* 실패 응답 (Error - 400 Bad Request, 404 NOT FOUND)

### 📌 PATCH / 선택 일정 수정 API
* Endpoint : PUT /schedules/{id}
* 설명 : 선택한 일정 내용 수정 (할일, 작성자명 변경 가능)
* 요청 예시 (JSON)

{

"name": "이름",

"todo": "할일",

"password": "1234"

}

* 응답 예시 (JSON)

{

"id": 1,

"name": "홍길동",

"todo": "할일",

"createdAt": "2025-03-21T10:00:00",

"updatedAt": "2025-03-21T12:30:00"

}

* 성공 응답 (Error - 200 OK)
* 실패 응답 (Error - 400 Bad Request, 404 NOT FOUND)

### 📌 DELETE / 선택 일정 삭제 API
* Endpoint : DELETE /schedules/{id}
* 설명 : 선택한 일정 삭제
* 요청 예시 (JSON)

{

"password": "1234"

}

* 응답 예시 (JSON)

{

NONE

}

* 성공 응답 (Error - 200 OK)
* 실패 응답 (Error - 400 Bad Request, 404 NOT FOUND)




