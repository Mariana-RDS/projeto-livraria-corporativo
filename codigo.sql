-- Promp de comando:
--     CREATE DATABASE livraria;
--     \c livraria

-- Tabela Editora
CREATE TABLE editora (
    id SERIAL PRIMARY KEY,
    nome_editora VARCHAR(128) NOT NULL
);

-- Tabela Autor
CREATE TABLE autor (
    id SERIAL PRIMARY KEY,
    nome_autor VARCHAR(128) NOT NULL
);

-- Tabela Livro
CREATE TABLE livro (
    id SERIAL PRIMARY KEY,
    titulo VARCHAR(128) NOT NULL,
    isbn VARCHAR(15) NOT NULL,
    id_editora BIGINT,
    CONSTRAINT fk_livro_editora FOREIGN KEY (id_editora) REFERENCES editora(id)
);

-- Tabela Livro_Autor
CREATE TABLE livro_autor (
    id_livro BIGINT NOT NULL,
    id_autor BIGINT NOT NULL,
    PRIMARY KEY (id_livro, id_autor),
    CONSTRAINT fk_livro FOREIGN KEY (id_livro) REFERENCES livro(id),
    CONSTRAINT fk_autor FOREIGN KEY (id_autor) REFERENCES autor(id)
);

-- Tabela Venda
CREATE TABLE venda (
    id SERIAL PRIMARY KEY,
    data DATE,
    nome_cliente VARCHAR(128),
    cpf_cliente VARCHAR(14)
);

-- Tabela Item_Venda
CREATE TABLE item_venda (
    id SERIAL PRIMARY KEY,
    venda_id BIGINT NOT NULL,
    livro_id BIGINT NOT NULL,
    quantidade INTEGER NOT NULL,
    CONSTRAINT fk_venda FOREIGN KEY (venda_id) REFERENCES venda(id) ON DELETE CASCADE,
    CONSTRAINT fk_livro FOREIGN KEY (livro_id) REFERENCES livro(id)
);
