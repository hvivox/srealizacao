package com.hvivox.srealizacao.dto;

import com.hvivox.srealizacao.model.Aprendizagem;
import com.hvivox.srealizacao.model.Gratidao;
import com.hvivox.srealizacao.model.Prioridade;
import com.hvivox.srealizacao.model.Restricao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * A DTO for the {@link com.hvivox.srealizacao.model.Folha} entity
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FolhaDto implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Size(max = 100)
    @NotNull
    private String foco;
    
    @NotNull
    @Min(0)
    @Max(10)
    /*@Pattern(regexp = "^\\d+$", message = "O campo notadia deve ser um número inteiro")*/
    private  Integer notadia;
   
    //@JsonFormat(pattern="dd/MM/yyyy")
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss'Z'")
    @NotNull
    private LocalDateTime dtarealizacao;
    @Size(max = 500)
    private  String observacao;
    @NotNull
    private  Boolean status;
    
   
    private  List<Prioridade> prioridadeList;
    private  List<Restricao> restricaoList;
    private  List<Gratidao> gratidaoList;
    private  List<Aprendizagem> aprendizagemList;
}