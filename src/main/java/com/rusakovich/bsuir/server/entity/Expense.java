package com.rusakovich.bsuir.server.entity;

import java.time.LocalDateTime;
import java.util.Objects;

public class Expense {
    private Long id;
    private Long categoryId;
    private Long memberId;
    private Long memberAccountId;
    private Long bankAccountId;
    private Long currencyId;
    private Float sum;
    private String note;
    private int quantity;
    private LocalDateTime date = LocalDateTime.now();

    public Expense() {
    }

    public Expense(Long id,
                   Long categoryId,
                   Long memberId,
                   Long memberAccountId,
                   Long bankAccountId,
                   Long currencyId,
                   Float sum,
                   int quantity,
                   LocalDateTime date,
                   String note) {
        this.id = id;
        this.categoryId = categoryId;
        this.memberId = memberId;
        this.memberAccountId = memberAccountId;
        this.bankAccountId = bankAccountId;
        this.currencyId = currencyId;
        this.sum = sum;
        this.quantity = quantity;
        this.date = date;
        this.note = note;
    }

    public Long getMemberAccountId() {
        return memberAccountId;
    }

    public void setMemberAccountId(Long memberAccountId) {
        this.memberAccountId = memberAccountId;
    }

    public Float getSum() {
        return sum;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public int getQuantity() {
        return quantity;
    }

    public Long getId() {
        return id;
    }

    public Long getCategory() {
        return categoryId;
    }

    public String getNote() {
        return note;
    }

    public Long getMemberId() {
        return memberId;
    }

    public Long getBankAccountId() {
        return bankAccountId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public Long getCurrencyId() {
        return currencyId;
    }

    public void setCategory(Long categoryId) {
        this.categoryId = categoryId;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setSum(Float sum) {
        this.sum = sum;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public void setBankAccountId(Long bankAccountId) {
        this.bankAccountId = bankAccountId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public void setCurrencyId(Long currencyId) {
        this.currencyId = currencyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Expense expense = (Expense) o;
        return quantity == expense.quantity &&
                Objects.equals(id, expense.id) &&
                Objects.equals(categoryId, expense.categoryId) &&
                Objects.equals(memberId, expense.memberId) &&
                Objects.equals(bankAccountId, expense.bankAccountId) &&
                Objects.equals(currencyId, expense.currencyId) &&
                Objects.equals(sum, expense.sum) &&
                Objects.equals(note, expense.note) &&
                Objects.equals(date, expense.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, categoryId, memberId, bankAccountId, currencyId, sum, note, quantity, date);
    }

    @Override
    public String toString() {
        return "{id=" + id +
                "&categoryId=" + categoryId +
                "&sum=" + sum +
                "&note=" + note +
                "&quantity=" + quantity +
                "&date=" + date +
                "&memberId=" + memberId +
                "&memberAccountId=" + memberAccountId +
                "&currencyId=" + currencyId +
                "&bankAccountId=" + bankAccountId+"}";
    }
}

