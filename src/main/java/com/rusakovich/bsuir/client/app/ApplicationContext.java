package com.rusakovich.bsuir.client.app;

import com.rusakovich.bsuir.server.entity.Account;

public class ApplicationContext {
    private static ApplicationContext instance;
    private Account currentAccount;

    private ApplicationContext() {
    }

    public Account getCurrentAccount() {
        return currentAccount;
    }

    public void setCurrentAccount(Account currentAccount) {
        this.currentAccount = currentAccount;
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
