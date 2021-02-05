package ca.study.purpose;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashSet;

public class ProxyClass {

    static <T> T createMyProxy(T object) {
        Class<?>[] interfaces = object.getClass().getInterfaces();
        InvocationHandler invocationHandler = new LogInvocationHandler(object);
        return (T)Proxy.newProxyInstance(invocationHandler.getClass().getClassLoader(),
                object.getClass().getInterfaces(), invocationHandler);
    }

    private static class LogInvocationHandler implements InvocationHandler {
        private final Object myProxyHandler;
        private final HashSet<String> logAnnotationMethods = new HashSet<>();

        LogInvocationHandler(Object object) {
            this.myProxyHandler = object;
            Method[] declaredMethods = object.getClass().getDeclaredMethods();
            for (int i = 0; i < declaredMethods.length; i++) {
                if (declaredMethods[i].isAnnotationPresent(Log.class)) {
                    logAnnotationMethods.add(declaredMethods[i].getName().toString() +
                            Arrays.toString(declaredMethods[i].getParameterTypes()));
                }
            }
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (logAnnotationMethods.contains(method.getName() + Arrays.toString(method.getParameterTypes()))) {
                System.out.print("Executed method: " + method.getName() + ", params:");
                for (int i = 0; i < args.length; i++) {
                    System.out.print(" (" + args[i].getClass().getSimpleName() + ") " + args[i]);
                    if (i != args.length - 1) System.out.print(",");
                }
                System.out.println();
            }
            return method.invoke(myProxyHandler, args);
        }
    }
}
