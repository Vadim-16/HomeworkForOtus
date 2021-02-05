package ca.study.purpose;

import ca.study.purpose.Testings.TestRunner;
import ca.study.purpose.Testings.UserTestingClass;

import java.lang.reflect.InvocationTargetException;

public class ComputerShop {

    public static void main(String[] args) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {

        TestRunner.runTest(UserTestingClass.class);

    }
}
