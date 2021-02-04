package ca.study.purpose;

public class Demo {

    public static void main(String[] args) {
        Demo demo = new Demo();
        demo.action();
        System.out.println();

        Logging myTestLogging = ProxyClass.createMyTestLogging();
        myTestLogging.calculation(6, "Five");
    }

    public void action() {
        new TestLogging().calculation(6);
    }
}
