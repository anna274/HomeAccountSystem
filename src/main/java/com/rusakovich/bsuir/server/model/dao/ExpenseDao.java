package com.rusakovich.bsuir.server.model.dao;

import com.rusakovich.bsuir.server.entity.Expense;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface ExpenseDao extends CrudDao<Expense> {

    List<Expense> findAllByMemberAccountId(Long memberAccountId);

    List<Expense> findAllByMemberId(Long memberAccountId);

    List<Expense> findAllByBankAccountId(Long id);

    List<Expense> findAllInDateDiapason(LocalDateTime begin, LocalDateTime end);

    Map<String, Float> groupByCategory(Long memberAccountId, LocalDate begin, LocalDate end);

    Map<String, Float> groupByBankAccount(Long memberAccountId, LocalDate begin, LocalDate end);
}
