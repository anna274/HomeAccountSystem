package com.rusakovich.bsuir.client.app;

import com.rusakovich.bsuir.server.entity.*;

import java.util.ArrayList;

public class ApplicationContext {
    private static ApplicationContext instance;
    private Account currentAccount;
    private ArrayList<Account> accountsList = new ArrayList<>();
    private ArrayList<AccountMember> membersList = new ArrayList<>();
    private ArrayList<Currency> currencies = new ArrayList<>();
    private ArrayList<BankAccount> bankAccounts = new ArrayList<>();
    private ArrayList<Category> incomeCategories = new ArrayList<>();
    private ArrayList<Category> expenseCategories = new ArrayList<>();

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
