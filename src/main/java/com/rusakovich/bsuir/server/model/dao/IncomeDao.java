package com.rusakovich.bsuir.server.model.dao;

import com.rusakovich.bsuir.server.entity.Income;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface IncomeDao extends CrudDao<Income> {
    List<Income> findAllByMemberAccountId(Long memberAccountId);

    List<Income> findAllByMemberId(Long memberId);

    List<Income> findAllByBankAccountId(Long id);

    List<Income> findAllInDateDiapason(LocalDateTime begin, LocalDateTime end);

    Map<String, Float> groupByCategory(Long memberAccountId, LocalDate begin, LocalDate end);

    Map<String, Float> groupByBankAccount(Long memberAccountId, LocalDate begin, LocalDate end);
}
