package com.hvivox.srealizacao.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.persistence.*;
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "USUARIO")
public class User {
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDUSUARIO", nullable = false)
    private Integer id;

    @Size(max = 200)
    @NotNull
    @Column(name = "NOME", nullable = false, length = 200)
    private String name;

    @Size(max = 50)
    @NotNull
    @Column(name = "USUARIO", nullable = false, length = 50)
    private String user;

    @Size(max = 200)
    @NotNull
    @Column(name = "EMAIL", nullable = false, length = 200)
    private String email;

    @Size(max = 200)
    @NotNull
    @Column(name = "SENHA", nullable = false, length = 200)
    private String password;

    @Column(name = "ATIVO", columnDefinition = "tinyint")
    private Short active;


}