package com.eisgroup.PracticalTest.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@Service
public class RequestService implements IRequestService{

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public <T> ResponseEntity<T> get(String uri, Class<T> clazz, HttpHeaders headers) {
        ResponseEntity<T> response = null;
        try {
            RequestEntity<T> req = new RequestEntity<T>(headers, HttpMethod.GET, new URI(uri));
            response = this.restTemplate.exchange(req, clazz);

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return response;
    }
}
