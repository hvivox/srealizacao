package com.hvivox.srealizacao.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "PRIORIDADE", indexes = { @Index(name = "indx_id", columnList = "IDPRIORIDADE") })
public class Priority implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDPRIORIDADE", nullable = false)
    private Integer id;

    @Size(max = 250)
    @NotNull
    @Column(name = "DESCRICAO", nullable = false, length = 250)
    private String description;

    @NotNull
    @Column(name = "ISCONCLUIDO", nullable = false)
    private Boolean isConcluded = false;

    @NotNull
    @Column(name = "ORDEM", nullable = false)
    private Integer order;

    @NotNull
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "IDFOLHA", nullable = false, foreignKey = @ForeignKey(name = "fk_pagamento_pedido"))
    private Sheet sheet;


}