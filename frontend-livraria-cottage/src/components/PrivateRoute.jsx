// src/components/PrivateRoute.jsx

import React from 'react';
import { Navigate } from 'react-router-dom';
import { getToken } from '../services/authService'; // Certifique-se que o caminho está correto

const PrivateRoute = ({ children }) => {
    const isAuthenticated = getToken();

    if (!isAuthenticated) {
        // Se não estiver autenticado, redireciona para a página de login
        return <Navigate to="/login" replace />;
    }

    // Se estiver autenticado, renderiza os componentes filhos
    return children;
};

export default PrivateRoute;