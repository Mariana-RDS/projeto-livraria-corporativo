package com.corporativo.livraria.Service;

import com.corporativo.livraria.Repositories.EstoqueRepository;
import com.corporativo.livraria.Repositories.LivroRepository;
import com.corporativo.livraria.Service.DTO.EstoqueDTO;
import com.corporativo.livraria.Service.Entities.Estoque;
import com.corporativo.livraria.Service.Entities.Livro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EstoqueService {

    @Autowired
    private EstoqueRepository estoqueRepository;

    @Autowired
    private LivroRepository livroRepository;

    public EstoqueDTO getByLivroId(Long livroId) {
        try {
            Estoque estoque = estoqueRepository.findByLivroId(livroId);
            if (estoque == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Estoque não encontrado");
            }

            EstoqueDTO dto = new EstoqueDTO();
            dto.setLivroId(livroId);
            dto.setQuantidade(estoque.getQuantidade());
            dto.setTituloLivro(estoque.getLivro().getTitulo());

            return dto;

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao buscar estoque");
        }
    }

    public List<EstoqueDTO> getAll() {
        try {
            return estoqueRepository.findAll().stream()
                    .map(e -> {
                        EstoqueDTO dto = new EstoqueDTO();
                        dto.setLivroId(e.getLivro().getId());
                        dto.setTituloLivro(e.getLivro().getTitulo());
                        dto.setQuantidade(e.getQuantidade());
                        return dto;
                    }).collect(Collectors.toList());

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao listar estoques");
        }
    }

    public EstoqueDTO atualizarQuantidade(Long livroId, Integer novaQuantidade) {
        try {
            Estoque estoque = estoqueRepository.findByLivroId(livroId);
            if (estoque == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Estoque não encontrado");
            }

            estoque.setQuantidade(novaQuantidade);
            estoqueRepository.save(estoque);

            EstoqueDTO dto = new EstoqueDTO();
            dto.setLivroId(livroId);
            dto.setQuantidade(novaQuantidade);
            dto.setTituloLivro(estoque.getLivro().getTitulo());

            return dto;

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao atualizar estoque");
        }
    }
}
