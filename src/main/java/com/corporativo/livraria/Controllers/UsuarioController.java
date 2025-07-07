package com.corporativo.livraria.Controllers;

import org.springframework.web.bind.annotation.RestController;

import com.corporativo.livraria.Service.AutenticacaoService;
import com.corporativo.livraria.Service.Entities.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    
    private final AutenticacaoService autenticacaoService;

    @Autowired
    public UsuarioController(AutenticacaoService autenticacaoService) {
        this.autenticacaoService = autenticacaoService;
    }

    @PostMapping("/registrar")
    public ResponseEntity<Usuario> registrar(@RequestBody Usuario usuario){
        try {

            if(usuario.getEmail() == null || usuario.getEmail().isEmpty() || 
                usuario.getSenha() == null || usuario.getSenha().isEmpty()) {
                return ResponseEntity.badRequest().build();
            }

            Usuario usuarioRegistrado = autenticacaoService.registrar(usuario);
            return ResponseEntity.ok(usuarioRegistrado);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    } 

}
