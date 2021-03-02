package ca.study.purpose.JdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface JdbcTemplateInterface<T> {
    void create(T obj) throws SQLException, IllegalAccessException;
    void update(T obj) throws SQLException;
    Optional<T> load(long id, Class<T> clazz);
}
