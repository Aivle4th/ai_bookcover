// src/pages/BookDetailPage.jsx
import React, { useState, useEffect } from 'react';
import { useParams, Link as RouterLink, useNavigate } from 'react-router-dom';
import { 
    Container, 
    Typography, 
    Box, 
    CircularProgress, 
    Alert, 
    Button, 
    Paper, 
    Grid, 
    CardMedia,
    Dialog,
    DialogActions,
    DialogContent,
    DialogContentText,
    DialogTitle
} from '@mui/material';
import { fetchBookById, deleteBook } from '../services/bookService';

function BookDetailPage() {
  const { id } = useParams(); // URL 경로에서 :id 부분을 가져옵니다.
  const navigate = useNavigate();
  const [book, setBook] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [isDeleting, setIsDeleting] = useState(false); // 삭제 진행 중 상태
  const [openDeleteConfirm, setOpenDeleteConfirm] = useState(false); // 삭제 확인 대화상자 표시 여부

  useEffect(() => {
    const loadBookDetails = async () => {
      if (!id) {
        setError('잘못된 도서 ID입니다.');
        setLoading(false);
        return;
      }
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

  const handleDeleteClick = () => {
    setOpenDeleteConfirm(true); // 삭제 확인 대화상자 열기
  };

  const handleCloseDeleteConfirm = () => {
    if (!isDeleting) { // 삭제 중이 아닐 때만 닫기 허용
        setOpenDeleteConfirm(false); // 삭제 확인 대화상자 닫기
    }
  };

  const handleConfirmDelete = async () => {
    setOpenDeleteConfirm(false); // 대화상자 먼저 닫기
    setIsDeleting(true);
    setError('');
    try {
      await deleteBook(id);
      alert('도서가 성공적으로 삭제되었습니다.'); // 간단한 알림
      navigate('/books'); // 삭제 성공 시 도서 목록 페이지로 이동
    } catch (err) {
      setError(err.message || `ID가 ${id}인 도서 삭제에 실패했습니다.`);
      console.error(err);
      setIsDeleting(false); // 에러 발생 시 삭제 중 상태 해제
    }
    // 성공 시에는 페이지 이동하므로 setIsDeleting(false)를 굳이 호출 안 해도 되지만,
    // 만약 페이지 이동 로직이 없다면 여기서도 호출 필요
  };

  if (loading) {
    return (
      <Container sx={{ textAlign: 'center', mt: 5 }}>
        <CircularProgress />
        <Typography>도서 정보를 불러오는 중...</Typography>
      </Container>
    );
  }

  if (error && !book) { // 초기 로딩 시 에러가 발생했고, book 데이터가 없을 때
    return (
      <Container>
        <Alert severity="error" sx={{ mt: 2 }}>{error}</Alert>
        <Button component={RouterLink} to="/books" variant="outlined" sx={{ mt: 2 }}>
          목록으로 돌아가기
        </Button>
      </Container>
    );
  }

  if (!book && !loading) { // 로딩이 끝났는데 book 데이터가 없는 경우 (404 등)
    return (
      <Container>
        <Alert severity="warning" sx={{ mt: 2 }}>요청하신 도서 정보를 찾을 수 없습니다.</Alert>
        <Button component={RouterLink} to="/books" variant="outlined" sx={{ mt: 2 }}>
          목록으로 돌아가기
        </Button>
      </Container>
    );
  }

  // book 객체가 아직 null이면 (위의 조건들에서 걸러지지 않은 매우 드문 경우) 렌더링하지 않음
  if (!book) return null;

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
                sx={{ width: '100%', maxHeight: 450, objectFit: 'contain', border: '1px solid #ddd' }}
              />
            </Grid>
            <Grid item xs={12} md={8}>
              <Typography variant="h3" component="h1" gutterBottom>
                {book.title}
              </Typography>
              <Typography variant="h6" color="text.secondary" gutterBottom>
                작가: {book.author || "정보 없음"}
              </Typography>
              <Typography variant="body2" color="text.secondary" gutterBottom>
                등록일: {book.createdAt ? new Date(book.createdAt).toLocaleDateString() : 'N/A'}
              </Typography>
              <Typography variant="body2" color="text.secondary" gutterBottom sx={{ mb: 2 }}>
                수정일: {book.updatedAt ? new Date(book.updatedAt).toLocaleDateString() : 'N/A'}
              </Typography>
              <Typography variant="body1" paragraph sx={{ whiteSpace: 'pre-wrap', maxHeight: '300px', overflowY: 'auto', border: '1px solid #eee', p:1 }}>
                {book.content || "내용 없음"}
              </Typography>
            </Grid>
          </Grid>
        </Paper>

        {error && <Alert severity="error" sx={{ mt: 2 }}>{error}</Alert>} {/* 삭제 시 발생하는 에러 표시용 */}

        <Box sx={{ mt: 3, display: 'flex', justifyContent: 'flex-end', gap: 1 }}>
          <Button
            variant="contained"
            color="primary"
            onClick={() => navigate(`/books/edit/${book.id}`)}
            disabled={isDeleting} // 삭제 중일 때 수정 버튼도 비활성화
          >
            수정
          </Button>
          <Button
            variant="outlined"
            color="error"
            onClick={handleDeleteClick}
            disabled={isDeleting}
          >
            {isDeleting ? <CircularProgress size={24} /> : '삭제'}
          </Button>
          {/* // 향후 추가될 AI 표지 생성 버튼 예시
          <Button variant="contained" color="secondary">
            AI 표지 생성
          </Button> 
          */}
        </Box>
      </Box>

      {/* 삭제 확인 대화상자 */}
      <Dialog
        open={openDeleteConfirm}
        onClose={handleCloseDeleteConfirm}
        aria-labelledby="alert-dialog-title"
        aria-describedby="alert-dialog-description"
      >
        <DialogTitle id="alert-dialog-title">
          {"도서 삭제 확인"}
        </DialogTitle>
        <DialogContent>
          <DialogContentText id="alert-dialog-description">
            정말로 "{book.title}" 도서를 삭제하시겠습니까? 이 작업은 되돌릴 수 없습니다.
          </DialogContentText>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleCloseDeleteConfirm} color="primary" disabled={isDeleting}>
            취소
          </Button>
          <Button onClick={handleConfirmDelete} color="error" autoFocus disabled={isDeleting}>
            {isDeleting ? <CircularProgress size={24} /> : '삭제 확인'}
          </Button>
        </DialogActions>
      </Dialog>
    </Container>
  );
}

export default BookDetailPage;