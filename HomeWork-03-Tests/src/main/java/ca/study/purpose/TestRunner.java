package ca.study.purpose;


import java.lang.annotation.Annotation;
import java.lang.reflect.Method;


public class TestRunner {
    private static boolean failed = false;
    private static boolean beforeAllfailed = false;

    public static void runTest(TestingClass testingClass) {
        BeforeOrAfterAllTest(testingClass, BeforeAll.class);
        executeAllTests(testingClass);
        BeforeOrAfterAllTest(testingClass, AfterAll.class);
    }

    private static void BeforeOrAfterAllTest(TestingClass testingClass, Class<? extends
            Annotation> annotationClass) {

        Class<? extends TestingClass> aClass = testingClass.getClass();
        Method[] declaredBAMethods = aClass.getDeclaredMethods();

        for (Method method : declaredBAMethods) {
            if (!method.isAnnotationPresent(annotationClass)) continue;
            try {
                method.invoke(testingClass);
            } catch (Exception e) {
                System.out.print(method.getName() + " with @" + annotationClass.getSimpleName() + " annotation FAILED");
                System.out.println();
                beforeAllfailed = true;
            }
        }
        System.out.println();
    }

    private static void executeAllTests(TestingClass testingClass) {
        if (beforeAllfailed) {
            System.out.println("Stopped tests execution. Finalizing...\n");
            return;
        }
        Class<? extends TestingClass> BEClass = testingClass.getClass();
        Method[] declaredBEMethods = BEClass.getDeclaredMethods();

        for (Method method : declaredBEMethods) {
            if (!method.isAnnotationPresent(Test.class)) continue;

            executeBEOrAE(testingClass, BeforeEach.class);

            if (!failed) {
                try {
                    method.invoke(testingClass);
                    System.out.println(" - Success");
                } catch (Exception e) {
                    System.out.println(method.getName() + " - FAILED");
                }
            } else System.out.println(method.getName() + " - Not executed due to errors");

            executeBEOrAE(testingClass, AfterEach.class);
            System.out.println();
            failed = false;
        }
    }

    private static void executeBEOrAE(TestingClass testingClass, Class<? extends
            Annotation> annotationClass) {

        Class<? extends TestingClass> aClass = testingClass.getClass();
        Method[] declaredMethods = aClass.getDeclaredMethods();

        for (Method method : declaredMethods) {
            if (!method.isAnnotationPresent(annotationClass)) continue;
            try {
                method.invoke(testingClass);
            } catch (Exception e) {
                System.out.println(method.getName() + " with @" + annotationClass.getSimpleName() + " annotation FAILED");
                failed = true;
            }
        }
    }
}
