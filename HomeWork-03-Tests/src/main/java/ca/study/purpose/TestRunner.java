package ca.study.purpose;


import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;


public class TestRunner {
    private boolean failed = false;
    private boolean beforeAllFailed = false;
    private ArrayList<String> completedTests = new ArrayList<>();
    private ArrayList<String> failedTests = new ArrayList<>();

    public void runTest(Class<?> testingClass) throws InvocationTargetException, IllegalAccessException {
        BeforeOrAfterAllTest(testingClass, BeforeAll.class);
        executeAllTests(testingClass);
        BeforeOrAfterAllTest(testingClass, AfterAll.class);
        System.out.print("Total tests: " + (completedTests.size() + failedTests.size()));
        System.out.print(", Successful tests: " + completedTests.size());
        System.out.println(", Failed tests: " + failedTests.size());
    }

    private void BeforeOrAfterAllTest(Class<?> testingClass, Class<? extends
            Annotation> annotationClass) throws InvocationTargetException, IllegalAccessException {

        Method[] declaredBAMethods = testingClass.getDeclaredMethods();
        for (Method method : declaredBAMethods) {
            if (!method.isAnnotationPresent(annotationClass)) continue;
            method.invoke(testingClass);
        }
        System.out.println();
    }

    private void executeAllTests(Class<?> testingClass) throws InvocationTargetException, IllegalAccessException {
        if (beforeAllFailed) {
            System.out.println("Stopped tests execution. Finalizing...\n");
            return;
        }

        Method[] declaredBEMethods = testingClass.getDeclaredMethods();

        for (Method method : declaredBEMethods) {
            if (!method.isAnnotationPresent(Test.class)) continue;

            TestingClass testingObject = new TestingClass();   //creating new Object for each Test

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
    }

    private void executeBEOrAE(TestingClass testingObject, Class<? extends
            Annotation> annotationClass) throws InvocationTargetException, IllegalAccessException {

        Class<? extends TestingClass> testingClazz = testingObject.getClass();
        Method[] declaredMethods = testingClazz.getDeclaredMethods();

        for (Method method : declaredMethods) {
            if (!method.isAnnotationPresent(annotationClass)) continue;
            method.invoke(testingObject);
        }
    }
}
