package com.hvivox.srealizacao.controller;

import com.hvivox.srealizacao.model.UserKeycloack;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserKeycloack userKeycloack) {
        return ResponseEntity.ok(userKeycloack);
    }

}
