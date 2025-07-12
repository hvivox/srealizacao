package com.hvivox.srealizacao.controller;

import com.hvivox.srealizacao.model.UserKeycloack;
import com.hvivox.srealizacao.service.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class AuthController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    @Operation(description = "Usuário passa o login para autenticação no Keycloack. Keycloack valida o login e retorna um token de autenticação.")
    public ResponseEntity<?> login(@Valid @RequestBody UserKeycloack userKeycloack) {
        Thread.currentThread().setName(String.valueOf(System.currentTimeMillis()));
        log.info("Login: {}", "Acessando loginController");
        return loginService.login(userKeycloack);

    }

    // criar um endpoint para refresh token
    @PostMapping("/refresh")
    @Operation(description = "Usuário passa o token de autenticação para autenticação no Keycloack. Keycloack valida o token e retorna um novo token de autenticação.")
    public ResponseEntity<?> refresh(@RequestParam("refresh_token") String refeshToken) {
        Thread.currentThread().setName(String.valueOf(System.currentTimeMillis()));
        log.info("Refresh: {}", "Acessando refreshController");
        return loginService.refreshToken(refeshToken);

    }






}
