package ca.study.purpose;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface DbExecutorInterface<T> {
    long create(T obj) throws SQLException;
    void update(T obj) throws SQLException;
    Optional<T> load(long id, Class<T> clazz);
}
