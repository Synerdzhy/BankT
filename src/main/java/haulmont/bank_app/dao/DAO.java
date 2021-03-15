package haulmont.bank_app.dao;

import java.util.List;
import java.util.UUID;

public interface DAO<T> {
    T getById(UUID id);
    void save(T t);
    boolean remove(T t);
    List<T> getAll();
}
