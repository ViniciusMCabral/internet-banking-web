import React, { useState } from 'react';
import { depositar } from '../services/contaService';

function DepositoForm({ conta, onOperacaoSucesso }) {
  const [valor, setValor] = useState('');
  const [loading, setLoading] = useState(false);
  
  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);

    try {
      const contaAtualizada = await depositar(conta.numero, parseFloat(valor));
      
      alert(`Depósito de R$ ${parseFloat(valor).toFixed(2)} realizado com sucesso!`);
      
      setValor('');
      onOperacaoSucesso(contaAtualizada);
    } catch (error) {
      const msgErro = error.response?.data?.error || "Erro ao realizar depósito.";
      
      alert(msgErro);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="operacao-form">
      <h4>Realizar Depósito</h4>
      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label htmlFor="deposito-valor">Valor</label>
          <input 
            id="deposito-valor" 
            type="number" 
            step="0.01" 
            value={valor} 
            onChange={(e) => setValor(e.target.value)} 
            required 
          />
        </div>
                
        <button type="submit" className="btn" disabled={loading}>
          {loading ? 'Processando...' : 'Depositar'}
        </button>
      </form>
    </div>
  );
}

export default DepositoForm;