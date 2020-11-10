package com.rusakovich.bsuir.server.model.service.impl;

import com.rusakovich.bsuir.server.entity.AccountMember;
import com.rusakovich.bsuir.server.model.dao.impl.AccountMemberDaoImpl;
import com.rusakovich.bsuir.server.model.service.AccountMemberService;

import java.sql.SQLException;
import java.util.List;

public class AccountMemberServiceImpl implements AccountMemberService {
    private final AccountMemberDaoImpl memberDao;

    public AccountMemberServiceImpl(AccountMemberDaoImpl memberDao) {
        this.memberDao = memberDao;
    }

    @Override
    public AccountMember addAccountMember(AccountMember accountMember) {
        try {
            memberDao.save(accountMember);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accountMember;
    }

    @Override
    public AccountMember getAccountMemberById(Long id) {
        return memberDao.findById(id);
    }

    @Override
    public List<AccountMember> getAllAccountMembers() {
        return memberDao.findAll();
    }

    @Override
    public List<AccountMember> getAccountMembersByAccountId(Long accountId) {
        return memberDao.findAllByAccountId(accountId);
    }

    @Override
    public void updateAccountMember(AccountMember accountMember) {
        try {
            memberDao.update(accountMember);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteAccountMember(Long id) {
        try {
            memberDao.delete(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
