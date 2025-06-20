package com.corporativo.livraria.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
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


    public LivroDto create(LivroDto dto){
        try {
            EditoraEntity editora = editoraRepository.findByNomeEditora(dto.getNomeEditora())
            .orElseGet(() ->{
                EditoraEntity novaEditora = new EditoraEntity();
                novaEditora.setNomeEditora(dto.getNomeEditora());
                return editoraRepository.save(novaEditora);
            });

            Set<AutorEntity> autores = dto.getNomesAutores().stream()
                .map(nome -> autorRepository.findByNomeAutor(nome)
                    .orElseGet(()->{
                        AutorEntity novoAutor = new AutorEntity();
                        novoAutor.setNomeAutor(nome);
                        return autorRepository.save(novoAutor);
                    }))
                .collect(Collectors.toSet());

            LivroEntity livro = new LivroEntity();
            livro.setTitulo(dto.getTitulo());
            livro.setIsbn(dto.getIsbn());
            livro.setEditora(editora);
            livro.setAutores((autores));

            LivroEntity livroSalvo = livroRepository.save(livro);

            EstoqueEntity estoque = new EstoqueEntity();
            estoque.setLivro(livroSalvo);
            estoque.setQuantidade(dto.getQuantidadeEstoque());
            estoqueRepository.save(estoque);

            return livroMapper.toDto(livroSalvo);
                        
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Please Try Again");
        }
    }


    public Set<LivroDto> getAll(){
        try {
            List<LivroEntity> livros = livroRepository.findAll();

            Set<LivroDto> livroDtos = livros.stream()
                .map(livro ->{
                    LivroDto dto = new LivroDto();
                    dto.setTitulo(livro.getTitulo());
                    dto.setIsbn(livro.getIsbn());
                    dto.setNomeEditora(livro.getEditora().getNomeEditora());
                    dto.setNomesAutores(livro.getAutores().stream()
                        .map(AutorEntity::getNomeAutor)
                        .collect(Collectors.toSet()));
                    return dto;
                })
                .collect(Collectors.toSet());
            
                return livroDtos;
    
        } catch (Exception e) {
          throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Please Try Again");
        }
    }


}
