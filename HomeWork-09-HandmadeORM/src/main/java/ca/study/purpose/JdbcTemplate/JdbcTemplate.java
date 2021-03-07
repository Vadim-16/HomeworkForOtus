package ca.study.purpose.JdbcTemplate;

import java.util.Optional;


public interface JdbcTemplate<T> {
    void create(T obj);
    void update(T obj);
    Optional<T> load(long id, Class<T> clazz);
}
