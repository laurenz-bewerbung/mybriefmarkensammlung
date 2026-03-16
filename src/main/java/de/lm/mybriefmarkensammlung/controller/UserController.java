package de.lm.mybriefmarkensammlung.controller;

import de.lm.mybriefmarkensammlung.dto.request.RegistrationRequest;
import de.lm.mybriefmarkensammlung.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login() {
        return "users/login";
    }

    @GetMapping("/register")
    public String register() {
        return "users/register";
    }

    @PostMapping("/register")
    public String register(RegistrationRequest registrationRequest) {
        userService.registerUser(registrationRequest);
        return "redirect:/";
    }

}