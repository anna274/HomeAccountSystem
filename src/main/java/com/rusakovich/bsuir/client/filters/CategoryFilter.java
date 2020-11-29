package com.rusakovich.bsuir.client.filters;

import com.rusakovich.bsuir.server.entity.Income;

import java.time.LocalDate;
import java.util.ArrayList;

public class CategoryFilter extends FilterStep{
    private final Long categoryId;

    public CategoryFilter(Long categoryId){
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
