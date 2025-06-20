package com.corporativo.livraria.Controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.corporativo.livraria.Dto.LivroDto;
import com.corporativo.livraria.Service.LivroService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
@RequestMapping("/api")
public class LivroController {

    @Autowired
    private LivroService livroService;


    @PostMapping("/create")
    public ResponseEntity<LivroDto> createLivro(@RequestBody LivroDto livroDto){
        try {
            LivroDto dto = livroService.create(livroDto);
            if (dto==null) {
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
            if(dtos==null || dtos.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(dtos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    

}
