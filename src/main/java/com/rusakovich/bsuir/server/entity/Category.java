package com.rusakovich.bsuir.server.entity;

import java.util.Map;
import java.util.Objects;

public class Category {
    private Long id;
    private String name;

    public Category() {
    }

    public Category(Long id, String name) {
        this.id = id;
        this.name = name;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category that = (Category) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "id:" + id +
                ",name:" + name;
    }

    public static Category fromMap(Map<String, String> params){
        Category category = new Category();
        if(params.get("id") != null) {
            category.setId(Long.parseLong(params.get("id")));
        }
        category.setName(params.get("name"));
        return category;
    }
}
