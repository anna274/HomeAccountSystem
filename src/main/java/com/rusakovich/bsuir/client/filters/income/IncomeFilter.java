package com.rusakovich.bsuir.client.filters.income;

import com.rusakovich.bsuir.server.entity.Income;

import java.util.ArrayList;

public class IncomeFilter {
    private IncomeFilter nextIncomeFilter = null;
    public ArrayList<Income> filter(ArrayList<Income> items){
        if(nextIncomeFilter != null) {
            return nextIncomeFilter.filter(items);
        }
        return items;
    }
    public void setNextFilter(IncomeFilter nextIncomeFilter) {
        this.nextIncomeFilter = nextIncomeFilter;
    }

    public static class FilterBuilder{
        private IncomeFilter firstIncomeFilter = null;
        private IncomeFilter lastIncomeFilter = null;

        public FilterBuilder addFilter(IncomeFilter incomeFilter) {
            if(firstIncomeFilter == null) {
                firstIncomeFilter = incomeFilter;
                lastIncomeFilter = incomeFilter;
                return this;
            }
            lastIncomeFilter.setNextFilter(incomeFilter);
            lastIncomeFilter = incomeFilter;
            return this;
        }

        public IncomeFilter build() {
            return firstIncomeFilter;
        }

    }
}
