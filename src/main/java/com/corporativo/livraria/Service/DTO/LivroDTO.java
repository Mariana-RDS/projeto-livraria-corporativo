package com.corporativo.livraria.Service.DTO;

import java.util.Set;
import java.math.BigDecimal; 

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LivroDTO {
    private Long id;
    private String titulo;
    private String isbn;
    private BigDecimal preco;
    private String nomeEditora;
    private Set<String> nomesAutores;
    private Integer quantidadeEstoque;

}
