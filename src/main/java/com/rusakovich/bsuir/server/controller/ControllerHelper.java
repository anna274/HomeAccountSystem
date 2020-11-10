package com.rusakovich.bsuir.server.controller;

import java.util.ArrayList;

public class ControllerHelper {
    public static String getResponse(String status, String data, String error) {
        ArrayList<String> response = new ArrayList<String>();
        if (isNotEmpty(status)) {
            response.add("status=" + status);
        }
        if (isNotEmpty(data)) {
            response.add(data);
        }
        if (isNotEmpty(error)) {
            response.add("error=" + error);
        }
        return String.join("&", response);
    }

    private static boolean isNotEmpty(String value) {
        return value != null && !"".equals(value);
    }
}
