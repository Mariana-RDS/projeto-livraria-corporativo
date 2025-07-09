package com.corporativo.livraria.Service.DTO;

import com.corporativo.livraria.Service.Entities.UserRole;

public record RegisterDTO(String login, String password, UserRole role) {

}
