package com.project.crypto_dashboard.service;

import com.project.crypto_dashboard.entity.BitcoinPrice;
import com.project.crypto_dashboard.repository.BitcoinPriceRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
public class CryptoService {

    private final WebClient webClient;
    private final BitcoinPriceRepository priceRepository;

    public CryptoService(WebClient.Builder webClientBuilder, BitcoinPriceRepository priceRepository) {
        this.webClient = webClientBuilder.baseUrl("https://api.coingecko.com/api/v3").build();
        this.priceRepository = priceRepository;
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
                })
                .flatMap(price -> {
                    BitcoinPrice btc = new BitcoinPrice(price, System.currentTimeMillis());
                    return priceRepository.save(btc).thenReturn(price);
                });
    }

    public Flux<BitcoinPrice> getBitcoinHistory() {
        return priceRepository.findAll();
    }
}
