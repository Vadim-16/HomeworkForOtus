package ca.study.purpose;

import java.util.ArrayList;

public class MemoryLeak {
    public static void main(String[] args) throws InterruptedException {
        long startTime = System.currentTimeMillis();
        System.out.println("App started at " + ((startTime - startTime) / 1000) + "s");
        doIterations();
        long endTime = System.currentTimeMillis();
        System.out.println("App worked for " + ((endTime - startTime) / 1000) + "s");
    }

    static void doIterations() throws InterruptedException {
        ArrayList<String> strings = new ArrayList<>();
        String stringSum = "";
        for (int i = 0; i < 400000; i++) {
            String s = String.valueOf(i);
            stringSum += s;
            strings.add(s);
            //if (i % 3 > 0) Thread.sleep(10);  //label1
            System.out.println("Element: " + s + " Type: String");
        }
        System.out.println("Strings created");

        for (String element : strings) {
            ArrayList<Double> integers = new ArrayList<>();
            integers.add(Double.valueOf(element));
//            if (element.charAt(0) == '5') {
//                integers.remove(Double.valueOf(element));
//                Thread.sleep(10);
//            }
            System.out.println("Element: " + Double.valueOf(element) + " Type: Double");

        }
        System.out.println("Doubles created");
    }
}
