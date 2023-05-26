package com.example.registrationlogindemo.repository;

import com.example.registrationlogindemo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Método para buscar un usuario por email
     *
     * @param email email del usuario
     * @return el usuario encontrado
     */
    User findByEmail(String email);
}
