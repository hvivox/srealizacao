package com.hvivox.srealizacao.controller;

import com.hvivox.srealizacao.model.UserKeycloack;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class AuthController {

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserKeycloack userKeycloack) {
        log.info("Login: {}", userKeycloack.toString());
        return ResponseEntity.ok(userKeycloack);
    }

}
