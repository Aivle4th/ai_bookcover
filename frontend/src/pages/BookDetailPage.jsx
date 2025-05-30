// src/pages/BookDetailPage.jsx
import React, { useState, useEffect } from 'react';
import { useParams, Link as RouterLink, useNavigate } from 'react-router-dom';
import {
    Container, Typography, Box, CircularProgress, Alert, Button,
    Paper, Grid, CardMedia, Dialog, DialogActions, DialogContent,
    DialogContentText, DialogTitle, TextField, FormControl, InputLabel, Select, MenuItem
} from '@mui/material';
// updateBookCoverUrl을 bookService에서 가져옵니다. generateCover는 이제 사용하지 않습니다 (OpenAI 직접 호출).
import { fetchBookById, deleteBook, updateBookCoverUrl } from '../services/bookService';

// DALL-E 모델 옵션 (실제 지원 모델은 OpenAI 문서 확인 필요)
const dallEModels = [
  { value: 'dall-e-2', label: 'DALL·E 2' },
  { value: 'dall-e-3', label: 'DALL·E 3' },
];

// 품질 옵션 (DALL-E 3는 standard, hd 지원. DALL-E 2는 이 옵션 없음)
const qualityOptions = [
  { value: 'standard', label: 'Standard' },
  { value: 'hd', label: 'HD (DALL·E 3 전용)' },
];

// 스타일 옵션 (DALL-E 3 전용)
const styleOptions = [
  { value: 'vivid', label: '선명하게 (Vivid)' },
  { value: 'natural', label: '자연스럽게 (Natural)' },
];


