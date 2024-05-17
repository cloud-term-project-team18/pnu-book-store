package org.example.pnubookstore.controller;

import jakarta.validation.constraints.Null;
import org.example.pnubookstore.domain.user.dto.CreateUserDto;
import org.example.pnubookstore.domain.user.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

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
    @GetMapping("/signUp/email-verify")
    public void emailVerify(@RequestParam String email){
        userService.emailVerify(email);
    }

    @GetMapping("/signUp/after-email")
    public String afterEmail(){
        return "afterEmail";
    }
    @PostMapping("/signUp")
    public String createUser(@ModelAttribute CreateUserDto userDto) {
        userService.createUser(userDto);
        return "redirect:/";
    }

    @PostMapping("/signUp/after-email-form")
    public String afterEmailForm(@RequestBody CreateUserDto request){
        userService.afterEmailForm();
        return "redirect:/";
    }

    @PostMapping("/login")
    public String login(){

        return "";
    }
}
