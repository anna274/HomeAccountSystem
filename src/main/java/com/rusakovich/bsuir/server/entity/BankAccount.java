package com.rusakovich.bsuir.server.entity;

import java.util.Objects;

public class BankAccount {
    private Long id;
    private String name;
    private Long memberId;
    private Long memberAccountId;
    private Long currencyId;
    private Float expenses;
    private Float income;
    private Float balance;

    public BankAccount() {
    }

    public BankAccount(Long id, String name, Long memberId, Long memberAccountId, Long currencyId, Float expenses, Float income) {
        this.id = id;
        this.name = name;
        this.memberId = memberId;
        this.memberAccountId = memberAccountId;
        this.currencyId = currencyId;
        this.expenses = expenses;
        this.income = income;
        balance = income - expenses;
    }

    public Long getMemberAccountId() {
        return memberAccountId;
    }

    public void setMemberAccountId(Long memberAccountId) {
        this.memberAccountId = memberAccountId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Long currencyId) {
        this.currencyId = currencyId;
    }

    public Float getExpenses() {
        return expenses;
    }

    public void setExpenses(Float expenses) {
        this.expenses = expenses;
    }

    public Float getIncome() {
        return income;
    }

    public void setIncome(Float income) {
        this.income = income;
    }

    public Float getBalance() {
        return balance;
    }

    public void setBalance(Float balance) {
        this.balance = balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BankAccount that = (BankAccount) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(memberId, that.memberId) &&
                Objects.equals(currencyId, that.currencyId) &&
                Objects.equals(expenses, that.expenses) &&
                Objects.equals(income, that.income) &&
                Objects.equals(balance, that.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, memberId, currencyId, expenses, income, balance);
    }

    @Override
    public String toString() {
        return "{id=" + id +
                "&name=" + name +
                "&memberId=" + memberId +
                "&accountId=" + memberAccountId +
                "&currency=" + currencyId +
                "&expenses=" + expenses +
                "&income=" + income +
                "&balance=" + balance + "}";
    }
}
