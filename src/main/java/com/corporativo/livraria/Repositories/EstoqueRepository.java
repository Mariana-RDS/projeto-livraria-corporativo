package com.corporativo.livraria.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.corporativo.livraria.Service.Entities.Estoque;
import com.corporativo.livraria.Service.Entities.Livro;

public interface EstoqueRepository extends JpaRepository<Estoque, Long> {

    
    Estoque findByLivroId(Long livroId);
    Optional<Estoque> findByLivro(Livro livroId);

    @Transactional
    void deleteByLivroId(Long livroId);
}
