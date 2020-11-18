package com.rusakovich.bsuir.server.controller;


import com.rusakovich.bsuir.server.controller.impl.*;
import com.rusakovich.bsuir.server.model.dao.impl.*;
import com.rusakovich.bsuir.server.model.service.impl.*;

public enum ControllerType {
    BANK_ACCOUNT(new BankAccountController(new BankAccountServiceImpl(new BankAccountDaoImpl()))),
    CURRENCY(new CurrencyController(new CurrencyServiceImpl(new CurrencyDaoImpl()))),
    EXPENSE(new ExpenseController(new ExpenseServiceImpl(new ExpenseDaoImpl()))),
    INCOME(new IncomeController(new IncomeServiceImpl(new IncomeDaoImpl()))),
    ACCOUNT(new AccountController(new AccountServiceImpl(new AccountDaoImpl()))),
    ACCOUNT_MEMBER(new AccountMemberController(new AccountMemberServiceImpl(new AccountMemberDaoImpl()))),
    INCOME_CATEGORY(new IncomeCategoryController(new IncomeCategoryServiceImpl(new IncomeCategoryDaoImpl()))),
    EXPENSE_CATEGORY(new ExpenseCategoryController(new ExpenseCategoryServiceImpl(new ExpenseCategoryDaoImpl())));

    private final Controller controller;

    ControllerType(Controller controller) {
        this.controller = controller;
    }

    public Controller getController() {
        return controller;
    }

    public static Controller getControllerByName(String controllerName) {
        Controller controller = null;
        for (ControllerType value : ControllerType.values()) {
            if (value.name().equalsIgnoreCase(controllerName)) {
                controller = value.getController();
            }
        }
        return controller;
    }
}
