package org.example.pnubookstore.controller;

import org.example.pnubookstore.domain.user.dto.CreateUserDto;
import org.example.pnubookstore.domain.user.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/signUp")
    public String signUpPage() {

        return "signUp";
    }
    @PostMapping("/signUp")
    public String createUser(@ModelAttribute CreateUserDto userDto) {
        userService.createUser(userDto);
        return "redirect:/";
    }
}
