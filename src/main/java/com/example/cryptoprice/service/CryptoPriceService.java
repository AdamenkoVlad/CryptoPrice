package com.example.cryptoprice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
@Slf4j
public class CryptoPriceService {

    private final RestTemplate restTemplate;
    private final ConcurrentMap<String, Double> prices = new ConcurrentHashMap<>();

    public CryptoPriceService() {
        this.restTemplate = new RestTemplate();
    }

    @EventListener(ContextRefreshedEvent.class)
    public void onApplicationEvent(ContextRefreshedEvent event) {
        updatePrices();
    }

    @Scheduled(fixedRate = 60000)
    public void updatePrices() {
        updatePriceForPair("BTCUSDT");
        updatePriceForPair("ETHUSDT");
    }

    private void updatePriceForPair(String pair) {
        try {
            String url = String.format("https://fapi.binance.com/fapi/v1/premiumIndex?symbol=%s", pair);
            PriceResponse response = restTemplate.getForObject(url, PriceResponse.class);
            if (response != null) {
                prices.put(pair, response.getMarkPrice());
                log.info("Updated price for {}: {}", pair, response.getMarkPrice());
            }
        } catch (Exception e) {
            log.error("Failed to update price for " + pair, e);
        }
    }

    public Double getPrice(String pair) {
        return prices.get(pair);
    }

    private static class PriceResponse {
        private double markPrice;

        public double getMarkPrice() {
            return markPrice;
        }

        public void setMarkPrice(double markPrice) {
            this.markPrice = markPrice;
        }
    }
}
