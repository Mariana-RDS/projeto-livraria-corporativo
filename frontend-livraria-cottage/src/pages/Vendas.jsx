import React, { useEffect, useState } from "react";
import { getVendas, createVenda, deleteVenda } from "../services/vendaService";
import { getLivros } from "../services/livroService";

export default function Vendas() {
  const [vendas, setVendas] = useState([]);
  const [livros, setLivros] = useState([]);
  const [erro, setErro] = useState("");
  
  const [novaVenda, setNovaVenda] = useState({
    cpfCliente: "",
    nomeCliente: "",
    data: new Date().toISOString().split('T')[0],
    itens: [],
  });
  
  const [itemAtual, setItemAtual] = useState({ livroId: "", quantidade: 1 });

  useEffect(() => {
    fetchVendas();
    fetchLivrosParaSelect();
  }, []);

  async function fetchVendas() {
    try {
      const response = await getVendas();
      setVendas(Array.isArray(response.data) ? response.data : []);
    } catch {
      setErro("Erro ao carregar vendas.");
    }
  }
  
  async function fetchLivrosParaSelect() {
    try {
      const response = await getLivros();
      setLivros(Array.isArray(response.data) ? response.data : []);
    } catch {
      console.error("Erro ao carregar lista de livros para o seletor.");
    }
  }
  
  const handleAddItem = () => {
    if (!itemAtual.livroId || itemAtual.quantidade <= 0) {
        setErro("Selecione um livro e uma quantidade válida.");
        return;
    }
    setErro("");
    setNovaVenda(prevVenda => ({
        ...prevVenda,
        itens: [...prevVenda.itens, { ...itemAtual, livroId: parseInt(itemAtual.livroId) }]
    }));
    setItemAtual({ livroId: "", quantidade: 1 });
  };
  
  async function handleCreateVenda(e) {
    e.preventDefault();
    if (novaVenda.itens.length === 0) {
        setErro("Adicione pelo menos um item à venda.");
        return;
    }
    setErro("");
    try {
      await createVenda(novaVenda);
      setNovaVenda({ cpfCliente: "", nomeCliente: "", data: new Date().toISOString().split('T')[0], itens: [] });
      fetchVendas();
    } catch (err) {
      setErro("Erro ao registar venda: " + (err.response?.data?.message || "Verifique o estoque."));
    }
  }

  async function handleDelete(id) {
    if (window.confirm("Deseja mesmo apagar esta venda?")) {
      try {
        await deleteVenda(id);
        fetchVendas();
      } catch {
        setErro("Erro ao apagar venda.");
      }
    }
  }
  
  const getTituloLivro = (id) => livros.find(l => l.id === id)?.titulo || `Livro ID: ${id}`;

  return (
    <div style={{ padding: "2rem" }}>
      <h2>Registo de Vendas</h2>
      {erro && <p style={{ color: "red", marginBottom: '1rem' }}>{erro}</p>}

      {/* Formulário de Venda */}
      <form onSubmit={handleCreateVenda} style={{ marginBottom: '2rem', border: '1px solid #ccc', padding: '1rem' }}>
        <h3>Nova Venda</h3>
        <div style={{ display: 'flex', gap: '1rem', marginBottom: '1rem' }}>
            <input placeholder="CPF do Cliente" value={novaVenda.cpfCliente} onChange={(e) => setNovaVenda({ ...novaVenda, cpfCliente: e.target.value })} required />
            <input placeholder="Nome do Cliente" value={novaVenda.nomeCliente} onChange={(e) => setNovaVenda({ ...novaVenda, nomeCliente: e.target.value })} required />
            <input type="date" value={novaVenda.data} onChange={(e) => setNovaVenda({ ...novaVenda, data: e.target.value })} required />
        </div>

        {/* Adicionar Itens */}
        <div style={{ borderTop: '1px solid #eee', paddingTop: '1rem' }}>
            <h4>Itens da Venda</h4>
            <div style={{ display: 'flex', gap: '1rem', marginBottom: '1rem' }}>
                <select value={itemAtual.livroId} onChange={(e) => setItemAtual({ ...itemAtual, livroId: e.target.value })}>
                    <option value="">Selecione um Livro</option>
                    {livros.map(livro => <option key={livro.id} value={livro.id}>{livro.titulo}</option>)}
                </select>
                <input type="number" min="1" placeholder="Qtd." value={itemAtual.quantidade} onChange={(e) => setItemAtual({ ...itemAtual, quantidade: parseInt(e.target.value, 10) })} />
                <button type="button" onClick={handleAddItem}>Adicionar Item</button>
            </div>
            
            {/* Itens no Carrinho */}
            <ul>
                {novaVenda.itens.map((item, index) => (
                    <li key={index}>{getTituloLivro(item.livroId)} - Quantidade: {item.quantidade}</li>
                ))}
            </ul>
        </div>
        
        <button type="submit" style={{ marginTop: '1rem', width: '100%' }}>Finalizar e Registar Venda</button>
      </form>

      {/* Histórico de Vendas */}
      <h3>Histórico de Vendas</h3>
      <ul>
        {vendas.map((venda) => (
          <li key={venda.id} style={{ border: '1px solid #eee', padding: '0.5rem', marginBottom: '0.5rem' }}>
            Venda #{venda.id} - Cliente: {venda.nomeCliente} (CPF: {venda.cpfCliente}) - Data: {venda.data}
            <button onClick={() => handleDelete(venda.id)} style={{ marginLeft: '1rem', backgroundColor: '#ff4d4d', color: 'white' }}>Apagar</button>
            <ul>
                {venda.itens?.map(item => (
                    <li key={item.id} style={{fontSize: '0.9em', marginLeft: '1rem'}}>
                        &raquo; {getTituloLivro(item.livroId)} (Qtd: {item.quantidade})
                    </li>
                ))}
            </ul>
          </li>
        ))}
      </ul>
    </div>
  );
}