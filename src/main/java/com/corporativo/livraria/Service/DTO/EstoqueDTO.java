package com.corporativo.livraria.Service.DTO;

import lombok.Data;

@Data
public class EstoqueDTO {
    private Long livroId;
    private String tituloLivro;
    private Integer quantidade;
}
