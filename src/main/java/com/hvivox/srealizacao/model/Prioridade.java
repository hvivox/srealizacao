package com.hvivox.srealizacao.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "PRIORIDADE", indexes = { @Index(name = "indx_id", columnList = "IDPRIORIDADE") })
public class Prioridade implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDPRIORIDADE", nullable = false)
    private Integer id;

    @Size(max = 250)
    @NotNull
    @Column(name = "DESCRICAO", nullable = false, length = 250)
    private String descricao;

    @NotNull
    @Column(name = "ISCONCLUIDO", nullable = false)
    private Boolean isconcluido = false;

    @NotNull
    @Column(name = "ORDEM", nullable = false)
    private Integer ordem;

    @NotNull
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "IDFOLHA", nullable = false, foreignKey = @ForeignKey(name = "fk_pagamento_pedido"))
    private Folha folha;


}