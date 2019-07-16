package br.com.spartaseller.persistence.dao;

import br.com.spartaseller.persistence.model.Entrada;
import br.com.spartaseller.persistence.model.PageableResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class EntradaDAO {
    private final String BASE_URL = "http://192.168.2.114:8080/v1/administrador/entrada";
    private final RestTemplate restTemplate;

    public EntradaDAO(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Entrada findById(long id, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        HttpEntity<String> authorization = new HttpEntity<>(headers);
        ResponseEntity<Entrada> exchange =
                restTemplate.exchange(BASE_URL + "/" + id, HttpMethod.GET, authorization, new ParameterizedTypeReference<Entrada>() {
                });
        return exchange.getBody();
    }

    public List<Entrada> listAll(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", token);
        HttpEntity<String> authorization = new HttpEntity<>(headers);
        ResponseEntity<PageableResponse<Entrada>> exchange =
                restTemplate.exchange(BASE_URL, HttpMethod.GET, authorization, new ParameterizedTypeReference<PageableResponse<Entrada>>() {
                });
        return exchange.getBody().getContent();
    }

    public Entrada save(Entrada entrada, String token) {
        ResponseEntity<Entrada> exchangePost =
                restTemplate.exchange(BASE_URL, HttpMethod.POST, new HttpEntity<>(entrada, createJSONHeader(token)), Entrada.class);
        return exchangePost.getBody();
    }

    public void update(Entrada entrada, String token) {
        ResponseEntity<Entrada> exchangePost =
                restTemplate.exchange(BASE_URL, HttpMethod.PUT, new HttpEntity<>(entrada, createJSONHeader(token)), Entrada.class);
    }

    public void delete(Long id, String token) {
        ResponseEntity<Entrada> exchangePost =
                restTemplate.exchange(BASE_URL + "/" + id, HttpMethod.DELETE, new HttpEntity<>(createJSONHeader(token)), new ParameterizedTypeReference<Entrada>() {
                });
    }

    private static HttpHeaders createJSONHeader(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}
