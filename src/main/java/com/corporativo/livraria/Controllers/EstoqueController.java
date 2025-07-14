package com.corporativo.livraria.Controllers;

import com.corporativo.livraria.Service.EstoqueService;
import com.corporativo.livraria.Service.DTO.EstoqueDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estoque")
public class EstoqueController {

    @Autowired
    private EstoqueService estoqueService;

    @GetMapping
    public ResponseEntity<List<EstoqueDTO>> getAllEstoque() {
        List<EstoqueDTO> lista = estoqueService.getAll();
        if (lista.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{livroId}")
    public ResponseEntity<EstoqueDTO> getEstoqueByLivro(@PathVariable Long livroId) {
        return ResponseEntity.ok(estoqueService.getByLivroId(livroId));
    }

    @PutMapping("/{livroId}")
    public ResponseEntity<EstoqueDTO> atualizarQuantidade(@PathVariable Long livroId, @RequestBody Integer novaQuantidade) {
        return ResponseEntity.ok(estoqueService.atualizarQuantidade(livroId, novaQuantidade));
    }
}
