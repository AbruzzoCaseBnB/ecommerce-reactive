package com.project.crypto_dashboard.controller;

import com.project.crypto_dashboard.service.CryptoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

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
}
