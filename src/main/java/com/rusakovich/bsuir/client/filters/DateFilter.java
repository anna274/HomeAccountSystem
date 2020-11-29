package com.rusakovich.bsuir.client.filters;

import com.rusakovich.bsuir.server.entity.Income;

import java.time.LocalDate;
import java.util.ArrayList;

public class DateFilter  extends FilterStep{
    private final LocalDate startDate;
    private final LocalDate endDate;

    public DateFilter(LocalDate startDate, LocalDate endDate){
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public ArrayList<Income> filter(ArrayList<Income> items) {
//        ArrayList<Income> result = new ArrayList<>();
//        for(Income item: items){
//            if(item.getDate() >= startDate && item.getDate() <= endDate){
//                result.add(item);
//            }
//        }
//        return super.filter(result);
        return super.filter(items);
    }
}
