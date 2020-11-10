package com.rusakovich.bsuir.server.entity;

public enum AccountRole {
    USER(0),
    ADMIN(1);

    private final int roleId;

    AccountRole(int roleId) {
        this.roleId = roleId;
    }

    public int getRoleId() {
        return roleId;
    }

    public static AccountRole getAccountRole(int roleId) {
        for (AccountRole value : AccountRole.values()) {
            if (value.roleId == roleId) {
                return value;
            }
        }
        return USER;
    }
}
