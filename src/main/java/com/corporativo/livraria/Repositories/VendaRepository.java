package com.corporativo.livraria.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.corporativo.livraria.Service.Entities.Venda;

import java.util.List;

public interface VendaRepository extends JpaRepository<Venda, Long> {
    List<Venda> findByCpfCliente(String cpfCliente);
}
