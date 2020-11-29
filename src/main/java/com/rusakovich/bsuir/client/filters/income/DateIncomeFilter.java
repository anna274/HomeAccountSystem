package com.rusakovich.bsuir.client.filters.income;

import com.rusakovich.bsuir.server.entity.Income;

import java.time.LocalDate;
import java.util.ArrayList;

public class DateIncomeFilter extends IncomeFilter{
    private final LocalDate startDate;
    private final LocalDate endDate;

    public DateIncomeFilter(LocalDate startDate, LocalDate endDate){
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public ArrayList<Income> filter(ArrayList<Income> items) {
        ArrayList<Income> result = new ArrayList<>();
        for(Income item: items){
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
