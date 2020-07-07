package com.midtrans.api.utility;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Arrays;

public class ApiCaller {
    private String credential;
    public ApiCaller(String credential){
        this.credential = credential;
    }

    public HttpEntity getEntity(){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setBasicAuth(credential);

        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        return httpEntity;
    }

}
