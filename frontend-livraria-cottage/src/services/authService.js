import axios from 'axios';
import {jwtDecode} from 'jwt-decode'

const API_URL = 'http://localhost:8080/api/auth';

export const login = async (login, password) => {
    const response = await axios.post(`${API_URL}/login`, {
        login,
        password
    });
    if (response.data.token) {
        localStorage.setItem('user', JSON.stringify(response.data));
    }
    return response.data;
};

export const logout = () => {
    localStorage.removeItem('user');
};

export const register = (login, password, role) => {
    return axios.post(`${API_URL}/register`, {
        login,
        password,
        role
    });
};

export const getToken = () => {
    const user = JSON.parse(localStorage.getItem('user'));
    return user ? user.token : null;
};

export const isAdmin = () => {
    const user = JSON.parse(localStorage.getItem('user'));
    console.log(user);
    //Problema: token não é um jwt válido
    //Servidor pode não estar gerando ou retornando um token válido
}



