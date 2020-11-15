package com.rusakovich.bsuir.server.controller.impl;

import com.rusakovich.bsuir.server.controller.Controller;
import com.rusakovich.bsuir.server.controller.ControllerHelper;
import com.rusakovich.bsuir.server.entity.Account;
import com.rusakovich.bsuir.server.entity.AccountMember;
import com.rusakovich.bsuir.server.entity.BankAccount;
import com.rusakovich.bsuir.server.model.service.impl.BankAccountServiceImpl;

import java.util.ArrayList;
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
                return add(params);
            }
            case "getOne": {
                return getOne(params);
            }
            case "getAllByAccountId": {
                return getAllByMemberAccountId(params);
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
        BankAccount bankAccount = bankAccountService.addBankAccount(BankAccount.fromMap(params));
        return ControllerHelper.getResponse("ok", bankAccount.toString(), "");
    }

    public String getOne(Map<String, String> params) {
        BankAccount bankAccount = bankAccountService.getBankAccountById(Long.valueOf(params.get("id")));
        return ControllerHelper.getResponse("ok", bankAccount.toString(), "");
    }

    public String getAllByMemberAccountId(Map<String, String> params) {
        List<BankAccount> bankAccounts = bankAccountService.getBankAccountsByMemberAccountId(
                Long.valueOf(params.get("accountId"))
        );
        List<String> bankAccountsStr = new ArrayList<String>();
        for (BankAccount member : bankAccounts) {
            bankAccountsStr.add(member.toString());
        }
        String data = String.join(";", bankAccountsStr);
        return ControllerHelper.getResponse("ok", data, "");
    }

    public String update(Map<String, String> params) {
        bankAccountService.updateBankAccount(BankAccount.fromMap(params));
        return ControllerHelper.getResponse("ok", "", "");
    }

    public String delete(Map<String, String> params) {
        bankAccountService.deleteBankAccount(Long.valueOf(params.get("id")));
        return ControllerHelper.getResponse("ok", "", "");
    }
}
