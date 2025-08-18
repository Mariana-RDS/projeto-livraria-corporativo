import React from "react";
import { Link } from "react-router-dom";

const Home = () => {
  const sections = [
    { 
      title: "Catálogo", 
      path: "/catalogo", 
      description: "Veja todos os livros disponíveis.",
      icon: "📚" 
    },
    { 
      title: "Estoque", 
      path: "/estoque", 
      description: "Gerencie o estoque de livros.",
      icon: "📦"
    },
    { 
      title: "Vendas", 
      path: "/vendas", 
      description: "Acompanhe o histórico de vendas.",
      icon: "💰" 
    },
  ];

  return (
    <div style={{ padding: "2rem" }}>
      {/* Título principal com a fonte e cor do tema */}
      <h1 
        style={{ 
          color: "#3b3a30", 
          fontFamily: "'Georgia', serif", 
          fontSize: '2.5rem', 
          textAlign: 'center',
          marginBottom: '1rem'
        }}
      >
        Bem-vindo à Livraria Cottage
      </h1>
      <p style={{ textAlign: 'center', color: '#5a4e3c', marginBottom: '3rem', fontSize: '1.1rem' }}>
        O seu refúgio de leituras e tranquilidade. O que gostaria de fazer hoje?
      </p>

      {/* Grid de seções com estilos atualizados */}
      <div style={{
        display: 'grid',
        gridTemplateColumns: 'repeat(auto-fit, minmax(250px, 1fr))',
        gap: '1.5rem'
      }}>
        {sections.map((section) => (
          <Link
            key={section.title}
            to={section.path}
            style={{
              backgroundColor: '#fdfaf6',
              border: '1px solid #e0d6c4',
              borderRadius: '8px',
              padding: '1.5rem',
              textDecoration: 'none',
              color: 'inherit',
              boxShadow: '0 2px 4px rgba(0,0,0,0.05)',
              transition: 'transform 0.2s ease-in-out, box-shadow 0.2s ease-in-out',
            }}
            onMouseEnter={e => {
              e.currentTarget.style.transform = 'translateY(-5px)';
              e.currentTarget.style.boxShadow = '0 8px 15px rgba(0,0,0,0.1)';
            }}
            onMouseLeave={e => {
              e.currentTarget.style.transform = 'translateY(0)';
              e.currentTarget.style.boxShadow = '0 2px 4px rgba(0,0,0,0.05)';
            }}
          >
            <h2 style={{ fontSize: '2rem', marginBottom: '0.5rem', color: '#5a4e3c' }}>
              <span style={{ marginRight: '0.75rem' }}>{section.icon}</span>
              {section.title}
            </h2>
            <p style={{ color: '#3b3a30' }}>{section.description}</p>
          </Link>
        ))}
      </div>
    </div>
  );
};

export default Home;