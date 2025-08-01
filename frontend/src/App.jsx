import React from 'react';
import { AuthProvider } from './context/AuthContext';
import AppRoutes from './routes/routes';

function App() {
  return (
    <AuthProvider>
      <AppRoutes />
    </AuthProvider>
  );
}

export default App;