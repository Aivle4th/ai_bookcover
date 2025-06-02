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

## ⚙️ 프로젝트 주요 기능 상세 구현 내용

주요 구현 내용과 관련 핵심 소스 코드입니다.

### 1. 도서 CRUD API 개발 (Backend)

도서 정보의 생성(Create), 조회(Read), 수정(Update), 삭제(Delete) 기능을 위한 백엔드 API 구현 내용입니다.

* **`Book` 엔티티 클래스 정의:**
    * **역할:** 도서 정보를 데이터베이스 테이블과 매핑하는 클래스입니다.
    * **소스 코드:** `Book.java` 
```java
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="title",nullable = false, length = 200)
    private String title;

    @Column(name="author",nullable = false, length = 100)
    private String author;

    @Column(name="content",nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "cover_image_url",columnDefinition = "TEXT")
    private String coverImageUrl;

    @CreationTimestamp // 엔터티 생성 및 수정 시 자동으로 현재 시간 기록
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
```
    * **세부 내용:** `id`, `title`, `author`, `content`, `coverImageUrl`, `createdAt`, `updatedAt` 필드를 정의합니다. JPA 어노테이션 (`@Entity`, `@Id`, `@GeneratedValue`, `@Column`, `@CreationTimestamp`, `@UpdateTimestamp`) 및 Lombok (`@Data`, `@AllArgsConstructor`, `@NoArgsConstructor`)을 활용합니다.

* **`BookRepository` 인터페이스 정의 (Spring Data JPA):**
    * **역할:** 데이터베이스와의 CRUD 작업을 위한 인터페이스입니다.
    * **소스 코드:** `BookRepository.java` 
```java
public interface BookRepository extends JpaRepository<Book, Long> {
    // JpaRepository를 상속받음으로써 save(), findById(), findAll(), deleteById() 등
    // 기본적인 CRUD 메소드들이 자동으로 제공됩니다.
}
```
    * **세부 내용:** `JpaRepository<Book, Long>` 상속을 통해 기본적인 DB 작업 메소드를 자동으로 제공합니다.

* **`BookService` 인터페이스 및 `BookServiceImpl` 구현 클래스:**
    * **역할:** 도서 관리 관련 비즈니스 로직을 처리합니다.
    * **소스 코드:**
```java
public interface BookService {
    BookDto.BookResponse createBook(BookDto.BookCreate dto);
    BookDto getBookById(Long id);
    List<BookDto.BookResponse> getAllBooks();
    BookDto updateBook(Long id, BookDto.BookUpdate dto);
    void deleteBook(Long id);
    BookDto updateBookImg(Long id, BookDto.BookUpdateImgUrl dto);
}
```
    * **세부 내용:** CRUD 관련 메소드(도서 생성, 전체/개별 조회, 수정, 삭제)를 정의하고 구현합니다. `BookRepository`를 주입받아 사용합니다.

* **`BookController`:**
    * **역할:** HTTP 요청을 받아 해당 비즈니스 로직(Service)으로 연결하고 결과를 응답합니다.
    * **소스 코드:** `BookController.java` 
```java
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // 새 도서 등록 (POST /api/books)
    @PostMapping
    public ResponseEntity<BookDto.BookResponse> createBook(@RequestBody BookDto.BookCreate request) {
        BookDto.BookResponse response = bookService.createBook(request);
        return ResponseEntity.ok(response); // 또는 ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 특정 도서 상세 조회 (GET /api/books/{id})
    @GetMapping("/{id}")
    public ResponseEntity<BookDto> getBookById(@PathVariable Long id) {
        BookDto book = bookService.getBookById(id);
        return ResponseEntity.ok(book);
    }

}
```
    * **세부 내용:** `@RestController`, `@RequestMapping("/api/books")`를 사용합니다. 각 CRUD 기능에 대한 `@PostMapping`, `@GetMapping`, `@PutMapping`, `@DeleteMapping`을 매핑하고, `BookService`를 주입하여 사용합니다. `ResponseEntity`를 사용하여 응답을 처리합니다.

* **DTO (Data Transfer Object) 정의:**
    * **역할:** 계층 간 데이터 전송을 위한 객체로, API 요청/응답 본문에 사용됩니다.
    * **소스 코드:** `BookDto.java` 
```java
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BookCreate {
        private String title;
        private String author;
        private String content;

        public static Book toBookEntity(BookCreate dto) {
            Book book = new Book();
            book.setTitle(dto.getTitle());
            book.setAuthor(dto.getAuthor());
            book.setContent(dto.getContent());
            return book;
        }
    }

```
    * **세부 내용:** `BookCreate`, `BookUpdate`, `BookResponse`, `BookUpdateImgUrl` 등 주요 DTO 클래스들을 정의하고 각 필드를 명시합니다.


