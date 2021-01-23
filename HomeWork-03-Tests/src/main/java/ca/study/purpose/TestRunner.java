package ca.study.purpose;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class TestRunner {


    public void runTest(TestingClass testingClass) {

        System.out.println("\nRunning Test\n");

        System.out.println("Searching for @BeforeAll annotations");
        Class<? extends TestingClass> BAClass = testingClass.getClass();
        Method[] declaredBAMethods = BAClass.getDeclaredMethods();
        for (Method method : declaredBAMethods) {
            if (!method.isAnnotationPresent(BeforeAll.class)) continue;
            try{
                method.invoke(method);
            } catch (Exception e) {
                System.out.println(method.getName() + " with @BeforeAll annotation FAILED");
            }
        }
        System.out.println();


        System.out.println("Searching for @BeforeEach annotations");
        Class<? extends TestingClass> BEClass = testingClass.getClass();
        Method[] declaredBEMethods = BEClass.getDeclaredMethods();
        for (Method method : declaredBEMethods) {
            if (!method.isAnnotationPresent(BeforeEach.class)) continue;
            try {
                method.invoke(testingClass);
            } catch (Exception e) {
                System.out.println(method.getName() + " with @BeforeEach annotation FAILED");
            }
        }
        System.out.println();


        System.out.println("Searching for @Test annotations");
        Class<? extends TestingClass> TestClass = testingClass.getClass();
        Method[] declaredTestMethods = TestClass.getDeclaredMethods();
        for (Method method : declaredTestMethods) {
            if (!method.isAnnotationPresent(Test.class)) continue;
            try {
                method.invoke(testingClass);
            } catch (Exception e) {
                System.out.println(method.getName() + " with @Test annotation FAILED");
            }
        }
        System.out.println();


        System.out.println("Searching for @AfterEach annotations");
        Class<? extends TestingClass> AEClass = testingClass.getClass();
        Method[] declaredAEMethods = AEClass.getDeclaredMethods();
        for (Method method : declaredAEMethods) {
            if (!method.isAnnotationPresent(AfterEach.class)) continue;
            try {
                method.invoke(testingClass);
            } catch (Exception e) {
                System.out.println(method.getName() + " with @AfterEach annotation FAILED");
            }
        }
        System.out.println();

        System.out.println("Searching for @AftereAll annotations");
        Class<? extends TestingClass> AAClass = testingClass.getClass();
        Method[] declaredAAMethods = AAClass.getDeclaredMethods();
        for (Method method : declaredAAMethods) {
            if (!method.isAnnotationPresent(AfterAll.class)) continue;
            try{
                method.invoke(method);
            } catch (Exception e) {
                System.out.println(method.getName() + " with @AfterAll annotation FAILED");
            }
        }
        System.out.println();


//        Class<TestingClass> BEClass = TestingClass.class;
//        Field[] declaredFields = BEClass.getDeclaredFields();
//        for (Field field: declaredFields) {
//            if (!field.isAnnotationPresent(BeforeEach.class)){
//                continue;
//            }
//
//            Object o = field.get(testingClass);
//
//        }


    }

}
