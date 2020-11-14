package com.rusakovich.bsuir.server.controller.impl;

import com.rusakovich.bsuir.server.controller.Controller;
import com.rusakovich.bsuir.server.controller.ControllerHelper;
import com.rusakovich.bsuir.server.entity.Account;
import com.rusakovich.bsuir.server.entity.AccountMember;
import com.rusakovich.bsuir.server.model.service.impl.AccountMemberServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AccountMemberController implements Controller {
    private final AccountMemberServiceImpl memberService;

    public AccountMemberController(AccountMemberServiceImpl memberService) {
        this.memberService = memberService;
    }

    @Override
    public String request(Map<String, String> params) {
        String command = params.get("command");
        switch (command) {
            case "add": {
                return add(params);
            }
            case "getOne": {
                return getOne(params);
            }
            case "getAll": {
                return getAll();
            }
            case "getAllByAccountId": {
                return getAllByAccountId(params);
            }
            case "update": {
                return update(params);
            }
            case "delete": {
                return delete(params);
            }
            default:
                return "";
        }
    }

    private String add(Map<String, String> params) {
        AccountMember account = memberService.addAccountMember(AccountMember.fromMap(params));
        return ControllerHelper.getResponse("ok", account.toString(), "");
    }

    private String getOne(Map<String, String> params) {
        AccountMember member = memberService.getAccountMemberById(Long.valueOf(params.get("id")));
        return ControllerHelper.getResponse("ok", member.toString(), "");
    }

    private String getAll() {
        StringBuilder response = new StringBuilder();
        List<AccountMember> members = memberService.getAllAccountMembers();
        for (AccountMember member : members) {
            response.append(member.toString());
        }
        return ControllerHelper.getResponse("ok", "[" + response + "]", "");
    }

    private String getAllByAccountId(Map<String, String> params) {
        List<AccountMember> members =
                memberService.getAccountMembersByAccountId(Long.parseLong(params.get("accountId")));
        List<String> membersStr = new ArrayList<String>();
        for (AccountMember member : members) {
            membersStr.add(member.toString());
        }
        String data = String.join(";", membersStr);
        return ControllerHelper.getResponse("ok", data, "");
    }

    private String update(Map<String, String> params) {
        memberService.updateAccountMember(AccountMember.fromMap(params));
        return ControllerHelper.getResponse("ok", "", "");
    }

    private String delete(Map<String, String> params) {
        memberService.deleteAccountMember(Long.valueOf(params.get("id")));
        return ControllerHelper.getResponse("ok", "", "");
    }
}
