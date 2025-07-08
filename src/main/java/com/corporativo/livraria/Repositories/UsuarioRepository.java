package com.corporativo.livraria.Repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import com.corporativo.livraria.Service.Entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
    UserDetails findByLogin(String login);
}
