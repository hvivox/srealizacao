package com.hvivox.srealizacao.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "FOLHA")
public class Folha implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDFOLHA", nullable = false)
    private Integer id;

    @Size(max = 100)
    @NotNull
    @Column(name = "FOCO", nullable = false, length = 100)
    private String foco;
    
    
    @Min(0)
    @Max(10)
    @Column(name = "NOTADIA")
    private Integer notadia;

    @NotNull
    @UpdateTimestamp
    @Column(name = "DTAREALIZACAO", nullable = false)
    private LocalDate dtarealizacao;

    @Size(max = 500)
    @Column(name = "OBSERVACAO", length = 500)
    private String observacao;

    @NotNull
    @Column(name = "STATUS", nullable = false)
    private Boolean status = false;
    
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "folha", fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE},
            orphanRemoval = true )
    private List<Prioridade> prioridadeList = new ArrayList<>();
    
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "folha", fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<Restricao> restricaoList = new ArrayList<>();
    
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "folha", fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<Gratidao> gratidaoList = new ArrayList<>();
    
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "folha", fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<Aprendizagem> aprendizagemList = new ArrayList<>();
    
}