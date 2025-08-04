import React, { useEffect, useState } from "react";
import { getLivros, createLivro, deleteLivro } from "../services/livroService";

export default function Catalogo() {
  const [livros, setLivros] = useState([]);
  const [erro, setErro] = useState("");
  
  // Estado inicial que corresponde ao LivroDTO do backend
  const initialState = {
    titulo: "",
    isbn: "",
    preco: "",
    nomeEditora: "",
    nomesAutores: "", // Usaremos uma string separada por vírgulas
    quantidadeEstoque: 0,
  };

  const [novoLivro, setNovoLivro] = useState(initialState);

  useEffect(() => {
    fetchLivros();
  }, []);

  async function fetchLivros() {
    try {
      const response = await getLivros();
      // O backend retorna um objeto, mas precisamos de um array para o map
      setLivros(Array.isArray(response.data) ? response.data : []);
    } catch {
      setErro("Erro ao carregar livros.");
    }
  }

  const handleChange = (e) => {
    const { name, value } = e.target;
    setNovoLivro({ ...novoLivro, [name]: value });
  };

  async function handleCreate(e) {
    e.preventDefault();
    setErro("");
    try {
      // Prepara o DTO para ser enviado, convertendo os tipos de dados
      const livroParaEnviar = {
        ...novoLivro,
        preco: parseFloat(novoLivro.preco),
        quantidadeEstoque: parseInt(novoLivro.quantidadeEstoque, 10),
        // Converte a string de autores "Autor 1, Autor 2" para um array ["Autor 1", "Autor 2"]
        nomesAutores: novoLivro.nomesAutores.split(',').map(autor => autor.trim()),
      };

      await createLivro(livroParaEnviar);
      setNovoLivro(initialState); // Limpa o formulário
      fetchLivros(); // Atualiza a lista
    } catch (err) {
      setErro("Erro ao criar livro. " + (err.response?.data?.message || ""));
    }
  }

  async function handleDelete(id) {
    if (window.confirm("Deseja mesmo apagar este livro? A ação não pode ser desfeita.")) {
      try {
        await deleteLivro(id);
        fetchLivros();
      } catch {
        setErro("Erro ao apagar livro.");
      }
    }
  }

  return (
    <div style={{ padding: "1rem" }}>
      <h2 style={{ marginBottom: '1.5rem' }}>Catálogo de Livros</h2>

      {erro && <p style={{ color: "red", marginBottom: '1rem' }}>{erro}</p>}

      {/* Formulário de Criação de Livro */}
      <form onSubmit={handleCreate} style={{ marginBottom: "2rem", display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '1rem' }}>
        <input name="titulo" placeholder="Título" value={novoLivro.titulo} onChange={handleChange} required />
        <input name="isbn" placeholder="ISBN" value={novoLivro.isbn} onChange={handleChange} required />
        <input name="preco" type="number" step="0.01" placeholder="Preço (ex: 29.99)" value={novoLivro.preco} onChange={handleChange} required />
        <input name="nomeEditora" placeholder="Editora" value={novoLivro.nomeEditora} onChange={handleChange} required />
        <input name="nomesAutores" placeholder="Autores (separados por vírgula)" value={novoLivro.nomesAutores} onChange={handleChange} required />
        <input name="quantidadeEstoque" type="number" placeholder="Quantidade em Estoque" value={novoLivro.quantidadeEstoque} onChange={handleChange} required />
        <button type="submit" style={{ gridColumn: 'span 2' }}>Adicionar Livro ao Catálogo</button>
      </form>

      {/* Tabela de Livros */}
      <table style={{ width: '100%', borderCollapse: 'collapse' }}>
        <thead>
          <tr style={{ backgroundColor: '#f2f2f2' }}>
            <th style={{ padding: '8px', border: '1px solid #ddd', textAlign: 'left' }}>Título</th>
            <th style={{ padding: '8px', border: '1px solid #ddd', textAlign: 'left' }}>Autores</th>
            <th style={{ padding: '8px', border: '1px solid #ddd', textAlign: 'left' }}>Preço</th>
            <th style={{ padding: '8px', border: '1px solid #ddd', textAlign: 'left' }}>Estoque</th>
            <th style={{ padding: '8px', border: '1px solid #ddd', textAlign: 'left' }}>Ações</th>
          </tr>
        </thead>
        <tbody>
          {livros.map((livro) => (
            <tr key={livro.id}>
              <td style={{ padding: '8px', border: '1px solid #ddd' }}>{livro.titulo}</td>
              <td style={{ padding: '8px', border: '1px solid #ddd' }}>{livro.nomesAutores?.join(', ')}</td>
              <td style={{ padding: '8px', border: '1px solid #ddd' }}>R$ {livro.preco?.toFixed(2)}</td>
              <td style={{ padding: '8px', border: '1px solid #ddd' }}>{livro.quantidadeEstoque}</td>
              <td style={{ padding: '8px', border: '1px solid #ddd' }}>
                <button onClick={() => handleDelete(livro.id)} style={{ backgroundColor: '#ff4d4d', color: 'white' }}>Apagar</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}