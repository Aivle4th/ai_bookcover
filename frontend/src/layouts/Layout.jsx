// src/layouts/Layout.jsx
import React from 'react';
import {
  AppBar,
  Toolbar,
  Typography,
  Container,
  Box,
  CssBaseline,
  Button
} from '@mui/material';
import { Outlet, Link as RouterLink, useLocation } from 'react-router-dom';

export default function Layout() {
  const { pathname } = useLocation();

  return (
    <>
      <CssBaseline />

      <Box sx={{ display: 'flex', flexDirection: 'column', minHeight: '100vh' }}>
        {/* ─── 네비게이션 바 ─── */}
        <AppBar position="static" color="primary">
          <Toolbar sx={{ justifyContent: 'center', gap: 4 }}>
            <Typography
              variant="h6"
              component={RouterLink}
              to="/"
              sx={{
                flexGrow: 1,
                textAlign: 'center',
                color: 'inherit',
                textDecoration: 'none',
                fontFamily: 'Poppins, sans-serif',
                fontWeight: 600,
                fontSize: { xs: '1rem', sm: '1.25rem' }
              }}
            >
              AI Book Cover 서비스
            </Typography>

            <Button
              component={RouterLink}
              to="/books"
              sx={{
                fontFamily: 'Poppins, sans-serif',
                fontWeight: 500,
                textTransform: 'none',
                fontSize: '1rem'
              }}
              color="inherit"
            >
              도서 목록
            </Button>

            <Button
              component={RouterLink}
              to="/books/new"
              sx={{
                fontFamily: 'Poppins, sans-serif',
                fontWeight: 500,
                textTransform: 'none',
                fontSize: '1rem'
              }}
              color="inherit"
            >
              새 도서 등록
            </Button>
          </Toolbar>
        </AppBar>

        {/* ─── 홈 경로일 때만 보여줄 환영 문구 ─── */}
        {pathname === '/' && (
          <Container
            component="main"
            maxWidth="md"
            sx={{
              flexGrow: 1,
              display: 'flex',
              flexDirection: 'column',
              alignItems: 'center',
              justifyContent: 'center',
              textAlign: 'center',
              py: 6
            }}
          >
            <Typography
              variant="h4"
              gutterBottom
              sx={{
                fontFamily: 'Poppins, sans-serif',
                fontWeight: 700
              }}
            >
              메인 페이지에 오신 것을 환영합니다!
            </Typography>

            <Typography
              variant="body1"
              sx={{
                fontFamily: 'Poppins, sans-serif',
                color: 'text.secondary',
                mb: 4,
                lineHeight: 1.6
              }}
            >
              상단 메뉴 또는 버튼을 통해 {' '}
              <Typography component="span" fontWeight={600}>
                도서 목록
              </Typography> 으로 이동하거나 {' '}
              <Typography component="span" fontWeight={600}>
                새 도서 등록
              </Typography> 을 할 수 있습니다.
            </Typography>
          </Container>
        )}

        {/* ─── 페이지 컨텐츠가 여기서 렌더링 됩니다 ─── */}
        <Container
          component="main"
          maxWidth="md"
          sx={{
            flexGrow: 1,
            py: 6,
            // 홈이 아닐 땐, Outlet만 띄워 주기 위해 minHeight 정도만 설정
            minHeight: pathname === '/' ? 0 : 'inherit'
          }}
        >
          <Outlet />
        </Container>

        {/* ─── 푸터 ─── */}
        <Box
          component="footer"
          sx={{
            py: 2,
            textAlign: 'center',
            borderTop: 1,
            borderColor: 'divider'
          }}
        >
          <Typography variant="caption" color="text.secondary">
            © {new Date().getFullYear()} AI Book Cover 서비스
          </Typography>
        </Box>
      </Box>
    </>
  );
}
