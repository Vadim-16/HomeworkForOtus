package ca.study.purpose.Executor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface DbExecutor<T> {
    long executeInsert(String sql, List<Object> params) throws SQLException;
    void executeUpdate(String sql, List<Object> params) throws SQLException;
    Optional<T> selectRecord(String sql, long id, Function<ResultSet, T> rsHandler) throws SQLException;
}
