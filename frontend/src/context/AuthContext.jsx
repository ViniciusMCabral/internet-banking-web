import React, { createContext, useState, useEffect } from 'react';
import { login as loginService } from '../services/autenticacaoService';

export const AuthContext = createContext({});

export const AuthProvider = ({ children }) => {
  const [token, setToken] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const storedToken = localStorage.getItem('authToken');
    if (storedToken) {
      setToken(storedToken);
    }
    setLoading(false);
  }, []);

  const login = async (email, senha) => {
    const tokenRecebido = await loginService(email, senha);
    setToken(tokenRecebido);
    localStorage.setItem('authToken', tokenRecebido);
  };

  const logout = () => {
    setToken(null);
    localStorage.removeItem('authToken');
  };

  if (loading) {
    return null; 
  }

  return (
    <AuthContext.Provider value={{ isAuthenticated: !!token, token, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
};