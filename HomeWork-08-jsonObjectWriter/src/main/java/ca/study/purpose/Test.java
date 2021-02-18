package ca.study.purpose;

import ca.study.purpose.testObjects.Car;
import ca.study.purpose.testObjects.Ford;
import com.google.gson.Gson;

import java.util.*;

public class Test {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {


        myObjectTest();

        arrayListTest();

        linkedListTest();

        mapTest();

        hashSetTest();

        stringTest();

        arrayPrimitiveTest();

        arrayObjTest();

        intTest();

        charTest();

        mapWithCarTest();
    }

    private static void mapTest() throws NoSuchFieldException, IllegalAccessException {
        Map<String, int[]> cars = new HashMap<>();
        int[] ints = {1, 2, 3};
        cars.put("1", ints);
        System.out.println(MyJSON.toMyJson(cars));


        Gson gson = new Gson();
        System.out.println(gson.toJson(cars));
        System.out.println();
    }

    private static void mapWithCarTest() throws NoSuchFieldException, IllegalAccessException {
        Map<String, Car> cars = new HashMap<>();
        cars.put("1", new Car());
        cars.put("2", new Car());
        System.out.println(MyJSON.toMyJson(cars));


        Gson gson = new Gson();
        System.out.println(gson.toJson(cars));
        System.out.println();
    }

    private static void linkedListTest() throws NoSuchFieldException, IllegalAccessException {
        List<Car> carList = new LinkedList<>();
        carList.add(new Car());
        carList.add(new Car());
        System.out.println(MyJSON.toMyJson(carList));


        Gson gson = new Gson();
        System.out.println(gson.toJson(carList));
        System.out.println();
    }

    private static void arrayListTest() throws NoSuchFieldException, IllegalAccessException {
        List<String> carList = new ArrayList<>();
        carList.add("1");
        carList.add(null);
        System.out.println(MyJSON.toMyJson(carList));


        Gson gson = new Gson();
        System.out.println(gson.toJson(carList));
        System.out.println();
    }

    private static void myObjectTest() throws NoSuchFieldException, IllegalAccessException {

        Ford ford = new Ford();
        System.out.println(MyJSON.toMyJson(ford));


        Gson gson = new Gson();
        System.out.println(gson.toJson(ford));
        System.out.println();
    }

    private static void hashSetTest() throws NoSuchFieldException, IllegalAccessException {
        Set<Car> carList = new HashSet<>();
        carList.add(new Car());
        carList.add(new Car());
        System.out.println(MyJSON.toMyJson(carList));


        Gson gson = new Gson();
        System.out.println(gson.toJson(carList));
        System.out.println();
    }

    private static void stringTest() throws NoSuchFieldException, IllegalAccessException {

        System.out.println(MyJSON.toMyJson("Привет"));


        Gson gson = new Gson();
        System.out.println(gson.toJson("Привет"));
        System.out.println();
    }

    private static void arrayPrimitiveTest() throws NoSuchFieldException, IllegalAccessException {
        int[][] ints = {{1, 2, 3}, {4, 5, 6}};
        System.out.println(MyJSON.toMyJson(ints));


        Gson gson = new Gson();
        System.out.println(gson.toJson(ints));
        System.out.println();
    }

    private static void arrayObjTest() throws NoSuchFieldException, IllegalAccessException {
        Car[][] cars = {{null}, {new Car()}};
        System.out.println(MyJSON.toMyJson(cars));


        Gson gson = new Gson();
        System.out.println(gson.toJson(cars));
        System.out.println();
    }

    private static void intTest() throws NoSuchFieldException, IllegalAccessException {

        System.out.println(MyJSON.toMyJson(1));


        Gson gson = new Gson();
        System.out.println(gson.toJson(1));
        System.out.println();
    }

    private static void charTest() throws NoSuchFieldException, IllegalAccessException {

        System.out.println(MyJSON.toMyJson('Z'));


        Gson gson = new Gson();
        System.out.println(gson.toJson('Z'));
        System.out.println();
    }
}
