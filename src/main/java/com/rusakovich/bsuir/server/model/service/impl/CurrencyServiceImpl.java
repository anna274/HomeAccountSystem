package com.rusakovich.bsuir.server.model.service.impl;

import com.rusakovich.bsuir.server.entity.Currency;
import com.rusakovich.bsuir.server.model.dao.impl.CurrencyDaoImpl;
import com.rusakovich.bsuir.server.model.service.CurrencyService;

import java.sql.SQLException;
import java.util.List;

public class CurrencyServiceImpl implements CurrencyService {
    private final CurrencyDaoImpl currencyDao;

    public CurrencyServiceImpl(CurrencyDaoImpl currencyDao) {
        this.currencyDao = currencyDao;
    }

    @Override
    public void addCurrency(Currency currency) {
        try {
            currencyDao.save(currency);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Currency getCurrencyById(Long id) {
        return currencyDao.findById(id);
    }

    @Override
    public List<Currency> getAllCurrencies() {
        return currencyDao.findAll();
    }

    @Override
    public void updateCurrency(Currency currency) {
        try {
            currencyDao.update(currency);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteCurrency(Long id) {
        try {
            currencyDao.delete(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
