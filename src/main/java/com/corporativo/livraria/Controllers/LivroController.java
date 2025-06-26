package com.corporativo.livraria.Controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.corporativo.livraria.Dto.LivroDto;
import com.corporativo.livraria.Service.LivroService;

@Controller
@RequestMapping("/api")
public class LivroController {

    @Autowired
    private LivroService livroService;

    @PostMapping("/livros/create")
    public ResponseEntity<LivroDto> createLivro(@RequestBody LivroDto livroDto){
        try {
            LivroDto dto = livroService.create(livroDto);
            if (dto == null) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/livros")
    public ResponseEntity<Set<LivroDto>> getAllLivros(){
        try {
            Set<LivroDto> dtos = livroService.getAll();
            if(dtos == null || dtos.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(dtos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/livros/{id}")
    public ResponseEntity<Void> deleteLivro(@PathVariable Long id) {
        boolean deleted = livroService.delete(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/livros/{id}")
    public ResponseEntity<LivroDto> updateLivro(@PathVariable Long id, @RequestBody LivroDto livroDto) {
        LivroDto updatedLivro = livroService.update(id, livroDto);
        if (updatedLivro == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedLivro);
    }
}
