package com.rusakovich.bsuir.server.model.service;

import com.rusakovich.bsuir.server.entity.BankAccount;

import java.util.List;

public interface BankAccountService {
    void addBankAccount(BankAccount bankAccount);

    BankAccount getBankAccountById(Long id);

    List<BankAccount> getAllBankAccounts();

    List<BankAccount> getBankAccountsByMemberAccountId(Long memberAccountId);

    List<BankAccount> getBankAccountsByMemberId(Long memberId);

    void updateBankAccount(BankAccount bankAccount);

    void deleteBankAccount(Long id);

}
