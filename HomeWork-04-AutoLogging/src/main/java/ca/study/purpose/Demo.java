package ca.study.purpose;

public class Demo {

    public static void main(String[] args) {
        Demo demo = new Demo();
        demo.action();
    }

    public void action() {
        new TestLogging().calculation(6);
    }
}
