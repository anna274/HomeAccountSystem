package com.rusakovich.bsuir.server.model.service;

import com.rusakovich.bsuir.server.entity.Account;
import com.rusakovich.bsuir.server.entity.AccountMember;

import java.util.List;

public interface AccountMemberService {
    AccountMember addAccountMember(AccountMember accountMember);

    AccountMember getAccountMemberById(Long id);

    List<AccountMember> getAllAccountMembers();

    List<AccountMember> getAccountMembersByAccountId(Long accountId);

    void updateAccountMember(AccountMember accountMember);

    void deleteAccountMember(Long id);
}
