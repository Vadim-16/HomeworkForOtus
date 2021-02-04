package ca.study.purpose;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public class ClassProxy {

    static Logging createMyTestLogging() {
        InvocationHandler invocationHandler = new LogInvocationHandler(new TestLogging());
        Logging myTestLoggingProxy = (Logging) Proxy.newProxyInstance(TestLogging.class.getClassLoader(),
                new Class[] { Logging.class }, invocationHandler);
        return myTestLoggingProxy;
    }

    static class LogInvocationHandler implements InvocationHandler {
        private final Logging myTestLogging;

        LogInvocationHandler(Logging myTestLogging) {
            this.myTestLogging = myTestLogging;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            if (method.isAnnotationPresent(Log.class) ) {
                System.out.print("Executed method: " + method.getName());
                for (int i = 0; i < args.length; i++) {
                    System.out.print(" - param" + i + ": (" + args[i].getClass().getSimpleName() + ") " + args[i]);
                }
                System.out.println();
            }
            return method.invoke(myTestLogging, args);

        }
    }
}
