import React, { useEffect, useState } from "react";
import { getEstoques, updateEstoque } from "../services/estoqueService";

export default function Estoque() {
  const [estoques, setEstoques] = useState([]);
  const [erro, setErro] = useState("");
  const [sucesso, setSucesso] = useState("");
  const [editando, setEditando] = useState({});

  useEffect(() => {
    fetchEstoques();
  }, []);

  async function fetchEstoques() {
    setErro("");
    setSucesso("");
    try {
      const response = await getEstoques();
      setEstoques(Array.isArray(response.data) ? response.data : []);
    } catch (err) {
      setErro("Erro ao carregar o estoque.");
    }
  }

  const handleQuantidadeChange = (livroId, quantidade) => {
    const valor = parseInt(quantidade, 10);
    setEditando({ ...editando, [livroId]: isNaN(valor) ? "" : valor });
  };

  const handleUpdate = async (livroId) => {
    const novaQuantidade = editando[livroId];

    if (novaQuantidade === "" || novaQuantidade < 0 || novaQuantidade === undefined) {
      setErro("Por favor, insira uma quantidade válida (número maior ou igual a zero).");
      return;
    }

    setErro("");
    setSucesso("");

    try {
      await updateEstoque(livroId, novaQuantidade);
      setSucesso(`Estoque do livro ID ${livroId} atualizado com sucesso!`);
      setEditando({});
      fetchEstoques(); 

      setTimeout(() => setSucesso(""), 3000);
    } catch (err) {
      setErro(`Erro ao atualizar estoque: ${err.response?.data?.message || "Tente novamente."}`);
    }
  };

  return (
    <div style={{ padding: "1rem" }}>
      <h2 style={{ fontFamily: "'Georgia', serif", fontSize: '2.5rem', color: '#5a4e3c', textAlign: 'center', marginBottom: '2rem' }}>
        📦 Controle de Estoque
      </h2>

      {erro && <p style={{ color: "#b00020", backgroundColor: '#fdd', padding: '1rem', borderRadius: '8px', marginBottom: '1rem', textAlign: 'center' }}>{erro}</p>}
      {sucesso && <p style={{ color: "#2e7d32", backgroundColor: '#dff0d8', padding: '1rem', borderRadius: '8px', marginBottom: '1rem', textAlign: 'center' }}>{sucesso}</p>}

      <div style={{ overflowX: 'auto', boxShadow: '0 4px 12px rgba(0,0,0,0.08)', borderRadius: '8px' }}>
        <table style={{ width: '100%', borderCollapse: 'collapse', backgroundColor: '#fdfaf6' }}>
          <thead>
            <tr style={{ backgroundColor: '#e0d6c4' }}>
              <th style={{ padding: '12px 15px', borderBottom: '2px solid #d9cfc1', textAlign: 'left', color: '#3b3a30' }}>ID</th>
              <th style={{ padding: '12px 15px', borderBottom: '2px solid #d9cfc1', textAlign: 'left', color: '#3b3a30' }}>Título do Livro</th>
              <th style={{ padding: '12px 15px', borderBottom: '2px solid #d9cfc1', textAlign: 'left', color: '#3b3a30' }}>Qtd. Atual</th>
              <th style={{ padding: '12px 15px', borderBottom: '2px solid #d9cfc1', textAlign: 'left', color: '#3b3a30' }}>Nova Quantidade</th>
              <th style={{ padding: '12px 15px', borderBottom: '2px solid #d9cfc1', textAlign: 'center', color: '#3b3a30' }}>Ação</th>
            </tr>
          </thead>
          <tbody>
            {estoques.map((estoque) => (
              <tr key={estoque.livroId} style={{ borderBottom: '1px solid #e0d6c4', backgroundColor: editando[estoque.livroId] !== undefined ? '#fffbeb' : 'transparent' }}>
                <td style={{ padding: '12px 15px' }}>{estoque.livroId}</td>
                <td style={{ padding: '12px 15px', fontWeight: '600' }}>{estoque.tituloLivro}</td>
                <td style={{ padding: '12px 15px' }}>{estoque.quantidade}</td>
                <td style={{ padding: '12px 15px' }}>
                  <input
                    type="number"
                    min="0"
                    value={editando[estoque.livroId] ?? ''}
                    onChange={(e) => handleQuantidadeChange(estoque.livroId, e.target.value)}
                    placeholder="Ex: 50"
                    style={{ padding: '8px', borderRadius: '6px', border: '1px solid #bfae9e', width: '100px' }}
                  />
                </td>
                <td style={{ padding: '12px 15px', textAlign: 'center' }}>
                  <button 
                    onClick={() => handleUpdate(estoque.livroId)}
                    disabled={editando[estoque.livroId] === undefined || editando[estoque.livroId] === ''}
                    style={{
                      backgroundColor: '#5a4e3c',
                      color: '#f7f6f2',
                      border: 'none',
                      padding: '8px 16px',
                      borderRadius: '6px',
                      cursor: 'pointer',
                      opacity: (editando[estoque.livroId] === undefined || editando[estoque.livroId] === '') ? 0.5 : 1,
                      transition: 'opacity 0.2s'
                    }}
                  >
                    Atualizar
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}