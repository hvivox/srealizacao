package com.hvivox.srealizacao.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserKeycloack {
    @NotBlank(message = "O campo usuário é obrigatório")
    //@Column(name = "USUARIO", nullable = false, length = 50)
    private String userName;

    @Size(min=6, message = "O campo senha é obrigatório" )
    //@Column(name = "SENHA", nullable = false, length = 200)
    private String password;
}
