package com.rusakovich.bsuir.server.controller.impl;

import com.rusakovich.bsuir.server.controller.Controller;
import com.rusakovich.bsuir.server.controller.ControllerHelper;
import com.rusakovich.bsuir.server.entity.Account;
import com.rusakovich.bsuir.server.model.service.impl.AccountServiceImpl;

import java.util.ArrayList;
import java.util.Map;
import java.util.List;

public class AccountController implements Controller {
    private final AccountServiceImpl accountService;

    public AccountController(AccountServiceImpl accountService) {
        this.accountService = accountService;
    }

    @Override
    public String request(Map<String, String> params) {
        String command = params.get("command");
        switch (command) {
            case "login": {
                return login(params);
            }
            case "register": {
                return register(params);
            }
            case "getById": {
                return getById(params);
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

    public String register(Map<String, String> params) {
        Account account = accountService.addAccount(Account.fromMap(params));
        return ControllerHelper.getResponse("ok", account.toString(), "");
    }

    public String login(Map<String, String> params) {
        try {
            Account account = accountService.login(params.get("login"), params.get("password"));
            return ControllerHelper.getResponse("ok", account.toString(), "");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return ControllerHelper.getResponse("", "", e.getMessage());
        }
    }

    public String getById(Map<String, String> params) {
        Account account = accountService.getAccountById(Long.valueOf(params.get("id")));
        return ControllerHelper.getResponse("ok", account.toString(), "");
    }

    public String getAll() {
        List<Account> accounts = accountService.getAllAccounts();
        List<String> accountsStr = new ArrayList<String>();
        for (Account account : accounts) {
            accountsStr.add(account.toString());
        }
        String data = String.join(";", accountsStr);
        return ControllerHelper.getResponse("ok", data, "");
    }

    public String update(Map<String, String> params) {
        accountService.updateAccount(Account.fromMap(params));
        return ControllerHelper.getResponse("ok", "", "");
    }

    public String delete(Map<String, String> params) {
        accountService.deleteAccount(Long.valueOf(params.get("id")));
        return ControllerHelper.getResponse("ok", "", "");
    }
}
