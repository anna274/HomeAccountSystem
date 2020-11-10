package com.rusakovich.bsuir.server.controller.impl;

import com.rusakovich.bsuir.server.controller.Controller;
import com.rusakovich.bsuir.server.controller.ControllerHelper;
import com.rusakovich.bsuir.server.entity.Income;
import com.rusakovich.bsuir.server.model.service.impl.IncomeServiceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class IncomeController implements Controller {
    private final IncomeServiceImpl incomeService;

    public IncomeController(IncomeServiceImpl incomeService) {
        this.incomeService = incomeService;
    }

    @Override
    public String request(Map<String, String> params) {
        String command = params.get("command");
        switch (command) {
            case "add": {
                return add(params);
            }
            case "getOne": {
                return getOne(params);
            }
            case "getAllByMemberAccountId": {
                return getAllByMemberAccountId(params);
            }
            case "getAllByMemberId": {
                return getAllByMemberId(params);
            }
            case "getAllByBankAccountId": {
                return getAllByBankAccountId(params);
            }
            case "getAllInDateDiapason": {
                return getAllInDateDiapason(params);
            }
            case "update": {
                return update(params);
            }
            case "delete": {
                return delete(params);
            }
            default:
                return "";
        }
    }

    private String add(Map<String, String> params) {
        Income income = createIncomeObjFromParams(params);
        incomeService.addIncome(income);
        return ControllerHelper.getResponse("ok", income.toString(), "");
    }

    private String getOne(Map<String, String> params) {
        Income income = incomeService.getIncomeById(Long.valueOf(params.get("id")));
        return ControllerHelper.getResponse("ok", income.toString(), "");
    }

    private String getAllByMemberAccountId(Map<String, String> params) {
        StringBuilder response = new StringBuilder();
        List<Income> incomes = incomeService.getIncomesByMemberAccountId(Long.parseLong(params.get("memberAccountId")));
        for (Income income : incomes) {
            response.append(income.toString());
        }
        return ControllerHelper.getResponse("ok", "[" + response + "]", "");
    }

    private String getAllByMemberId(Map<String, String> params) {
        StringBuilder response = new StringBuilder();
        List<Income> incomes = incomeService.getIncomesByMemberId(Long.parseLong(params.get("memberId")));
        for (Income income : incomes) {
            response.append(income.toString());
        }
        return ControllerHelper.getResponse("ok", "[" + response + "]", "");
    }

    private String getAllByBankAccountId(Map<String, String> params) {
        StringBuilder response = new StringBuilder();
        List<Income> incomes = incomeService.getIncomesByBankAccountId(Long.parseLong(params.get("bankAccountId")));
        for (Income inc : incomes) {
            response.append(inc.toString());
        }
        return ControllerHelper.getResponse("ok", "[" + response + "]", "");
    }

    private String getAllInDateDiapason(Map<String, String> params) {
        StringBuilder response = new StringBuilder();
        List<Income> incomes = incomeService.getIncomesInDateDiapason(
                LocalDateTime.parse(params.get("begin")),
                LocalDateTime.parse(params.get("end"))
        );
        for (Income income : incomes) {
            response.append(income.toString());
        }
        return ControllerHelper.getResponse("ok", "[" + response + "]", "");
    }

    private String update(Map<String, String> params) {
        incomeService.updateIncome(
                createIncomeObjFromParams(params)
        );
        return ControllerHelper.getResponse("ok", "", "");
    }

    private String delete(Map<String, String> params) {
        incomeService.deleteIncome(Long.valueOf(params.get("id")));
        return ControllerHelper.getResponse("ok", "", "");
    }

    private Income createIncomeObjFromParams(Map<String, String> params) {
        return new Income(
                Long.parseLong(params.get("id")),
                Long.parseLong(params.get("categoryId")),
                Long.parseLong(params.get("memberId")),
                Long.parseLong(params.get("memberAccountId")),
                Long.parseLong(params.get("bankAccountId")),
                Long.parseLong(params.get("currencyId")),
                Float.parseFloat(params.get("sum")),
                params.get("note"),
                Integer.parseInt(params.get("quantity")),
                LocalDateTime.parse(params.get("date"))
        );
    }
}
