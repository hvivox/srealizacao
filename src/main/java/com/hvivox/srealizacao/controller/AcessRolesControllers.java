package com.hvivox.srealizacao.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AcessRolesControllers {

    @GetMapping("/admin")
    @PreAuthorize("hasAnyAuthority('ADMIN_READ','ADMIN_WRITE')")
    public String adminAccess(){
        return "Acesso concedido ao ADMIN";
    }

    @GetMapping("/operation")
    @PreAuthorize("hasAnyAuthority('OPERATION_READ','OPERATION_WRITE')")
    public String operationAccess(){
        return "Acesso concedido à OPERAÇÃO";
    }

}
