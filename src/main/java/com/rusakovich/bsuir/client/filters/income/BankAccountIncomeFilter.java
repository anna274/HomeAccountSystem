package com.rusakovich.bsuir.client.filters.income;

import com.rusakovich.bsuir.server.entity.Income;

import java.util.ArrayList;

public class BankAccountIncomeFilter extends IncomeFilter{
    private final Long bankAccountId;

    public BankAccountIncomeFilter(Long categoryId){
        this.bankAccountId = categoryId;
    }

    @Override
    public ArrayList<Income> filter(ArrayList<Income> items) {
        ArrayList<Income> result = new ArrayList<>();
        for(Income item: items){
            if(item.getBankAccountId().equals(bankAccountId)){
                result.add(item);
            }
        }
        return super.filter(result);
    }
}
