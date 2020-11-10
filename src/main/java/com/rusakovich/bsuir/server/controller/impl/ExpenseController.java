package com.rusakovich.bsuir.server.controller.impl;

import com.rusakovich.bsuir.server.controller.Controller;
import com.rusakovich.bsuir.server.controller.ControllerHelper;
import com.rusakovich.bsuir.server.entity.Expense;
import com.rusakovich.bsuir.server.model.service.impl.ExpenseServiceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class ExpenseController implements Controller {
    private final ExpenseServiceImpl expenseService;

    public ExpenseController(ExpenseServiceImpl expenseService) {
        this.expenseService = expenseService;
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
        Expense expense = createExpenseObjFromParams(params);
        expenseService.addExpense(expense);
        return ControllerHelper.getResponse("ok", expense.toString(), "");
    }

    private String getOne(Map<String, String> params) {
        Expense expense = expenseService.getExpenseById(Long.valueOf(params.get("id")));
        return ControllerHelper.getResponse("ok", expense.toString(), "");
    }

    private String getAllByMemberAccountId(Map<String, String> params) {
        StringBuilder response = new StringBuilder();
        List<Expense> expenses = expenseService.getExpensesByMemberAccountId(Long.parseLong(params.get("memberAccountId")));
        for (Expense expense : expenses) {
            response.append(expense.toString());
        }
        return ControllerHelper.getResponse("ok", "[" + response + "]", "");
    }

    private String getAllByMemberId(Map<String, String> params) {
        StringBuilder response = new StringBuilder();
        List<Expense> expenses = expenseService.getExpensesByMemberId(Long.parseLong(params.get("memberId")));
        for (Expense expense : expenses) {
            response.append(expense.toString());
        }
        return ControllerHelper.getResponse("ok", "[" + response + "]", "");
    }

    private String getAllByBankAccountId(Map<String, String> params) {
        StringBuilder response = new StringBuilder();
        List<Expense> expenses = expenseService.getExpensesByBankAccountId(Long.parseLong(params.get("bankAccountId")));
        for (Expense expense : expenses) {
            response.append(expense.toString());
        }
        return ControllerHelper.getResponse("ok", "[" + response + "]", "");
    }

    private String getAllInDateDiapason(Map<String, String> params) {
        StringBuilder response = new StringBuilder();
        List<Expense> expenses = expenseService.getExpensesInDateDiapason(
                LocalDateTime.parse(params.get("begin")),
                LocalDateTime.parse(params.get("end"))
        );
        for (Expense expense : expenses) {
            response.append(expense.toString());
        }
        return ControllerHelper.getResponse("ok", "[" + response + "]", "");
    }

    private String update(Map<String, String> params) {
        expenseService.updateExpense(
                createExpenseObjFromParams(params)
        );
        return ControllerHelper.getResponse("ok", "", "");
    }

    private String delete(Map<String, String> params) {
        expenseService.deleteExpense(Long.valueOf(params.get("id")));
        return ControllerHelper.getResponse("ok", "", "");
    }

    private Expense createExpenseObjFromParams(Map<String, String> params) {
        return new Expense(
                Long.parseLong(params.get("id")),
                Long.parseLong(params.get("categoryId")),
                Long.parseLong(params.get("memberId")),
                Long.parseLong(params.get("memberAccountId")),
                Long.parseLong(params.get("bankAccountId")),
                Long.parseLong(params.get("currencyId")),
                Float.parseFloat(params.get("sum")),
                Integer.parseInt(params.get("quantity")),
                LocalDateTime.parse(params.get("date")),
                params.get("note")
        );
    }
}
