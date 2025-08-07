import React, { useState, useEffect } from 'react';
import { NavLink } from 'react-router-dom';
import { useAuth } from '../hooks/useAuth';
import { getMinhaConta, getExtrato } from '../services/contaService';

function ExtratoPage() {
  const { isAuthenticated, logout } = useAuth();
  const [operacoes, setOperacoes] = useState([]);
  const [conta, setConta] = useState(null);
  const [paginaInfo, setPaginaInfo] = useState({ pageNumber: 0, totalPages: 0 });
  const [filtros, setFiltros] = useState({ tipo: '', dataInicio: '', dataFim: '' });
  const [loading, setLoading] = useState(true);

  const carregarExtrato = async (numeroPagina = 0) => {
    setLoading(true);
    try {
      let numeroConta = conta?.numero;
      if (!numeroConta) {
        const dadosConta = await getMinhaConta();
        setConta(dadosConta);
        numeroConta = dadosConta.numero;
      }
      const dadosExtrato = await getExtrato(numeroConta, numeroPagina, filtros);
      setOperacoes(dadosExtrato.content);
      setPaginaInfo({
        pageNumber: dadosExtrato.number,
        totalPages: dadosExtrato.totalPages,
        isFirst: dadosExtrato.first,
        isLast: dadosExtrato.last,
      });
    } catch (error) {
      alert('Não foi possível carregar o extrato.');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    carregarExtrato(0);
  }, []);

  const formatarData = (dataString) => {
    if (!dataString) return 'N/A';
    const data = new Date(dataString);
    return data.toLocaleDateString('pt-BR') + ' ' + data.toLocaleTimeString('pt-BR');
  };

  const handleFiltroChange = (e) => {
    const { name, value } = e.target;
    setFiltros(prev => ({ ...prev, [name]: value }));
  };

  return (
    <>
      <header className="main-header">
        <div className="header-content">
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
        <h2>Extrato da Conta</h2>
        <div style={{ display: 'flex', gap: '1rem', alignItems: 'flex-end', marginBottom: '1rem' }}>
          <div className="form-group">
            <label htmlFor="dataInicio">Data Início</label>
            <input id="dataInicio" type="datetime-local" name="dataInicio" value={filtros.dataInicio} onChange={handleFiltroChange} />
          </div>
          <div className="form-group">
            <label htmlFor="dataFim">Data Fim</label>
            <input id="dataFim" type="datetime-local" name="dataFim" value={filtros.dataFim} onChange={handleFiltroChange} />
          </div>
          <div className="form-group">
            <label htmlFor="tipo">Tipo</label>
            <select id="tipo" name="tipo" value={filtros.tipo} onChange={handleFiltroChange} style={{ padding: '0.75rem', borderRadius: '5px', border: '1px solid var(--cor-borda)' }}>
              <option value="">Todos</option>
              <option value="DEPOSITO">Depósito</option>
              <option value="SAQUE">Saque</option>
              <option value="PAGAMENTO">Pagamento</option>
            </select>
          </div>
          <button className="btn" onClick={() => carregarExtrato(0)} style={{ height: 'fit-content' }}>Filtrar</button>
        </div>

        {loading ? <p>Carregando...</p> : (
          <>
            <table style={{ width: '100%', borderCollapse: 'collapse' }}>
              <thead>
                <tr>
                  <th>Data</th>
                  <th>Tipo</th>
                  <th>Descrição</th>
                  <th style={{ textAlign: 'right' }}>Valor</th>
                </tr>
              </thead>
              <tbody>
                {operacoes.length > 0 ? (
                  operacoes.map(op => (
                    <tr key={op.id}>
                      <td style={{ padding: '8px' }}>{formatarData(op.dataHora)}</td>
                      <td style={{ padding: '8px' }}>{op.tipo}</td>
                      <td style={{ padding: '8px' }}>{op.descricao}</td>
                      <td style={{ textAlign: 'right', color: op.tipo === 'DEPOSITO' ? 'green' : 'red' }}>
                        {op.valor.toFixed(2)}
                      </td>
                    </tr>
                  ))
                ) : (
                  <tr>
                    <td colSpan="4" style={{ textAlign: 'center', padding: '1rem' }}>Nenhuma operação encontrada.</td>
                  </tr>
                )}
              </tbody>
            </table>
            <div style={{ display: 'flex', justifyContent: 'center', gap: '1rem', marginTop: '1rem' }}>
              <button className="btn" onClick={() => carregarExtrato(paginaInfo.pageNumber - 1)} disabled={paginaInfo.isFirst}>Anterior</button>
              <span>Página {paginaInfo.totalPages > 0 ? paginaInfo.pageNumber + 1 : 0} de {paginaInfo.totalPages}</span>
              <button className="btn" onClick={() => carregarExtrato(paginaInfo.pageNumber + 1)} disabled={paginaInfo.isLast}>Próxima</button>
            </div>
          </>
        )}
      </main>
    </>
  );
}

export default ExtratoPage;