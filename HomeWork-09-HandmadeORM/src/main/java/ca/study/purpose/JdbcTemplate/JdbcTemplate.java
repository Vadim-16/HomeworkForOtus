package ca.study.purpose.JdbcTemplate;

import ca.study.purpose.DBClasses.Id;
import ca.study.purpose.Executor.MyDbExecutor;


import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;
import java.util.function.Function;

public class JdbcTemplate<T> implements JdbcTemplateInterface<T> {
    private final MyDbExecutor<T> executor;
    private final MyDataSource myDataSource;

    public JdbcTemplate(MyDbExecutor<T> executor, MyDataSource myDataSource) {
        this.executor = executor;
        this.myDataSource = myDataSource;
    }

    public void create(T obj) throws SQLException, IllegalAccessException {
        checkIdAnnotation(obj);
        String sql = formInsertStatement(obj);
        List<Object> params = formInsertParams(obj);

        long l = executor.executeDBStatement(sql, params);
        System.out.println("Created " + obj.getClass().getSimpleName().toLowerCase() + ": " + l);

//        try (PreparedStatement pst = connection.prepareStatement(String.format("INSERT INTO %s(%s, %s) VALUES (?, ?)",
//                tableName, fieldName1, fieldName2), Statement.RETURN_GENERATED_KEYS)) {
//            for (int i = 1; i < declaredFields.length; i++) {
//                declaredFields[i].setAccessible(true);
//                if (declaredFields[i].get(obj) instanceof String) {
//                    pst.setString(i, (String) declaredFields[i].get(obj));
//                } else pst.setLong(i, (Long) declaredFields[i].get(obj));
//                declaredFields[i].setAccessible(false);
//            }
//            pst.executeUpdate();
//            ResultSet rs = pst.getGeneratedKeys();
//            rs.next();
//            int userId = rs.getInt(1);
//            connection.commit();
//            System.out.println("created " + tableName.toLowerCase() + ":" + userId);
//            return userId;
//        } catch (IllegalAccessException ex) {
//            this.connection.rollback(savePoint);
//            System.out.println(ex.getMessage());
//            throw new RuntimeException();
//        }
    }

    private String formInsertStatement(T obj) {
        StringBuilder columnsName = new StringBuilder();
        StringBuilder values = new StringBuilder();
        Class<?> clazz = obj.getClass();
        Field[] declaredFields = clazz.getDeclaredFields();

        for (int i = 0; i < declaredFields.length; i++) {
            Field field = declaredFields[i];
            if (field.isAnnotationPresent(Id.class)) continue;
            columnsName.append(field.getName()).append(",");
            values.append("?").append(",");
        }
        columnsName.deleteCharAt(columnsName.length() - 1);
        values.deleteCharAt(values.length() - 1);
        return String.format("INSERT INTO %s(%s) VALUES (%s)", clazz.getSimpleName(), columnsName, values);
    }

    private List<Object> formInsertParams(T obj) throws IllegalAccessException {
        List<Object> params = new ArrayList<>();
        Class<?> clazz = obj.getClass();
        Field[] declaredFields = clazz.getDeclaredFields();
        for (int i = 0; i < declaredFields.length; i++) {
            Field field = declaredFields[i];
            field.setAccessible(true);
            if (field.isAnnotationPresent(Id.class)) continue;
            params.add(field.get(obj));
            field.setAccessible(false);
        }
        return params;
    }

    private void checkIdAnnotation(T obj) {
        Class<?> clazz = obj.getClass();
        if (!validate(obj.getClass().getDeclaredFields())) {
            System.out.println("No @Id present in " + clazz.getSimpleName());
        }
    }

    private boolean validate(Field[] declaredFields) {
        for (Field field : declaredFields) {
            if (field.isAnnotationPresent(Id.class)) return true;
        }
        return false;
    }

    @Override
    public void update(T obj) {
//        Class<?> clazz = obj.getClass();
//        Field[] declaredFields = getFields(clazz);
//        String tableName = clazz.getSimpleName();
//        String fieldName0 = declaredFields[0].getName();
//        String fieldName1 = declaredFields[1].getName();
//        String fieldName2 = declaredFields[2].getName();
//        Savepoint savePoint = null;
//
//        try (PreparedStatement pst = connection.prepareStatement(String.format("UPDATE %s SET %s = ?, %s = ? WHERE %s = ?",
//                tableName, fieldName1, fieldName2, fieldName0), Statement.NO_GENERATED_KEYS)) {
//            savePoint = this.connection.setSavepoint("savePointName");
//            long id = 0;
//            for (int i = 0; i < declaredFields.length; i++) {
//                declaredFields[i].setAccessible(true);
//                if (i == 0) {
//                    id = (Long) declaredFields[i].get(obj);
//                    pst.setLong(declaredFields.length, id);
//                }else if (declaredFields[i].get(obj) instanceof String) {
//                    pst.setString(i, (String) declaredFields[i].get(obj));
//                } else pst.setLong(i, (Long) declaredFields[i].get(obj));
//                declaredFields[i].setAccessible(false);
//            }
//            pst.executeUpdate();
//            connection.commit();
//            System.out.println("updated " + tableName.toLowerCase() + ":" + id);
//        } catch (IllegalAccessException | SQLException e) {
//            try {
//                this.connection.rollback(savePoint);
//            } catch (SQLException throwables) {
//                throwables.printStackTrace();
//            }
//            System.out.println(e.getMessage());
//            throw new RuntimeException();
//        }

    }

    public Optional<T> load(long id, Class<T> clazz) {
//        Field[] declaredFields = getFields(clazz);
//        String tableName = clazz.getSimpleName();
//        String fieldName0 = declaredFields[0].getName();
//        String fieldName1 = declaredFields[1].getName();
//        String fieldName2 = declaredFields[2].getName();
//        try {
//            Optional<T> user = selectRecord(String.format("SELECT %s, %s, %s FROM %s WHERE %s  = ?",
//                    fieldName0, fieldName1, fieldName2, tableName, fieldName0),
//                    id, resultSet -> {
//                        try {
//                            if (resultSet.next()) {
//                                return clazz.getDeclaredConstructor(long.class, String.class, long.class).
//                                        newInstance(resultSet.getLong(fieldName0),
//                                                resultSet.getString(fieldName1),
//                                                resultSet.getLong(fieldName2));
//                            }
//                        } catch (SQLException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
//                            e.printStackTrace();
//                        }
//                        return null;
//                    });
//            return user;
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            throw new RuntimeException(ex);
//        }
        return null;
    }


    private Optional<T> selectRecord(String sql, long id, Function<ResultSet, T> rsHandler) throws SQLException {
//        try (PreparedStatement pst = this.connection.prepareStatement(sql)) {
//            pst.setLong(1, id);
//            try (ResultSet rs = pst.executeQuery()) {
//                return Optional.ofNullable(rsHandler.apply(rs));
//            }
//        }
        return null;
    }
}

