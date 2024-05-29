package com.example.cryptoprice.controller;

import com.example.cryptoprice.service.CryptoPriceService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CryptoPriceController {

    private final CryptoPriceService cryptoPriceService;

    public CryptoPriceController(CryptoPriceService cryptoPriceService) {
        this.cryptoPriceService = cryptoPriceService;
    }

    @GetMapping("/price")
    public Double getPrice(@RequestParam String pair) {
        return cryptoPriceService.getPrice(pair);
    }
}
