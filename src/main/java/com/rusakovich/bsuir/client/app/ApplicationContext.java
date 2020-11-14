package com.rusakovich.bsuir.client.app;

import com.rusakovich.bsuir.server.entity.Account;
import com.rusakovich.bsuir.server.entity.AccountMember;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class ApplicationContext {
    private static ApplicationContext instance;
    private Account currentAccount;
    private ArrayList<AccountMember> membersList = new ArrayList<>();

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
