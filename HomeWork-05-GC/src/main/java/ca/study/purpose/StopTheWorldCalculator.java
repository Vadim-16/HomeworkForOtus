package ca.study.purpose;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class StopTheWorldCalculator {

    public static void main(String[] args) {
        double stopTime = 0;

        String logPath = ".\\HomeWork-05-GC\\logs\\";
        String log = logPath + "gc-5196-2021-02-05_20-03-26.log";

        try (FileReader fileReader = new FileReader(log);
             BufferedReader bufferedReader = new BufferedReader(fileReader)){
            while (bufferedReader.ready()){
                String line = bufferedReader.readLine();
                String[] words = line.split(" ");
                if (words[words.length - 1].contains("ms")){
                    String[] ms = words[words.length - 1].trim().split("ms");
                    stopTime += Double.parseDouble(ms[0]);
                }
            }
        } catch (IOException ex){
            ex.printStackTrace();
        }
        System.out.println("JVM stopped for " + stopTime/1000 + "s");
    }

}
