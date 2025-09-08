import axios from "axios";
const API_URL = "http://localhost:8080/library/user";

export const registerUser = (user) => axios.post(`${API_URL}/register`, user);
export const loginUser = (loginData) => axios.post(`${API_URL}/login`, loginData);
export const getAllUsers = (adminId) => axios.get(`${API_URL}/${adminId}`);
export const deleteUser = (adminId, userId) =>
  axios.delete(`${API_URL}/${adminId}/admin`, { params: { userId } });

export const updateUserPassword = (userId, userDto) =>
  axios.put(`${API_URL}/${userId}/password`, userDto);
