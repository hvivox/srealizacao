package com.hvivox.srealizacao.prioridade;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class prioridadeControllerIT {
    /*
    @Autowired
    private PrioridadeService prioridadeService;
    */
    @LocalServerPort
    private int port;
    
    @Test
    public void deveRetornarStatus200_QuandoConsultarCozinhas(){
        RestAssured.given()
                .auth().basic("thiago", "123")
                .basePath("/folhas")
                .port(port)
                .accept(ContentType.JSON)
            .when()
                .get()
            .then()
                .statusCode(HttpStatus.OK.value());
    }
    
   /* @Test
    public void buscarPrioridadeTeste() {
        List<Prioridade> listaPrioridade = prioridadeService.findAllByFolha(1);
        
        for ( Prioridade prioridade: listaPrioridade) {
            System.out.println( prioridade.toString() );
        };
        assertThat( listaPrioridade ).isNotNull();
    }*/
}
