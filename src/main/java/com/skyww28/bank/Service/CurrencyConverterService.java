package com.skyww28.bank.Service;

import com.skyww28.bank.Exception.InvalidTransferException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

@Service
public class CurrencyConverterService {

    private final Map<String, BigDecimal> exchangeRates;

    public CurrencyConverterService() {
        exchangeRates = new HashMap<>();
        exchangeRates.put("RUB-USD", BigDecimal.valueOf(0.0107));
        exchangeRates.put("RUB-EUR", BigDecimal.valueOf(0.0102));
        exchangeRates.put("USD-RUB", BigDecimal.valueOf(93.45));
        exchangeRates.put("EUR-RUB", BigDecimal.valueOf(98.20));
        exchangeRates.put("USD-EUR", BigDecimal.valueOf(0.951));
        exchangeRates.put("EUR-USD", BigDecimal.valueOf(1.051));
    }

    public BigDecimal convert(BigDecimal amount, String fromCurrency, String toCurrency) {
        if (fromCurrency.equals(toCurrency)) {
            return amount;
        }

        String rateKey = fromCurrency + "-" + toCurrency;
        BigDecimal rate = exchangeRates.get(rateKey);

        if (rate == null) {
            throw new InvalidTransferException("Conversion not supported from " + fromCurrency + " to " + toCurrency);
        }

        return amount.multiply(rate).setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getExchangeRate(String fromCurrency, String toCurrency) {
        if (fromCurrency.equals(toCurrency)) {
            return BigDecimal.ONE;
        }

        String rateKey = fromCurrency + "-" + toCurrency;
        BigDecimal rate = exchangeRates.get(rateKey);

        if (rate == null) {
            throw new InvalidTransferException("Exchange rate not available for " + rateKey);
        }

        return rate;
    }
}
