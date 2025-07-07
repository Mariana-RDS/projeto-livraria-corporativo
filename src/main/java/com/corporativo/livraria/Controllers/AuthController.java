package com.corporativo.livraria.Controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.corporativo.livraria.Service.AutenticacaoService;
import com.corporativo.livraria.Service.Entities.Usuario;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AutenticacaoService autenticacaoService;

    

    @PostMapping("/login")
    public ResponseEntity<Usuario> login(@RequestBody Usuario usuario, HttpSession session){
        try {

            if(autenticacaoService.autenticar(usuario.getEmail(), usuario.getSenha())){
                Optional<Usuario> usuarioRegistrado = autenticacaoService.buscarPorEmail(usuario.getEmail());

                if(usuarioRegistrado.isPresent()){
                    session.setAttribute("usuarioLogado", usuarioRegistrado.get());
                    return ResponseEntity.ok(usuarioRegistrado.get());
                }
            }
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session){
        try {
            session.invalidate();
            return ResponseEntity.ok("Logout realizado com sucesso");

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/usuario")
    public ResponseEntity<Usuario> getUsuarioLogado(HttpSession session){
        try {
            Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");

            if(usuario != null){
                return ResponseEntity.ok(usuario);
            }
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    

    

}
