package com.corporativo.livraria.Mapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.*;

import com.corporativo.livraria.Service.DTO.LivroDTO;
import com.corporativo.livraria.Service.Entities.Autor;
import com.corporativo.livraria.Service.Entities.Livro;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface LivroMapper {

    @Mapping(target = "nomeEditora", source = "editora.nomeEditora")
    @Mapping(target = "nomesAutores", expression = "java(mapAutoresToNomes(livro.getAutores()))")
    LivroDTO toDto(Livro livro);

    List<LivroDTO> toDtoList(List<Livro> livro);

    Livro toEntity(LivroDTO livroDto);

    List<Livro> toEntityList(List<LivroDTO> livroDtos);

    default Set<String> mapAutoresToNomes(Set<Autor> autores) {
        if (autores == null) {
            return null;
        }
        return autores.stream()
            .map(Autor::getNomeAutor)
            .collect(Collectors.toSet());
    }
}
