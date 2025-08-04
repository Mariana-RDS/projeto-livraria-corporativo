import React, { useState } from "react";
import { register } from "../services/authService";
import { useNavigate, Link } from "react-router-dom";

export default function Register() {
  const [form, setForm] = useState({
    username: "",
    password: "",
    confirmPassword: "",
  });
  const [error, setError] = useState(null);
  const [success, setSuccess] = useState(null);
  const navigate = useNavigate();

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError(null);
    setSuccess(null);

    if (form.password !== form.confirmPassword) {
      setError("As senhas não conferem.");
      return;
    }

    try {
      await register(form.username, form.password, "USER");
      setSuccess("Cadastro realizado com sucesso! Redirecionando para login...");
      setTimeout(() => {
        navigate("/login");
      }, 2000);
    } catch (err) {
      if (err.response && err.response.status === 400) {
        setError("Usuário já existe.");
      } else {
        setError("Erro ao cadastrar usuário.");
      }
    }
  };

  return (
    <div
      style={{
        minHeight: "100vh",
        backgroundColor: "#f7f6f2",
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        fontFamily: "'Quicksand', sans-serif",
        padding: "1rem",
      }}
    >
      <form
        onSubmit={handleSubmit}
        style={{
          backgroundColor: "#d9cfc1",
          padding: "2rem",
          borderRadius: "12px",
          boxShadow: "0 4px 12px rgba(0,0,0,0.1)",
          width: "100%",
          maxWidth: "400px",
          display: "flex",
          flexDirection: "column",
          gap: "1rem",
        }}
      >
        <h2
          style={{
            marginBottom: "1rem",
            color: "#5a4e3c",
            fontFamily: "serif",
            textAlign: "center",
          }}
        >
          Cadastro - Livraria Cottage
        </h2>
        <input
          name="username"
          placeholder="Usuário"
          value={form.username}
          onChange={handleChange}
          required
          style={{
            padding: "0.75rem",
            borderRadius: "6px",
            border: "1px solid #bfae9e",
            fontSize: "1rem",
            fontFamily: "'Quicksand', sans-serif",
          }}
        />
        <input
          name="password"
          type="password"
          placeholder="Senha"
          value={form.password}
          onChange={handleChange}
          required
          style={{
            padding: "0.75rem",
            borderRadius: "6px",
            border: "1px solid #bfae9e",
            fontSize: "1rem",
            fontFamily: "'Quicksand', sans-serif",
          }}
        />
        <input
          name="confirmPassword"
          type="password"
          placeholder="Confirme a senha"
          value={form.confirmPassword}
          onChange={handleChange}
          required
          style={{
            padding: "0.75rem",
            borderRadius: "6px",
            border: "1px solid #bfae9e",
            fontSize: "1rem",
            fontFamily: "'Quicksand', sans-serif",
          }}
        />
        <button
          type="submit"
          style={{
            backgroundColor: "#5a4e3c",
            color: "#f7f6f2",
            border: "none",
            padding: "0.75rem",
            borderRadius: "6px",
            cursor: "pointer",
            fontSize: "1rem",
            fontWeight: "600",
            transition: "background-color 0.3s",
          }}
          onMouseEnter={(e) => (e.target.style.backgroundColor = "#3e3425")}
          onMouseLeave={(e) => (e.target.style.backgroundColor = "#5a4e3c")}
        >
          Cadastrar
        </button>

        {error && (
          <p
            style={{
              color: "#b00020",
              fontWeight: "600",
              marginTop: "0.5rem",
              textAlign: "center",
            }}
          >
            {error}
          </p>
        )}

        {success && (
          <p
            style={{
              color: "#2e7d32",
              fontWeight: "600",
              marginTop: "0.5rem",
              textAlign: "center",
            }}
          >
            {success}
          </p>
        )}

        <p style={{ marginTop: "1rem", textAlign: "center", color: "#5a4e3c" }}>
          Já tem conta?{" "}
          <Link to="/login" style={{ color: "#3e3425", fontWeight: "600" }}>
            Faça login aqui
          </Link>
        </p>
      </form>
    </div>
  );
}
