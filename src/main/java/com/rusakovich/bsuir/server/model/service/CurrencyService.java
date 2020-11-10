package com.rusakovich.bsuir.server.model.service;

import com.rusakovich.bsuir.server.entity.Currency;

import java.util.List;

public interface CurrencyService {
    void addCurrency(Currency currency);

    Currency getCurrencyById(Long id);

    List<Currency> getAllCurrencies();

    void updateCurrency(Currency currency);

    void deleteCurrency(Long id);
}
