package br.com.spartaseller.persistence.dao;

import br.com.spartaseller.persistence.model.MovimentacaoEntrada;
import br.com.spartaseller.persistence.model.PageableResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class MovimentacaoEntradaDAO {
    private final String BASE_URL = "http://192.168.2.114:8080/v1/administrador/entrada/movimentacaoEntrada";
    private final RestTemplate restTemplate;

    public MovimentacaoEntradaDAO(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public MovimentacaoEntrada findById(long id, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        HttpEntity<String> authorization = new HttpEntity<>(headers);
        ResponseEntity<MovimentacaoEntrada> exchange =
                restTemplate.exchange(BASE_URL + "/" + id, HttpMethod.GET, authorization, new ParameterizedTypeReference<MovimentacaoEntrada>() {
                });
        return exchange.getBody();
    }

    public List<MovimentacaoEntrada> findByEntrada(Long idEntrada, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", token);
        HttpEntity<String> authorization = new HttpEntity<>(headers);
        int id = Math.toIntExact(idEntrada);
        ResponseEntity<List<MovimentacaoEntrada>> exchange =
                restTemplate.exchange(BASE_URL + "/findByEntrada/" + idEntrada,
                        HttpMethod.GET, authorization, new ParameterizedTypeReference<List<MovimentacaoEntrada>>() {
                });
        return exchange.getBody();
    }

    public List<MovimentacaoEntrada> listAll(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", token);
        HttpEntity<String> authorization = new HttpEntity<>(headers);
        ResponseEntity<PageableResponse<MovimentacaoEntrada>> exchange =
                restTemplate.exchange(BASE_URL, HttpMethod.GET, authorization, new ParameterizedTypeReference<PageableResponse<MovimentacaoEntrada>>() {
                });
        return exchange.getBody().getContent();
    }

    public MovimentacaoEntrada save(MovimentacaoEntrada movimentacaoEntrada, String token) {
        ResponseEntity<MovimentacaoEntrada> exchangePost =
                restTemplate.exchange(BASE_URL, HttpMethod.POST, new HttpEntity<>(movimentacaoEntrada, createJSONHeader(token)), MovimentacaoEntrada.class);
        return exchangePost.getBody();
    }

    public List<MovimentacaoEntrada> saveAll(List<MovimentacaoEntrada> movimentacaoEntradaList, String token) {
        ResponseEntity<List<MovimentacaoEntrada>> exchange =
                restTemplate.exchange(BASE_URL + "/saveAll", HttpMethod.POST, new HttpEntity<>(movimentacaoEntradaList, createJSONHeader(token)), new ParameterizedTypeReference<List<MovimentacaoEntrada>>() {
                });
        return exchange.getBody();
    }

    public void update(MovimentacaoEntrada movimentacaoEntrada, String token) {
        ResponseEntity<MovimentacaoEntrada> exchangePost =
                restTemplate.exchange(BASE_URL, HttpMethod.PUT, new HttpEntity<>(movimentacaoEntrada, createJSONHeader(token)), MovimentacaoEntrada.class);
    }

    public void delete(Long id, String token) {
        ResponseEntity<MovimentacaoEntrada> exchangePost =
                restTemplate.exchange(BASE_URL + "/" + id, HttpMethod.DELETE, new HttpEntity<>(createJSONHeader(token)), new ParameterizedTypeReference<MovimentacaoEntrada>() {
                });
    }

    public List<MovimentacaoEntrada> deleteAll(List<MovimentacaoEntrada> movimentacaoEntradaList, String token) {
        ResponseEntity<List<MovimentacaoEntrada>> exchange =
                restTemplate.exchange(BASE_URL + "/deleteAll", HttpMethod.DELETE, new HttpEntity<>(movimentacaoEntradaList, createJSONHeader(token)), new ParameterizedTypeReference<List<MovimentacaoEntrada>>() {
                });
        return exchange.getBody();
    }

    private static HttpHeaders createJSONHeader(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}
