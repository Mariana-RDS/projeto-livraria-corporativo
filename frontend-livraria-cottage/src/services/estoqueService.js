import axios from 'axios';
import { getToken } from './authService';

const API_URL = 'http://localhost:8080/api/estoque';

const getAuthHeaders = () => {
    const token = getToken();
    return token ? { Authorization: `Bearer ${token}` } : {};
};

export const getEstoques = () => {
    return axios.get(API_URL, { headers: getAuthHeaders() });
};

export const updateEstoque = (livroId, novaQuantidade) => {
    // O backend espera a nova quantidade no corpo da requisição
    return axios.put(`${API_URL}/${livroId}`, novaQuantidade, { 
        headers: {
            ...getAuthHeaders(),
            'Content-Type': 'application/json' 
        }
    });
};