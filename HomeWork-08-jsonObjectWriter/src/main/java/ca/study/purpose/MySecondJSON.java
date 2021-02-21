package ca.study.purpose;

import com.google.gson.Gson;
import com.google.gson.internal.Primitives;


import javax.json.*;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Map;


public class MySecondJSON {

    public static void main(String[] args) {
        MyJSON.SomeClass class1 = new MyJSON.SomeClass();
        Gson gson = new Gson();

        String fromGSON = gson.toJson(class1);
        System.out.println(fromGSON);

        String fromMySecondJson = toMySecondJson(class1);
        System.out.println(fromMySecondJson);


        System.out.println("fromGSON = fromMySecondJson ? " + fromGSON.equals(fromMySecondJson));


//        MyJSON.SomeClass class2 = gson.fromJson(fromGSON, MyJSON.SomeClass.class);
//
//        System.out.println("class1 = class2 ? " + class1.equals(class2));
//
//        System.out.println(class1);
//        System.out.println(class2);
    }

    public static String toMySecondJson(Object obj) {
        String result = "";
        if (obj == null) {
            result = "null";
        } else if (obj instanceof String || obj instanceof Character) {
            result = "\"" + obj + "\"";
        } else if (Primitives.isWrapperType(obj.getClass()) || obj instanceof BigDecimal || obj instanceof BigInteger) {
            result = obj.toString();
        } else if (obj.getClass().isArray() || obj instanceof Collection) {
            result = navigateArray(obj).toString();
        } else {
            result = objToJson(obj).toString();
        }
        return result;
    }

    private static JsonObject objToJson(Object obj) {
        JsonObject jsonObject;
        if (obj instanceof Map) {
            jsonObject = navigateMap(obj);
        } else {
            jsonObject = classToJson(obj);
        }
        return jsonObject;
    }

    private static JsonObject classToJson(Object obj) {
        JsonObjectBuilder jsonObjBuilder = Json.createObjectBuilder();
        Class<?> aClazz = obj.getClass();
        Field[] fields = aClazz.getDeclaredFields();
        for (Field field : fields) {
            int modifiers = field.getModifiers();
            if (Modifier.isStatic(modifiers)) {
                continue;
            }
            field.setAccessible(true);
            String fieldName = field.getName();
            Object fieldObj = null;
            try {
                fieldObj = field.get(obj);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (fieldObj == null) continue;
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
            } else if (fieldObj.getClass().isArray() || fieldObj instanceof Collection) {
                jsonObjBuilder.add(fieldName, navigateArray(fieldObj));
            } else if (fieldObj instanceof Map) {
                jsonObjBuilder.add(fieldName, navigateMap(fieldObj));
            } else {
                jsonObjBuilder.add(fieldName, classToJson(fieldObj));
            }
            field.setAccessible(false);
        }
        return jsonObjBuilder.build();
    }

    private static JsonArray navigateArray(Object fieldObj) {
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        if (fieldObj instanceof Collection) {
            fieldObj = ((Collection<?>) fieldObj).toArray();
        }
        for (int i = 0; i < Array.getLength(fieldObj); i++) {
            Object arrayObj = Array.get(fieldObj, i);

            if (arrayObj == null) {
                arrayBuilder.addNull();
            } else if (arrayObj instanceof String) {
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
            } else if (arrayObj instanceof Map) {
                arrayBuilder.add(navigateMap(arrayObj));
            } else {
                arrayBuilder.add(classToJson(arrayObj));
            }
        }
        return arrayBuilder.build();
    }

    private static JsonObject navigateMap(Object fieldObj) {
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        for (Map.Entry<?, ?> entry : ((Map<?, ?>) fieldObj).entrySet()) {
            Object key = entry.getKey();
            Object value = entry.getValue();

            if (key == null) key = "null";
            if (value == null) continue;

            if (value instanceof String) {
                objectBuilder.add(key.toString(), (String) value);
            } else if (value instanceof Integer) {
                objectBuilder.add(key.toString(), (int) value);
            } else if (value instanceof Long) {
                objectBuilder.add(key.toString(), (long) value);
            } else if (value instanceof Double) {
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
                objectBuilder.add(key.toString(), objToJson(value));
            }
        }
        return objectBuilder.build();
    }

    private static void jsonBuilderAdd(JsonObjectBuilder jsonObjBuilder, JsonArrayBuilder jsonOArrayBuilder,
                                       String fieldName, Object fieldObj){
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
        } else if (fieldObj.getClass().isArray() || fieldObj instanceof Collection) {
            jsonObjBuilder.add(fieldName, navigateArray(fieldObj));
        } else if (fieldObj instanceof Map) {
            jsonObjBuilder.add(fieldName, navigateMap(fieldObj));
        } else {
            jsonObjBuilder.add(fieldName, classToJson(fieldObj));
        }
    }

}
