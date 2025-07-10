package com.corporativo.livraria.Service.Entities;

import java.util.HashSet;
import java.util.Set;
import java.math.BigDecimal; 

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
@Table(name = "livro")
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titulo", nullable = false, length = 128)
    private String titulo;

    @Column(name = "isbn", nullable = false, length = 15)
    private String isbn;

    @Column(name = "preco", precision = 10, scale = 2)
    private BigDecimal preco;

    @ManyToOne
    @JoinColumn(name = "id_editora")
    private Editora editora;

    @ManyToMany
    @JoinTable(
        name = "livro_autor",
        joinColumns = @JoinColumn(name = "id_livro"),
        inverseJoinColumns = @JoinColumn(name = "id_autor")
    )
    private Set<Autor> autores = new HashSet<>();
    

}
