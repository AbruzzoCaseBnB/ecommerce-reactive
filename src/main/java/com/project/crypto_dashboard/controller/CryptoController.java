package com.project.crypto_dashboard.controller;

import com.project.crypto_dashboard.entity.BitcoinPrice;
import com.project.crypto_dashboard.service.CryptoService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Map;

@RestController
public class CryptoController {

    private final CryptoService cryptoService;

    public CryptoController(CryptoService cryptoService) {
        this.cryptoService = cryptoService;
    }

    @GetMapping("/prices/bitcoin")
    public Mono<Map<String, Object>> getBitcoinPrice() {
        return cryptoService.getBitcoinPrice()
                .map(price -> Map.of("currency", "USD", "price", price));
    }

    @GetMapping(value = "/stream/bitcoin", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Map<String, Object>> streamBitcoinPrice() {
        return Flux.interval(Duration.ofSeconds(5))
                .flatMap(tick -> cryptoService.getBitcoinPrice())
                .map(price -> Map.of(
                        "currency", "USD",
                        "price", price,
                        "timestamp", System.currentTimeMillis()
                ));
    }

    @GetMapping("/history/bitcoin")
    public Flux<BitcoinPrice> getBitcoinPriceHistory() {
        return cryptoService.getBitcoinHistory();
    }
}
