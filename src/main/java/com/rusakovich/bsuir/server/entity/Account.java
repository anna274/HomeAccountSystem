package com.rusakovich.bsuir.server.entity;

import java.util.Map;
import java.util.Objects;

public class Account {
    private Long id;
    private String login;
    private String password;
    private Integer roleId;

    public Account() {
    }

    public Account(Long id, String login, String password, Integer roleId) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.roleId = roleId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(id, account.id) &&
                Objects.equals(login, account.login) &&
                Objects.equals(password, account.password) &&
                roleId == account.roleId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, roleId);
    }

    @Override
    public String toString() {
        return "id:" + id +
                ",login:" + login +
                ",password:" + password +
                ",roleId:" + roleId;
    }

    public static Account fromMap(Map<String, String> params){
        Account account = new Account();
        account.setId(Long.parseLong(params.get("id")));
        account.setLogin(params.get("login"));
        account.setPassword(params.get("password"));
        account.setRoleId(Integer.parseInt(params.get("roleId")));
        return account;
    }
}
