// src/pages/BookDetailPage.jsx
import React, { useState, useEffect } from 'react';
import { useParams, Link as RouterLink, useNavigate } from 'react-router-dom';
import { 
    Container, Typography, Box, CircularProgress, Alert, Button, 
    Paper, Grid, CardMedia, Dialog, DialogActions, DialogContent, 
    DialogContentText, DialogTitle, TextField // TextField 추가
} from '@mui/material';
// generateCover 임포트 추가
import { fetchBookById, deleteBook, generateCover } from '../services/bookService'; 

function BookDetailPage() {
  const { id } = useParams();
  const navigate = useNavigate();
  const [book, setBook] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [isDeleting, setIsDeleting] = useState(false);
  const [openDeleteConfirm, setOpenDeleteConfirm] = useState(false);

  // --- 👇 AI 표지 생성 관련 상태 추가 👇 ---
  const [prompt, setPrompt] = useState(''); // 사용자 입력 프롬프트
  const [isGeneratingCover, setIsGeneratingCover] = useState(false); // 표지 생성 중 상태
  const [coverError, setCoverError] = useState(''); // 표지 생성 오류 메시지
  // --- 👆 AI 표지 생성 관련 상태 추가 완료 👆 ---

  useEffect(() => {
    const loadBookDetails = async () => {
      if (!id) {
        setError('잘못된 도서 ID입니다.');
        setLoading(false);
        return;
      }
      setLoading(true);
      setError('');
      setCoverError(''); // 상세 정보 로드 시 표지 생성 오류는 초기화
      try {
        const data = await fetchBookById(id);
        setBook(data);
        // 책 내용이나 제목을 기반으로 초기 프롬프트 제안 (선택 사항)
        // setPrompt(`"${data.title}"에 어울리는 멋진 표지`); 
      } catch (err) {
        setError(err.message || `ID가 ${id}인 도서 정보를 불러오는 데 실패했습니다.`);
        console.error(err);
      } finally {
        setLoading(false);
      }
    };
    loadBookDetails();
  }, [id]);

  // ... (handleDeleteClick, handleCloseDeleteConfirm, handleConfirmDelete 함수는 이전과 동일) ...
  const handleDeleteClick = () => { setOpenDeleteConfirm(true); };
  const handleCloseDeleteConfirm = () => { if (!isDeleting) setOpenDeleteConfirm(false); };
  const handleConfirmDelete = async () => { /* ... 이전과 동일 ... */ 
    setOpenDeleteConfirm(false);
    setIsDeleting(true);
    setError('');
    try {
      await deleteBook(id);
      alert('도서가 성공적으로 삭제되었습니다.');
      navigate('/books');
    } catch (err) {
      setError(err.message || `ID가 ${id}인 도서 삭제에 실패했습니다.`);
      console.error(err);
      setIsDeleting(false);
    }
  };


  // --- 👇 AI 표지 생성 핸들러 함수 추가 👇 ---
  const handleGenerateCover = async () => {
    if (!book) return;
    setIsGeneratingCover(true);
    setCoverError('');
    try {
      const promptData = prompt.trim() ? { prompt: prompt.trim() } : {}; // 프롬프트가 있으면 전달
      const updatedBook = await generateCover(book.id, promptData);
      setBook(updatedBook); // 화면에 바로 반영되도록 book 상태 업데이트
      alert('새로운 표지가 생성되었습니다! (임시 이미지)');
    } catch (err) {
      setCoverError(err.message || 'AI 표지 생성에 실패했습니다.');
      console.error(err);
    } finally {
      setIsGeneratingCover(false);
    }
  };
  // --- 👆 AI 표지 생성 핸들러 함수 추가 완료 👆 ---


  if (loading) { /* ... (이전과 동일) ... */ }
  if (error && !book) { /* ... (이전과 동일) ... */ }
  if (!book && !loading) { /* ... (이전과 동일) ... */ }
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
                sx={{ width: '100%', maxHeight: 450, objectFit: 'contain', border: '1px solid #ddd', mb: 2 }}
              />
              {/* --- 👇 AI 표지 생성 UI 추가 👇 --- */}
              <Typography variant="subtitle1" gutterBottom sx={{mt: 2}}>AI 표지 생성</Typography>
              <TextField
                label="표지 생성 프롬프트 (선택 사항)"
                fullWidth
                multiline
                rows={2}
                value={prompt}
                onChange={(e) => setPrompt(e.target.value)}
                margin="normal"
                helperText="비워두면 책 제목과 내용 기반으로 자동 생성됩니다."
                disabled={isGeneratingCover}
              />
              <Button
                variant="contained"
                color="secondary"
                onClick={handleGenerateCover}
                disabled={isGeneratingCover || isDeleting}
                fullWidth
                sx={{ mt: 1 }}
              >
                {isGeneratingCover ? <CircularProgress size={24} color="inherit" /> : 'AI 표지 생성 요청'}
              </Button>
              {coverError && <Alert severity="error" sx={{ mt: 1 }}>{coverError}</Alert>}
              {/* --- 👆 AI 표지 생성 UI 추가 완료 👆 --- */}
            </Grid>
            <Grid item xs={12} md={8}>
              {/* ... (기존 도서 정보 표시 Typography 들) ... */}
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

        {error && <Alert severity="error" sx={{ mt: 2, mb:2 }}>{error}</Alert>} {/* 기존 error는 여기에 표시 */}

        <Box sx={{ mt: 3, display: 'flex', justifyContent: 'flex-end', gap: 1 }}>
          <Button
            variant="contained"
            color="primary"
            onClick={() => navigate(`/books/edit/${book.id}`)}
            disabled={isDeleting || isGeneratingCover}
          >
            수정
          </Button>
          <Button
            variant="outlined"
            color="error"
            onClick={handleDeleteClick}
            disabled={isDeleting || isGeneratingCover}
          >
            {isDeleting ? <CircularProgress size={24} /> : '삭제'}
          </Button>
        </Box>
      </Box>

      {/* ... (삭제 확인 Dialog 이전과 동일) ... */}
       <Dialog
        open={openDeleteConfirm}
        onClose={handleCloseDeleteConfirm}
        aria-labelledby="alert-dialog-title"
        aria-describedby="alert-dialog-description"
      >
        {/* ... Dialog 내용 동일 ... */}
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