package com.rusakovich.bsuir.server.entity;

import javafx.scene.control.CheckBox;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
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
    private LocalDate date;
    private CheckBox selected;

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
                   LocalDate date,
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

    public LocalDate getDate() {
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

    public void setDate(LocalDate date) {
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

    public CheckBox getSelected() {
        return selected;
    }

    public void setSelected(CheckBox selected) {
        this.selected = selected;
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MM yyyy");
        String str = "id:" + id +
                ",categoryId:" + categoryId +
                ",sum:" + sum +
                ",quantity:" + quantity +
                ",date:" + date.format(formatter) +
                ",memberId:" + memberId +
                ",memberAccountId:" + memberAccountId +
                ",currencyId:" + currencyId +
                ",bankAccountId:" + bankAccountId;
        if(note != null) {
            str += ",note:" + note;
        }
        return str;
    }

    public static Expense fromMap(Map<String, String> params){
        Expense expense = new Expense();
        if(params.get("id") != null) {
            expense.setId(Long.parseLong(params.get("id")));
        }
        expense.setBankAccountId(Long.parseLong(params.get("bankAccountId")));
        expense.setCategoryId(Long.parseLong(params.get("categoryId")));
        expense.setCurrencyId(Long.parseLong(params.get("currencyId")));
        expense.setMemberId(Long.parseLong(params.get("memberId")));
        expense.setMemberAccountId(Long.parseLong(params.get("memberAccountId")));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MM yyyy");
        expense.setDate(LocalDate.parse(params.get("date"), formatter));
        if(params.get("note") != null) {
            expense.setNote(params.get("note"));
        }
        if(params.get("sum") != null) {
            expense.setSum(Float.parseFloat(params.get("sum")));
        }
        return expense;
    }
}

