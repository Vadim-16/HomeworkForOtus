package ca.study.purpose;

import com.google.gson.Gson;

import javax.json.*;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Map;


public class MySecondJSON {

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        MyJSON.SomeClass class1 = new MyJSON.SomeClass();
        Gson gson = new Gson();

        String fromGSON = gson.toJson(class1);
        System.out.println(fromGSON);

        JsonObject fromMySecondJson = toMySecondJson(class1);
        System.out.println(fromMySecondJson);

        System.out.println("fromGSON = fromMySecondJson ? " + fromGSON.equals(fromMySecondJson.toString()));

        MyJSON.SomeClass class2 = gson.fromJson(fromGSON, MyJSON.SomeClass.class);
        System.out.println("class1 = class2 ? " + class1.equals(class2));

        System.out.println(class1);
        System.out.println(class2);
    }

    public static JsonObject toMySecondJson(Object obj) throws IllegalAccessException, NoSuchFieldException {
        JsonObjectBuilder jsonObjBuilder = Json.createObjectBuilder();

        if (obj instanceof String) {
            jsonObjBuilder.add(obj.toString(), obj.toString());
        } else if (obj instanceof Integer || obj instanceof Short) {
            jsonObjBuilder.add(obj.toString(), (int) obj);
        } else if (obj instanceof Long || obj instanceof Byte || obj instanceof Character) {
            jsonObjBuilder.add(obj.toString(), (long) obj);
        } else if (obj instanceof Double || obj instanceof Float) {
            jsonObjBuilder.add(obj.toString(), (double) obj);
        } else if (obj instanceof Boolean) {
            jsonObjBuilder.add(obj.toString(), (boolean) obj);
        } else if (obj instanceof BigDecimal) {
            jsonObjBuilder.add(obj.toString(), (BigDecimal) obj);
        } else if (obj instanceof BigInteger) {
            jsonObjBuilder.add(obj.toString(), (BigInteger) obj);
        } else if (obj.getClass().isArray() || obj instanceof Collection) {
            jsonObjBuilder.add(obj.toString(), navigateArray(obj));
        } else if (obj instanceof Map) {
            jsonObjBuilder.add(obj.toString(), navigateMap(obj));
        } else {
            Class<?> aClazz = obj.getClass();
            Field[] fields = aClazz.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                String fieldName = field.getName();
                Object fieldObj = field.get(obj);

                if (fieldObj instanceof String) {
                    jsonObjBuilder.add(fieldName, fieldObj.toString());
                } else if (fieldObj instanceof Integer || fieldObj instanceof Short) {
                    jsonObjBuilder.add(fieldName, (int) fieldObj);
                } else if (fieldObj instanceof Long || fieldObj instanceof Byte || fieldObj instanceof Character) {
                    jsonObjBuilder.add(fieldName, (long) fieldObj);
                } else if (fieldObj instanceof Double || fieldObj instanceof Float) {
                    jsonObjBuilder.add(fieldName, (double) fieldObj);
                } else if (fieldObj instanceof Boolean) {
                    jsonObjBuilder.add(fieldName, (boolean) fieldObj);
                } else if (fieldObj instanceof BigDecimal) {
                    jsonObjBuilder.add(fieldName, (BigDecimal) fieldObj);
                } else if (fieldObj instanceof BigInteger) {
                    jsonObjBuilder.add(fieldName, (BigInteger) fieldObj);
                } else if (field.getType().isArray() || fieldObj instanceof Collection) {
                    jsonObjBuilder.add(fieldName, navigateArray(fieldObj));
                } else if (fieldObj instanceof Map) {
                    jsonObjBuilder.add(fieldName, navigateMap(fieldObj));
                } else {
                    jsonObjBuilder.add(fieldName, toMySecondJson(fieldObj));
                }
                field.setAccessible(false);
            }
        }
        return jsonObjBuilder.build();
    }

    private static JsonArray navigateArray(Object fieldObj) throws IllegalAccessException, NoSuchFieldException {
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        if (fieldObj instanceof Collection) {
            fieldObj = ((Collection<?>) fieldObj).toArray();
        }

        for (int i = 0; i < Array.getLength(fieldObj); i++) {
            Object arrayObj = Array.get(fieldObj, i);
            if (arrayObj instanceof String) {
                arrayBuilder.add((String) arrayObj);
            } else if (arrayObj instanceof Integer || fieldObj instanceof Short) {
                arrayBuilder.add((int) arrayObj);
            } else if (arrayObj instanceof Long || fieldObj instanceof Byte || fieldObj instanceof Character) {
                arrayBuilder.add((long) arrayObj);
            } else if (arrayObj instanceof Double || fieldObj instanceof Float) {
                arrayBuilder.add((double) arrayObj);
            } else if (arrayObj instanceof Boolean) {
                arrayBuilder.add((boolean) arrayObj);
            } else if (arrayObj instanceof BigDecimal) {
                arrayBuilder.add((BigDecimal) arrayObj);
            } else if (arrayObj instanceof BigInteger) {
                arrayBuilder.add((BigInteger) arrayObj);
            } else if (arrayObj.getClass().isArray()) {
                arrayBuilder.add(navigateArray(arrayObj));
            } else {
                arrayBuilder.add(toMySecondJson(arrayObj));
            }
        }
        return arrayBuilder.build();
    }

    private static JsonObjectBuilder navigateMap(Object fieldObj) throws IllegalAccessException, NoSuchFieldException {
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        if (fieldObj instanceof Map) {

            for (Map.Entry<?, ?> entry : ((Map<?, ?>) fieldObj).entrySet()) {
                Object key = entry.getKey();
                Object value = entry.getValue();
                if (key == null) key = "null";
                if (value == null) continue;

                if (value instanceof String) {
                    objectBuilder.add(key.toString(), (String) value);
                } else if (value instanceof Integer || fieldObj instanceof Short) {
                    objectBuilder.add(key.toString(), (int) value);
                } else if (value instanceof Long || fieldObj instanceof Byte || fieldObj instanceof Character) {
                    objectBuilder.add(key.toString(), (long) value);
                } else if (value instanceof Double || fieldObj instanceof Float) {
                    objectBuilder.add(key.toString(), (double) value);
                } else if (value instanceof Boolean) {
                    objectBuilder.add(key.toString(), (Boolean) value);
                } else if (value instanceof BigDecimal) {
                    objectBuilder.add(key.toString(), (BigDecimal) value);
                } else if (value instanceof BigInteger) {
                    objectBuilder.add(key.toString(), (BigInteger) value);
                } else if (value.getClass().isArray() || fieldObj instanceof Collection) {
                    objectBuilder.add(key.toString(), navigateArray(value));
                } else {
                    objectBuilder.add(key.toString(), toMySecondJson(value));
                }
            }
        }
        return objectBuilder;
    }

}
