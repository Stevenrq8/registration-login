package com.example.registrationlogindemo.repository;

import com.example.registrationlogindemo.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    /**
     * Método para buscar un rol por su nombre
     *
     * @param name nombre del rol
     * @return el rol encontrado
     */
    Role findByName(String name);
}
