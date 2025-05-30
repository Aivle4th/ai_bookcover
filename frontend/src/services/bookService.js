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

// --- 👇 새 도서 등록 함수 추가 👇 ---
export const createBook = async (bookData) => {
  try {
    // bookData 객체에는 { title: '...', author: '...', content: '...' } 정보가 들어옵니다.
    const response = await apiClient.post('/books', bookData);
    return response.data; // 성공 시 백엔드에서 반환하는 생성된 도서 정보
  } catch (error) {
    console.error('새 도서 등록 중 오류 발생:', error);
    // 오류 객체에 response가 있고, 그 안에 data가 있으면 서버가 보낸 오류 메시지를 사용할 수 있습니다.
    if (error.response && error.response.data && error.response.data.message) {
      throw new Error(error.response.data.message);
    }
    throw error; // 그 외의 경우 원래 오류를 다시 던짐
  }
};
// --- 👆 새 도서 등록 함수 추가 완료 👆 ---

// --- 👇 특정 ID의 도서 정보를 가져오는 함수 추가 👇 ---
export const fetchBookById = async (id) => {
  try {
    const response = await apiClient.get(`/books/${id}`);
    return response.data; // 성공 시 백엔드에서 반환하는 특정 도서 정보
  } catch (error) {
    console.error(`ID가 ${id}인 도서 정보를 불러오는 중 오류 발생:`, error);
    if (error.response && error.response.data && error.response.data.message) {
      throw new Error(error.response.data.message);
    } else if (error.response && error.response.status === 404) {
      throw new Error('해당 도서를 찾을 수 없습니다.');
    }
    throw error;
  }
};
// --- 👆 특정 ID의 도서 정보를 가져오는 함수 추가 완료 👆 ---
