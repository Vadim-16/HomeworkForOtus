package ca.study.purpose;

public interface Logging {


    public void calculation(int param);


    public void calculation(int param1, int param2);

    @Log
    public void calculation(int param, String param2);
}
