package ca.study.purpose.JdbcTemplate;

import ca.study.purpose.DBClasses.Id;
import ca.study.purpose.DBClasses.User;
import ca.study.purpose.Executor.MyDbExecutor;


import javax.sql.DataSource;
import java.lang.reflect.*;
import java.sql.*;
import java.util.*;
import java.util.function.Function;

public class JdbcTemplate<T> implements JdbcTemplateInterface<T> {
    private final DataSource myDataSource;

    public JdbcTemplate(DataSource myDataSource) {
        this.myDataSource = myDataSource;
    }

    public void create(T obj) {
        Class<?> clazz = obj.getClass();
        String clazzName = clazz.getSimpleName();
        Field[] declaredFields = clazz.getDeclaredFields();

        if (!idAnnotationCheck(declaredFields)) {
            System.out.println("Abort create: no @Id present in " + clazzName);
            return;
        }
        String sql = formInsertStatement(obj, declaredFields, clazzName);
        List<Object> params = formInsertParams(obj, declaredFields);

        try (Connection connection = myDataSource.getConnection()) {
            MyDbExecutor<T> executor = new MyDbExecutor<>(connection);
            long generatedId = executor.executeDBStatement(sql, params);
            connection.commit();
            System.out.println("Created " + clazzName.toLowerCase() + ": " + generatedId);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private String formInsertStatement(T obj, Field[] declaredFields, String clazzName) {
        StringBuilder columnsName = new StringBuilder();
        StringBuilder values = new StringBuilder();

        for (int i = 0; i < declaredFields.length; i++) {
            Field field = declaredFields[i];
            if (field.isAnnotationPresent(Id.class)) continue;
            columnsName.append(field.getName()).append(",");
            values.append("?").append(",");
        }
        columnsName.deleteCharAt(columnsName.length() - 1);
        values.deleteCharAt(values.length() - 1);
        return String.format("INSERT INTO %s(%s) VALUES (%s)", clazzName, columnsName, values);
    }

    private List<Object> formInsertParams(T obj, Field[] declaredFields) {
        List<Object> params = new ArrayList<>();
        for (int i = 0; i < declaredFields.length; i++) {
            Field field = declaredFields[i];
            if (field.isAnnotationPresent(Id.class)) continue;
            field.setAccessible(true);
            try {
                params.add(field.get(obj));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            field.setAccessible(false);
        }
        return params;
    }

    private boolean idAnnotationCheck(Field[] declaredFields) {
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
        String clazzName = clazz.getSimpleName();
        Field[] declaredFields = clazz.getDeclaredFields();

        if (!idAnnotationCheck(declaredFields)) {
            System.out.println("Abort load: no @Id present in " + clazzName);
            return Optional.empty();
        }

        String sql = formSelectStatement(declaredFields, clazzName);
        System.out.println(sql);
        try (Connection connection = myDataSource.getConnection()) {
            MyDbExecutor<T> executor = new MyDbExecutor<>(connection);
            executor.selectRecord(sql, id, resultSet -> {
                        try {
                            if (resultSet.next()) {
                                ResultSetMetaData metaData = resultSet.getMetaData();
                                System.out.println(metaData.getColumnLabel(1));
                                Object object = resultSet.getObject(1);
                                System.out.println(object);

                                Constructor<?>[] declaredConstructors = clazz.getDeclaredConstructors();
                                T instance = null;
                                for (Constructor<?> constructor: declaredConstructors) {


                                }

                                return null;
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                return null;
                    });
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
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

    private Optional<T> makeClassFromDb(Class<T> clazz, Field[] declaredFields, ResultSet resultSet) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<?>[] declaredConstructors = clazz.getDeclaredConstructors();



        for (int i = 0; i < declaredFields.length; i++) {
            Field field = declaredFields[i];
            field.setAccessible(true);

        }

        return Optional.empty();
    }

    private String formSelectStatement(Field[] declaredFields, String clazzName) {
        StringBuilder columnsName = new StringBuilder();
        String id = "";
        for (int i = 0; i < declaredFields.length; i++) {
            Field field = declaredFields[i];
            if (field.isAnnotationPresent(Id.class)) {
                id = field.getName();
                continue;
            }
            columnsName.append(field.getName()).append(",");
        }
        columnsName.deleteCharAt(columnsName.length() - 1);
        return String.format("SELECT (%s) FROM %s WHERE %s = ?", columnsName, clazzName, id);
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

