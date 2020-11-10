package com.rusakovich.bsuir.server.controller;

import java.util.Map;

public interface Controller {
    String request(Map<String, String> params);
}
