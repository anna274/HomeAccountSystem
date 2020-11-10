package com.rusakovich.bsuir.server.model.service.impl;

import com.rusakovich.bsuir.server.entity.BankAccount;
import com.rusakovich.bsuir.server.model.dao.impl.BankAccountDaoImpl;
import com.rusakovich.bsuir.server.model.service.BankAccountService;

import java.sql.SQLException;
import java.util.List;

public class BankAccountServiceImpl implements BankAccountService {
    private final BankAccountDaoImpl bankAccountDao;

    public BankAccountServiceImpl(BankAccountDaoImpl bankAccountDao) {
        this.bankAccountDao = bankAccountDao;
    }

    @Override
    public void addBankAccount(BankAccount bankAccount) {
        try {
            bankAccountDao.save(bankAccount);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public BankAccount getBankAccountById(Long id) {
        return bankAccountDao.findById(id);
    }

    @Override
    public List<BankAccount> getAllBankAccounts() {
        return bankAccountDao.findAll();
    }

    @Override
    public List<BankAccount> getBankAccountsByMemberAccountId(Long memberAccountId) {
        return bankAccountDao.findAllByMemberAccountId(memberAccountId);
    }

    @Override
    public List<BankAccount> getBankAccountsByMemberId(Long memberId) {
        return bankAccountDao.findAllByMemberId(memberId);
    }
    
    @Override
    public void updateBankAccount(BankAccount bankAccount) {
        try {
            bankAccountDao.update(bankAccount);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteBankAccount(Long id) {
        try {
            bankAccountDao.delete(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
