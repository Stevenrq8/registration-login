package com.example.registrationlogindemo.service.impl;

import com.example.registrationlogindemo.dto.UserDto;
import com.example.registrationlogindemo.entity.Role;
import com.example.registrationlogindemo.entity.User;
import com.example.registrationlogindemo.repository.RoleRepository;
import com.example.registrationlogindemo.repository.UserRepository;
import com.example.registrationlogindemo.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    /**
     * Método constructor para inyectar las dependencias
     *
     * @param userRepository  repositorio de usuarios
     * @param roleRepository  repositorio de roles
     * @param passwordEncoder codificador de contraseñas
     */
    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Método para guardar un usuario
     *
     * @param userDto objeto con los datos del usuario
     */
    @Override
    public void saveUser(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getFirstName() + " " + userDto.getLastName());
        user.setEmail(userDto.getEmail());

        // Cifrar la contraseña una vez que integremos Spring Security
        // user.setPassword(userDto.getPassword());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        Role role = roleRepository.findByName("ROLE_ADMIN");
        if (role == null) {
            role = checkRoleExist();
        }
        user.setRoles(Arrays.asList(role));
        userRepository.save(user);
    }

    /**
     * Método para buscar un usuario por su email
     *
     * @param email email del usuario
     * @return el usuario encontrado
     */
    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Método para buscar a todos los usuarios
     *
     * @return la lista de usuarios
     */
    @Override
    public List<UserDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map((user) -> convertEntityToDto(user))
                .collect(Collectors.toList());
    }

    /**
     * Método para convertir una entidad a un DTO, en este caso de User a UserDto
     *
     * @param user entidad de usuario
     * @return DTO de usuario
     */
    private UserDto convertEntityToDto(User user) {
        UserDto userDto = new UserDto();
        String[] name = user.getName().split(" ");
        userDto.setFirstName(name[0]);
        userDto.setLastName(name[1]);
        userDto.setEmail(user.getEmail());
        return userDto;
    }

    /**
     * Método para verificar si existe el rol ROLE_ADMIN, si no existe lo crea
     *
     * @return el rol ROLE_ADMIN
     */
    private Role checkRoleExist() {
        Role role = new Role();
        role.setName("ROLE_ADMIN");
        return roleRepository.save(role);
    }
}
