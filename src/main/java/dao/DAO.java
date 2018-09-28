package dao;

import java.util.List;

public interface DAO<T> {
    T get(String tableName1, String tableName2, int id);

    List<T> getAll(String tableName1, String tableName2);

    void removeAll(String tableName);
}
