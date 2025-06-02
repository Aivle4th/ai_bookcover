# 📚 AI 도서 표지 생성 및 관리 시스템 (Project: AI Book Cover)

## 🌟 프로젝트 개요 (Overview)

본 프로젝트는 사용자가 도서 정보를 효과적으로 관리(등록, 조회, 수정, 삭제)하고, 입력된 도서 정보(제목, 내용 등)를 기반으로 OpenAI의 DALL·E API를 활용하여 AI가 추천하는 도서 표지 이미지를 생성하는 기능을 제공하는 풀스택 웹 애플리케이션입니다. Spring Boot와 React를 사용하여 개발 역량을 강화하고 실제 협업 환경을 경험하는 것을 목표로 하는 미니 프로젝트입니다.

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
* Spring Boot (v3.5.0)
* Spring MVC (REST API)
* Spring Data JPA
* H2 Database (개발용 인메모리 DB)
* Lombok
* Gradle

### 프론트엔드 (Frontend)
* React (v19.1.0)
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
    * 전체 도서 목록 조회 
    * 도서 제목 기반 검색 기능 
    * 특정 도서 상세 정보 조회
    * 기존 도서 정보 수정
    * 등록된 도서 삭제 (사용자 확인 절차 포함)
* **AI 표지 생성 (OpenAI DALL·E 연동):**
    * 도서 상세 페이지에서 OpenAI API Key 입력 UI 제공
    * 사용자 지정 프롬프트 입력 및 DALL·E 모델/옵션 선택 UI 제공
    * 프론트엔드에서 직접 OpenAI API 호출하여 이미지 생성 및 URL 수신
    * 생성된 이미지 URL을 백엔드에 전송하여 해당 도서 정보에 업데이트
    * 업데이트된 표지 이미지를 프론트엔드 UI에 즉시 반영
* **기본 UI 레이아웃:**
    * 상단 앱 바, 콘텐츠 표시 영역, 하단 푸터로 구성된 일관된 레이아웃 제공

---

## 🚀 프로젝트 실행 방법 (Setup & How to Run)

### 1. 사전 준비 사항 (Prerequisites)
* Java 17 (또는 프로젝트에 맞는 JDK 버전) 설치
* Node.js (v18.x.x 이상 권장) 및 npm (또는 yarn) 설치
* IntelliJ IDEA (백엔드용), VS Code (프론트엔드용) 등 IDE 설치
* Git 설치
* OpenAI API Key (AI 표지 생성 기능을 테스트하려면 필요합니다.)

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
2.  필요한 라이브러리(의존성 패키지)를 설치합니다.
    ```bash
    npm install
    # 또는 yarn install
    ```
3.  React 개발 서버를 실행합니다.
    ```bash
    npm run dev
    # 또는 yarn dev
    ```
4.  웹 브라우저에서 터미널에 안내된 주소 (보통 `http://localhost:5173`)으로 접속합니다.

### 4. Git 저장소 및 브랜치 전략
* **원격 저장소 URL:** `https://github.com/Aivle4th/ai_bookcover.git`
* **주요 브랜치:**
    * `main`: 안정적인 최신 버전의 코드를 통합하는 브랜치. 배포의 기준이 됩니다.
    * `frontend`: 프론트엔드 개발을 진행하는 주 작업 브랜치.
    * `backend`: 백엔드 개발을 진행하는 주 작업 브랜치.


---

## 📝 API 엔드포인트 (간략 요약 - 상세 내용은 별도 API 명세서 참고)

* `GET /api/books`: 전체 도서 목록 조회 (검색 기능 포함)
* `POST /api/books`: 새 도서 등록
* `GET /api/books/{id}`: 특정 도서 상세 조회
* `PUT /api/books/{id}`: 특정 도서 정보 수정
* `DELETE /api/books/{id}`: 특정 도서 삭제
* `PUT /api/books/{id}/cover-url`: AI 표지 이미지 URL 업데이트

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

* 백엔드: 실제 데이터베이스(예: MySQL, PostgreSQL) 연동 및 JPA를 활용한 영구 데이터 관리 구현.
* 프론트엔드/백엔드: 사용자 인증 및 인가 기능 추가.
* 프론트엔드: UI/UX 개선 (더 나은 알림 메시지, 상세한 폼 유효성 검사, 반응형 디자인 등).
* 프론트엔드: 검색 기능 고도화 (다양한 조건, 정렬 등).
* 테스트 코드 작성 (백엔드 유닛/통합 테스트, 프론트엔드 컴포넌트 테스트).
* 배포 환경 구성 및 실제 배포.

---