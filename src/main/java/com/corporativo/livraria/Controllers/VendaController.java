package com.corporativo.livraria.Controllers;

import com.corporativo.livraria.Dto.VendaDto;
import com.corporativo.livraria.Service.VendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
        try{
            return ResponseEntity.ok(vendaService.create(dto));
        }catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }

    @GetMapping
    public ResponseEntity<List<VendaDto>> getAll() {
        try{
            List<VendaDto> vendas = vendaService.getAll();
            if (vendas.isEmpty()) return ResponseEntity.noContent().build();
            return ResponseEntity.ok(vendas);
        }catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }

    @GetMapping("/{id}")
    public ResponseEntity<VendaDto> getById(@PathVariable Long id) {
        try{
            return vendaService.getById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
        }catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<List<VendaDto>> getByCpf(@PathVariable String cpf) {
        try{
            List<VendaDto> vendas = vendaService.getByCpf(cpf);
            if (vendas.isEmpty()) return ResponseEntity.noContent().build();
            return ResponseEntity.ok(vendas);
        }catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try{
            vendaService.delete(id);
            return ResponseEntity.noContent().build();
        }catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }
}