* **Postman 사용 API 단위 테스트:**
    * **세부 내용:** 주요 CRUD API 엔드포인트에 대한 단위 테스트를 Postman을 사용하여 진행했습니다. 

### 2. 도서 CRUD UI 개발 (Frontend)

사용자가 웹 브라우저를 통해 도서 정보를 관리할 수 있도록 UI를 구현한 내용입니다.

* **도서 목록 페이지 구현:**
    * **역할:** 등록된 도서들의 목록을 사용자에게 보여주는 페이지입니다.
    * **주요 파일:** `frontend/src/pages/BookListPage.jsx`
    * **세부 내용:** `useEffect` 훅을 사용하여 API를 호출하고 도서 목록 데이터를 로드합니다. `useState` 훅으로 목록, 로딩, 오류 상태를 관리합니다. `map()` 함수를 사용하여 각 도서 정보를 `BookCard.jsx` 컴포넌트로 렌더링합니다.

* **API 호출하여 도서 목록 표시 (서비스 모듈):**
    * **역할:** 백엔드 API와 통신하는 함수들을 모듈화하여 관리합니다.
    * **주요 파일:** `frontend/src/services/bookService.js`
    * **세부 내용:** `axios` 인스턴스를 생성하고 `baseURL`을 설정합니다. `fetchBooks()` 함수를 구현하여 도서 목록을 비동기적으로 가져옵니다.

* **검색 기능 UI 및 API 연동:**
    * **역할:** 도서 목록에서 제목을 기반으로 도서를 검색하는 기능을 제공합니다.
    * **주요 파일:** `frontend/src/pages/BookListPage.jsx`, `frontend/src/services/bookService.js`
    * **세부 내용:** MUI `TextField`를 사용하여 검색창 UI를 구현합니다. `useState`로 검색어(`searchTerm`)를 관리하고, `searchTerm` 변경 시 `useEffect`를 통해 `fetchBooks(searchTerm)`를 호출하여 API에 검색어를 전달하고 결과를 반영합니다.

* **새 도서 등록 페이지 구현:**
    * **역할:** 사용자가 새 도서 정보를 입력하고 등록할 수 있는 페이지입니다.
    * **주요 파일:** `frontend/src/pages/BookCreatePage.jsx`
    * **세부 내용:** MUI `TextField`, `Button` 등을 사용하여 입력 폼 UI를 구성합니다. `useState`로 폼 데이터(제목, 작가, 내용)를 관리합니다.

* **폼 입력 값으로 도서 생성 API 호출:**
    * **역할:** 입력된 도서 정보를 백엔드 API로 전송하여 시스템에 저장합니다.
    * **주요 파일:** `frontend/src/pages/BookCreatePage.jsx`, `frontend/src/services/bookService.js`
    * **세부 내용:** 폼 제출 시 `handleSubmit` 함수 내에서 `bookService.createBook(formData)`를 호출합니다. 등록 성공 시 `useNavigate` 훅을 사용하여 목록 페이지로 이동합니다.

* **도서 상세 정보 페이지:**
    * **역할:** 특정 도서의 상세 정보를 보여주는 페이지입니다.
    * **주요 파일:** `frontend/src/pages/BookDetailPage.jsx`
    * **세부 내용:** `useParams` 훅을 사용하여 URL에서 도서 ID를 획득합니다. `useEffect`로 `bookService.fetchBookById(id)`를 호출하여 해당 도서 데이터를 로드합니다. 도서 정보(제목, 작가, 내용, 표지 이미지, 등록일, 수정일 등)를 화면에 표시하고, 수정/삭제 버튼을 제공합니다.

### 3. 백엔드 개발 (AI 표지 생성 API)

도서 정보를 기반으로 AI를 통해 표지 이미지를 생성하고 관리하는 백엔드 기능입니다. (현재 프론트엔드에서 OpenAI 직접 호출 후 백엔드에 URL을 업데이트하는 방식으로 구현 중)

* **표지 이미지 URL 업데이트 API 엔드포인트 추가:**
    * **역할:** 프론트엔드에서 OpenAI API를 통해 생성된 표지 이미지 URL을 받아 해당 도서 정보에 업데이트합니다.
    * **소스 코드:** `BookController.java` 
