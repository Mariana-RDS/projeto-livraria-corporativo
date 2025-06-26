package com.corporativo.livraria.Repositories;

import com.corporativo.livraria.Entities.VendaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VendaRepository extends JpaRepository<VendaEntity, Long> {
    List<VendaEntity> findByCpfCliente(String cpfCliente);
}
