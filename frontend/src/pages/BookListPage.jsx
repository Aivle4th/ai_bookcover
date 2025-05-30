import React, { useState, useEffect } from 'react';
import { Container, Typography, Grid, CircularProgress, Alert, TextField, Button, Box } from '@mui/material';
import BookCard from '../components/BookCard'; // 방금 만든 BookCard 컴포넌트
import { fetchBooks } from '../services/bookService'; // API 호출 함수
import { Link } from 'react-router-dom'; // 새 도서 등록 페이지로 이동하기 위해

function BookListPage() {
  const [books, setBooks] = useState([]); // 도서 목록을 저장할 상태
  const [loading, setLoading] = useState(true); // 로딩 중인지 여부를 나타내는 상태
  const [error, setError] = useState(''); // 오류 메시지를 저장할 상태
  const [searchTerm, setSearchTerm] = useState(''); // 검색어 상태

  // 컴포넌트가 마운트될 때 (처음 화면에 나타날 때) 도서 목록을 불러옵니다.
  // searchTerm이 변경될 때도 도서 목록을 다시 불러옵니다.
  useEffect(() => {
    const loadBooks = async () => {
      setLoading(true);
      setError('');
      try {
        const data = await fetchBooks(0, 10, searchTerm); // 페이지, 사이즈, 검색어 전달
        setBooks(data);
      } catch (err) {
        setError('도서 목록을 불러오는 데 실패했습니다. 서버가 실행 중인지, API가 올바르게 작동하는지 확인해주세요.');
        console.error(err);
      } finally {
        setLoading(false);
      }
    };

    loadBooks();
  }, [searchTerm]); // searchTerm이 바뀔 때마다 useEffect가 다시 실행됩니다.

  const handleSearchChange = (event) => {
    setSearchTerm(event.target.value);
  };

  // 간단한 검색 실행 함수 (실제 검색은 useEffect에서 searchTerm 변경 시 자동 실행)
  // 필요하다면 명시적인 검색 버튼 클릭 시 동작하도록 수정 가능
  // const handleSearchSubmit = () => {
  //   // useEffect가 searchTerm 변경을 감지하므로 별도 호출 필요 없음
  //   // 만약 버튼 클릭 시에만 검색하고 싶다면, loadBooks 함수를 여기서 호출하도록 변경
  // };

  if (loading) {
    return (
      <Container sx={{ textAlign: 'center', mt: 5 }}>
        <CircularProgress />
        <Typography>도서 목록을 불러오는 중...</Typography>
      </Container>
    );
  }

  if (error) {
    return (
      <Container>
        <Alert severity="error" sx={{ mt: 2 }}>{error}</Alert>
      </Container>
    );
  }

  return (
    <Container>
      <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', mb: 3, mt:2 }}>
        <Typography variant="h4" component="h1" gutterBottom>
          도서 목록
        </Typography>
        <Button variant="contained" color="primary" component={Link} to="/books/new">
          새 도서 등록
        </Button>
      </Box>
      <Box sx={{ display: 'flex', mb: 3 }}>
        <TextField
          fullWidth
          label="도서 제목 검색"
          variant="outlined"
          value={searchTerm}
          onChange={handleSearchChange}
          sx={{ mr: 1 }}
        />
        {/* <Button variant="outlined" onClick={handleSearchSubmit}>검색</Button> */}
        {/* 검색 버튼 없이 입력 시 바로 반영되도록 useEffect 사용 중 */}
      </Box>

      {books.length === 0 && !loading ? (
        <Typography>등록된 도서가 없습니다. 새 도서를 등록해주세요!</Typography>
      ) : (
        <Grid container spacing={3}>
          {books.map((book) => (
            <Grid item xs={12} sm={6} md={4} key={book.id}>
              <BookCard book={book} />
            </Grid>
          ))}
        </Grid>
      )}
    </Container>
  );
}

export default BookListPage;