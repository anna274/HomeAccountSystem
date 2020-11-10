package com.rusakovich.bsuir.server.entity;

import java.time.LocalDateTime;
import java.util.Objects;

public class Income {
    private Long id;
    private Long categoryId;
    private Long memberId;
    private Long memberAccountId;
    private Long bankAccountId;
    private Long currencyId;
    private Float sum;
    private String note;
    private int quantity;
    private LocalDateTime date;

    public Income() {
    }

    public Income(Long id,
                  Long categoryId,
                  Long memberId,
                  Long memberAccountId,
                  Long bankAccountId,
                  Long currencyId,
                  Float sum,
                  String note,
                  int quantity,
                  LocalDateTime date) {
        this.id = id;
        this.categoryId = categoryId;
        this.memberId = memberId;
        this.memberAccountId = memberAccountId;
        this.bankAccountId = bankAccountId;
        this.currencyId = currencyId;
        this.sum = sum;
        this.note = note;
        this.quantity = quantity;
        this.date = date;
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

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getBankAccountId() {
        return bankAccountId;
    }

    public void setBankAccountId(Long bankAccountId) {
        this.bankAccountId = bankAccountId;
    }

    public Long getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Long currencyId) {
        this.currencyId = currencyId;
    }

    public Float getSum() {
        return sum;
    }

    public void setSum(Float sum) {
        this.sum = sum;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Income income = (Income) o;
        return quantity == income.quantity &&
                Objects.equals(id, income.id) &&
                Objects.equals(categoryId, income.categoryId) &&
                Objects.equals(memberId, income.memberId) &&
                Objects.equals(bankAccountId, income.bankAccountId) &&
                Objects.equals(currencyId, income.currencyId) &&
                Objects.equals(sum, income.sum) &&
                Objects.equals(note, income.note) &&
                Objects.equals(date, income.date);
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
