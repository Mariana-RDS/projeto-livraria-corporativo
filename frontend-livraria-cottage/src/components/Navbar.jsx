// Em seu componente Navbar.jsx

import { Link, useNavigate } from 'react-router-dom';
import { getToken, logout } from "../services/authService";

export default function Navbar() {
  const token = getToken();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate("/login");
  };

  return (
    <nav style={{
        backgroundColor: "#e0d6c4",
        boxShadow: "0 2px 4px rgba(0,0,0,0.1)",
        padding: "1rem 2rem",
        display: "flex",
        justifyContent: "space-between",
        alignItems: "center",
        fontSize: "1.125rem",
        fontWeight: "500",
      }}>
      <div style={{ display: "flex", gap: "1.5rem" }}>
        <Link to="/" style={{ textDecoration: "none", color: "#3b3a30" }}>
          📖 Livraria Cottage
        </Link>
      </div>
      <div style={{ display: "flex", gap: "1.5rem", alignItems: 'center' }}>
        {token ? (
          <>
            <Link to="/">🏠 Início</Link>
            <Link to="/sobre">🌿 Sobre</Link>
            <button
              onClick={handleLogout}
              style={{
                background: "none",
                border: "none",
                cursor: "pointer",
                color: "#5a4e3c",
                fontSize: "1rem",
                fontWeight: "600",
              }}
            >
              🚪 Logout
            </button>
          </>
        ) : (
          <>
            <Link to="/login">🔐 Login</Link>
            <Link to="/register">📝 Cadastro</Link>
          </>
        )}
      </div>
    </nav>
  );
}