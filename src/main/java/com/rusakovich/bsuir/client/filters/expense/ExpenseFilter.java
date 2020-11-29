package com.rusakovich.bsuir.client.filters.expense;

import com.rusakovich.bsuir.server.entity.Expense;
import com.rusakovich.bsuir.server.entity.Income;

import java.util.ArrayList;

public class ExpenseFilter {
    private ExpenseFilter nextIncomeFilter = null;
    public ArrayList<Expense> filter(ArrayList<Expense> items){
        if(nextIncomeFilter != null) {
            return nextIncomeFilter.filter(items);
        }
        return items;
    }
    public void setNextFilter(ExpenseFilter nextIncomeFilter) {
        this.nextIncomeFilter = nextIncomeFilter;
    }

    public static class FilterBuilder{
        private ExpenseFilter firstIncomeFilter = null;
        private ExpenseFilter lastIncomeFilter = null;

        public FilterBuilder addFilter(ExpenseFilter incomeFilter) {
            if(firstIncomeFilter == null) {
                firstIncomeFilter = incomeFilter;
                lastIncomeFilter = incomeFilter;
                return this;
            }
            lastIncomeFilter.setNextFilter(incomeFilter);
            lastIncomeFilter = incomeFilter;
            return this;
        }

        public ExpenseFilter build() {
            return firstIncomeFilter;
        }

    }
}
