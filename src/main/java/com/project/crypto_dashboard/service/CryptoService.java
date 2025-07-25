package com.project.crypto_dashboard.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
public class CryptoService {

    private final WebClient webClient;

    public CryptoService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://api.coingecko.com/api/v3").build();
    }

    public Mono<Double> getBitcoinPrice() {
        return webClient
                .get()
                .uri("/simple/price?ids=bitcoin&vs_currencies=usd")
                .retrieve()
                .bodyToMono(Map.class)
                .map(response -> {
                    Map<String, Object> bitcoin = (Map<String, Object>) response.get("bitcoin");
                    Number usdValue = (Number) bitcoin.get("usd");
                    return usdValue.doubleValue();
                });
    }
}
