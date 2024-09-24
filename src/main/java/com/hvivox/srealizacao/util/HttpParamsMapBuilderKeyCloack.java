package com.hvivox.srealizacao.util;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class HttpParamsMapBuilderKeyCloack {

    private final MultiValueMap<String,String> params = new LinkedMultiValueMap<>();

    public static HttpParamsMapBuilderKeyCloack builder(){
        return new HttpParamsMapBuilderKeyCloack();
    }

    public HttpParamsMapBuilderKeyCloack withClient(String clientId){
        params.add("client_id",clientId);
        return this;
    }

    public HttpParamsMapBuilderKeyCloack  withClientSecret(String clientSecret){
        params.add("client_secret", clientSecret);
        return this;
    }

    public HttpParamsMapBuilderKeyCloack withGrantType(String grantType){
        params.add("grant_type", grantType);
        return this;
    }

    public HttpParamsMapBuilderKeyCloack withUsername(String username){
        params.add("username",username);
        return this;
    }

    public HttpParamsMapBuilderKeyCloack withPassword(String password){
        params.add("password",password);
        return this;
    }

    public HttpParamsMapBuilderKeyCloack withRefreshToken(String refreshToken){
        params.add("refresh_token", refreshToken);
        return this;
    }

    public MultiValueMap<String, String> build(){return params;}


}
