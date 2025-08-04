package com.corporativo.livraria.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.corporativo.livraria.Repositories.UsuarioRepository;
import com.corporativo.livraria.Service.DTO.AuthenticationDTO;
import com.corporativo.livraria.Service.DTO.LoginResponseDTO;
import com.corporativo.livraria.Service.DTO.RegisterDTO; // Importação necessária
import com.corporativo.livraria.Service.Entities.Usuario;
import com.corporativo.livraria.infra.security.TokenService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    TokenService tokenService;

    @Autowired 
    UsuarioRepository usuarioRepository;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO dto){
        // 'var' foi substituído por 'UsernamePasswordAuthenticationToken'
        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(dto.login(), dto.password());
        
        // 'var' foi substituído por 'Authentication'
        Authentication auth = this.authenticationManager.authenticate(usernamePassword);

        // 'var' foi substituído por 'String'
        String token = tokenService.generateToken((Usuario)auth.getPrincipal());
        
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO dto){
        if(this.usuarioRepository.findByLogin(dto.login()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(dto.password());
        Usuario newUser = new Usuario(dto.login(), encryptedPassword, dto.role());

        this.usuarioRepository.save(newUser);

        return ResponseEntity.ok().build();
    }
}