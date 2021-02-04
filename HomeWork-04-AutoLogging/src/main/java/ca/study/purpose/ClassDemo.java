package ca.study.purpose;

public class ClassDemo {


    public static void main(String[] args) {
        Logging myTestLogging = ClassProxy.createMyTestLogging();
        myTestLogging.calculation(5);
        System.out.println();

        myTestLogging.calculation(94, 178);
        System.out.println();

        myTestLogging.calculation(5, "Five");
        System.out.println();

    }


    public void action() {
        new TestLogging().calculation(5);
    }


    public void action2() {
        new TestLogging().calculation(5, 115);
    }


    public void action3() {
        new TestLogging().calculation(5, "Five");
    }
}
