import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Layout from './layouts/Layout';
import BookListPage from './pages/BookListPage';
import BookCreatePage from './pages/BookCreatePage'; // BookCreatePage 임포트

// 임시 테스트용 컴포넌트 (이전에 백엔드 통신 테스트용으로 사용)
function PlaceholderHomePage() {
  // 이 컴포넌트 내용은 더 이상 백엔드 API 테스트를 직접 하지 않아도 됩니다.
  // 필요하다면 이전에 작성했던 API 호출 로직을 BookListPage.jsx처럼 실제 페이지로 옮깁니다.
  return (
    <div>
      <h1>메인 페이지에 오신 것을 환영합니다!</h1>
      <p>상단 메뉴 또는 버튼을 통해 도서 목록으로 이동하거나 새 도서를 등록할 수 있습니다.</p>
    </div>
  );
}

// (나중에 만들 새 도서 등록 페이지 컴포넌트 임포트 예시)
// import BookCreatePage from './pages/BookCreatePage';
// (나중에 만들 도서 상세 페이지 컴포넌트 임포트 예시)
// import BookDetailPage from './pages/BookDetailPage';


function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Layout />}>
          <Route index element={<PlaceholderHomePage />} />
          <Route path="books" element={<BookListPage />} />
          <Route path="books/new" element={<BookCreatePage />} /> {/* 새 도서 등록 페이지 라우트 추가 */}
          {/* <Route path="books/:id" element={<BookDetailPage />} /> */} {/* 도서 상세 페이지 (나중에 추가) */}
        </Route>
      </Routes>
    </Router>
  );
}

export default App;