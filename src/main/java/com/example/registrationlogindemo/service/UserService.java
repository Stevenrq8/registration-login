package com.example.registrationlogindemo.service;

import com.example.registrationlogindemo.dto.UserDto;
import com.example.registrationlogindemo.entity.User;

import java.util.List;

public interface UserService {

    /**
     * Método para guardar un usuario
     *
     * @param userDto objeto con los datos del usuario
     */
    void saveUser(UserDto userDto);

    /**
     * Método para buscar un usuario por su email
     *
     * @param email email del usuario
     * @return el usuario encontrado
     */
    User findByEmail(String email);

    /**
     * Método para buscar a todos los usuarios
     *
     * @return la lista de usuarios
     */
    List<UserDto> findAllUsers();
}
