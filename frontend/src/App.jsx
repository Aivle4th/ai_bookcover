import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Layout from './layouts/Layout';
import BookListPage from './pages/BookListPage';
import BookCreatePage from './pages/BookCreatePage';
import BookDetailPage from './pages/BookDetailPage';
import BookEditPage from './pages/BookEditPage';

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Layout />}>
          {/* index route를 없애면 Layout에서만 홈 안내문구가 보입니다 */}
          <Route path="books" element={<BookListPage />} />
          <Route path="books/new" element={<BookCreatePage />} />
          <Route path="books/:id" element={<BookDetailPage />} />
          <Route path="books/edit/:id" element={<BookEditPage />} />
        </Route>
      </Routes>
    </Router>
  );
}

export default App;
