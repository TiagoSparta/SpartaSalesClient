package br.com.spartaseller.persistence.dao;

import br.com.spartaseller.persistence.model.PageableResponse;
import br.com.spartaseller.persistence.model.Produto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class ProdutoDAO {
    private final String BASE_URL = "http://192.168.2.114:8080/v1/administrador/produto";
    private final RestTemplate restTemplate;

    public ProdutoDAO(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Produto findById(long id, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        HttpEntity<String> authorization = new HttpEntity<>(headers);
        ResponseEntity<Produto> exchange =
                restTemplate.exchange(BASE_URL + "/" + id, HttpMethod.GET, authorization, new ParameterizedTypeReference<Produto>() {
                });
        return exchange.getBody();
    }

    public Produto findByNome(String nome, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        HttpEntity<String> authorization = new HttpEntity<>(headers);
        ResponseEntity<Produto> exchange =
                restTemplate.exchange(BASE_URL + "/findByNome/" + nome, HttpMethod.GET, authorization, new ParameterizedTypeReference<Produto>() {
                });
        return exchange.getBody();
    }

    public List<Produto> listAll(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", token);
        HttpEntity<String> authorization = new HttpEntity<>(headers);
        ResponseEntity<PageableResponse<Produto>> exchange =
                restTemplate.exchange(BASE_URL, HttpMethod.GET, authorization, new ParameterizedTypeReference<PageableResponse<Produto>>() {
                });
        return exchange.getBody().getContent();
    }

    public Produto save(Produto produto, String token) {
        ResponseEntity<Produto> exchangePost =
                restTemplate.exchange(BASE_URL, HttpMethod.POST, new HttpEntity<>(produto, createJSONHeader(token)), Produto.class);
        return exchangePost.getBody();
    }

    public void update(Produto produto, String token) {
        ResponseEntity<Produto> exchangePost =
                restTemplate.exchange(BASE_URL, HttpMethod.PUT, new HttpEntity<>(produto, createJSONHeader(token)), Produto.class);
    }

    public void delete(Long id, String token) {
        ResponseEntity<Produto> exchangePost =
                restTemplate.exchange(BASE_URL + "/" + id, HttpMethod.DELETE, new HttpEntity<>(createJSONHeader(token)), new ParameterizedTypeReference<Produto>() {
                });
    }

    private static HttpHeaders createJSONHeader(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}
