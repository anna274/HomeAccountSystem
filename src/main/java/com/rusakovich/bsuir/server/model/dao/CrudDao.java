package com.rusakovich.bsuir.server.model.dao;

import java.sql.SQLException;
import java.util.List;

public interface CrudDao<T> {
    void save(T entity) throws SQLException;

    T findById(Long id);

    List<T> findAll();

    void update(T entity) throws SQLException;

    void delete(Long id) throws SQLException;
}
