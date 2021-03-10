package ca.study.purpose;

import java.util.Optional;

public interface UserDao<T> {
    void create(T obj);
    void update(T obj);
    Optional<T> load(long id, Class<T> clazz);
}
