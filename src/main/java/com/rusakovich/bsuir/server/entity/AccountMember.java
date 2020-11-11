package com.rusakovich.bsuir.server.entity;

import java.util.Map;
import java.util.Objects;

public class AccountMember {
    private Long id;
    private String name;
    private Long accountId;

    public AccountMember() {}

    public AccountMember(Long id, String name, Long accountId) {
        this.id = id;
        this.name = name;
        this.accountId = accountId;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    @Override
    public String toString() {
        return "id:" + id +
                ",name:" + name +
                ",accountId:" + accountId;
    }

    public static AccountMember fromMap(Map<String, String> params){
        AccountMember member = new AccountMember();
        if(params.containsKey("id")) {
            member.setId(Long.parseLong(params.get("id")));
        }
        member.setName(params.get("name"));
        member.setAccountId(Long.parseLong(params.get("accountId")));
        return member;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountMember that = (AccountMember) o;
        return id.equals(that.id) &&
                name.equals(that.name) &&
                accountId.equals(that.accountId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, accountId);
    }


}
