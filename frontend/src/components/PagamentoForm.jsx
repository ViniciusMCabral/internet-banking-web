import React, { useState } from 'react';
import { pagamento } from '../services/contaService';

function PagamentoForm({ conta, onOperacaoSucesso }) {
  const [valor, setValor] = useState('');
  const [descricao, setDescricao] = useState('');
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);

    try {
      const contaAtualizada = await pagamento(conta.numero, parseFloat(valor), descricao);

      alert(`Pagamento de R$ ${parseFloat(valor).toFixed(2)} realizado com sucesso!`);

      setValor('');
      setDescricao('');
      onOperacaoSucesso(contaAtualizada);
    } catch (error) {
      const msgErro = error.response?.data?.error || "Erro ao realizar pagamento.";

      alert(msgErro);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="operacao-form">
      <h4>Realizar Pagamento</h4>
      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label htmlFor="pag-valor">Valor</label>
          <input
            id="pag-valor"
            type="number"
            step="0.01"
            value={valor}
            onChange={(e) => setValor(e.target.value)}
            required
          />
        </div>
        <div className="form-group">
          <label htmlFor="pag-desc">Descrição</label>
          <input
            id="pag-desc"
            type="text"
            value={descricao}
            onChange={(e) => setDescricao(e.target.value)}
            required
          />
        </div>

        <button type="submit" className="btn" disabled={loading}>
          {loading ? 'Processando...' : 'Pagar'}
        </button>
      </form>
    </div>
  );
}

export default PagamentoForm;