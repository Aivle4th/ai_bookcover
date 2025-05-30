import React from 'react';
import { AppBar, Toolbar, Typography, Container, Box, CssBaseline, Button } from '@mui/material';
import { Outlet, Link as RouterLink } from 'react-router-dom'; // RouterLink 임포트

function Layout() {
  return (
    <>
      <CssBaseline />
      <Box sx={{ display: 'flex', flexDirection: 'column', minHeight: '100vh' }}>
        <AppBar position="static">
          <Toolbar>
            <Typography variant="h6" component={RouterLink} to="/" sx={{ flexGrow: 1, color: 'inherit', textDecoration: 'none' }}>
              AI Book Cover 서비스
            </Typography>
            <Button color="inherit" component={RouterLink} to="/books">
              도서 목록
            </Button>
            {/* 필요하다면 다른 메뉴 버튼들도 여기에 추가 */}
          </Toolbar>
        </AppBar>
        <Container component="main" sx={{ mt: 4, mb: 2, flexGrow: 1 }}>
          <Outlet />
        </Container>
        <Box
          component="footer"
          sx={{ /* ... 이전과 동일 ... */ }}
        >
          {/* ... 이전과 동일 ... */}
        </Box>
      </Box>
    </>
  );
}

export default Layout;