```
    @PutMapping("/{id}/cover-url")
    public ResponseEntity<BookDto> updateBookCoverUrl(@PathVariable Long id, @RequestBody BookDto.BookUpdateImgUrl request) {
        System.out.println("백엔드: /api/books/" + id + "/cover-url (PUT) 호출됨. 요청 본문: " + request); // 요청 DTO 로그 추가
        // coverImageUrl 필드가 있는지 확인하려면 request.getCoverImageUrl() 등을 로깅
        System.out.println("백엔드: 전달받은 coverImageUrl: " + (request != null ? request.getCoverImageUrl() : "null"));

        BookDto updated = bookService.updateBookImg(id, request); // 또는 updateBookCoverUrl
        System.out.println("백엔드: 서비스에서 반환된 업데이트된 BookDto: " + updated); // 서비스 반환값 로그 추가
        return ResponseEntity.ok(updated);
    }
```
    * **세부 내용:** `PUT /api/books/{id}/cover-url` 엔드포인트를 정의합니다. `@RequestBody`로 `BookDto.BookUpdateImgUrl` (포함 필드: `coverImageUrl`) DTO를 받아 처리합니다.


### 4. 프론트엔드 개발 (AI 표지 생성 UI 및 연동)

사용자가 AI 표지 생성 기능을 이용할 수 있도록 UI를 제공하고 관련 API와 연동합니다.

* **도서 상세 페이지에 "표지 생성" 관련 UI 요소 추가:**
    * **역할:** 사용자가 AI 표지 생성을 요청하고 관련 옵션을 설정할 수 있는 인터페이스를 제공합니다.
    * **주요 파일:** `frontend/src/pages/BookDetailPage.jsx`
    * **세부 내용:** OpenAI API 키 입력 필드 (`TextField type="password"`), 사용자 지정 프롬프트 입력 `TextField`, DALL·E 모델/품질/스타일 선택 `Select` 컴포넌트, "AI 표지 생성 요청" `Button` UI를 구현합니다. `useState`를 사용하여 관련 상태(API 키, 프롬프트, 선택 옵션, 로딩 상태, 오류 메시지)를 관리합니다.

* **버튼 클릭 시 API 호출 로직:**
    * **역할:** 사용자의 요청에 따라 OpenAI API를 직접 호출하고, 그 결과를 백엔드에 전달하여 저장합니다.
    * **주요 파일:** `frontend/src/pages/BookDetailPage.jsx` (주요 로직), `frontend/src/services/bookService.js` (`updateBookCoverUrl` 함수)
    * **세부 내용:**
        * `handleGenerateCover` 함수: API 키 유효성 검사, 요청 본문(프롬프트, 모델, 옵션)을 구성합니다.
        * 브라우저 내장 `fetch` API를 사용하여 OpenAI 이미지 생성 API (`https://api.openai.com/v1/images/generations`)를 직접 호출합니다. (요청 헤더에 `Authorization: Bearer ${userApiKey}` 포함)
        * OpenAI API 응답에서 이미지 URL을 추출합니다.
        * 추출된 이미지 URL을 사용하여 `bookService.updateBookCoverUrl(bookId, receivedImageUrl)` 함수를 호출하고, 백엔드에 이미지 URL 업데이트를 요청합니다.
        * 이미지 생성 및 DB 업데이트 과정 동안 사용자에게 로딩 인디케이터를 보여주고, 각 단계별 오류 발생 시 사용자에게 적절한 알림을 제공합니다.

* **반환된 이미지 URL을 화면에 표시:**
    * **역할:** 새로 생성/업데이트된 표지 이미지를 사용자에게 즉시 보여줍니다.
    * **주요 파일:** `frontend/src/pages/BookDetailPage.jsx`
    * **세부 내용:** 백엔드로부터 업데이트된 도서 정보를 받아 `book` 상태를 업데이트하고, `CardMedia` 컴포넌트를 통해 새로운 `coverImageUrl`을 화면에 반영하여 이미지를 갱신합니다.

---


## 📅 향후 계획 (To-Do / Future Enhancements)

* 백엔드: 실제 데이터베이스(예: MySQL, PostgreSQL) 연동 및 JPA를 활용한 영구 데이터 관리 구현.
* 프론트엔드/백엔드: 사용자 인증 및 인가 기능 추가.
* 프론트엔드: UI/UX 개선 (더 나은 알림 메시지, 상세한 폼 유효성 검사, 반응형 디자인 등).
* 프론트엔드: 검색 기능 고도화 (다양한 조건, 정렬 등).
* 테스트 코드 작성 (백엔드 유닛/통합 테스트, 프론트엔드 컴포넌트 테스트).
* 배포 환경 구성 및 실제 배포.

---