// src/main.jsx
import React from 'react';
import ReactDOM from 'react-dom/client';
import App from './App';
import './index.css'; // 기본 CSS
import { ThemeProvider, createTheme } from '@mui/material/styles'; // MUI 테마 관련 임포트
import CssBaseline from '@mui/material/CssBaseline'; // MUI CSS 초기화 도구

// 기본 MUI 테마 생성 (나중에 커스터마이징 가능)
const theme = createTheme();

ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    <ThemeProvider theme={theme}>
      <CssBaseline /> {/* Layout.jsx 에서 이미 사용했지만, 여기서 한 번 더 감싸줘도 좋습니다. */}
      <App />
    </ThemeProvider>
  </React.StrictMode>,
);