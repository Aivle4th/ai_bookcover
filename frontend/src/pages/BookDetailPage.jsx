import React, { useState, useEffect } from 'react';
import { useParams, Link as RouterLink, useNavigate } from 'react-router-dom';
import { Container, Typography, Box, CircularProgress, Alert, Button, Paper, Grid, CardMedia } from '@mui/material';
import { fetchBookById } from '../services/bookService';

function BookDetailPage() {
  const { id } = useParams(); // URL 경로에서 :id 부분을 가져옵니다.
  const navigate = useNavigate();
  const [book, setBook] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    const loadBookDetails = async () => {
      if (!id) return; // id가 없으면 실행하지 않음

      setLoading(true);
      setError('');
      try {
        const data = await fetchBookById(id);
        setBook(data);
      } catch (err) {
        setError(err.message || `ID가 ${id}인 도서 정보를 불러오는 데 실패했습니다.`);
        console.error(err);
      } finally {
        setLoading(false);
      }
    };

    loadBookDetails();
  }, [id]); // id 값이 변경될 때마다 useEffect를 다시 실행

  if (loading) {
    return (
      <Container sx={{ textAlign: 'center', mt: 5 }}>
        <CircularProgress />
        <Typography>도서 정보를 불러오는 중...</Typography>
      </Container>
    );
  }

  if (error) {
    return (
      <Container>
        <Alert severity="error" sx={{ mt: 2 }}>{error}</Alert>
        <Button component={RouterLink} to="/books" variant="outlined" sx={{ mt: 2 }}>
          목록으로 돌아가기
        </Button>
      </Container>
    );
  }

  if (!book) {
    // 로딩이 끝났는데 book 데이터가 없는 경우 (이론적으로는 error에서 걸러질 수 있음)
    return (
      <Container>
        <Alert severity="warning" sx={{ mt: 2 }}>도서 정보를 찾을 수 없습니다.</Alert>
        <Button component={RouterLink} to="/books" variant="outlined" sx={{ mt: 2 }}>
          목록으로 돌아가기
        </Button>
      </Container>
    );
  }

  return (
    <Container maxWidth="md">
      <Box sx={{ my: 4 }}>
        <Button component={RouterLink} to="/books" variant="outlined" sx={{ mb: 2 }}>
          ← 목록으로
        </Button>
        <Paper elevation={3} sx={{ p: 3 }}>
          <Grid container spacing={3}>
            <Grid item xs={12} md={4}>
              <CardMedia
                component="img"
                image={book.coverImageUrl || "https://via.placeholder.com/300x450.png?text=No+Cover"}
                alt={book.title}
                sx={{ width: '100%', maxHeight: 450, objectFit: 'contain' }}
              />
            </Grid>
            <Grid item xs={12} md={8}>
              <Typography variant="h3" component="h1" gutterBottom>
                {book.title}
              </Typography>
              <Typography variant="h6" color="text.secondary" gutterBottom>
                작가: {book.author}
              </Typography>
              <Typography variant="body2" color="text.secondary" gutterBottom>
                등록일: {book.createdAt ? new Date(book.createdAt).toLocaleDateString() : 'N/A'}
              </Typography>
              <Typography variant="body2" color="text.secondary" gutterBottom sx={{ mb: 2 }}>
                수정일: {book.updatedAt ? new Date(book.updatedAt).toLocaleDateString() : 'N/A'}
              </Typography>
              <Typography variant="body1" paragraph sx={{ whiteSpace: 'pre-wrap' }}>
                {book.content}
              </Typography>
            </Grid>
          </Grid>
        </Paper>
        <Box sx={{ mt: 3, display: 'flex', justifyContent: 'flex-end', gap: 1 }}>
          {/* 향후 수정, 삭제, AI 표지 생성 버튼 추가 위치 */}
          {/* <Button variant="contained" color="primary" onClick={() => navigate(`/books/edit/${book.id}`)}> 
            수정
          </Button>
          <Button variant="outlined" color="error">
            삭제
          </Button>
          <Button variant="contained" color="secondary">
            AI 표지 생성
          </Button> 
          */}
        </Box>
      </Box>
    </Container>
  );
}

export default BookDetailPage;