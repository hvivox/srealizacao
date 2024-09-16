package com.hvivox.srealizacao.integracao.prioridade;

import com.hvivox.srealizacao.model.Priority;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.hamcrest.Matchers.greaterThan;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class prioridadeControllerIT {

    @LocalServerPort
    private int port;

    @BeforeAll
    public void setUp(){
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = "/prioridades";
        //RestAssured.basic("thiago", "123");
    }

    @Test
    public void deveRetornarListaComStatus200_QuandoConsultarPrioridades(){
        Priority prioridade = new Priority();
        prioridade.setId(1);

        Response response =  RestAssured.given()
                .pathParam("idPrioridade", prioridade.getId() )
                .auth().basic("thiago", "123")
                .accept(ContentType.JSON)
            .when()
                .get("/folha/{idPrioridade}");

        response.then().statusCode(HttpStatus.OK.value());
        // Verifica se a lista de prioridades não está vazia (caso esperado)
        response.then().body("size()", greaterThan(0));

    }

}
