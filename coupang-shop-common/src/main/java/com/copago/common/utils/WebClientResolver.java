package com.copago.common.utils;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.function.Function;

public class WebClientResolver {
    public static <T> T resolve(WebClient.ResponseSpec response, Class<T> className) {
        return handleResponse(handleStatus(response, className)).block();
    }

    public static <T> T resolve(WebClient.ResponseSpec response, ParameterizedTypeReference<T> parameterizedTypeReference) {
        return handleResponse(handleStatus(response, parameterizedTypeReference)).block();
    }

    private static <T> Mono<T> handleStatus(WebClient.ResponseSpec response, Class<T> className) {
        return response
                .onStatus(HttpStatus::isError, clientResponse -> clientResponse.createException().flatMap(Mono::error))
                .bodyToMono(className);
    }

    private static <T> Mono<T> handleStatus(WebClient.ResponseSpec response, ParameterizedTypeReference<T> parameterizedTypeReference) {
        return response
                .onStatus(HttpStatus::isError, clientResponse -> clientResponse.createException().flatMap(Mono::error))
                .bodyToMono(parameterizedTypeReference);
    }

    private static <T> Mono<T> handleResponse(Mono<T> response) {
        return response.onErrorResume(error -> {
            if (error instanceof WebClientResponseException) {
                return Mono.error(new RuntimeException(error.getMessage()));
            } else {
                return Mono.error(error);
            }
        });
    }
}
