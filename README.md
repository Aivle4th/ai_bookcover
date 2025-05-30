# 📚 AI 도서 표지 생성 및 관리 시스템 (Project: AI Book Cover)

## 🌟 프로젝트 개요 (Overview)

이 프로젝트는 사용자가 도서 정보를 등록, 조회, 수정, 삭제할 수 있는 웹 애플리케이션입니다. 특히, 도서 제목이나 내용과 같은 정보를 기반으로 OpenAI의 DALL·E API (또는 유사 AI)를 활용하여 자동으로 도서 표지 이미지를 생성하는 기능을 제공하는 것을 목표로 합니다. Spring Boot와 React를 사용한 풀스택 웹 애플리케이션 개발 역량 강화를 위한 미니 프로젝트입니다.

---

## 🧑‍💻 팀 정보 (Team Information)

* **팀명:** (AI 9반 24조)
* **팀원:**
    * [조장/PM] - 이철훈
    * [서기] - 이은정
    * [프론트엔드] - 김남교, 김민석, 이은정
    * [백엔드] - 이현성, 장윤호, 최재민
    * ...

---

## 🛠️ 기술 스택 (Tech Stack)

### 백엔드 (Backend)
* Java 17
* Spring Boot (v3.x.x)
* Spring MVC (REST API)
* Spring Data JPA
* H2 Database (개발용 인메모리 DB)
* Lombok
* Gradle

### 프론트엔드 (Frontend)
* React (v18.x.x)
* Vite
* JavaScript (ES6+)
* Axios (HTTP Client)
* React Router DOM (Routing)
* Material-UI (MUI) - UI Components

### 외부 API (External API)
* OpenAI DALL·E API (표지 이미지 생성용)

---

## ✨ 주요 기능 (Features Implemented So Far)

* **도서 관리 (CRUD):**
    * 새 도서 등록 (제목, 작가, 내용 입력)
    * 전체 도서 목록 조회 (페이지네이션은 백엔드 목업에서 현재 미구현)
    * 도서 제목 기반 검색 기능 (백엔드 목업에서 기본 구현)
    * 특정 도서 상세 정보 조회
    * 기존 도서 정보 수정
    * 등록된 도서 삭제 (확인 절차 포함)
* **AI 표지 생성:**
    * 도서 상세 페이지에서 AI 표지 생성 요청 UI 제공
    * 사용자 프롬프트 입력 기능 (선택 사항)
    * (현재는 목업 API 연동으로, 실제 AI 호출은 백엔드 구현 예정)
* **기본 UI 레이아웃:**
    * 상단 앱 바, 콘텐츠 영역, 하단 푸터 구성

---

## 🚀 프로젝트 실행 방법 (Setup & How to Run)

### 1. 사전 준비 사항 (Prerequisites)
* Java 17 (또는 프로젝트에 맞는 JDK 버전) 설치
* Node.js (v18.x.x 이상 권장) 및 npm (또는 yarn) 설치
* IntelliJ IDEA (백엔드용), VS Code (프론트엔드용) 등 IDE 설치
* Git 설치

### 2. 백엔드 실행 (Spring Boot - `bookcover-api` 폴더)
1.  프로젝트 루트(`ai_bookcover`) 폴더 내의 `bookcover-api` 폴더를 IntelliJ IDEA로 엽니다.
2.  IntelliJ IDEA가 Gradle 프로젝트를 인식하고 동기화할 때까지 기다립니다.
3.  `src/main/java/com/myproject/bookcover_api/BookcoverApiApplication.java` 파일을 찾아 실행합니다.
4.  서버가 정상적으로 실행되면 `http://localhost:8080` 에서 실행됩니다.
5.  H2 데이터베이스 콘솔은 `http://localhost:8080/h2-console` 로 접속 가능합니다.
    * JDBC URL: `jdbc:h2:mem:testdb`
    * User Name: `sa`
    * Password: (비워둠)

### 3. 프론트엔드 실행 (React - `frontend` 폴더)
1.  터미널을 열고 프로젝트 루트(`ai_bookcover`) 폴더 내의 `frontend` 폴더로 이동합니다.
    ```bash
    cd frontend
    ```
2.  필요한 라이브러리를 설치합니다.
    ```bash
    npm install
    # 또는 yarn install
    ```
3.  개발 서버를 실행합니다.
    ```bash
    npm run dev
    # 또는 yarn dev
    ```
4.  웹 브라우저에서 `http://localhost:5173` (또는 터미널에 안내된 주소)으로 접속합니다.

---

## 📝 API 엔드포인트 (간략 요약 - 상세 내용은 별도 API 명세서 참고)

* `GET /api/books`: 전체 도서 목록 조회 (검색 기능 포함)
* `POST /api/books`: 새 도서 등록
* `GET /api/books/{id}`: 특정 도서 상세 조회
* `PUT /api/books/{id}`: 특정 도서 정보 수정
* `DELETE /api/books/{id}`: 특정 도서 삭제
* `POST /api/books/{id}/generate-cover`: 특정 도서의 AI 표지 생성 요청

---

## 📂 폴더 구조 (간략 개요)
```bash
ai_bookcover/
├── .git/
├── .gitignore
├── README.md
├── bookcover-api/      # Spring Boot 백엔드 프로젝트
│   ├── build.gradle
│   └── src/
└── frontend/           # React 프론트엔드 프로젝트
├── package.json
└── src/
├── components/
├── layouts/
├── pages/
└── services/
```


---

## 📅 향후 계획 (To-Do / Future Enhancements)
* 실제 OpenAI DALL·E API 연동하여 표지 이미지 생성 기능 구현
* 사용자 인증 기능 추가
* 데이터베이스 H2에서 실제 영구 DB(예: MySQL, PostgreSQL)로 변경
* UI/UX 개선 및 반응형 디자인 적용
* 배포 환경 구성

---