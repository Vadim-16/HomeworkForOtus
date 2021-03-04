package ca.study.purpose.JdbcTemplate;

import ca.study.purpose.DBClasses.Id;
import ca.study.purpose.Executor.MyDbExecutor;


import javax.sql.DataSource;
import java.lang.reflect.*;
import java.sql.*;
import java.util.*;

public class JdbcTemplate<T> implements JdbcTemplateInterface<T> {
    private final DataSource myDataSource;

    public JdbcTemplate(DataSource myDataSource) {
        this.myDataSource = myDataSource;
    }

    @Override
    public void create(T obj) {
        Class<?> clazz = obj.getClass();
        String clazzName = clazz.getSimpleName();
        Field[] declaredFields = clazz.getDeclaredFields();

        if (getIdAnnotationIndex(declaredFields) < 0) {
            System.out.println("Abort create: no @Id present in " + clazzName);
            return;
        }
        String sql = formInsertStatement(declaredFields, clazzName);
        List<Object> params = formParams(obj, declaredFields);

        try (Connection connection = myDataSource.getConnection()) {
            MyDbExecutor<T> executor = new MyDbExecutor<>(connection);
            long generatedId = executor.executeInsert(sql, params);
            connection.commit();
            System.out.println("Created " + clazzName.toLowerCase() + ": " + generatedId);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void update(T obj) {
        Class<?> clazz = obj.getClass();
        Field[] declaredFields = clazz.getDeclaredFields();
        String tableName = clazz.getSimpleName();

        int idAnnotationIndex = getIdAnnotationIndex(declaredFields);
        if (getIdAnnotationIndex(declaredFields) < 0) {
            System.out.println("Abort update: no @Id present in " + tableName);
            return;
        }

        String sql = formUpdateStatement(declaredFields, tableName);
        List<Object> params = formParams(obj, declaredFields);
        addIdToParams(obj, declaredFields[idAnnotationIndex], params);

        try (Connection connection = myDataSource.getConnection()) {
            MyDbExecutor<T> executor = new MyDbExecutor<>(connection);
            executor.executeUpdate(sql, params);
            connection.commit();
            System.out.println("Updated " + tableName.toLowerCase() + ": " + idAnnotationIndex);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<T> load(long id, Class<T> clazz) {
        String clazzName = clazz.getSimpleName();
        Field[] declaredFields = clazz.getDeclaredFields();
        if (getIdAnnotationIndex(declaredFields) < 0) {
            System.out.println("Abort load: no @Id present in " + clazzName);
            return Optional.empty();
        }
        String sql = formSelectStatement(declaredFields, clazzName);
        try (Connection connection = myDataSource.getConnection()) {
            MyDbExecutor<T> executor = new MyDbExecutor<>(connection);
            Optional<T> createdClass = executor.selectRecord(sql, id, resultSet -> {
                try {
                    if (resultSet.next()) {
                        return makeClassFromDb(clazz, declaredFields, id, resultSet);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return null;
            });
            return createdClass;
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    private String formInsertStatement(Field[] declaredFields, String tableName) {
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
        return String.format("INSERT INTO %s(%s) VALUES (%s)", tableName, columnsName, values);
    }

    private List<Object> formParams(T obj, Field[] declaredFields) {
        List<Object> params = new ArrayList<>();
        for (int i = 0; i < declaredFields.length; i++) {
            Field field = declaredFields[i];
            if (field.isAnnotationPresent(Id.class)) continue;
            field.setAccessible(true);
            try {
                params.add(field.get(obj));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } finally {
                field.setAccessible(false);
            }
        }
        return params;
    }

    private int getIdAnnotationIndex(Field[] declaredFields) {
        for (int i = 0; i < declaredFields.length; i++) {
            if (declaredFields[i].isAnnotationPresent(Id.class)) return i;
        }
        return -1;
    }

    private void addIdToParams(T obj, Field field, List<Object> params) {
        field.setAccessible(true);
        try {
            params.add(field.get(obj));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        field.setAccessible(false);
    }

    private String formUpdateStatement(Field[] declaredFields, String tableName) {
        StringBuilder setValues = new StringBuilder();
        String idFieldName = "";
        for (int i = 0; i < declaredFields.length; i++) {
            Field field = declaredFields[i];
            if (field.isAnnotationPresent(Id.class)) {
                idFieldName = field.getName();
                continue;
            }
            setValues.append(field.getName()).append(" = ?, ");
        }
        setValues.delete(setValues.length() - 2, setValues.length());
        return String.format("UPDATE %s SET %s WHERE %s = ?", tableName, setValues, idFieldName);
    }

    private T makeClassFromDb(Class<T> clazz, Field[] declaredFields, long id, ResultSet resultSet) {
        T t = null;
        try {
            t = clazz.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        for (Field declaredField : declaredFields) {
            putIntoObjField(t, id, declaredField, resultSet);
        }
        return t;
    }

    private void putIntoObjField(T t, long id, Field field, ResultSet resultSet) {
        String fieldName = field.getName();
        field.setAccessible(true);
        try {
            if (field.isAnnotationPresent(Id.class)) {
                field.set(t, id);
                return;
            }
            Object o = field.get(t);
            if (field.getType().isInstance("")) {
                field.set(t, resultSet.getString(fieldName));
            } else if (o instanceof Byte) {
                field.set(t, resultSet.getByte(fieldName));
            } else if (o instanceof Short) {
                field.set(t, resultSet.getShort(fieldName));
            } else if (o instanceof Integer) {
                field.set(t, resultSet.getInt(fieldName));
            } else if (o instanceof Long) {
                field.set(t, resultSet.getLong(fieldName));
            } else if (o instanceof Float) {
                field.set(t, resultSet.getFloat(fieldName));
            } else if (o instanceof Double) {
                field.set(t, resultSet.getDouble(fieldName));
            } else if (o instanceof Boolean) {
                field.set(t, resultSet.getBoolean(fieldName));
            }
        } catch (IllegalAccessException | SQLException e) {
            e.printStackTrace();
        } finally {
            field.setAccessible(false);
        }
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
        return String.format("SELECT %s FROM %s WHERE %s = ?", columnsName, clazzName, id);
    }
}
