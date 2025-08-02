import React, { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { cadastrarUsuario } from '../services/autenticacaoService';

function CadastroPage() {
  const [nome, setNome] = useState('');
  const [cpf, setCpf] = useState('');
  const [email, setEmail] = useState('');
  const [senha, setSenha] = useState('');
  const [erro, setErro] = useState('');
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  const handleCadastro = async (e) => {
    e.preventDefault();
    setLoading(true);
    setErro('');
    try {
      await cadastrarUsuario({ nome, cpf, email, senha });
      alert('Cadastro realizado com sucesso!');
      navigate('/login');
    } catch (error) {
      const msgErro = error.response?.data?.error || "Erro ao realizar cadastro.";
      setErro(msgErro);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="form-container">
      <h2>Cadastro de Novo Usuário</h2>
      <form onSubmit={handleCadastro}>
        <div className="form-group">
          <label htmlFor="nome">Nome Completo</label>
          <input id="nome" type="text" value={nome} onChange={(e) => setNome(e.target.value)} required />
        </div>
        <div className="form-group">
          <label htmlFor="cpf">CPF</label>
          <input id="cpf" type="text" value={cpf} onChange={(e) => setCpf(e.target.value)} required />
        </div>
        <div className="form-group">
          <label htmlFor="email">E-mail</label>
          <input id="email" type="email" value={email} onChange={(e) => setEmail(e.target.value)} required />
        </div>
        <div className="form-group">
          <label htmlFor="senha">Senha</label>
          <input id="senha" type="password" value={senha} onChange={(e) => setSenha(e.target.value)} required />
        </div>
        
        {erro && <p className="error-message">{erro}</p>}
        
        <button type="submit" className="btn" disabled={loading}>
          {loading ? 'Cadastrando...' : 'Cadastrar'}
        </button>
      </form>
      <p>Já tem uma conta? <Link to="/login">Faça o login</Link></p>
    </div>
  );
}

export default CadastroPage;