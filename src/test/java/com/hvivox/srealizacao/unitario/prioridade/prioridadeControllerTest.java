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
    private PriorityRepository priorityRepository;

    @InjectMocks
    private PriorityService priorityService;

   @Test
    public void buscarPrioridadeTeste() {
       Mockito.when(priorityRepository.findAllPriorityIntoSheet(1009))
                    .thenReturn(
                         Arrays.asList(new Priority(1, "teste", false, 1, null ),
                                 new Priority(2, "teste02", true, 2, null ))
                    );

       List<Priority> priorityList = priorityService.findAllBySheet(1009);

        for ( Priority priority : priorityList) {
            System.out.println( priority );
        }
        assertThat( priorityList ).isNotNull();
    }

    @Test
    public void verify_if_priority_was_updated() {
       // TEste não funciona como esperado necessario verificar porque não está dando teste02]
       // entender o funcionamento do metodo
       // Buscar informações direto do banco sem mocar dados
        //Pesquisar se e interssante usar o mockito ou não para representar da dodos do banco
       Priority priority = new Priority(1, "teste", false, 1, null);

       Mockito.when(priorityRepository.findById(1)).thenReturn(java.util.Optional.of(priority));

       priorityService.updatePriority(new Priority(1, "teste02", true, 1, null));

       Priority priorityUpdated = priorityRepository.findById(1).get();

       assertThat(priorityUpdated.getDescription()).isEqualTo("teste02");
    }


}
