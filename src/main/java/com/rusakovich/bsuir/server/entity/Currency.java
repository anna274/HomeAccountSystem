package com.rusakovich.bsuir.server.entity;

import java.util.Objects;

public class Currency {
    private Long id;
    private String name;
    private String code;
    private String shortName;

    public Currency() {
    }

    public Currency(Long id, String name, String code, String shortName) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.shortName = shortName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Currency currency = (Currency) o;
        return Objects.equals(id, currency.id) &&
                Objects.equals(name, currency.name) &&
                Objects.equals(code, currency.code) &&
                Objects.equals(shortName, currency.shortName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, code, shortName);
    }

    @Override
    public String toString() {
        return "{id=" + id +
                "&name=" + name +
                "&code=" + code +
                "&shortName='" + shortName+"}";
    }
}
