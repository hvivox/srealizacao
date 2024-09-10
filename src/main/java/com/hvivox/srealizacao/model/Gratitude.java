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
@Table(name = "GRATIDAO")
public class Gratitude {
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDGRATIDAO", nullable = false)
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
    @ManyToOne(fetch = FetchType.LAZY, optional = false)//optional=false cria inner join e não left join
    @JoinColumn(name = "IDFOLHA", nullable = false)
    private Sheet sheet;



}