package com.corporativo.livraria.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.corporativo.livraria.Repositories.UsuarioRepository;
import com.corporativo.livraria.Service.Entities.Usuario;

@Service
public class AutenticacaoService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Usuario registrar(Usuario usuario){
        
        try {
            usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
            return usuarioRepository.save(usuario);   

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Please Try Again"); 
        }
    }

    public boolean autenticar(String email, String senha){
        try {
            Optional<Usuario> usuarioRegistrado = usuarioRepository.findByEmail(email);

            if(usuarioRegistrado.isPresent()){
                Usuario usuario = usuarioRegistrado.get();
                return passwordEncoder.matches(senha, usuario.getSenha());
            }

            return false;

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Please Try Again");
        }
    }

    public Optional<Usuario> buscarPorEmail(String email){
        return usuarioRepository.findByEmail(email);
    }

}
