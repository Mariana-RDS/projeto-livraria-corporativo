package com.corporativo.livraria.Service;

import com.corporativo.livraria.Repositories.EstoqueRepository;
import com.corporativo.livraria.Repositories.ItemVendaRepository;
import com.corporativo.livraria.Repositories.LivroRepository;
import com.corporativo.livraria.Repositories.VendaRepository;
import com.corporativo.livraria.Service.DTO.ItemVendaDTO;
import com.corporativo.livraria.Service.DTO.VendaDTO;
import com.corporativo.livraria.Service.Entities.Estoque;
import com.corporativo.livraria.Service.Entities.ItemVenda;
import com.corporativo.livraria.Service.Entities.Livro;
import com.corporativo.livraria.Service.Entities.Venda;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VendaService {

    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private ItemVendaRepository itemVendaRepository;

    @Autowired
    private EstoqueRepository estoqueRepository;

    /*
     * @Transactional
     * public VendaDTO registrarVenda(VendaDTO dto) {
     * try {
     * Venda venda = new Venda();
     * 
     * venda.setData(dto.getData() != null ? LocalDate.parse(dto.getData()) : null);
     * venda.setNomeCliente(dto.getNomeCliente());
     * venda.setCpfCliente(dto.getCpfCliente());
     * 
     * Venda vendaSalva = vendaRepository.save(venda);
     * 
     * List<ItemVendaDTO> itensDto = dto.getItens() != null ? dto.getItens() :
     * Collections.emptyList();
     * 
     * List<ItemVenda> itens = itensDto.stream().map(itemDto -> {
     * Livro livro = livroRepository.findById(itemDto.getLivroId())
     * .orElseThrow(
     * () -> new RuntimeException("Livro não encontrado com id: " +
     * itemDto.getLivroId()));
     * 
     * Estoque estoque = estoqueRepository.findByLivro(livro)
     * .orElseThrow(
     * () -> new RuntimeException(
     * "Estoque não encontrado para o livro: " + livro.getTitulo()));
     * 
     * if (estoque.getQuantidade() < itemDto.getQuantidade()) {
     * throw new RuntimeException("Estoque insuficiente para o livro: " +
     * livro.getTitulo());
     * }
     * 
     * estoque.setQuantidade(estoque.getQuantidade() - itemDto.getQuantidade());
     * estoqueRepository.save(estoque);
     * 
     * ItemVenda item = new ItemVenda();
     * item.setLivro(livro);
     * item.setQuantidade(itemDto.getQuantidade());
     * item.setVenda(vendaSalva);
     * item.setPrecoUnitario(livro.getPreco());
     * return item;
     * }).collect(Collectors.toList());
     * 
     * itemVendaRepository.saveAll(itens);
     * 
     * dto.setId(vendaSalva.getId());
     * return dto;
     * 
     * } catch (Exception e) {
     * e.printStackTrace();
     * throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
     * "Please Try Again");
     * }
     * 
     * }
     */

    @Transactional
    public VendaDTO registrarVenda(VendaDTO dto) {
        try {
            Venda venda = new Venda();

            venda.setData(dto.getData() != null ? LocalDate.parse(dto.getData()) : null);
            venda.setNomeCliente(dto.getNomeCliente());
            venda.setCpfCliente(dto.getCpfCliente());

            List<ItemVendaDTO> itensDto = dto.getItens() != null ? dto.getItens() : Collections.emptyList();

            List<ItemVenda> itens = itensDto.stream().map(itemDto -> {
                Livro livro = livroRepository.findById(itemDto.getLivroId())
                        .orElseThrow(
                                () -> new RuntimeException("Livro não encontrado com id: " + itemDto.getLivroId()));

                Estoque estoque = estoqueRepository.findByLivro(livro)
                        .orElseThrow(() -> new RuntimeException(
                                "Estoque não encontrado para o livro: " + livro.getTitulo()));

                if (estoque.getQuantidade() < itemDto.getQuantidade()) {
                    throw new RuntimeException("Estoque insuficiente para o livro: " + livro.getTitulo());
                }

                estoque.setQuantidade(estoque.getQuantidade() - itemDto.getQuantidade());
                estoqueRepository.save(estoque);

                ItemVenda item = new ItemVenda();
                item.setLivro(livro);
                item.setQuantidade(itemDto.getQuantidade());
                item.setVenda(venda);
                return item;
            }).collect(Collectors.toList());

            BigDecimal total = itens.stream()
                    .map(item -> item.getLivro().getPreco().multiply(BigDecimal.valueOf(item.getQuantidade())))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            venda.setPrecoTotal(total);
            Venda vendaSalva = vendaRepository.save(venda);

            itens.forEach(item -> item.setVenda(vendaSalva));
            itemVendaRepository.saveAll(itens);

            dto.setId(vendaSalva.getId());
            return dto;

        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Please Try Again");
        }
    }

    public List<VendaDTO> getAll() {
        try {
            return vendaRepository.findAll().stream()
                    .map(this::toDto)
                    .collect(Collectors.toList());

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Please Try Again");
        }

    }

    public Optional<VendaDTO> getById(Long id) {
        try {
            return vendaRepository.findById(id).map(this::toDto);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Please Try Again");
        }

    }

    public List<VendaDTO> getByCpf(String cpf) {
        try {
            return vendaRepository.findByCpfCliente(cpf).stream()
                    .map(this::toDto)
                    .collect(Collectors.toList());

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Please Try Again");
        }

    }

    // Será que deveria ter como apagar uma venda? acho perigoso, a policia federal
    // vai vir atrás

    public void delete(Long id) {
        try {
            vendaRepository.deleteById(id);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Please Try Again");
        }

    }

    private VendaDTO toDto(Venda venda) {
        try {
            VendaDTO dto = new VendaDTO();
            dto.setId(venda.getId());

            dto.setData(venda.getData() != null ? venda.getData().toString() : null);
            dto.setNomeCliente(venda.getNomeCliente());
            dto.setCpfCliente(venda.getCpfCliente());

            if (venda.getItens() != null) {
                List<ItemVendaDTO> itensDto = venda.getItens().stream()
                        .map(item -> {
                            ItemVendaDTO itemDto = new ItemVendaDTO();
                            itemDto.setId(item.getId());
                            itemDto.setLivroId(item.getLivro().getId());
                            itemDto.setQuantidade(item.getQuantidade());
                            return itemDto;
                        }).collect(Collectors.toList());
                dto.setItens(itensDto);
            } else {
                dto.setItens(Collections.emptyList());
            }

            return dto;

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Please Try Again");
        }

    }
}
