import axios from 'axios';

// Axios 인스턴스 생성 (baseURL 등 공통 설정)
const apiClient = axios.create({
  baseURL: 'http://localhost:8080/api', // 백엔드 API 기본 URL
  timeout: 10000, // 요청 타임아웃 시간 (10초)
  headers: {
    'Content-Type': 'application/json',
  },
});

// 모든 도서 목록을 가져오는 함수
export const fetchBooks = async (page = 0, size = 10, searchTerm = '') => {
  try {
    // 검색어가 있다면 쿼리 파라미터로 추가
    const params = { page, size };
    if (searchTerm) {
      params.title = searchTerm; // 예시: 제목으로 검색 (백엔드 API 명세에 따라 수정 필요)
    }
    const response = await apiClient.get('/books', { params });
    return response.data; // 응답 데이터 (도서 목록 배열) 반환
  } catch (error) {
    console.error('도서 목록을 불러오는 중 오류 발생:', error);
    // 실제 애플리케이션에서는 사용자에게 보여줄 오류 메시지 처리 로직 필요
    throw error; // 오류를 다시 던져서 호출한 쪽에서 처리할 수 있도록 함
  }
};