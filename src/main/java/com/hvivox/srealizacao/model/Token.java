package com.hvivox.srealizacao.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "TOKENS")
public class Token {
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDTOKEN", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "IDUSUARIO", nullable = false)
    private User userId;

    @Size(max = 1000)
    @NotNull
    @Column(name = "TOKEN", nullable = false, length = 1000)
    private String token;

    @Size(max = 1000)
    @NotNull
    @Column(name = "REFRESH_TOKEN", nullable = false, length = 1000)
    private String refreshToken;

    @NotNull
    @UpdateTimestamp
    @Column(name = "EXPIRED_AT", nullable = false)
    private LocalDateTime expiredAt;

    @Column(name = "ACTIVE", columnDefinition = "tinyint not null")
    private Short active;


}