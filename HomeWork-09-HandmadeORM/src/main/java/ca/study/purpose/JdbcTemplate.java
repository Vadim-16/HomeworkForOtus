package ca.study.purpose;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.*;
import java.util.function.Function;

public class JdbcTemplate<T> implements DbExecutorInterface<T> {
    private final Connection connection;

    public JdbcTemplate(Connection connection) {
        this.connection = connection;
    }

    long createUser(T obj) throws SQLException, IllegalAccessException {
        Class<?> clazz = obj.getClass();
        Field[] declaredFields = getFields(clazz);

        String tableName = clazz.getSimpleName();
        String fieldName1 = declaredFields[1].getName();
        String fieldName2 = declaredFields[2].getName();
        Savepoint savePoint = this.connection.setSavepoint("savePointName");

        try (PreparedStatement pst = connection.prepareStatement(String.format("insert into %s(%s, %s) values (?, ?)",
                tableName, fieldName1, fieldName2), Statement.RETURN_GENERATED_KEYS)) {

            for (int i = 1; i < declaredFields.length; i++) {
                declaredFields[i].setAccessible(true);
                if (declaredFields[i].get(obj) instanceof String) {
                    pst.setString(i, (String) declaredFields[i].get(obj));
                } else pst.setLong(i, (Long) declaredFields[i].get(obj));
                declaredFields[i].setAccessible(false);
            }
            pst.executeUpdate();
            try (ResultSet rs = pst.getGeneratedKeys()) {
                rs.next();
                int userId = rs.getInt(1);
                connection.commit();
                return userId;
            }
        } catch (SQLException ex) {
            this.connection.rollback(savePoint);
            System.out.println(ex.getMessage());
            throw ex;
        }
    }

    <T> T load(long id, Class<T> clazz) {
        Field[] declaredFields = getFields(clazz);
        String tableName = clazz.getSimpleName();
        String fieldName0 = declaredFields[0].getName();
        String fieldName1 = declaredFields[1].getName();
        String fieldName2 = declaredFields[2].getName();
        try {
            Optional<T> user = selectRecord(String.format("select %s, %s, %s from %s where %s  = ?",
                    fieldName0, fieldName1, fieldName2, tableName, fieldName0),
                    id, resultSet -> {
                        try {
                            if (resultSet.next()) {
                                return clazz.getDeclaredConstructor(long.class, String.class, long.class).
                                        newInstance(resultSet.getLong(fieldName0),
                                                resultSet.getString(fieldName1),
                                                resultSet.getLong(fieldName2));
                            }
                        } catch (SQLException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                            e.printStackTrace();
                        }
                        return null;
                    });

//            try (PreparedStatement pst = this.connection.prepareStatement(sql)) {
//                pst.setLong(1, id);
//                try (ResultSet rs = pst.executeQuery()) {
//                    return Optional.ofNullable(rsHandler.apply(rs));
//                }
//            }

            System.out.println("user:" + user);
            return user;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        return null;
    }

    private <T> Field[] getFields(Class<T> clazz) {
        Field[] declaredFields = clazz.getDeclaredFields();
        if (!validate(declaredFields)) {
            System.out.println("No @Id present in " + clazz.getSimpleName());
            return null;
        }
        return declaredFields;
    }

    private boolean validate(Field[] declaredFields) {
        for (Field field : declaredFields) {
            if (field.isAnnotationPresent(Id.class)) return true;
        }
        return false;
    }

    @Override
    public long insertRecord(String sql, List<String> params) throws SQLException {
        Savepoint savePoint = this.connection.setSavepoint("savePointName");
        try (PreparedStatement pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            for (int idx = 0; idx < params.size(); idx++) {
                pst.setString(idx + 1, params.get(idx));
            }
            pst.executeUpdate();
            try (ResultSet rs = pst.getGeneratedKeys()) {
                rs.next();
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            this.connection.rollback(savePoint);
            System.out.println(ex.getMessage());
            throw ex;
        }
    }

    @Override
    public Optional<T> selectRecord(String sql, long id, Function<ResultSet, T> rsHandler) throws SQLException {
        try (PreparedStatement pst = this.connection.prepareStatement(sql)) {
            pst.setLong(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                return Optional.ofNullable(rsHandler.apply(rs));
            }
        }
    }
}
