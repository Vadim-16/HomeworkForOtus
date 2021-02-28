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

    public long create(T obj) throws SQLException{
        Class<?> clazz = obj.getClass();
        Field[] declaredFields = getFields(clazz);
        String tableName = clazz.getSimpleName();
        String fieldName1 = declaredFields[1].getName();
        String fieldName2 = declaredFields[2].getName();

        Savepoint savePoint = this.connection.setSavepoint("savePointName");

        try (PreparedStatement pst = connection.prepareStatement(String.format("INSERT INTO %s(%s, %s) VALUES (?, ?)",
                tableName, fieldName1, fieldName2), Statement.RETURN_GENERATED_KEYS)) {
            for (int i = 1; i < declaredFields.length; i++) {
                declaredFields[i].setAccessible(true);
                if (declaredFields[i].get(obj) instanceof String) {
                    pst.setString(i, (String) declaredFields[i].get(obj));
                } else pst.setLong(i, (Long) declaredFields[i].get(obj));
                declaredFields[i].setAccessible(false);
            }
            pst.executeUpdate();
            ResultSet rs = pst.getGeneratedKeys();
            rs.next();
            int userId = rs.getInt(1);
            connection.commit();
            System.out.println("created " + tableName.toLowerCase() + ":" + userId);
            return userId;
        } catch (IllegalAccessException ex) {
            this.connection.rollback(savePoint);
            System.out.println(ex.getMessage());
            throw new RuntimeException();
        }
    }

    @Override
    public void update(T obj) throws SQLException {
        Class<?> clazz = obj.getClass();
        Field[] declaredFields = getFields(clazz);
        String tableName = clazz.getSimpleName();
        String fieldName0 = declaredFields[0].getName();
        String fieldName1 = declaredFields[1].getName();
        String fieldName2 = declaredFields[2].getName();

        Savepoint savePoint = this.connection.setSavepoint("savePointName");

        try (PreparedStatement pst = connection.prepareStatement(String.format("UPDATE %s SET %s = ?, %s = ? WHERE %s = ?",
                tableName, fieldName1, fieldName2, fieldName0), Statement.NO_GENERATED_KEYS)) {
            long id = 0;
            for (int i = 0; i < declaredFields.length; i++) {
                declaredFields[i].setAccessible(true);
                if (i == 0) {
                    id = (Long) declaredFields[i].get(obj);
                    pst.setLong(declaredFields.length, id);
                }else if (declaredFields[i].get(obj) instanceof String) {
                    pst.setString(i, (String) declaredFields[i].get(obj));
                } else pst.setLong(i, (Long) declaredFields[i].get(obj));
                declaredFields[i].setAccessible(false);
            }
            pst.executeUpdate();
            connection.commit();
            System.out.println("updated " + tableName.toLowerCase() + ":" + id);
        } catch (IllegalAccessException e) {
            this.connection.rollback(savePoint);
            System.out.println(e.getMessage());
            throw new RuntimeException();
        }

    }

    public Optional<T> load(long id, Class<T> clazz) {
        Field[] declaredFields = getFields(clazz);
        String tableName = clazz.getSimpleName();
        String fieldName0 = declaredFields[0].getName();
        String fieldName1 = declaredFields[1].getName();
        String fieldName2 = declaredFields[2].getName();
        try {
            Optional<T> user = selectRecord(String.format("SELECT %s, %s, %s FROM %s WHERE %s  = ?",
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
            return user;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
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

    private Optional<T> selectRecord(String sql, long id, Function<ResultSet, T> rsHandler) throws SQLException {
        try (PreparedStatement pst = this.connection.prepareStatement(sql)) {
            pst.setLong(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                return Optional.ofNullable(rsHandler.apply(rs));
            }
        }
    }
}
