package ca.study.purpose;

import java.lang.reflect.InvocationTargetException;

public class Demo {

    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

        Logging myProxy1 = ProxyClass.createMyProxy(AnotherTestLogging.class);
        myProxy1.calculation(555);  //no @Log on the method (for testing)
        System.out.println();

        myProxy1.calculation(77, "Fifty five");  //@Log
        System.out.println();

        Logging myProxy2 = ProxyClass.createMyProxy(TestLogging.class);
        myProxy2.calculation(878);  //@Log
        System.out.println();

        myProxy2.calculation(24, "That's better");  //no @Log on the method (for testing)
        System.out.println();

    }
}
