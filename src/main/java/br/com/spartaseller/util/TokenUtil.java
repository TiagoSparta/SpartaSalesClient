package br.com.spartaseller.util;

import br.com.spartaseller.persistence.model.Token;

import java.time.LocalDateTime;

public class TokenUtil {

    public boolean isTokenValid(Token token) {
        return !token.getToken().isEmpty();
    }

    public boolean isExpirationTimeValid(Token token) {
        return LocalDateTime.now().isBefore(token.getExpirationTime());
    }
}
