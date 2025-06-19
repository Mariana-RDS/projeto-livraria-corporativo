package com.corporativo.livraria.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.corporativo.livraria.Entities.LivroEntity;

public interface LivroRepository extends JpaRepository<LivroEntity, Long>{

}
