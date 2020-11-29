package com.rusakovich.bsuir.client.filters.expense;

import com.rusakovich.bsuir.server.entity.Expense;

import java.util.ArrayList;

public class BankAccountExpenseFilter extends ExpenseFilter {
    private final Long bankAccountId;

    public BankAccountExpenseFilter(Long categoryId){
        this.bankAccountId = categoryId;
    }

    @Override
    public ArrayList<Expense> filter(ArrayList<Expense> items) {
        ArrayList<Expense> result = new ArrayList<>();
        for(Expense item: items){
            if(item.getBankAccountId().equals(bankAccountId)){
                result.add(item);
            }
        }
        return super.filter(result);
    }
}
