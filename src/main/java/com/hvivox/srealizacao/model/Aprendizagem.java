package com.hvivox.srealizacao.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "APRENDIZAGEM")
public class Aprendizagem {
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDAPRENDIZAGEM", nullable = false)
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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDFOLHA", nullable = false)
    private Folha folha;
    

}