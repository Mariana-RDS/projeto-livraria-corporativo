package com.corporativo.livraria.Controllers;

import com.corporativo.livraria.Dto.VendaDto;
import com.corporativo.livraria.Service.VendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vendas")
public class VendaController {

    @Autowired
    private VendaService vendaService;

    @PostMapping
    public ResponseEntity<VendaDto> create(@RequestBody VendaDto dto) {
        return ResponseEntity.ok(vendaService.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<VendaDto>> getAll() {
        List<VendaDto> vendas = vendaService.getAll();
        if (vendas.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(vendas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VendaDto> getById(@PathVariable Long id) {
        return vendaService.getById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<List<VendaDto>> getByCpf(@PathVariable String cpf) {
        List<VendaDto> vendas = vendaService.getByCpf(cpf);
        if (vendas.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(vendas);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        vendaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
