package ca.study.purpose;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyClass {

    static Logging createMyTestLogging() {
        InvocationHandler invocationHandler = new LogInvocationHandler(new TestLogging());
        Logging myTestLogging = (Logging) Proxy.newProxyInstance(invocationHandler.getClass().getClassLoader(), TestLogging.class.getInterfaces(), invocationHandler);
        return myTestLogging;
    }

    static class LogInvocationHandler implements InvocationHandler {
        private final TestLogging myTestLogging;

        LogInvocationHandler(TestLogging myTestLogging) {
            this.myTestLogging = myTestLogging;
        }


        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (method.isAnnotationPresent(Log.class)){
                System.out.print("Executed method: " + method.getName() + ", params:");
                for (int i = 0; i < args.length; i++) {
                    System.out.print(" (" + args[i].getClass().getSimpleName() + ") " + args[i]);
                    if (i != args.length - 1) System.out.print(",");
                }
                System.out.println();
            }
            return method.invoke(myTestLogging, args);
        }
    }
}
