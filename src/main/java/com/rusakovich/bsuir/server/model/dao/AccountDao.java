package com.rusakovich.bsuir.server.model.dao;

import com.rusakovich.bsuir.server.entity.Account;

public interface AccountDao extends CrudDao<Account> {
    Account findByLogin(String login);
}
