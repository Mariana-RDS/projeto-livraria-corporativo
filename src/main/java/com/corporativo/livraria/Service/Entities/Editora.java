package com.corporativo.livraria.Service.Entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "editora")
public class Editora {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome_editora", nullable = false, length = 128)
    private String nomeEditora;

    @OneToMany(mappedBy = "editora")
    private List<Livro> livros = new ArrayList<>();

    
}
