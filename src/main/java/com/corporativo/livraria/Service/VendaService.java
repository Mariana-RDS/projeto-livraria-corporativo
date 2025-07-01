package com.corporativo.livraria.Service;

import com.corporativo.livraria.Dto.ItemVendaDto;
import com.corporativo.livraria.Dto.VendaDto;
import com.corporativo.livraria.Entities.ItemVendaEntity;
import com.corporativo.livraria.Entities.LivroEntity;
import com.corporativo.livraria.Entities.VendaEntity;
import com.corporativo.livraria.Repositories.ItemVendaRepository;
import com.corporativo.livraria.Repositories.LivroRepository;
import com.corporativo.livraria.Repositories.VendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

    public VendaDto create(VendaDto dto) {
        try{
            VendaEntity venda = new VendaEntity();

            venda.setData(dto.getData() != null ? LocalDate.parse(dto.getData()) : null);
            venda.setNomeCliente(dto.getNomeCliente());
            venda.setCpfCliente(dto.getCpfCliente());

            VendaEntity vendaSalva = vendaRepository.save(venda);

            List<ItemVendaDto> itensDto = dto.getItens() != null ? dto.getItens() : Collections.emptyList();

            List<ItemVendaEntity> itens = itensDto.stream().map(itemDto -> {
                LivroEntity livro = livroRepository.findById(itemDto.getLivroId())
                    .orElseThrow(() -> new RuntimeException("Livro não encontrado com id: " + itemDto.getLivroId()));
                ItemVendaEntity item = new ItemVendaEntity();
                item.setLivro(livro);
                item.setQuantidade(itemDto.getQuantidade());
                item.setVenda(vendaSalva);
                return item;
            }).collect(Collectors.toList());

            itemVendaRepository.saveAll(itens);

            dto.setId(vendaSalva.getId());
            return dto;

        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Please Try Again");
        }
        
    }

    public List<VendaDto> getAll() {
        try{
            return vendaRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());

        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Please Try Again");
        }
        
    }

    public Optional<VendaDto> getById(Long id) {
        try{
            return vendaRepository.findById(id).map(this::toDto);

        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Please Try Again");
        }
        
    }

    public List<VendaDto> getByCpf(String cpf) {
        try{
            return vendaRepository.findByCpfCliente(cpf).stream()
                .map(this::toDto)
                .collect(Collectors.toList());

        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Please Try Again");
        }
        
    }

    public void delete(Long id) {
        try{
            vendaRepository.deleteById(id);

        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Please Try Again");
        }
        
    }

    private VendaDto toDto(VendaEntity venda) {
        try{
            VendaDto dto = new VendaDto();
            dto.setId(venda.getId());

            dto.setData(venda.getData() != null ? venda.getData().toString() : null);
            dto.setNomeCliente(venda.getNomeCliente());
            dto.setCpfCliente(venda.getCpfCliente());

            if (venda.getItens() != null) {
                List<ItemVendaDto> itensDto = venda.getItens().stream()
                    .map(item -> {
                        ItemVendaDto itemDto = new ItemVendaDto();
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

        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Please Try Again");
        }
        
    }
}
