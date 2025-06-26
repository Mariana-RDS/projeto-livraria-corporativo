package com.corporativo.livraria.Mapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.*;

import com.corporativo.livraria.Dto.LivroDto;
import com.corporativo.livraria.Entities.AutorEntity;
import com.corporativo.livraria.Entities.LivroEntity;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface LivroMapper {

    @Mapping(target = "nomeEditora", source = "editora.nomeEditora")
    @Mapping(target = "nomesAutores", expression = "java(mapAutoresToNomes(livroEntity.getAutores()))")
    LivroDto toDto(LivroEntity livroEntity);

    List<LivroDto> toDtoList(List<LivroEntity> livroEntities);

    LivroEntity toEntity(LivroDto livroDto);

    List<LivroEntity> toEntityList(List<LivroDto> livroDtos);

    default Set<String> mapAutoresToNomes(Set<AutorEntity> autores) {
        if (autores == null) {
            return null;
        }
        return autores.stream()
            .map(AutorEntity::getNomeAutor)
            .collect(Collectors.toSet());
    }
}
