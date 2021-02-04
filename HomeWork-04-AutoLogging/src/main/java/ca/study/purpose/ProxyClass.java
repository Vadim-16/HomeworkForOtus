package ca.study.purpose;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyClass {

    static Logging createMyTestLogging() {
        InvocationHandler invocationHandler = new LogInvocationHandler(new TestLogging());
        Logging myTestLogging = (Logging) Proxy.newProxyInstance(invocationHandler.getClass().getClassLoader(), invocationHandler.getClass().getInterfaces(), invocationHandler);
        return myTestLogging;
    }

    static class LogInvocationHandler implements InvocationHandler {
        private final TestLogging myTestLogging;

        LogInvocationHandler(TestLogging myTestLogging) {
            this.myTestLogging = myTestLogging;
        }


        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println(1);
            return method.invoke(myTestLogging, args);
        }
    }
}
