package ca.study.purpose;


import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;


public class TestRunner {

    public static void runTest(Class<?> testingClass) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {

        beforeOrAfterAllTest(testingClass, BeforeAll.class);
        String result = executeAllTests(testingClass);
        beforeOrAfterAllTest(testingClass, AfterAll.class);
        System.out.println(result);
    }

    private static void beforeOrAfterAllTest(Class<?> testingClass, Class<? extends
            Annotation> annotationClass) throws InvocationTargetException, IllegalAccessException {

        Method[] declaredBAMethods = testingClass.getDeclaredMethods();
        for (Method method : declaredBAMethods) {
            if (!method.isAnnotationPresent(annotationClass)) continue;
            method.invoke(testingClass);
        }
        System.out.println();
    }

    private static String executeAllTests(Class<?> testingClass) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        ArrayList<String> completedTests = new ArrayList<>();
        ArrayList<String> failedTests = new ArrayList<>();


        Method[] declaredBEMethods = testingClass.getDeclaredMethods();

        for (Method method : declaredBEMethods) {
            if (!method.isAnnotationPresent(Test.class)) continue;

            Object testingObject = testingClass.getDeclaredConstructor().newInstance(); //creating new Object for each Test

            executeBEOrAE(testingObject, BeforeEach.class);

            try {
                method.invoke(testingObject);
                System.out.println(" - Success");
                completedTests.add(method.getName());
            } catch (Exception e) {
                System.out.println(method.getName() + " - FAILED");
                failedTests.add(method.getName());
            }

            executeBEOrAE(testingObject, AfterEach.class);
            System.out.println();
        }
        return "Total tests: " + (completedTests.size() + failedTests.size()) +
        ", Successful tests: " + completedTests.size() +
        ", Failed tests: " + failedTests.size();
    }

    private static void executeBEOrAE(Object testingObject, Class<? extends
            Annotation> annotationClass) throws InvocationTargetException, IllegalAccessException {

        Class<?> objectClazz = testingObject.getClass();
        Method[] declaredMethods = objectClazz.getDeclaredMethods();

        for (Method method : declaredMethods) {
            if (!method.isAnnotationPresent(annotationClass)) continue;
            method.invoke(testingObject);
        }
    }
}
