package com.rusakovich.bsuir.server.entity;

import javafx.scene.control.CheckBox;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Objects;

public class Income{
    private Long id;
    private Long categoryId;
    private Long memberId;
    private Long memberAccountId;
    private Long bankAccountId;
    private Long currencyId;
    private Float sum = 0F;
    private String note;
    private int quantity = 0;
    private LocalDate date;

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
                  LocalDate date) {
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MM yyyy");
        String str ="id:" + id +
                ",categoryId:" + categoryId +
                ",sum:" + sum +
                ",quantity:" + quantity +
                ",date:" + date.format(formatter) +
                ",memberId:" + memberId +
                ",memberAccountId:" + memberAccountId +
                ",currencyId:" + currencyId +
                ",bankAccountId:" + bankAccountId;
        if(note != null) {
            System.out.println("not null: " + note);
            str += ",note:" + note;
        }
        return str;
    }

    public static Income fromMap(Map<String, String> params){
        Income income = new Income();
        if(params.get("id") != null) {
            income.setId(Long.parseLong(params.get("id")));
        }
        income.setBankAccountId(Long.parseLong(params.get("bankAccountId")));
        income.setCategoryId(Long.parseLong(params.get("categoryId")));
        income.setCurrencyId(Long.parseLong(params.get("currencyId")));
        income.setMemberId(Long.parseLong(params.get("memberId")));
        income.setMemberAccountId(Long.parseLong(params.get("memberAccountId")));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MM yyyy");
        income.setDate(LocalDate.parse(params.get("date"), formatter));
        if(params.get("note") != null) {
            income.setNote(params.get("note"));
        }
        if(params.get("sum") != null) {
            income.setSum(Float.parseFloat(params.get("sum")));
        }
        return income;
    }
}
