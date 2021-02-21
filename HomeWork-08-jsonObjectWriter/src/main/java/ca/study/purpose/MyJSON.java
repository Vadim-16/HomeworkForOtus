package ca.study.purpose;

import ca.study.purpose.testObjects.ClassABC;
import ca.study.purpose.testObjects.Rule;
import com.google.gson.Gson;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.*;


public class MyJSON {

    public static void main(String[] args) throws IllegalAccessException, NoSuchFieldException {
        SomeClass class1 = new SomeClass();
        Gson gson = new Gson();

        String fromGSON = gson.toJson(class1);
        System.out.println(fromGSON);

        String fromMySecondJson = toMyJson(class1);
        System.out.println(fromMySecondJson);

        System.out.println("fromGSON = fromMySecondJson ? " + fromGSON.equals(fromMySecondJson));
//
//        SomeClass class2 = gson.fromJson(fromGSON, SomeClass.class);
//        System.out.println("class1 = class2 ? " + class1.equals(class2));
//
//        System.out.println(class1);
//        System.out.println(class2);
    }

    public static String toMyJson(Object obj) throws IllegalAccessException, NoSuchFieldException {
        Class<?> aClazz = obj.getClass();
        Field[] fields = aClazz.getDeclaredFields();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        for (Field field : fields) {
            field.setAccessible(true);
            stringBuilder.append("\"").append(field.getName()).append("\"").append(":");
            String s = field.getType().getSimpleName();
            switch (s) {
                case "String": {
                    stringBuilder.append("\"").append(field.get(obj)).append("\"");
                    break;
                }
                case "Object": {
                    stringBuilder.append(toMyJson(field.get(obj)));
                    break;
                }
                case "boolean[]":
                case "byte[]":
                case "char[]":
                case "short[]":
                case "int[]":
                case "long[]":
                case "float[]":
                case "double[]":
                case "Object[]": {
                    stringBuilder.append("[");
                    for (int i = 0; i < Array.getLength(field.get(obj)); i++) {
                        Object o = Array.get(field.get(obj), i);
                        if (o instanceof String) {
                            stringBuilder.append("\"").append(o).append("\"");
                        } else if (o.toString().contains("@")) {
                            stringBuilder.append(toMyJson(o));
                        } else stringBuilder.append(o);
                        if (i < Array.getLength(field.get(obj)) - 1) stringBuilder.append(",");
                    }
                    stringBuilder.append("]");
                    break;
                }
                case "BitSet":
                case "LinkedHasSet":
                case "TreeSet":
                case "HashSet":
                case "Set":
                case "LinkedList":
                case "ArrayList":
                case "List": {
                    String typeName = field.getGenericType().getTypeName();
                    if (typeName.contains("<java.lang.String>")) {
                        stringBuilder.append("[");
                        Collection<String> strings = (Collection<String>) field.get(obj);
                        Object[] objects = strings.toArray();
                        for (int i = 0; i < objects.length; i++) {
                            stringBuilder.append("\"").append(objects[i]).append("\"");
                            if (i < objects.length - 1) {
                                stringBuilder.append(",");
                            }
                        }
                        stringBuilder.append("]");
                    } else if (typeName.contains("@")) {
                        stringBuilder.append(toMyJson(field.get(obj)));
                    } else {
                        stringBuilder.append(field.get(obj).toString().replaceAll("\\s+", ""));
                    }

                    break;
                }
                case "WeakHashMap":
                case "IdentityHashMap":
                case "EnumMap":
                case "LinkedHashMap":
                case "TreeMap":
                case "HashMap":
                case "Map": {
                    String typeName = field.getGenericType().getTypeName();
                    Map map = (Map) field.get(obj);
                    String mapToString = map.toString();
                    mapToString = mapToString.replaceAll("=", ":");
                    mapToString = mapToString.replaceAll(" ", "");
                    Collection<String> strings = null;
                    if (typeName.contains("<java.lang.String")) {
                        strings = map.keySet();
                    } else if (typeName.contains("java.lang.String>")) {
                        strings = map.values();
                    }
                    for (String key : strings) {
                        mapToString = mapToString.replace(key, "\"" + key + "\"");
                    }
                    stringBuilder.append(mapToString);
                    break;
                }
                default: {
                    stringBuilder.append(field.get(obj));
                }
            }

            if (field != fields[fields.length - 1]) stringBuilder.append(",");
            field.setAccessible(false);
        }
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    static class SomeClass {
        private ClassABC abc = new ClassABC();
        protected String word = "oneWord";
        public double doubleNumber = 55.093d;
        private int[][] numbers = {{1, 2, 3, 4, 5}, {1}, {3, 4}};
        public Object[] objects = new Object[]{new Object(), "", 54, new ClassABC()};
        ArrayList<Integer> integerList = new ArrayList<>(Arrays.asList(9, 8, 7, 6, 5));
        private Set<String> set = new HashSet<>(Arrays.asList("ten", "eleven", "twelve"));
        private Map<Rule, Map<Rule, Rule>> map = new HashMap();
        private final ArrayList<Map<String, Rule>> rules3 = new ArrayList<>();

        {
            Map<String, Rule> maps = new HashMap<>();
            maps.put("1", null);
            rules3.add(maps);
            map.put(new Rule(), null);
            Map<Rule, Rule> maps2 = new HashMap<>();
            maps2.put(null, new Rule());
            maps2.put(new Rule(), new Rule());
            map.put(new Rule(), maps2);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            SomeClass someClass = (SomeClass) o;
            return Double.compare(someClass.doubleNumber, doubleNumber) == 0 &&
                    Objects.equals(abc, someClass.abc) &&
                    Objects.equals(word, someClass.word) &&
                    Arrays.equals(numbers, someClass.numbers) &&
                    Arrays.equals(objects, someClass.objects) &&
                    Objects.equals(integerList, someClass.integerList) &&
                    Objects.equals(set, someClass.set) &&
                    Objects.equals(map, someClass.map) &&
                    Objects.equals(rules3, someClass.rules3);
        }

        @Override
        public int hashCode() {
            int result = Objects.hash(abc, word, doubleNumber, integerList, set, map, rules3);
            result = 31 * result + Arrays.hashCode(numbers);
            result = 31 * result + Arrays.hashCode(objects);
            return result;
        }
    }
}