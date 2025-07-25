package com.project.crypto_dashboard.repository;

import com.project.crypto_dashboard.entity.BitcoinPrice;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface BitcoinPriceRepository extends ReactiveMongoRepository<BitcoinPrice, String> {
}
