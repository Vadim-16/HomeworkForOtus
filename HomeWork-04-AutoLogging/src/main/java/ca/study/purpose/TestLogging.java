package ca.study.purpose;

public class TestLogging implements Logging{


    public void calculation(int param) {
        System.out.println("TestLogging: " + param);
    }

    public void calculation(int param1, String param2) {
        System.out.println("TestLogging: " + param1 + ", " + param2);
    }
}
