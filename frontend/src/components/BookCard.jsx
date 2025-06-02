import React from 'react';
import { Card, CardContent, CardMedia, Typography, Button, CardActions } from '@mui/material';
import { Link } from 'react-router-dom'; // 상세 페이지로 이동하기 위해
import defaultCoverImage from '../assets/book.png'; // 👈 로컬 이미지 import 추가

// book 객체를 props로 받아서 정보를 표시합니다.
function BookCard({ book }) {
  return (
    <Card sx={{ maxWidth: 345, mb: 2, display: 'flex', flexDirection: 'column', justifyContent: 'space-between' }}>
      <CardMedia 
        component="img"
        height="140"
        // 실제 표지 이미지가 있다면 book.coverImageUrl을 사용하고, 없다면 기본 이미지 표시
        image={book.coverImageUrl || defaultCoverImage} // 👈 이 부분 수정
        alt={book.title}
        onError={(e) => { // 이미지 로드 실패 시 대체 이미지 설정
            console.warn(`이미지 로드 실패: ${e.target.src}. 기본 이미지로 대체합니다.`);
            e.target.src = defaultCoverImage;
        }}
      />
      <CardContent>
        <Typography gutterBottom variant="h5" component="div">
          {book.title || '제목 없음'}
        </Typography>
        <Typography variant="body2" color="text.secondary" sx={{ mb: 1 }}>
          작가: {book.author || '작가 정보 없음'}
        </Typography>
        <Typography variant="body2" color="text.secondary" noWrap>
          {/* 내용이 너무 길면 잘라서 보여주거나, noWrap으로 한 줄만 표시 */}
          {book.content || '내용 없음'}
        </Typography>
        <Typography variant="caption">
          등록일: {new Date(book.createdAt).toLocaleDateString()}
        </Typography>
      </CardContent>
      <CardActions>
        {/* to={`/books/${book.id}`} 는 나중에 상세 페이지 라우트를 만들 때 사용합니다. */}
        <Button size="small" component={Link} to={`/books/${book.id}`}>상세보기</Button>
      </CardActions>
    </Card>
  );
}

export default BookCard; //