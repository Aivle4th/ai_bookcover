import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { Container, Typography, TextField, Button, Box, CircularProgress, Alert } from '@mui/material';
import { fetchBookById, updateBook } from '../services/bookService';

function BookEditPage() {
  const { id } = useParams(); // URL 경로에서 도서 ID를 가져옵니다.
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    title: '',
    author: '',
    content: '',
  });
  const [loading, setLoading] = useState(true); // 초기에는 데이터 로딩을 위해 true
  const [saving, setSaving] = useState(false); // 저장 중 상태
  const [error, setError] = useState('');
  const [initialLoadError, setInitialLoadError] = useState('');

  // 컴포넌트 마운트 시 또는 id가 변경될 때 기존 도서 정보를 불러옵니다.
  useEffect(() => {
    const loadBook = async () => {
      if (!id) return;
      setLoading(true);
      setInitialLoadError('');
      try {
        const bookData = await fetchBookById(id);
        setFormData({
          title: bookData.title || '',
          author: bookData.author || '',
          content: bookData.content || '',
        });
      } catch (err) {
        setInitialLoadError(err.message || `ID가 ${id}인 도서 정보를 불러오는 데 실패했습니다.`);
        console.error(err);
      } finally {
        setLoading(false);
      }
    };
    loadBook();
  }, [id]);

  const handleChange = (event) => {
    const { name, value } = event.target;
    setFormData(prevFormData => ({
      ...prevFormData,
      [name]: value,
    }));
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    setSaving(true);
    setError('');

    if (!formData.title.trim() || !formData.author.trim()) {
      setError('제목과 작가는 필수 항목입니다.');
      setSaving(false);
      return;
    }

    try {
      const updatedBook = await updateBook(id, formData);
      console.log('도서 정보 수정 성공:', updatedBook);
      navigate(`/books/${id}`); // 수정 성공 시 해당 도서의 상세 페이지로 이동
    } catch (err) {
      setError(err.message || '도서 정보 수정에 실패했습니다. 다시 시도해주세요.');
      console.error(err);
    } finally {
      setSaving(false);
    }
  };

  if (loading) {
    return (
      <Container sx={{ textAlign: 'center', mt: 5 }}>
        <CircularProgress />
        <Typography>도서 정보를 불러오는 중...</Typography>
      </Container>
    );
  }

  if (initialLoadError) {
    return (
      <Container>
        <Alert severity="error" sx={{ mt: 2 }}>{initialLoadError}</Alert>
        <Button onClick={() => navigate('/books')} variant="outlined" sx={{ mt: 2 }}>
          목록으로 돌아가기
        </Button>
      </Container>
    );
  }

  return (
    <Container maxWidth="sm">
      <Box sx={{ my: 4 }}>
        <Typography variant="h4" component="h1" gutterBottom>
          도서 정보 수정
        </Typography>
        <form onSubmit={handleSubmit}>
          <TextField
            label="제목"
            name="title"
            value={formData.title}
            onChange={handleChange}
            fullWidth
            required
            margin="normal"
          />
          <TextField
            label="작가"
            name="author"
            value={formData.author}
            onChange={handleChange}
            fullWidth
            required
            margin="normal"
          />
          <TextField
            label="내용"
            name="content"
            value={formData.content}
            onChange={handleChange}
            fullWidth
            multiline
            rows={4}
            margin="normal"
          />

          {error && (
            <Alert severity="error" sx={{ mt: 2, mb: 2 }}>
              {error}
            </Alert>
          )}

          <Box sx={{ mt: 2, display: 'flex', justifyContent: 'space-between' }}>
            <Button
              variant="outlined"
              onClick={() => navigate(`/books/${id}`)} // 상세 페이지로 돌아가기
              disabled={saving}
            >
              취소
            </Button>
            <Button
              type="submit"
              variant="contained"
              color="primary"
              disabled={saving}
            >
              {saving ? <CircularProgress size={24} /> : '저장'}
            </Button>
          </Box>
        </form>
      </Box>
    </Container>
  );
}

export default BookEditPage;