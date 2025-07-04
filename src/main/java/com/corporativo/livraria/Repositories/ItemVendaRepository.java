package com.corporativo.livraria.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.corporativo.livraria.Service.Entities.ItemVenda;

import java.util.List;

public interface ItemVendaRepository extends JpaRepository<ItemVenda, Long> {
    List<ItemVenda> findByVendaId(Long vendaId);
}
