package com.project.crypto_dashboard.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CoinGeckoPriceResponse {

    @JsonProperty("usd")
    private double usd;

}
