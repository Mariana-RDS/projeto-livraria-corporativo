package com.corporativo.livraria.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.corporativo.livraria.Dto.LivroDto;
import com.corporativo.livraria.Entities.AutorEntity;
import com.corporativo.livraria.Entities.EditoraEntity;
import com.corporativo.livraria.Entities.EstoqueEntity;
import com.corporativo.livraria.Entities.LivroEntity;
import com.corporativo.livraria.Mapper.LivroMapper;
import com.corporativo.livraria.Repositories.AutorRepository;
import com.corporativo.livraria.Repositories.EditoraRepository;
import com.corporativo.livraria.Repositories.EstoqueRepository;
import com.corporativo.livraria.Repositories.LivroRepository;

@Service
public class LivroService {

    @Autowired
    private LivroRepository livroRepository;
    @Autowired
    private EstoqueRepository estoqueRepository;
    @Autowired
    private AutorRepository autorRepository;
    @Autowired
    private EditoraRepository editoraRepository;
    @Autowired
    private LivroMapper livroMapper;

    public LivroDto create(LivroDto dto) {
        try {
            EditoraEntity editora = editoraRepository.findByNomeEditora(dto.getNomeEditora())
                .orElseGet(() -> {
                    EditoraEntity novaEditora = new EditoraEntity();
                    novaEditora.setNomeEditora(dto.getNomeEditora());
                    return editoraRepository.save(novaEditora);
                });

            Set<AutorEntity> autores = dto.getNomesAutores().stream()
                .map(nome -> autorRepository.findByNomeAutor(nome)
                    .orElseGet(() -> {
                        AutorEntity novoAutor = new AutorEntity();
                        novoAutor.setNomeAutor(nome);
                        return autorRepository.save(novoAutor);
                    }))
                .collect(Collectors.toSet());

            LivroEntity livro = livroMapper.toEntity(dto);
            livro.setTitulo(dto.getTitulo());
            livro.setIsbn(dto.getIsbn());
            livro.setEditora(editora);
            livro.setAutores(autores);

            LivroEntity livroSalvo = livroRepository.save(livro);

            EstoqueEntity estoque = new EstoqueEntity();
            estoque.setLivro(livroSalvo);
            estoque.setQuantidade(dto.getQuantidadeEstoque() != null ? dto.getQuantidadeEstoque() : 0);
            estoqueRepository.save(estoque);

            LivroDto retorno = livroMapper.toDto(livroSalvo);
            retorno.setQuantidadeEstoque(estoque.getQuantidade());

            return retorno;

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Please Try Again");
        }
    }

    public Set<LivroDto> getAll() {
        try {
            List<LivroEntity> livros = livroRepository.findAll();

            Set<LivroDto> livroDtos = livros.stream()
                .map(livro -> {
                    LivroDto dto = livroMapper.toDto(livro);
                    EstoqueEntity estoque = estoqueRepository.findByLivroId(livro.getId());
                    dto.setQuantidadeEstoque(estoque != null ? estoque.getQuantidade() : 0);
                    return dto;
                })
                .collect(Collectors.toSet());

            return livroDtos;

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Please Try Again");
        }
    }

    @Transactional
    public boolean delete(Long id) {
        try{
            LivroEntity livro = livroRepository.findById(id).orElse(null);
                if (livro != null) {
                    estoqueRepository.deleteByLivroId(id);
                    livroRepository.deleteById(id);
                    return true;
                }
                return false;
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Please Try Again");
        }
        
    }

    public LivroDto update(Long id, LivroDto dto) {
        try{
            LivroEntity livro = livroRepository.findById(id).orElse(null);
                if (livro == null) {
                    return null;
            }

            livro.setTitulo(dto.getTitulo());
            livro.setIsbn(dto.getIsbn());

            EditoraEntity editora = editoraRepository.findByNomeEditora(dto.getNomeEditora()).orElse(null);
            if (editora == null) {
                EditoraEntity novaEditora = new EditoraEntity();
                novaEditora.setNomeEditora(dto.getNomeEditora());
                editora = editoraRepository.save(novaEditora);
            }
            livro.setEditora(editora);

            Set<AutorEntity> autores = dto.getNomesAutores().stream()
                .map(nome -> {
                    AutorEntity autor = autorRepository.findByNomeAutor(nome).orElse(null);
                    if (autor == null) {
                        AutorEntity novoAutor = new AutorEntity();
                        novoAutor.setNomeAutor(nome);
                        autor = autorRepository.save(novoAutor);
                    }
                    return autor;
                })
                .collect(Collectors.toSet());

            livro.setAutores(autores);

            LivroEntity livroAtualizado = livroRepository.save(livro);

            EstoqueEntity estoque = estoqueRepository.findByLivroId(livroAtualizado.getId());
            if (estoque != null) {
                estoque.setQuantidade(dto.getQuantidadeEstoque() != null ? dto.getQuantidadeEstoque() : 0);
                estoqueRepository.save(estoque);
            }

            LivroDto retorno = livroMapper.toDto(livroAtualizado);
            retorno.setQuantidadeEstoque(estoque != null ? estoque.getQuantidade() : 0);

            return retorno;

        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Please Try Again");
        }
    }
        
}
