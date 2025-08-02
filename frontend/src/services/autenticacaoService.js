import api from './axios';

export const login = async (email, senha) => {
  const response = await api.post('/login', {
    login: email,
    senha: senha,
  });

  if (response.data.token) {
    localStorage.setItem('authToken', response.data.token);
  }

  return response.data.token;
};

export const cadastrarUsuario = async (dadosUsuario) => {
    const response = await api.post('/usuarios', dadosUsuario);
    return response.data; 
};

export const logout = () => {
  localStorage.removeItem('authToken');
};