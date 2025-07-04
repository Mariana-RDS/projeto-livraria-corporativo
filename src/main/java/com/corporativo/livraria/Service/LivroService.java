package com.corporativo.livraria.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.corporativo.livraria.Mapper.LivroMapper;
import com.corporativo.livraria.Repositories.AutorRepository;
import com.corporativo.livraria.Repositories.EditoraRepository;
import com.corporativo.livraria.Repositories.EstoqueRepository;
import com.corporativo.livraria.Repositories.LivroRepository;
import com.corporativo.livraria.Service.DTO.LivroDTO;
import com.corporativo.livraria.Service.Entities.Autor;
import com.corporativo.livraria.Service.Entities.Editora;
import com.corporativo.livraria.Service.Entities.Estoque;
import com.corporativo.livraria.Service.Entities.Livro;

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

    public LivroDTO create(LivroDTO dto) {
        try {
            Editora editora = editoraRepository.findByNomeEditora(dto.getNomeEditora())
                .orElseGet(() -> {
                    Editora novaEditora = new Editora();
                    novaEditora.setNomeEditora(dto.getNomeEditora());
                    return editoraRepository.save(novaEditora);
                });

            Set<Autor> autores = dto.getNomesAutores().stream()
                .map(nome -> autorRepository.findByNomeAutor(nome)
                    .orElseGet(() -> {
                        Autor novoAutor = new Autor();
                        novoAutor.setNomeAutor(nome);
                        return autorRepository.save(novoAutor);
                    }))
                .collect(Collectors.toSet());

            Livro livro = livroMapper.toEntity(dto);
            livro.setTitulo(dto.getTitulo());
            livro.setIsbn(dto.getIsbn());
            livro.setEditora(editora);
            livro.setAutores(autores);

            Livro livroSalvo = livroRepository.save(livro);

            Estoque estoque = new Estoque();
            estoque.setLivro(livroSalvo);
            estoque.setQuantidade(dto.getQuantidadeEstoque() != null ? dto.getQuantidadeEstoque() : 0);
            estoqueRepository.save(estoque);

            LivroDTO retorno = livroMapper.toDto(livroSalvo);
            retorno.setQuantidadeEstoque(estoque.getQuantidade());

            return retorno;

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Please Try Again");
        }
    }

    public Set<LivroDTO> getAll() {
        try {
            List<Livro> livros = livroRepository.findAll();

            Set<LivroDTO> livroDtos = livros.stream()
                .map(livro -> {
                    LivroDTO dto = livroMapper.toDto(livro);
                    Estoque estoque = estoqueRepository.findByLivroId(livro.getId());
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
            Livro livro = livroRepository.findById(id).orElse(null);
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

    public LivroDTO update(Long id, LivroDTO dto) {
        try{
            Livro livro = livroRepository.findById(id).orElse(null);
                if (livro == null) {
                    return null;
            }

            livro.setTitulo(dto.getTitulo());
            livro.setIsbn(dto.getIsbn());

            Editora editora = editoraRepository.findByNomeEditora(dto.getNomeEditora()).orElse(null);
            if (editora == null) {
                Editora novaEditora = new Editora();
                novaEditora.setNomeEditora(dto.getNomeEditora());
                editora = editoraRepository.save(novaEditora);
            }
            livro.setEditora(editora);

            Set<Autor> autores = dto.getNomesAutores().stream()
                .map(nome -> {
                    Autor autor = autorRepository.findByNomeAutor(nome).orElse(null);
                    if (autor == null) {
                        Autor novoAutor = new Autor();
                        novoAutor.setNomeAutor(nome);
                        autor = autorRepository.save(novoAutor);
                    }
                    return autor;
                })
                .collect(Collectors.toSet());

            livro.setAutores(autores);

            Livro livroAtualizado = livroRepository.save(livro);

            Estoque estoque = estoqueRepository.findByLivroId(livroAtualizado.getId());
            if (estoque != null) {
                estoque.setQuantidade(dto.getQuantidadeEstoque() != null ? dto.getQuantidadeEstoque() : 0);
                estoqueRepository.save(estoque);
            }

            LivroDTO retorno = livroMapper.toDto(livroAtualizado);
            retorno.setQuantidadeEstoque(estoque != null ? estoque.getQuantidade() : 0);

            return retorno;

        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Please Try Again");
        }
    }
        
}
