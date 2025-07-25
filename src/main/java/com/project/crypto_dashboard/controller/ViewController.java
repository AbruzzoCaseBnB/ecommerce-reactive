package com.project.crypto_dashboard.controller;

import com.project.crypto_dashboard.entity.BitcoinPrice;
import com.project.crypto_dashboard.service.CryptoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Mono;

import java.util.Comparator;
import java.util.List;

@Controller
public class ViewController {

    private final CryptoService cryptoService;

    public ViewController(CryptoService cryptoService) {
        this.cryptoService = cryptoService;
    }

    @GetMapping("/")
    public Mono<String> dashboard(Model model) {
        return cryptoService.getBitcoinPrice()
                .zipWith(cryptoService.getBitcoinHistory().collectList())
                .map(tuple -> {
                    Double currentPrice = tuple.getT1();
                    List<BitcoinPrice> history = tuple.getT2();
                    history.sort(Comparator.comparingLong(BitcoinPrice::getTimestamp).reversed());

                    model.addAttribute("currentPrice", currentPrice);
                    model.addAttribute("history", history);
                    return "dashboard";
                });
    }
}
