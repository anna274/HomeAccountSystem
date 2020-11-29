package com.rusakovich.bsuir.client.filters.income;

import com.rusakovich.bsuir.server.entity.Income;

import java.util.ArrayList;

public class CategoryIncomeFilter extends IncomeFilter{
    private final Long categoryId;
    public CategoryIncomeFilter(Long categoryId){
        this.categoryId = categoryId;
    }

    @Override
    public ArrayList<Income> filter(ArrayList<Income> items) {
        ArrayList<Income> result = new ArrayList<>();
        for(Income item: items){
            if(item.getCategoryId().equals(categoryId)){
                result.add(item);
            }
        }
        return super.filter(result);
    }
}
