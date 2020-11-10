package com.rusakovich.bsuir.server.model.dao;

import com.rusakovich.bsuir.server.entity.BankAccount;

import java.util.List;

public interface BankAccountDao extends CrudDao<BankAccount> {
    List<BankAccount> findAllByMemberId(Long memberId);
    List<BankAccount> findAllByMemberAccountId(Long accountId);
}
