// src/components/PrivateRoute.jsx

import React from 'react';
import { Navigate } from 'react-router-dom';
import { getToken } from '../services/authService'; 

const PrivateRoute = ({ children }) => {
    const isAuthenticated = getToken();

    if (!isAuthenticated) {
        return <Navigate to="/login" replace />;
    }

    return children;
};

export default PrivateRoute;