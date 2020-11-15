package com.rusakovich.bsuir.server.controller.impl;

import com.rusakovich.bsuir.server.controller.Controller;
import com.rusakovich.bsuir.server.controller.ControllerHelper;
import com.rusakovich.bsuir.server.entity.AccountMember;
import com.rusakovich.bsuir.server.entity.Currency;
import com.rusakovich.bsuir.server.model.service.impl.CurrencyServiceImpl;


import java.util.ArrayList;
import java.util.Map;
import java.util.List;

public class CurrencyController implements Controller {
    private final CurrencyServiceImpl currencyService;

    public CurrencyController(CurrencyServiceImpl currencyService) {
        this.currencyService = currencyService;
    }

    @Override
    public String request(Map<String, String> params) {
        String command = params.get("command");
        switch (command) {
            case "add": {
                return add(params);
            }
            case "getOne": {
                return getOne(params);
            }
            case "getAll": {
                return getAll();
            }
            case "update": {
                return update(params);
            }
            case "delete": {
                return delete(params);
            }
            default:
                return "";
        }
    }

    public String add(Map<String, String> params) {
        Currency currency = Currency.fromMap(params);
        currencyService.addCurrency(currency);
        return ControllerHelper.getResponse("ok", currency.toString(), "");
    }

    public String getOne(Map<String, String> params) {
        Currency currency = currencyService.getCurrencyById(Long.valueOf(params.get("id")));
        return ControllerHelper.getResponse("ok", currency.toString(), "");
    }

    public String getAll() {
        List<Currency> currencies = currencyService.getAllCurrencies();
        List<String> currenciesStr = new ArrayList<String>();
        for (Currency currency : currencies) {
            currenciesStr.add(currency.toString());
        }
        String data = String.join(";", currenciesStr);
        return ControllerHelper.getResponse("ok", data, "");
    }

    public String update(Map<String, String> params) {
        currencyService.updateCurrency(Currency.fromMap(params));
        return ControllerHelper.getResponse("ok", "", "");
    }

    public String delete(Map<String, String> params) {
        currencyService.deleteCurrency(Long.valueOf(params.get("id")));
        return ControllerHelper.getResponse("ok", "", "");
    }
}
