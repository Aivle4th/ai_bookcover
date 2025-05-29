// src/layouts/Layout.jsx
import React from 'react';
import { AppBar, Toolbar, Typography, Container, Box, CssBaseline } from '@mui/material';
import { Outlet } from 'react-router-dom'; // react-router-dom v6+ 에서 사용

function Layout() {
  return (
    <>
      <CssBaseline /> {/* MUI의 기본 CSS 스타일을 적용하여 브라우저 간 일관성을 높여줍니다. */}
      <Box sx={{ display: 'flex', flexDirection: 'column', minHeight: '100vh' }}>
        <AppBar position="static">
          <Toolbar>
            <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
              AI Book Cover 서비스
            </Typography>
          </Toolbar>
        </AppBar>
        <Container component="main" sx={{ mt: 4, mb: 2, flexGrow: 1 }}>
          {/* 이 Outlet 부분에 각 페이지의 내용이 렌더링됩니다. */}
          <Outlet />
        </Container>
        <Box
          component="footer"
          sx={{
            py: 3,
            px: 2,
            mt: 'auto',
            backgroundColor: (theme) =>
              theme.palette.mode === 'light'
                ? theme.palette.grey[200]
                : theme.palette.grey[800],
          }}
        >
          <Container maxWidth="sm">
            <Typography variant="body2" color="text.secondary" align="center">
              {'© AI Book Cover Project '}
              {new Date().getFullYear()}
              {'.'}
            </Typography>
          </Container>
        </Box>
      </Box>
    </>
  );
}

export default Layout;