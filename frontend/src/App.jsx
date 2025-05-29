// src/App.jsx
import React, { useState, useEffect } from 'react'; // useState, useEffect 임포트
import axios from 'axios'; // axios 임포트
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Layout from './layouts/Layout';

// 임시 테스트용 컴포넌트
function PlaceholderHomePage() {
  const [backendMessage, setBackendMessage] = useState(''); // 백엔드 메시지를 저장할 상태
  const [loading, setLoading] = useState(true); // 로딩 상태
  const [error, setError] = useState(''); // 오류 메시지를 저장할 상태

  // 컴포넌트가 처음 렌더링될 때 백엔드 API를 호출합니다.
  useEffect(() => {
    axios.get('http://localhost:8080/api/hello') // Spring Boot 백엔드 주소
      .then(response => {
        console.log('백엔드로부터 받은 응답:', response.data);
        setBackendMessage(response.data); // 성공 시 메시지 저장
        setLoading(false);
      })
      .catch(error => {
        console.error('API 호출 중 오류 발생:', error);
        // CORS 오류 또는 다른 네트워크 오류일 수 있습니다.
        if (error.response) {
          // 서버가 응답했지만 상태 코드가 2xx 범위가 아님
          setError(`백엔드 API 오류: ${error.response.status} - ${error.response.data}`);
        } else if (error.request) {
          // 요청은 이루어졌으나 응답을 받지 못함 (CORS, 네트워크 문제 등)
          setError('백엔드 서버로부터 응답이 없습니다. 서버가 실행 중이고 CORS 설정이 올바른지 확인하세요.');
        } else {
          // 요청 설정 중 문제 발생
          setError(`API 요청 설정 오류: ${error.message}`);
        }
        setLoading(false);
      });
  }, []); // 빈 배열을 전달하여 컴포넌트 마운트 시 한 번만 실행되도록 함

  if (loading) {
    return <p>백엔드에서 데이터를 불러오는 중...</p>;
  }

  return (
    <div>
      <h1>메인 페이지에 오신 것을 환영합니다!</h1>
      <p>이곳에 서비스의 주요 내용이 표시될 예정입니다.</p>
      <hr />
      <h2>백엔드 API 테스트:</h2>
      {error ? (
        <p style={{ color: 'red' }}>{error}</p>
      ) : (
        <p>서버로부터 받은 메시지: <strong>{backendMessage}</strong></p>
      )}
    </div>
  );
}

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Layout />}>
          <Route index element={<PlaceholderHomePage />} />
        </Route>
      </Routes>
    </Router>
  );
}

export default App;