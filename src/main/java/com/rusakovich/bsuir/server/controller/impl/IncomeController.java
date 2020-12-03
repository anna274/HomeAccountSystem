package com.rusakovich.bsuir.server.controller.impl;

import com.rusakovich.bsuir.server.controller.Controller;
import com.rusakovich.bsuir.server.controller.ControllerHelper;
import com.rusakovich.bsuir.server.entity.BankAccount;
import com.rusakovich.bsuir.server.entity.Income;
import com.rusakovich.bsuir.server.model.service.impl.IncomeServiceImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
            case "groupBy": {
                return groupBy(params);
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

    private String groupBy(Map<String, String> params) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MM yyyy");
        Map<String, Float> res = null;
        if(params.get("groupField").equals("category")) {
            res = incomeService.groupByCategory(
                    Long.valueOf(params.get("memberAccountId")),
                    LocalDate.parse(params.get("begin"), formatter),
                    LocalDate.parse(params.get("end"), formatter)
            );
        } else {
            res = incomeService.groupByBankAccount(
                    Long.valueOf(params.get("memberAccountId")),
                    LocalDate.parse(params.get("begin"), formatter),
                    LocalDate.parse(params.get("end"), formatter)
            );
        }
        List<String> groupsStr = new ArrayList<>();
        for (Map.Entry<String, Float> entry : res.entrySet()) {
                groupsStr.add(params.get("groupField") + ":" + entry.getKey() + ",amount:" + entry.getValue());
        }
        String data = String.join(";", groupsStr);
        return ControllerHelper.getResponse("ok", data, "");
    }

    private String add(Map<String, String> params) {
        Income income = Income.fromMap(params);
        incomeService.addIncome(income);
        return ControllerHelper.getResponse("ok", income.toString(), "");
    }

    private String getOne(Map<String, String> params) {
        Income income = incomeService.getIncomeById(Long.valueOf(params.get("id")));
        return ControllerHelper.getResponse("ok", income.toString(), "");
    }

    private String getAllByMemberAccountId(Map<String, String> params) {
        List<Income> incomes = incomeService.getIncomesByMemberAccountId(
                Long.valueOf(params.get("memberAccountId"))
        );
        List<String> incomesStr = new ArrayList<String>();
        for (Income income : incomes) {
            incomesStr.add(income.toString());
        }
        String data = String.join(";", incomesStr);
        return ControllerHelper.getResponse("ok", data, "");
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
        incomeService.updateIncome(Income.fromMap(params));
        return ControllerHelper.getResponse("ok", "", "");
    }

    private String delete(Map<String, String> params) {
        incomeService.deleteIncome(Long.valueOf(params.get("id")));
        return ControllerHelper.getResponse("ok", "", "");
    }
}
