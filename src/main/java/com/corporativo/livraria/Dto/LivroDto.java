package com.corporativo.livraria.Dto;

import java.util.Set;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LivroDto {
    private String titulo;
    private String isbn;
    private String nomeEditora;
    private Set<String> nomesAutores;
    private Integer quantidadeEstoque;

}
