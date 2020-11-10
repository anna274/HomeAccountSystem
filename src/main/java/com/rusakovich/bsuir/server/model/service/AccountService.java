package com.rusakovich.bsuir.server.model.service;

import com.rusakovich.bsuir.server.entity.Account;

import java.util.List;

public interface AccountService {

    Account addAccount(Account account);

    Account getAccountById(Long id);

    List<Account> getAllAccounts();

    void updateAccount(Account account);

    void deleteAccount(Long id);

    Account login(String login, String password) throws IllegalAccessException;
}
