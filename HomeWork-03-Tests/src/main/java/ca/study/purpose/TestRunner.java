package ca.study.purpose;


import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;


public class TestRunner {
    private static boolean failed = false;
    private static boolean beforeAllFailed = false;
    private static ArrayList<String> completedTests = new ArrayList<>();
    private static ArrayList<String> failedTests = new ArrayList<>();

    public static void runTest(Class<? extends TestingClass> testingClass) {
        BeforeOrAfterAllTest(testingClass, BeforeAll.class);
        executeAllTests(testingClass);
        BeforeOrAfterAllTest(testingClass, AfterAll.class);
        System.out.print("Total tests: " + (completedTests.size() + failedTests.size()));
        System.out.print(", Successful tests: " + completedTests.size());
        System.out.println(", Failed tests: " + failedTests.size());
    }

    private static void BeforeOrAfterAllTest(Class<? extends TestingClass> testingClass, Class<? extends
            Annotation> annotationClass) {

        Method[] declaredBAMethods = testingClass.getDeclaredMethods();
        for (Method method : declaredBAMethods) {
            if (!method.isAnnotationPresent(annotationClass)) continue;
            try {
                method.invoke(testingClass);
            } catch (Exception e) {
                System.out.print(method.getName() + " with @" + annotationClass.getSimpleName() + " annotation FAILED");
                System.out.println();
                beforeAllFailed = true;
            }
        }
        System.out.println();
    }

    private static void executeAllTests(Class<? extends TestingClass> testingClass) {
        if (beforeAllFailed) {
            System.out.println("Stopped tests execution. Finalizing...\n");
            return;
        }

        Method[] declaredBEMethods = testingClass.getDeclaredMethods();

        for (Method method : declaredBEMethods) {
            if (!method.isAnnotationPresent(Test.class)) continue;

            TestingClass testingObject = new TestingClass();   //creating new Object for each Test

            executeBEOrAE(testingObject, BeforeEach.class);

            if (!failed) {
                try {
                    method.invoke(testingObject);
                    System.out.println(" - Success");
                    completedTests.add(method.getName());
                } catch (Exception e) {
                    System.out.println(method.getName() + " - FAILED");
                    failedTests.add(method.getName());
                }
            } else {
                System.out.println(method.getName() + " - not executed due to errors");
                failedTests.add(method.getName());
            }

            executeBEOrAE(testingObject, AfterEach.class);
            System.out.println();
            failed = false;
        }
    }

    private static void executeBEOrAE(TestingClass testingObject, Class<? extends
            Annotation> annotationClass) {

        Class<? extends TestingClass> testingClazz = testingObject.getClass();
        Method[] declaredMethods = testingClazz.getDeclaredMethods();

        for (Method method : declaredMethods) {
            if (!method.isAnnotationPresent(annotationClass)) continue;
            if (failed) {
                System.out.println(method.getName() + " - not executed due to errors");
                continue;
            }
            try {
                method.invoke(testingObject);
            } catch (Exception e) {
                System.out.println(method.getName() + " with @" + annotationClass.getSimpleName() + " annotation FAILED");
                failed = true;
            }
        }
    }
}
