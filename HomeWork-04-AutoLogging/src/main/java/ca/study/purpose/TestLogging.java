package ca.study.purpose;

public class TestLogging implements Logging {


    public void calculation(int param1) {
        System.out.println("TestLogging: " + param1);
    }


    public void calculation(int param1, int param2) {
        System.out.println("TestLogging: " + param1 + ", " + param2);
    }


    public void calculation(int param1, String param2) {
        System.out.println("TestLogging: " + param1 + ", " + param2);
    }

}
