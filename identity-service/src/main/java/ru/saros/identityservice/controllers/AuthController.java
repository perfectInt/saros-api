package ru.saros.identityservice.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.saros.identityservice.dto.AuthRequestDto;
import ru.saros.identityservice.models.UserCredential;
import ru.saros.identityservice.services.AuthService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public String addNewUser(@RequestBody UserCredential user) {
        return authService.saveUser(user);
    }

    @PostMapping("/login")
    public String getToken(@RequestBody AuthRequestDto authRequestDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDto.getUsername(), authRequestDto.getPassword()));
        if (authentication.isAuthenticated()) return authService.generateToken(authRequestDto.getUsername());
        else throw new RuntimeException("invalid access");
    }
//
//    @GetMapping("/validate")
//    public String validateToken(@RequestParam("token") String token) {
//        authService.validateToken(token);
//        return "token is valid";
//    }
}
