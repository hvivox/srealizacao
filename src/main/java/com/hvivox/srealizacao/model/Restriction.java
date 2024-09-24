package com.hvivox.srealizacao.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.persistence.*;
import java.io.Serializable;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "RESTRICAO")
public class Restriction implements ISheetAssociation, Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDRESTRICAO", nullable = false)
    private Integer id;

    @Size(max = 250)
    @NotNull
    @Column(name = "DESCRICAO", nullable = false, length = 250)
    private String description;

    @NotNull
    @Column(name = "ISCONCLUIDO", nullable = false)
    private Boolean isConcluded = false;

    @Column(name = "ORDEM")
    private Integer order;

    @NotNull
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "IDFOLHA", nullable = false)
    private Sheet sheet;

}