import React from 'react';
import { Routes, Route, Navigate } from 'react-router-dom';
import { useAuth } from '../hooks/useAuth';

import LoginPage from '../pages/LoginPage';
import HomePage from '../pages/HomePage';
import CadastroPage from '../pages/CadastroPage'; 
import ExtratoPage from '../pages/ExtratoPage';

const PrivateRoute = ({ children }) => {
  const { isAuthenticated } = useAuth();
  return isAuthenticated ? children : <Navigate to="/login" />;
};

const AppRoutes = () => {
  return (
    <Routes>
      <Route path="/login" element={<LoginPage />} />
      <Route path="/cadastro" element={<CadastroPage />} />
      
      <Route 
        path="/" 
        element={
          <PrivateRoute>
            <Navigate to="/home" />
          </PrivateRoute>
        } 
      />

      <Route 
        path="/home" 
        element={<PrivateRoute><HomePage /></PrivateRoute>} 
      />
      <Route 
        path="/extrato" 
        element={<PrivateRoute><ExtratoPage /></PrivateRoute>} 
      />

      <Route path="*" element={<Navigate to="/login" />} />
    </Routes>
  );
};

export default AppRoutes;