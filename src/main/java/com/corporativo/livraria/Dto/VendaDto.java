package com.corporativo.livraria.Dto;

import java.util.List;

public class VendaDto {
    private Long id;
    private String data;
    private String nomeCliente;
    private String cpfCliente;
    private List<ItemVendaDto> itens; 

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public String getCpfCliente() {
        return cpfCliente;
    }

    public void setCpfCliente(String cpfCliente) {
        this.cpfCliente = cpfCliente;
    }

    public List<ItemVendaDto> getItens() {
        return itens;
    }

    public void setItens(List<ItemVendaDto> itens) {
        this.itens = itens;
    }
}
