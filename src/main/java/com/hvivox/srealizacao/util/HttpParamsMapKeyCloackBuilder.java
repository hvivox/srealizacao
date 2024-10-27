package com.hvivox.srealizacao.util;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class HttpParamsMapKeyCloackBuilder {

    private final MultiValueMap<String,String> params = new LinkedMultiValueMap<>();

    public static HttpParamsMapKeyCloackBuilder builder(){
        return new HttpParamsMapKeyCloackBuilder();
    }

    public HttpParamsMapKeyCloackBuilder withClient(String clientId){
        params.add("client_id",clientId);
        return this;
    }

    public HttpParamsMapKeyCloackBuilder withClientSecret(String clientSecret){
        params.add("client_secret", clientSecret);
        return this;
    }

    public HttpParamsMapKeyCloackBuilder withGrantType(String grantType){
        params.add("grant_type", grantType);
        return this;
    }

    public HttpParamsMapKeyCloackBuilder withUsername(String username){
        params.add("username",username);
        return this;
    }

    public HttpParamsMapKeyCloackBuilder withPassword(String password){
        params.add("password",password);
        return this;
    }

    public HttpParamsMapKeyCloackBuilder withRefreshToken(String refreshToken){
        params.add("refresh_token", refreshToken);
        return this;
    }

    public MultiValueMap<String, String> build(){return params;}


}
