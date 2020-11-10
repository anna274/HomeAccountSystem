package com.rusakovich.bsuir.server.model.dao;

import com.rusakovich.bsuir.server.entity.AccountMember;
import java.util.List;

public interface AccountMemberDao extends CrudDao<AccountMember>{
    List<AccountMember> findAllByAccountId(Long accountId);
}
