package com.corporativo.livraria.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.corporativo.livraria.Entities.EstoqueEntity;

public interface EstoqueRepository extends JpaRepository<EstoqueEntity, Long>{

}
