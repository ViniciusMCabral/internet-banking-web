import api from './axios';

export const getMinhaConta = async () => {
  const response = await api.get('/contas/minha-conta');
  return response.data;
};

export const depositar = async (numeroConta, valor) => {
  const response = await api.post(`/contas/${numeroConta}/depositos`, { valor });
  return response.data;
};

export const sacar = async (numeroConta, valor) => {
  const response = await api.post(`/contas/${numeroConta}/saques`, { valor });
  return response.data;
};

export const pagamento = async (numeroConta, valor, descricao) => {
  const response = await api.post(`/contas/${numeroConta}/pagamentos`, {
    valor: valor,
    descricao: descricao
  });
  return response.data;
};

export const getExtrato = async (numeroConta, pagina = 0, filtros = {}) => {
  const params = new URLSearchParams({
    page: pagina,
    size: 10,
    sort: 'dataHora,desc',
  });

  if (filtros.tipo) {
    params.append('tipo', filtros.tipo);
  }
  if (filtros.dataInicio) {
    params.append('dataInicio', filtros.dataInicio);
  }
  if (filtros.dataFim) {
    params.append('dataFim', filtros.dataFim);
  }

  const response = await api.get(`/contas/${numeroConta}/extrato`, { params });
  return response.data;
};


