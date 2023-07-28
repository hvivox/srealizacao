package com.hvivox.srealizacao.folha;

import com.hvivox.srealizacao.model.Prioridade;
import com.hvivox.srealizacao.service.PrioridadeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat; // Importação do AssertJ

@ExtendWith(SpringExtension.class)
@SpringBootTest()
public class prioridadeControllerTest {
    
    @Autowired
    private PrioridadeService prioridadeService;
    

    
   @Test
    public void buscarPrioridadeTeste() {
        List<Prioridade> listaPrioridade = prioridadeService.findAllByFolha(1);
        
        for ( Prioridade prioridade: listaPrioridade) {
            System.out.println( prioridade.toString() );
        };
        assertThat( listaPrioridade ).isNotNull();
    }
}
