import axios from 'axios';
import { getToken } from './authService';

const API_URL = 'http://localhost:8080/api';

const getAuthHeaders = () => {
    const token = getToken();
    return token ? { Authorization: `Bearer ${token}` } : {};
};

export const getLivros = () => {
    return axios.get(`${API_URL}/livros`, { headers: getAuthHeaders() });
};

export const createLivro = (livro) => {
    return axios.post(`${API_URL}/livros/create`, livro, { headers: getAuthHeaders() });
};

export const deleteLivro = (id) => {
    return axios.delete(`${API_URL}/livros/${id}`, { headers: getAuthHeaders() });
};