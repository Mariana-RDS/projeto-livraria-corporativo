package com.corporativo.livraria.Mapper;

import java.util.List;
import org.mapstruct.*;

import com.corporativo.livraria.Dto.LivroDto;
import com.corporativo.livraria.Entities.LivroEntity;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface LivroMapper {

    LivroEntity toEntity(LivroDto livroDto);
    List<LivroEntity> toEntityList(List<LivroDto> livroDtos);
    
    LivroDto toDto(LivroEntity livroEntity);
    List<LivroDto> toDtoList(List<LivroEntity> livroEntities);
    
    
}
