package ca.study.purpose;

public class AnotherTestLogging implements Logging {

    public void calculation(int param) {
        System.out.println("AnotherTestLogging: " + param);
    }

    @Log
    public void calculation(int param1, String param2) {
        System.out.println("AnotherTestLogging: " + param1 + ", " + param2);
    }
}

