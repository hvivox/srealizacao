package com.hvivox.srealizacao.unitario.prioridade;

import com.hvivox.srealizacao.model.Prioridade;
import com.hvivox.srealizacao.repository.PrioridadeRepository;
import com.hvivox.srealizacao.service.PrioridadeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class prioridadeControllerTest {
    @Mock
    private PrioridadeRepository prioridadeRepository;
    @InjectMocks
    private PrioridadeService prioridadeService;
    
   @Test
    public void buscarPrioridadeTeste() {
       Mockito.when(prioridadeRepository.findAllPrioridadeIntoFolha(1009))
                    .thenReturn(
                         Arrays.asList(new Prioridade(1, "teste", false, 1, null ),
                                 new Prioridade(2, "teste02", true, 2, null ))
                    );
       
       List<Prioridade> listaPrioridade = prioridadeService.findAllByFolha(1009);
        
        for ( Prioridade prioridade: listaPrioridade) {
            System.out.println( prioridade );
        };
        assertThat( listaPrioridade ).isNotNull();
    }
}
