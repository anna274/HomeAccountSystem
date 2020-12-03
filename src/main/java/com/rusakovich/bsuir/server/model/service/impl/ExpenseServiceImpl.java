package com.rusakovich.bsuir.server.model.service.impl;

import com.rusakovich.bsuir.server.entity.Expense;
import com.rusakovich.bsuir.server.model.dao.impl.ExpenseDaoImpl;
import com.rusakovich.bsuir.server.model.service.ExpenseService;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class ExpenseServiceImpl implements ExpenseService {
    private final ExpenseDaoImpl expenseDao;

    public ExpenseServiceImpl(ExpenseDaoImpl expenseDao) {
        this.expenseDao = expenseDao;
    }

    @Override
    public void addExpense(Expense expense) {
        try {
            expenseDao.save(expense);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Expense getExpenseById(Long id) {
        return expenseDao.findById(id);
    }

    @Override
    public List<Expense> getAllExpenses() {
        return expenseDao.findAll();
    }

    @Override
    public List<Expense> getExpensesByMemberAccountId(Long memberAccountId) {
        return expenseDao.findAllByMemberAccountId(memberAccountId);
    }

    @Override
    public List<Expense> getExpensesByMemberId(Long memberId) {
        return expenseDao.findAllByMemberId(memberId);
    }

    @Override
    public List<Expense> getExpensesByBankAccountId(Long bankAccountId) {
        return expenseDao.findAllByBankAccountId(bankAccountId);
    }

    @Override
    public List<Expense> getExpensesInDateDiapason(LocalDateTime begin, LocalDateTime end) {
        return expenseDao.findAllInDateDiapason(begin, end);
    }

    // TO REFACTOR
    @Override
    public void updateExpense(Expense expense) {
        try {
            expenseDao.update(expense);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteExpense(Long id) {
        try {
            expenseDao.delete(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Map<String, Float> groupByCategory(Long memberAccountId, LocalDate begin, LocalDate end) {
        return expenseDao.groupByCategory(memberAccountId, begin, end);
    }

    @Override
    public Map<String, Float> groupByBankAccount(Long memberAccountId, LocalDate begin, LocalDate end) {
        return expenseDao.groupByBankAccount(memberAccountId, begin, end);
    }
}
