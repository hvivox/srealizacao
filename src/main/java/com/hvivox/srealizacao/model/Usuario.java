package com.hvivox.srealizacao.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "USUARIO")
public class Usuario {
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDUSUARIO", nullable = false)
    private Integer id;

    @Size(max = 200)
    @NotNull
    @Column(name = "NOME", nullable = false, length = 200)
    private String nome;

    @Size(max = 50)
    @NotNull
    @Column(name = "USUARIO", nullable = false, length = 50)
    private String usuario;

    @Size(max = 200)
    @NotNull
    @Column(name = "EMAIL", nullable = false, length = 200)
    private String email;

    @Size(max = 200)
    @NotNull
    @Column(name = "SENHA", nullable = false, length = 200)
    private String senha;

    @Column(name = "ATIVO", columnDefinition = "tinyint")
    private Short ativo;


}