package com.hvivox.srealizacao.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "FOLHA")
public class Sheet implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDFOLHA", nullable = false)
    private Integer id;


    @Column(name = "FOCO", nullable = false, length = 100)
    private String focus;



    @Column(name = "NOTADIA")
    private Integer dayNote;


   //@UpdateTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss'Z'")
    @Column(name = "DTAREALIZACAO", nullable = false)
    private LocalDateTime realizationDate;


    @Column(name = "OBSERVACAO", length = 500)
    private String observation;


    @Column(name = "STATUS", nullable = false)
    private Boolean status = false;

    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    @OneToMany(mappedBy = "sheet", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST,
            CascadeType.MERGE}, orphanRemoval = true)
    private List<Priority> priorityList = new ArrayList<>();

    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    @OneToMany(mappedBy = "sheet", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            orphanRemoval = true)
    private List<Restriction> restrictionList = new ArrayList<>();

    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    @OneToMany(mappedBy = "sheet", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            orphanRemoval = true)
    private List<Gratitude> gratitudeList = new ArrayList<>();

    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    @OneToMany(mappedBy = "sheet", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            orphanRemoval = true)
    private List<Learning> learningList = new ArrayList<>();

}