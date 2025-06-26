package com.corporativo.livraria.Repositories;

import com.corporativo.livraria.Entities.ItemVendaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemVendaRepository extends JpaRepository<ItemVendaEntity, Long> {
    List<ItemVendaEntity> findByVendaId(Long vendaId);
}
