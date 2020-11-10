package com.rusakovich.bsuir.server.model.service.impl;

import com.rusakovich.bsuir.server.entity.Account;
import com.rusakovich.bsuir.server.model.dao.impl.AccountDaoImpl;
import com.rusakovich.bsuir.server.model.service.AccountService;

import java.sql.SQLException;
import java.util.List;

public class AccountServiceImpl implements AccountService {
    private final AccountDaoImpl accountDao;

    public AccountServiceImpl(AccountDaoImpl accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    public Account addAccount(Account account) {
        try {
            accountDao.save(account);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return account;
    }

    @Override
    public Account getAccountById(Long id) {
        return accountDao.findById(id);
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountDao.findAll();
    }

    @Override
    public void updateAccount(Account account) {
        try {
            accountDao.update(account);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteAccount(Long id) {
        try {
            accountDao.delete(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Account login(String login, String password) throws IllegalAccessException {
        Account account = accountDao.findByLogin(login);
        if(account != null && account.getPassword().equals(password)) {
            return account;
        } else {
            throw new IllegalAccessException("Incorrect login or password");
        }
    }
}
