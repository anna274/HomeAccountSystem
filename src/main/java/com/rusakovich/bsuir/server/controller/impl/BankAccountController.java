package com.rusakovich.bsuir.server.controller.impl;

import com.rusakovich.bsuir.server.controller.Controller;
import com.rusakovich.bsuir.server.entity.BankAccount;
import com.rusakovich.bsuir.server.model.service.impl.BankAccountServiceImpl;

import java.util.Map;
import java.util.List;

public class BankAccountController implements Controller {
    private final BankAccountServiceImpl bankAccountService;

    public BankAccountController(BankAccountServiceImpl bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @Override
    public String request(Map<String, String> params) {
        String command = params.get("command");
        switch (command) {
            case "add": {
                return getOne(params);
            }
            case "getOne": {
                return getOne(params);
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
        String response = "";
        return response;
    }

    public String getOne(Map<String, String> params) {
        String response;
        BankAccount bankAccount = bankAccountService.getBankAccountById(Long.valueOf(params.get("id")));
        response = "status=ok&bankAccount={" + bankAccount.toString() + "}";
        return response;
    }

    public String getAll(Map<String, String> params) {
        String response = "";
        List<BankAccount> bankAccounts = bankAccountService.getBankAccountsByMemberAccountId(
                Long.valueOf(params.get("userId"))
        );
        for (BankAccount bankAccount : bankAccounts) {
            response += "{" + bankAccount + "},";
        }
        response = "status=ok&bankAccounts=[" + response + "]";
        return response;
    }

    public String update(Map<String, String> params) {
        String response;
        // how do I should pass updated user info in params?
        // Should it be seperated fields or like json object that needed to parse
        // ( to be honest I supose that it should be seperated fileds:) )
        // bankAccountService.updateBankAccount(params.get("id"));
        response = "status=ok";
        return response;
    }

    public String delete(Map<String, String> params) {
        String response;
        bankAccountService.deleteBankAccount(Long.valueOf(params.get("id")));
        response = "status=ok";
        return response;
    }
}
