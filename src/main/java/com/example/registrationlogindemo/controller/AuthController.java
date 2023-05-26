package com.example.registrationlogindemo.controller;

import com.example.registrationlogindemo.dto.UserDto;
import com.example.registrationlogindemo.entity.User;
import com.example.registrationlogindemo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class AuthController {

    private UserService userService;

    /**
     * Método constructor para inyectar el servicio de usuario
     *
     * @param userService Servicio de usuario
     */
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Método para manejar la solicitud de la página de inicio
     *
     * @return la página de inicio (index.html)
     */
    @GetMapping("index")
    public String home() {
        return "index";
    }

    /**
     * Método para manejar la solicitud de inicio de sesión
     *
     * @return la página de inicio de sesión (login.html)
     */
    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    /**
     * Método para manejar la solicitud de registro de usuario
     *
     * @param model que se utilizará para agregar el objeto de usuario para el formulario de registro
     * @return la página de registro (register.html) con el objeto de usuario agregado al modelo
     */
    @GetMapping("register")
    public String showRegistrationForm(Model model) {
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "register";
    }

    /**
     * Método para manejar la solicitud de envío del formulario de registro de usuario
     *
     * @param user   que se utilizará para guardar el usuario en la base de datos
     * @param result que se utilizará para validar el formulario de registro
     * @param model  que se utilizará para agregar el objeto de usuario para el formulario de registro
     * @return la página de registro (register.html) si hay errores, de lo contrario, redirige a la página de
     * inicio de sesión (login.html)
     */
    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto user, BindingResult result, Model model) {
        User existing = userService.findByEmail(user.getEmail());
        if (existing != null) {
            result.rejectValue("email", null, "There is already an account registered with that email");
        }
        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return "register";
        }
        userService.saveUser(user);
        return "redirect:/register?success";
    }

    /**
     * Método para manejar la solicitud de la página de usuarios registrados
     *
     * @param model que se utilizará para agregar la lista de usuarios registrados
     * @return la página de usuarios registrados (users.html)
     */
    @GetMapping("/users")
    public String listRegisteredUsers(Model model) {
        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "users";
    }
}
