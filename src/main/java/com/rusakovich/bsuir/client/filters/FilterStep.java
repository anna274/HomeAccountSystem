package com.rusakovich.bsuir.client.filters;

import com.rusakovich.bsuir.server.entity.Income;

import java.util.ArrayList;

public class FilterStep {
    private FilterStep nextFilter = null;
    public ArrayList<Income> filter(ArrayList<Income> items){
        if(nextFilter != null) {
            return nextFilter.filter(items);
        }
        return items;
    }

    public void setNextFilter(FilterStep nextFilter) {
        this.nextFilter = nextFilter;
    }
}
