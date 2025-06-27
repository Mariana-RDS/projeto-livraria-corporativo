package com.corporativo.livraria.Entities;

import java.util.HashSet;
import java.util.Set;

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
@Table(name = "autor")
public class AutorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome_autor", nullable = false, length = 128)
    private String nomeAutor;

    @ManyToMany(mappedBy = "autores")
    private Set<LivroEntity> livros = new HashSet<>();
}

//colocar na pasta service o que for de modelo
//alterar Dto para DTO
//Renomear Entidade, nome simples
//olhar para livro service, ajustar uso mapping
//ajustar try catch no livro service
//ajustar cascade do delete