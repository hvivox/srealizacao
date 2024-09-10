package com.hvivox.srealizacao.dto;

import com.hvivox.srealizacao.model.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * A DTO for the {@link Sheet} entity
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SheetDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @Size(max = 100)
    @NotNull
    private String focus;

    @NotNull
    @Min(0)
    @Max(10)
    /*@Pattern(regexp = "^\\d+$", message = "O campo notadia deve ser um número inteiro")*/ private Integer dayNote;

    //@JsonFormat(pattern="dd/MM/yyyy")
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss'Z'")
    @NotNull
    private LocalDateTime realizationDate;


    @Size(max = 500)
    private String observation;

    @NotNull
    private Boolean status;


    private List<Priority> priorityList;
    private List<Restriction> restrictionList;
    private List<Gratitude> gratitudeList;
    private List<Learning> learningList;
}