function BookDetailPage() {
  const { id } = useParams();
  const navigate = useNavigate();
  const [book, setBook] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(''); // 페이지 로딩 또는 일반 오류
  const [isDeleting, setIsDeleting] = useState(false);
  const [openDeleteConfirm, setOpenDeleteConfirm] = useState(false);

  // AI 표지 생성 관련 상태
  const [userApiKey, setUserApiKey] = useState(''); // 사용자가 입력할 OpenAI API 키
  const [customPrompt, setCustomPrompt] = useState(''); // 사용자 지정 프롬프트
  const [selectedApiModel, setSelectedApiModel] = useState('dall-e-3'); // 기본 모델 선택
  const [selectedQuality, setSelectedQuality] = useState('standard'); // DALL-E 3 기본 품질
  const [selectedStyle, setSelectedStyle] = useState('vivid'); // DALL-E 3 기본 스타일
  const [isGeneratingCover, setIsGeneratingCover] = useState(false);
  const [coverGenerationError, setCoverGenerationError] = useState('');


  useEffect(() => {
    const loadBookDetails = async () => {
      if (!id) {
        setError('잘못된 도서 ID입니다.');
        setLoading(false);
        return;
      }
      setLoading(true);
      setError('');
      setCoverGenerationError(''); // 상세 정보 로드 시 표지 생성 오류는 초기화
      try {
        const data = await fetchBookById(id);
        setBook(data);
        // 책 제목을 기반으로 초기 프롬프트 제안
        if (data && data.title) {
          setCustomPrompt(`"${data.title}" 책의 분위기를 잘 나타내는 멋진 표지 이미지`);
        }
      } catch (err) {
        setError(err.message || `ID가 ${id}인 도서 정보를 불러오는 데 실패했습니다.`);
        console.error(err);
      } finally {
        setLoading(false);
      }
    };
    loadBookDetails();
  }, [id]);

  const handleDeleteClick = () => {
    setOpenDeleteConfirm(true);
  };

  const handleCloseDeleteConfirm = () => {
    if (!isDeleting) {
      setOpenDeleteConfirm(false);
    }
  };

  const handleConfirmDelete = async () => {
    setOpenDeleteConfirm(false);
    setIsDeleting(true);
    setError(''); // 이전 일반 오류 초기화
    try {
      await deleteBook(id);
      alert('도서가 성공적으로 삭제되었습니다.');
      navigate('/books');
    } catch (err) {
      setError(err.message || `ID가 ${id}인 도서 삭제에 실패했습니다.`); // 일반 오류에 표시
      console.error(err);
      setIsDeleting(false);
    }
  };

  const handleGenerateCover = async () => {
    if (!book) {
      setCoverGenerationError('도서 정보가 로드되지 않았습니다.');
      return;
    }
    if (!userApiKey.trim()) {
      setCoverGenerationError('OpenAI API 키를 입력해주세요.');
      return;
    }

    setIsGeneratingCover(true);
    setCoverGenerationError(''); // 이전 표지 생성 오류 초기화
    setError(''); // 이전 일반 오류도 초기화

    let finalPrompt = customPrompt.trim();
    if (!finalPrompt) {
      finalPrompt = `"${book.title}"이라는 제목의 책 표지. 주요 내용은 다음과 같음: ${book.content ? book.content.substring(0, 100) + "..." : "내용 없음"}`;
    }
    finalPrompt = finalPrompt.substring(0, selectedApiModel === 'dall-e-3' ? 4000 : 1000);

    const requestBody = {
      model: selectedApiModel,
      prompt: finalPrompt,
      n: 1,
      size: "1024x1024",
      quality: selectedApiModel === 'dall-e-3' ? selectedQuality : undefined,
      style: selectedApiModel === 'dall-e-3' ? selectedStyle : undefined,
    };
    if (requestBody.quality === undefined) delete requestBody.quality;
    if (requestBody.style === undefined) delete requestBody.style;

    try {
      console.log("OpenAI API 요청 시작:", requestBody);
      const openaiResponse = await fetch('https://api.openai.com/v1/images/generations', {
        method: 'POST',
        headers: {
          'Authorization': `Bearer ${userApiKey}`,
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(requestBody),
      });

      const openaiData = await openaiResponse.json();

      if (!openaiResponse.ok) {
        console.error('OpenAI API 오류 응답:', openaiData);
        const errorMessage = openaiData.error?.message || `OpenAI API 호출 실패 (HTTP 상태 코드: ${openaiResponse.status})`;
        throw new Error(errorMessage);
      }

      const receivedImageUrl = openaiData.data?.[0]?.url;
      if (!receivedImageUrl) {
        throw new Error('OpenAI API 응답에서 이미지 URL을 찾을 수 없습니다.');
      }
      console.log('OpenAI로부터 받은 이미지 URL:', receivedImageUrl);

      const updatedBookFromBackend = await updateBookCoverUrl(book.id, receivedImageUrl);
      setBook(updatedBookFromBackend);
      alert('새로운 AI 표지가 성공적으로 생성되고 저장되었습니다!');

    } catch (err) {
      console.error('AI 표지 생성 전체 과정 중 오류:', err);
      setCoverGenerationError(err.message || 'AI 표지 생성 중 알 수 없는 오류가 발생했습니다.');
    } finally {
      setIsGeneratingCover(false);
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

  if (error && !book) {
    return (
      <Container>
        <Alert severity="error" sx={{ mt: 2 }}>{error}</Alert>
        <Button component={RouterLink} to="/books" variant="outlined" sx={{ mt: 2 }}>
          목록으로 돌아가기
        </Button>
      </Container>
    );
  }

  if (!book && !loading) {
    return (
      <Container>
        <Alert severity="warning" sx={{ mt: 2 }}>요청하신 도서 정보를 찾을 수 없습니다.</Alert>
        <Button component={RouterLink} to="/books" variant="outlined" sx={{ mt: 2 }}>
          목록으로 돌아가기
        </Button>
      </Container>
    );
  }

  if (!book) return null;

  return (
    <Container maxWidth="lg">
      <Box sx={{ my: 4 }}>
        <Button component={RouterLink} to="/books" variant="outlined" sx={{ mb: 2 }}>
          ← 목록으로
        </Button>
        <Paper elevation={3} sx={{ p: 3 }}>
          <Grid container spacing={3}>
            <Grid item xs={12} md={4}>
              <Typography variant="h6" gutterBottom>표지 이미지</Typography>
              <CardMedia
                component="img"
                image={book.coverImageUrl || "https://via.placeholder.com/300x450.png?text=No+Cover"}
                alt={book.title}
                sx={{ width: '100%', maxHeight: 450, objectFit: 'contain', border: '1px solid #ddd', mb: 2 }}
              />
              
              <Typography variant="subtitle1" gutterBottom sx={{mt: 2}}>AI 표지 생성 도구</Typography>
              <TextField
                label="OpenAI API Key"
                type="password"
                fullWidth
                value={userApiKey}
                onChange={(e) => setUserApiKey(e.target.value)}
                margin="normal"
                helperText="API 키는 저장되지 않으며, 표지 생성 시에만 사용됩니다."
                disabled={isGeneratingCover || isDeleting}
              />
              <TextField
                label="표지 생성 프롬프트 (AI에게 전달할 요청)"
                fullWidth
                multiline
                rows={3}
                value={customPrompt}
                onChange={(e) => setCustomPrompt(e.target.value)}
                margin="normal"
                helperText="비워두면 책 제목/내용 기반으로 자동 생성됩니다."
                disabled={isGeneratingCover || isDeleting}
              />
              <Grid container spacing={2} sx={{mt:1}}>
                <Grid item xs={12} sm={4}>
                  <FormControl fullWidth margin="none" disabled={isGeneratingCover || isDeleting}> {/* margin="none" or "dense" */}
                    <InputLabel id="model-select-label">모델</InputLabel>
                    <Select
                      labelId="model-select-label"
                      value={selectedApiModel}
                      label="모델"
                      onChange={(e) => setSelectedApiModel(e.target.value)}
                      size="small"
                    >
                      {dallEModels.map((option) => (
                        <MenuItem key={option.value} value={option.value}>
                          {option.label}
                        </MenuItem>
                      ))}
                    </Select>
                  </FormControl>
                </Grid>
                <Grid item xs={12} sm={4}>
                  <FormControl fullWidth margin="none" disabled={isGeneratingCover || isDeleting || selectedApiModel === 'dall-e-2'}>
                    <InputLabel id="quality-select-label">품질</InputLabel>
                    <Select
                      labelId="quality-select-label"
                      value={selectedQuality}
                      label="품질"
                      onChange={(e) => setSelectedQuality(e.target.value)}
                      size="small"
                    >
                      {qualityOptions.map((option) => (
                        <MenuItem key={option.value} value={option.value}>
                          {option.label}
                        </MenuItem>
                      ))}
                    </Select>
                  </FormControl>
                </Grid>
                <Grid item xs={12} sm={4}>
                   <FormControl fullWidth margin="none" disabled={isGeneratingCover || isDeleting || selectedApiModel === 'dall-e-2'}>
                    <InputLabel id="style-select-label">스타일</InputLabel>
                    <Select
                      labelId="style-select-label"
                      value={selectedStyle}
                      label="스타일"
                      onChange={(e) => setSelectedStyle(e.target.value)}
                      size="small"
                    >
                      {styleOptions.map((option) => (
                        <MenuItem key={option.value} value={option.value}>
                          {option.label}
                        </MenuItem>
                      ))}
                    </Select>
                  </FormControl>
                </Grid>
              </Grid>

              <Button
                variant="contained"
                color="secondary"
                onClick={handleGenerateCover}
                disabled={isGeneratingCover || isDeleting || !userApiKey.trim()} // API 키가 비어있으면 비활성화
                fullWidth
                sx={{ mt: 2, mb:1 }}
              >
                {isGeneratingCover ? <CircularProgress size={24} color="inherit" /> : 'AI 표지 생성 요청'}
              </Button>
              {coverGenerationError && <Alert severity="error" sx={{ mt: 1 }}>{coverGenerationError}</Alert>}
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

        {/* 일반적인 오류 메시지 (예: 도서 삭제 실패 등) */}
        {error && <Alert severity="error" sx={{ mt: 2, mb:2 }}>{error}</Alert>}

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
      </Box> //

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