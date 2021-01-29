package ca.study.purpose;

import java.lang.reflect.InvocationTargetException;

public class ComputerShop {

    public static void main(String[] args) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {

        TestRunner.runTest(UserTestingClass.class);

    }
}
