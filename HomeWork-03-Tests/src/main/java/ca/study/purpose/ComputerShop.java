package ca.study.purpose;

import java.lang.reflect.InvocationTargetException;

public class ComputerShop {
    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {

        new TestRunner().runTest(new TestingClass());
    }
}
