package com.sudipmondal.apigateway;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class Authorization extends AbstractGatewayFilterFactory<Authorization.Config> {
    private final Environment environment;
    @Autowired
    public Authorization(Environment environment) {
        super(Config.class);
        this.environment = environment;
    }
    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            if(!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                return onError(exchange, "Authorization header is missing", HttpStatus.UNAUTHORIZED);
            }
            String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION)+"";
            if(!authHeader.startsWith("Bearer ")) {
                return onError(exchange, "Authorization header is not valid", HttpStatus.UNAUTHORIZED);
            }
            String token = authHeader.replace("Bearer ","");
            if(!isJWTValid(token)) {
                return onError(exchange, "Authorization header is not valid", HttpStatus.UNAUTHORIZED);
            }
            return chain.filter(exchange);
        };
    }

    private Mono<Void> onError(ServerWebExchange exchange, String message, HttpStatus statusCode) {
        exchange.getResponse().setStatusCode(statusCode);
        return exchange.getResponse().setComplete();
    }

    private boolean isJWTValid(String jwt) {
        boolean isValidToken = false;
        try{
            JwtParser parser = Jwts.parser().setSigningKey(environment.getProperty("token.secret")+"");
            String subject = parser
                    .parseClaimsJws(jwt)
                    .getBody()
                    .getSubject();
            if(subject != null) {
                isValidToken = true;
            }
        } catch (Exception e) {
            return false;
        }
        return isValidToken;
    }

    public static class Config {
        private String token;

        public String getToken() {
            return token;
        }
    }

}
