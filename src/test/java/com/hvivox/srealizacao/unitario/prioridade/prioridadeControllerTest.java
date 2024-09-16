package com.hvivox.srealizacao.unitario.prioridade;

import com.hvivox.srealizacao.model.Priority;
import com.hvivox.srealizacao.repository.PriorityRepository;
import com.hvivox.srealizacao.service.PriorityService;
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
    private PriorityRepository prioridadeRepository;
    @InjectMocks
    private PriorityService prioridadeService;

   @Test
    public void buscarPrioridadeTeste() {
       Mockito.when(prioridadeRepository.findAllPriorityIntoSheet(1009))
                    .thenReturn(
                         Arrays.asList(new Priority(1, "teste", false, 1, null ),
                                 new Priority(2, "teste02", true, 2, null ))
                    );

       List<Priority> listaPrioridade = prioridadeService.findAllBySheet(1009);

        for ( Priority prioridade: listaPrioridade) {
            System.out.println( prioridade );
        };
        assertThat( listaPrioridade ).isNotNull();
    }
}
