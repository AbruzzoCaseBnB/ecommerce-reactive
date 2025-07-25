package com.project.crypto_dashboard.dto;

import lombok.*;

import java.util.Map;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CoinGeckoResponseWrapper {
    private Map<String, CoinGeckoPriceResponse> bitcoin;
}
