import React, { useState, useEffect } from 'react';
import { NavLink } from 'react-router-dom';
import { useAuth } from '../hooks/useAuth';
import { getMinhaConta } from '../services/contaService';
import DepositoForm from '../components/DepositoForm';
import SaqueForm from '../components/SaqueForm';
import PagamentoForm from '../components/PagamentoForm';

function HomePage() {
  const { isAuthenticated, logout } = useAuth();
  const [conta, setConta] = useState(null);
  const [loading, setLoading] = useState(true);
  const [erro, setErro] = useState('');

  useEffect(() => {
    getMinhaConta()
      .then(setConta)
      .catch(() => setErro('Falha ao carregar dados da conta.'))
      .finally(() => setLoading(false));
  }, []);

  const handleOperacaoSucesso = (contaAtualizada) => {
    setConta(contaAtualizada);
  };

  if (loading) return <p>Carregando...</p>;
  if (erro) return <p className="error-message">{erro}</p>;

  return (
    <>
      <header className="main-header">
        <div className="header-content container">
          <NavLink to="/home" className="header-logo">Internet Banking</NavLink>

          {isAuthenticated && (
            <div className="header-right">
              <nav className="main-navbar">
                <ul>
                  <li><NavLink to="/home" className={({ isActive }) => isActive ? "active" : ""}>Home</NavLink></li>
                  <li><NavLink to="/extrato" className={({ isActive }) => isActive ? "active" : ""}>Extrato</NavLink></li>
                </ul>
              </nav>
              <div className="header-user-info">
                <button onClick={logout} className="header-logout-button">Sair</button>
              </div>
            </div>
          )}
        </div>
      </header>

      <main className="container">
        {conta ? (
          <>
            <p><strong>Titular:</strong> {conta.usuario}</p>
            <p><strong>Agência:</strong> {conta.agencia} | <strong>Conta:</strong> {conta.numero}</p>
            <h3>Saldo: R$ {conta.saldo.toFixed(2)}</h3>
            <hr style={{ margin: '2rem 0' }} />

            <h3>Realizar Operações</h3>
            <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(300px, 1fr))', gap: '2rem', marginTop: '1rem' }}>
              <DepositoForm conta={conta} onOperacaoSucesso={handleOperacaoSucesso} />
              <SaqueForm conta={conta} onOperacaoSucesso={handleOperacaoSucesso} />
              <PagamentoForm conta={conta} onOperacaoSucesso={handleOperacaoSucesso} />
            </div>
          </>
        ) : <p>Não foi possível carregar os dados da conta.</p>}
      </main>
    </>
  );
}

export default HomePage;