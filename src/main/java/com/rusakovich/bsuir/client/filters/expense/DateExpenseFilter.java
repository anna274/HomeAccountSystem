package com.rusakovich.bsuir.client.filters.expense;

import com.rusakovich.bsuir.server.entity.Expense;

import java.time.LocalDate;
import java.util.ArrayList;

public class DateExpenseFilter extends ExpenseFilter {
    private final LocalDate startDate;
    private final LocalDate endDate;

    public DateExpenseFilter(LocalDate startDate, LocalDate endDate){
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public ArrayList<Expense> filter(ArrayList<Expense> items) {
        ArrayList<Expense> result = new ArrayList<>();
        for(Expense item: items){
            if(compareDates(item.getDate(), startDate) >= 0 && compareDates(item.getDate(), endDate) <= 0){
                result.add(item);
            }
        }
        return super.filter(result);
    }

    private int compareDates(LocalDate date1, LocalDate date2) {
        int cmp = (date1.getYear() - date2.getYear());
        if (cmp == 0) {
            cmp = (date1.getMonthValue() - date2.getMonthValue());
            if (cmp == 0) {
                cmp = (date1.getDayOfMonth() - date2.getDayOfMonth());
            }
        }
        return cmp;
    }
}
