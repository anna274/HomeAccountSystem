package com.rusakovich.bsuir.server.controller.impl;

import com.rusakovich.bsuir.server.controller.Controller;
import com.rusakovich.bsuir.server.controller.ControllerHelper;
import com.rusakovich.bsuir.server.entity.Account;
import com.rusakovich.bsuir.server.entity.AccountRole;
import com.rusakovich.bsuir.server.model.service.impl.AccountServiceImpl;

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

    public String register(Map<String, String> params) {
        Account account = accountService.addAccount(
                createAccountObjFromParams(params)
        );
        System.out.println(account);
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

    public String getOne(Map<String, String> params) {
        Account account = accountService.getAccountById(Long.valueOf(params.get("id")));
        return ControllerHelper.getResponse("ok", account.toString(), "");
    }

    public String getAll() {
        StringBuilder response = new StringBuilder();
        List<Account> accounts = accountService.getAllAccounts();
        for (Account account : accounts) {
            response.append(account.toString());
        }
        return ControllerHelper.getResponse("ok", "[" + response + "]", "");
    }

    public String update(Map<String, String> params) {
        accountService.updateAccount(
                createAccountObjFromParams(params)
        );
        return ControllerHelper.getResponse("ok", "", "");
    }

    public String delete(Map<String, String> params) {
        accountService.deleteAccount(Long.valueOf(params.get("id")));
        return ControllerHelper.getResponse("ok", "", "");
    }

    private Account createAccountObjFromParams(Map<String, String> params) {
        Account account = new Account();
        account.setLogin(params.get("login"));
        account.setPassword(params.get("password"));
        if(params.containsKey("id")) {
            account.setId(Long.parseLong(params.get("id")));
        }
        if(params.containsKey("role")) {
            account.setRoleId(Integer.parseInt(params.get("role")));
        } else {
            account.setRoleId(0);
        }
        return account;
    }
}
