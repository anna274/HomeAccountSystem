package com.rusakovich.bsuir.server.model.service;

import com.rusakovich.bsuir.server.entity.Income;

import java.time.LocalDateTime;
import java.util.List;
import java.sql.Date;

public interface IncomeService {
    void addIncome(Income income);

    Income getIncomeById(Long id);

    List<Income> getIncomesByMemberAccountId(Long memberAccountId);

    List<Income> getIncomesByMemberId(Long memberId);

    List<Income> getIncomesByBankAccountId(Long bankAccountId);

    List<Income> getIncomesInDateDiapason(LocalDateTime begin, LocalDateTime end);

    List<Income> getAllIncomes();

    void updateIncome(Income income);

    void deleteIncome(Long id);
}
