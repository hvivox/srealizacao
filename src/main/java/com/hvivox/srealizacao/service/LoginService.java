package com.hvivox.srealizacao.service;

import com.hvivox.srealizacao.component.HttpComponent;
import com.hvivox.srealizacao.model.UserKeycloack;
import com.hvivox.srealizacao.util.HttpParamsMapKeyCloackBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;


@Service
@Slf4j
public class LoginService {

    @Value("${keycloak.auth-server-url}")
    private String keycloackServiceUrl;
    @Value("${keycloak.realm}")
    private String realm;
    @Value("${keycloak.resource}")
    private String clientId;
    @Value("${keycloak.credentials.secret}")
    private String clientSecret;
    @Value("${keycloak.user-login.grant-type}")
    private String grantType;

    @Autowired
    private HttpComponent httpComponent;

    public ResponseEntity<?> login(UserKeycloack userKeycloack) {
        log.info("Login: gt{}", "Acessando loginController");

        String tokenEndpoint = keycloackServiceUrl + "/protocol/openid-connect/token";
        httpComponent.httpHeaders().setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> params = HttpParamsMapKeyCloackBuilder.builder()
                .withClient(clientId)
                .withClientSecret(clientSecret)
                .withGrantType(grantType)
                .withUsername(userKeycloack.getUsername())
                .withPassword(userKeycloack.getPassword())
                .build();
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, httpComponent.httpHeaders());

        try {
            ResponseEntity<String> response = httpComponent.restTemplate()
                    .postForEntity(tokenEndpoint,
                            request,
                            String.class);
            return ResponseEntity.ok(response.getBody());
        } catch (Exception e) {
            log.error("Error: {}", e.getMessage());
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }

    }


    //criar o refesh token para o usuario logado utilizando os recursos do keycloack
    public ResponseEntity<String> refreshToken(String refreshToken) {
        log.info("RefreshToken: {}", "Acessando refreshToken");
        httpComponent.httpHeaders().setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = HttpParamsMapKeyCloackBuilder.builder()
                .withClient(clientId)
                .withClientSecret(clientSecret)
                .withGrantType("refresh_token")
                .withRefreshToken(refreshToken)
                .build();

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, httpComponent.httpHeaders());

        try {
            ResponseEntity<String> response = httpComponent.restTemplate()
                    .postForEntity(
                            keycloackServiceUrl + "/protocol/openid-connect/token",
                            request,
                            String.class);
            return ResponseEntity.ok(response.getBody());
        } catch (HttpClientErrorException e) {
            log.error("Error: {}", e.getMessage());
            return ResponseEntity.status(e.getRawStatusCode()).body(e.getResponseBodyAsString());

        }


    }
}
