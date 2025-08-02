import React, { useState } from 'react';
import { sacar } from '../services/contaService';

function SaqueForm({ conta, onOperacaoSucesso }) {
  const [valor, setValor] = useState('');
  const [loading, setLoading] = useState(false);
  const [erro, setErro] = useState('');
  const [sucesso, setSucesso] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setErro('');
    setSucesso('');
    try {
      const contaAtualizada = await sacar(conta.numero, parseFloat(valor));
      setSucesso(`Saque de R$ ${parseFloat(valor).toFixed(2)} realizado com sucesso!`);
      setValor('');
      onOperacaoSucesso(contaAtualizada);
    } catch (error) {
      const msgErro = error.response?.data?.error || "Erro ao realizar saque.";
      setErro(msgErro);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="operacao-form">
      <h4>Realizar Saque</h4>
      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label htmlFor="saque-valor">Valor</label>
          <input id="saque-valor" type="number" step="0.01" value={valor} onChange={(e) => setValor(e.target.value)} required />
        </div>
        {erro && <p className="error-message">{erro}</p>}
        {sucesso && <p className="success-message">{sucesso}</p>}
        <button type="submit" className="btn" disabled={loading}>
          {loading ? 'Processando...' : 'Sacar'}
        </button>
      </form>
    </div>
  );
}

export default SaqueForm;