package com.corporativo.livraria.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.corporativo.livraria.Service.Entities.Livro;

public interface LivroRepository extends JpaRepository<Livro, Long>{

}
