package com.rusakovich.bsuir.client.app;

import com.rusakovich.bsuir.server.entity.*;

import java.util.ArrayList;

public class ApplicationContext {
    private static ApplicationContext instance;
    private Account currentAccount;
    private ArrayList<Account> accountsList = null;
    private ArrayList<AccountMember> membersList = null;
    private ArrayList<Currency> currencies = null;
    private ArrayList<BankAccount> bankAccounts = null;
    private ArrayList<Category> incomeCategories = null;
    private ArrayList<Category> expenseCategories = null;
    private ArrayList<Income> incomes = null;
    private ArrayList<Expense> expenses = null;

    private ApplicationContext() {
    }

    public Account getCurrentAccount() {
        return currentAccount;
    }

    public ArrayList<AccountMember> getMembersList() {
        return membersList;
    }

    public void setMembersList(ArrayList<AccountMember> membersList) {
        this.membersList = membersList;
    }

    public ArrayList<Currency> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(ArrayList<Currency> currencies) {
        this.currencies = currencies;
    }

    public void setCurrentAccount(Account currentAccount) {
        this.currentAccount = currentAccount;
    }

    public ArrayList<BankAccount> getBankAccounts() {
        return bankAccounts;
    }

    public void setBankAccounts(ArrayList<BankAccount> bankAccounts) {
        this.bankAccounts = bankAccounts;
    }

    public ArrayList<Account> getAccountsList() {
        return accountsList;
    }

    public void setAccountsList(ArrayList<Account> accountsList) {
        this.accountsList = accountsList;
    }

    public ArrayList<Category> getIncomeCategories() {
        return incomeCategories;
    }

    public void setIncomeCategories(ArrayList<Category> incomeCategories) {
        this.incomeCategories = incomeCategories;
    }

    public ArrayList<Category> getExpenseCategories() {
        return expenseCategories;
    }

    public void setExpenseCategories(ArrayList<Category> expenseCategories) {
        this.expenseCategories = expenseCategories;
    }

    public ArrayList<Income> getIncomes() {
        return incomes;
    }

    public void setIncomes(ArrayList<Income> incomes) {
        this.incomes = incomes;
    }

    public ArrayList<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(ArrayList<Expense> expenses) {
        this.expenses = expenses;
    }

    public Currency findCurrencyById(Long id) {
        if(currencies == null) {
            return new Currency();
        }
        for(Currency currency: currencies) {
            if(currency.getId().equals(id)) {
                return currency;
            }
        }
        return new Currency();
    }

    public Category findIncomeCategoryById (Long id) {
        if(incomeCategories == null) {
            return new Category();
        }
        for(Category category: incomeCategories) {
            if(category.getId().equals(id)) {
                return category;
            }
        }
        return new Category();
    }

    public Category findExpenseCategoryById (Long id) {
        if(expenseCategories == null) {
            return new Category();
        }
        for(Category category: expenseCategories) {
            if(category.getId().equals(id)) {
                return category;
            }
        }
        return new Category();
    }

    public BankAccount findBankAccountById (Long id) {
        if(bankAccounts == null) {
            return new BankAccount();
        }
        for(BankAccount bankAccount: bankAccounts) {
            if(bankAccount.getId().equals(id)) {
                return bankAccount;
            }
        }
        return new BankAccount();
    }

    public AccountMember findMemberById(Long id) {
        if(membersList == null) {
            return new AccountMember();
        }
        for(AccountMember member: membersList) {
            if(member.getId().equals(id)) {
                return member;
            }
        }
        return new AccountMember();
    }

    public static ApplicationContext getInstance() {
        if (instance == null) {
            instance = new ApplicationContext();
        }
        return instance;
    }

    public static void clearContext() {
        instance = null;
    }

}
