package ca.study.purpose;

public interface Logging {

    void calculation(int param1);

    @Log
    void calculation(int param1, String param2);
}
