import axios from 'axios';
import { getToken } from './authService';

const API_URL = 'http://localhost:8080/api/vendas';

const getAuthHeaders = () => {
    const token = getToken();
    return token ? { Authorization: `Bearer ${token}` } : {};
};

export const getVendas = () => {
    return axios.get(`${API_URL}/buscar`, { headers: getAuthHeaders() });
};

export const createVenda = (venda) => {
    return axios.post(`${API_URL}/venda`, venda, { headers: getAuthHeaders() });
};

export const deleteVenda = (id) => {
    return axios.delete(`${API_URL}/${id}`, { headers: getAuthHeaders() });
};