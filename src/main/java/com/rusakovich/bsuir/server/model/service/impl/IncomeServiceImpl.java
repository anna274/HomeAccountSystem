package com.rusakovich.bsuir.server.model.service.impl;

import com.rusakovich.bsuir.server.entity.Income;
import com.rusakovich.bsuir.server.model.dao.impl.IncomeDaoImpl;
import com.rusakovich.bsuir.server.model.service.IncomeService;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class IncomeServiceImpl implements IncomeService {
    private final IncomeDaoImpl incomeDao;

    public IncomeServiceImpl(IncomeDaoImpl incomeDao) {
        this.incomeDao = incomeDao;
    }

    @Override
    public void addIncome(Income income) {
        try {
            incomeDao.save(income);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Income getIncomeById(Long id) {
        return incomeDao.findById(id);
    }

    @Override
    public List<Income> getAllIncomes() {
        return incomeDao.findAll();
    }

    @Override
    public List<Income> getIncomesByMemberAccountId(Long memberAccountId) {
        return incomeDao.findAllByMemberAccountId(memberAccountId);
    }

    @Override
    public List<Income> getIncomesByMemberId(Long memberId) {
        return incomeDao.findAllByMemberId(memberId);
    }

    @Override
    public List<Income> getIncomesByBankAccountId(Long bankAccountId) {
        return incomeDao.findAllByBankAccountId(bankAccountId);
    }

    @Override
    public List<Income> getIncomesInDateDiapason(LocalDateTime begin, LocalDateTime end) {
        return incomeDao.findAllInDateDiapason(begin, end);
    }

    // TO REFACTOR
    @Override
    public void updateIncome(Income income) {
        try {
            incomeDao.update(income);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteIncome(Long id) {
        try {
            incomeDao.delete(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Map<String, Float> groupByCategory(Long memberAccountId, LocalDate begin, LocalDate end) {
        return incomeDao.groupByCategory(memberAccountId, begin, end);
    }

    @Override
    public Map<String, Float> groupByBankAccount(Long memberAccountId, LocalDate begin, LocalDate end) {
        return incomeDao.groupByBankAccount(memberAccountId, begin, end);
    }
}
