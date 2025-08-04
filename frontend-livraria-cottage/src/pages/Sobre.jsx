import React from 'react';

function Sobre() {
  return (
    <div style={{
      padding: "2rem",
      color: '#3b3a30',
      maxWidth: '800px',
      margin: '0 auto'
    }}>
      
      {/* Título Principal */}
      <div style={{ textAlign: 'center', marginBottom: '3rem' }}>
        <h2 style={{ fontFamily: "'Georgia', serif", fontSize: '2.8rem', color: '#5a4e3c' }}>
          🌿 Sobre a Livraria Cottage
        </h2>
        <p style={{ marginTop: '1rem', fontSize: '1rem' }}>
          Um cantinho acolhedor para os amantes de livros, café e plantas.
        </p>
      </div>

      {/* Imagem e Texto Principal */}
      <div style={{
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
        gap: '2rem'
      }}>
        <p style={{
          textAlign: 'center',
          fontSize: '1.1rem',
          lineHeight: '1.8',
          backgroundColor: 'rgba(253, 250, 246, 0.7)',
          padding: '1.5rem',
          borderRadius: '8px'
        }}>
          Fundada por <strong>Carolina Macedo</strong> e <strong>Mariana Santos</strong>, a nossa livraria oferece não só livros incríveis, mas também um ambiente para relaxar, inspirar e se reconectar com o verdadeiro prazer da leitura.
        </p>
      </div>

      {/* Seção de Valores */}
      <div style={{ marginTop: '4rem', borderTop: '1px solid #e0d6c4', paddingTop: '3rem' }}>
        <h3 style={{ textAlign: 'center', fontFamily: "'Georgia', serif", fontSize: '2rem', color: '#5a4e3c', marginBottom: '2rem' }}>
          O que nos inspira
        </h3>
        <div style={{
          display: 'grid',
          gridTemplateColumns: 'repeat(auto-fit, minmax(200px, 1fr))',
          gap: '1.5rem',
          textAlign: 'center'
        }}>
          <div style={{ backgroundColor: '#fdfaf6', padding: '1.5rem', borderRadius: '8px', border: '1px solid #e0d6c4' }}>
            <span style={{ fontSize: '2.5rem' }}>📚</span>
            <h4 style={{ fontSize: '1.2rem', fontWeight: '600', margin: '0.5rem 0' }}>Livros Selecionados</h4>
            <p>Uma curadoria cuidadosa para garantir a sua próxima grande leitura.</p>
          </div>
          <div style={{ backgroundColor: '#fdfaf6', padding: '1.5rem', borderRadius: '8px', border: '1px solid #e0d6c4' }}>
            <span style={{ fontSize: '2.5rem' }}>☕</span>
            <h4 style={{ fontSize: '1.2rem', fontWeight: '600', margin: '0.5rem 0' }}>Café Especial</h4>
            <p>O acompanhamento perfeito para a sua jornada literária.</p>
          </div>
          <div style={{ backgroundColor: '#fdfaf6', padding: '1.5rem', borderRadius: '8px', border: '1px solid #e0d6c4' }}>
            <span style={{ fontSize: '2.5rem' }}>🌱</span>
            <h4 style={{ fontSize: '1.2rem', fontWeight: '600', margin: '0.5rem 0' }}>Ambiente Verde</h4>
            <p>Um espaço que respira vida, ideal para relaxar e se inspirar.</p>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Sobre;