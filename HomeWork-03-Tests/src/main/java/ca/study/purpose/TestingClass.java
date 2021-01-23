package ca.study.purpose;

import java.lang.reflect.InvocationTargetException;
import java.util.IllegalFormatCodePointException;

public class TestingClass {

    long timeMil = System.currentTimeMillis();

    @Test
    void test02() {
        System.out.println("test02");
    }

    @Test
    void test03() {
        if (timeMil % 3 == 0)
        throw new IllegalArgumentException();
        else System.out.println("test03");
    }

    @Test
    void test01() {
        System.out.println("test01");
    }

    @BeforeEach
    void preTest01() {
        System.out.println("preTest01");
    }
    @BeforeEach
    void preTest03() {
        if (timeMil % 4 == 0)
            throw new NullPointerException();
        else System.out.println("preTest03");
    }
    @BeforeEach
    void preTest02() {
        if (timeMil % 5 == 0)
            throw new IllegalArgumentException();
        else System.out.println("preTest02");
    }

    @AfterEach
    void postTest02() {
        if (timeMil % 6 == 0)
            throw new RuntimeException();
        else System.out.println("postTest02");
    }

    @AfterEach
    void postTest01() {
        System.out.println("postTest01");
    }

    @AfterAll
    static void afterAllTest01() {
        if (System.currentTimeMillis() % 7 == 0)
            throw new NullPointerException();
        else System.out.println("afterAllTest01");
    }

    @AfterAll
    static void afterAllTest02() {
        System.out.println("afterAllTest02");
    }

    @BeforeAll
    static void beforeAllTest() {
        System.out.println("beforeAllTest");
    }


}
