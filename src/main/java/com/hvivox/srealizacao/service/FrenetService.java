package com.hvivox.srealizacao.service;

import com.hvivox.srealizacao.dto.FrenetTrackingResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/*
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;
*/
@Slf4j
@Service
public class FrenetService {
    /*public FrenetTrackingResponse getTrackingInfo(String trackingNumber) {
        Client client = ClientBuilder.newClient();
        Entity payload = Entity.json("{  \"ShippingServiceCode\": \"03298\",  \"TrackingNumber\": \"TI137026905BR\",
         \"InvoiceNumber\": \"\",  \"InvoiceSerie\": \"\",  \"RecipientDocument\": \"\",  \"OrderNumber\": \"\"}");
        Response response = client.target("https://api.frenet.com.br/tracking/trackinginfo")
            .request(MediaType.APPLICATION_JSON_TYPE)
            .header("Accept", "application/json")
            .header("token", "8635E599R2E34R420BRB366R0622FAFD9BA7")
            .post(payload);

        System.out.println("status: " + response.getStatus());
        System.out.println("headers: " + response.getHeaders());
        System.out.println("body:"+response.readEntity(String.class));
        return null;
    }*/
    private final String FRENET_API_URL = "https://api.frenet.com.br/tracking/trackinginfo";
    private final String API_KEY = "8635E599R2E34R420BRB366R0622FAFD9BA7";
    
    public FrenetTrackingResponse getTrackingInfo(String trackingNumber) {
        RestTemplate restTemplate = new RestTemplate();
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Accept", MediaType.APPLICATION_JSON.toString());
        headers.set("token", API_KEY);
        
        try {
            log.info("Acessando API da Frenet, número de rastreamento: {}", trackingNumber);
            
            // Crie um objeto com os dados a serem enviados no corpo da solicitação
            Map<String, String> requestBody = new HashMap<>();
            requestBody.put("ShippingServiceCode", "03298");
            requestBody.put("TrackingNumber", "TI137026905BR");
            
            // Crie uma entidade HttpEntity com os cabeçalhos e o corpo da solicitação
            HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);
            ResponseEntity<FrenetTrackingResponse> responseEntity = restTemplate.postForEntity(FRENET_API_URL,
                    requestEntity, FrenetTrackingResponse.class);

           /* ResponseEntity<FrenetTrackingResponse> responseEntity = restTemplate.exchange(
                FRENET_API_URL,
                HttpMethod.POST,
                requestEntity, // Use o objeto de solicitação criado acima
                FrenetTrackingResponse.class
            );*/
            
            return responseEntity.getBody();
        } catch (Exception exception) {
            log.error("getTrackingInfo: Falha na conexão ou autorização de acesso: {}, causa: {}",
                    exception.getMessage(), exception.getStackTrace());
        }
        return null;
    }


    /*public FrenetTrackingResponse getTrackingInfo(String trackingNumber) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.set("token", API_KEY);

        try {
            log.info("Acessando API da Frenet, número de rastreamento: {}", trackingNumber);

            // Crie um objeto com os dados a serem enviados no corpo da solicitação
            Map<String, String> requestBody = new HashMap<>();
            requestBody.put("ShippingServiceCode", "032988888");
            requestBody.put("TrackingNumber", trackingNumber);

            // Crie uma entidade HttpEntity com os cabeçalhos e o corpo da solicitação
            HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

            ResponseEntity<FrenetTrackingResponse> responseEntity = restTemplate.exchange(
                FRENET_API_URL,
                HttpMethod.POST,
                requestEntity, // Use o objeto de solicitação criado acima
                FrenetTrackingResponse.class
            );

            return responseEntity.getBody();
        } catch (Exception exception) {
            log.error("getTrackingInfo: Falha na conexão ou autorização de acesso: {}, causa: {}", exception
            .getMessage(), exception.getStackTrace());
        }
        return null;
    }*/
}
