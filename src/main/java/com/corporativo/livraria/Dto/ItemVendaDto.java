package com.corporativo.livraria.Dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ItemVendaDto {
    private Long id;
    private Long livroId;
    private Integer quantidade;
    private BigDecimal precoUnitario;
}
