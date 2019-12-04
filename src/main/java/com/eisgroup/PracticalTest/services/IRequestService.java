package com.eisgroup.PracticalTest.services;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

public interface IRequestService {

    public <T> ResponseEntity<T> get(final String uri, final Class<T> clazz, HttpHeaders headers);

}
