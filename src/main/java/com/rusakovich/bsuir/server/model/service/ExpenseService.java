package com.rusakovich.bsuir.server.model.service;

import com.rusakovich.bsuir.server.entity.Expense;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface ExpenseService {
    void addExpense(Expense expense);

    Expense getExpenseById(Long id);

    List<Expense> getExpensesByMemberAccountId(Long memberAccountId);

    List<Expense> getExpensesByMemberId(Long memberId);

    List<Expense> getExpensesByBankAccountId(Long bankAccountId);

    List<Expense> getExpensesInDateDiapason(LocalDateTime begin, LocalDateTime end);

    List<Expense> getAllExpenses();

    void updateExpense(Expense expense);

    void deleteExpense(Long id);

    Map<String, Float> groupByCategory(Long memberAccountId, LocalDate begin, LocalDate end);

    Map<String, Float> groupByBankAccount(Long memberAccountId, LocalDate begin, LocalDate end);
}
