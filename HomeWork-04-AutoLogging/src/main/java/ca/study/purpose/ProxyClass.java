package ca.study.purpose;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashSet;

public class ProxyClass {

    static Logging createMyProxy(Class<? extends Logging> classForProxy) {
        InvocationHandler invocationHandler = new LogInvocationHandler(classForProxy);
        return (Logging) Proxy.newProxyInstance(invocationHandler.getClass().getClassLoader(),
                classForProxy.getInterfaces(), invocationHandler);
    }

    private static class LogInvocationHandler implements InvocationHandler {
        private final Class<? extends Logging> myProxyHandler;
        private final HashSet<String> logAnnotationMethods = new HashSet<>();

        LogInvocationHandler(Class<? extends Logging> myProxyHandler) {
            this.myProxyHandler = myProxyHandler;
            Method[] declaredMethods = myProxyHandler.getDeclaredMethods();
            for (int i = 0; i < declaredMethods.length; i++) {
                if (declaredMethods[i].isAnnotationPresent(Log.class)){
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
            return method.invoke(myProxyHandler.getConstructor().newInstance(), args);
        }
    }
}
