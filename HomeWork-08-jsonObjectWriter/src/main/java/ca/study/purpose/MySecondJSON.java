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
        } else if (obj instanceof Map) {
            result = navigateMap(obj).toString();
        } else {
            result = classToJson(obj).toString();
        }
        return result;
    }

    private static JsonObject classToJson(Object obj) {
        JsonObjectBuilder jsonObjBuilder = Json.createObjectBuilder();
        Class<?> aClazz = obj.getClass();
        Field[] fields = aClazz.getDeclaredFields();
        for (Field field : fields) {
            int modifiers = field.getModifiers();
            if (Modifier.isStatic(modifiers)) continue;
            field.setAccessible(true);
            String fieldName = field.getName();
            Object fieldObj = null;
            try {
                fieldObj = field.get(obj);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (fieldObj == null) continue;
            jsonBuilderAdd(jsonObjBuilder, null, fieldName, fieldObj);
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
                continue;
            }
            jsonBuilderAdd(null, arrayBuilder, null, arrayObj);
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
            jsonBuilderAdd(objectBuilder, null, key.toString(), value);
        }
        return objectBuilder.build();
    }

    private static void jsonBuilderAdd(JsonObjectBuilder jsonObjBuilder, JsonArrayBuilder jsonArrayBuilder,
                                       String fieldName, Object fieldObj) {
        if (fieldObj instanceof String) {
            if (jsonObjBuilder != null) jsonObjBuilder.add(fieldName, fieldObj.toString());
            else jsonArrayBuilder.add(fieldObj.toString());
        } else if (fieldObj instanceof Integer || fieldObj instanceof Short) {
            if (jsonObjBuilder != null) jsonObjBuilder.add(fieldName, (int) fieldObj);
            else jsonArrayBuilder.add((int) fieldObj);
        } else if (fieldObj instanceof Long || fieldObj instanceof Byte || fieldObj instanceof Character) {
            if (jsonObjBuilder != null) jsonObjBuilder.add(fieldName, (long) fieldObj);
            else jsonArrayBuilder.add((long) fieldObj);
        } else if (fieldObj instanceof Double || fieldObj instanceof Float) {
            if (jsonObjBuilder != null) jsonObjBuilder.add(fieldName, (double) fieldObj);
            else jsonArrayBuilder.add((double) fieldObj);
        } else if (fieldObj instanceof Boolean) {
            if (jsonObjBuilder != null) jsonObjBuilder.add(fieldName, (boolean) fieldObj);
            else jsonArrayBuilder.add((boolean) fieldObj);
        } else if (fieldObj instanceof BigDecimal) {
            if (jsonObjBuilder != null) jsonObjBuilder.add(fieldName, (BigDecimal) fieldObj);
            else jsonArrayBuilder.add((BigDecimal) fieldObj);
        } else if (fieldObj instanceof BigInteger) {
            if (jsonObjBuilder != null) jsonObjBuilder.add(fieldName, (BigInteger) fieldObj);
            else jsonArrayBuilder.add((BigInteger) fieldObj);
        } else if (fieldObj.getClass().isArray() || fieldObj instanceof Collection) {
            if (jsonObjBuilder != null) jsonObjBuilder.add(fieldName, navigateArray(fieldObj));
            else jsonArrayBuilder.add(navigateArray(fieldObj));
        } else if (fieldObj instanceof Map) {
            if (jsonObjBuilder != null) jsonObjBuilder.add(fieldName, navigateMap(fieldObj));
            else jsonArrayBuilder.add(navigateMap(fieldObj));
        } else {
            if (jsonObjBuilder != null) jsonObjBuilder.add(fieldName, classToJson(fieldObj));
            else jsonArrayBuilder.add(classToJson(fieldObj));
        }
    }
}