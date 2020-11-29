package com.rusakovich.bsuir.client.filters.expense;

import com.rusakovich.bsuir.server.entity.Expense;

import java.util.ArrayList;

public class CategoryExpenseFilter extends ExpenseFilter {
    private final Long categoryId;
    public CategoryExpenseFilter(Long categoryId){
        this.categoryId = categoryId;
    }

    @Override
    public ArrayList<Expense> filter(ArrayList<Expense> items) {
        ArrayList<Expense> result = new ArrayList<>();
        for(Expense item: items){
            if(item.getCategoryId().equals(categoryId)){
                result.add(item);
            }
        }
        return super.filter(result);
    }
}
