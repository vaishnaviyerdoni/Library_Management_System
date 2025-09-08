import axios from "axios";
const API_URL = "http://localhost:8080/library/book";

export const getAllBooks = () => axios.get(`${API_URL}/allBooks`);
export const getAvailableBooks = () => axios.get(`${API_URL}/availableBooks`);
export const addBook = (book) => axios.post(API_URL, book);
export const deleteBook = (adminId, title) =>
  axios.delete(`${API_URL}/${adminId}`, { params: { title } });
export const borrowBook = (userId, title) =>
  axios.get(`${API_URL}/${userId}`, { params: { title } });
export const returnBook = (userId, title) =>
  axios.put(`${API_URL}/${userId}`, null, { params: { title } });
