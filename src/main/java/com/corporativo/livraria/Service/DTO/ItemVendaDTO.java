package com.corporativo.livraria.Service.DTO;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ItemVendaDTO {
    private Long id;
    private Long livroId;
    private Integer quantidade;
    //private BigDecimal precoUnitario;
}
