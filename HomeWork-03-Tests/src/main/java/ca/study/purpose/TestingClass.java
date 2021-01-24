package ca.study.purpose;


public class TestingClass {


    @Test
    void test02() {
        System.out.print("test02");
    }

    @Test
    void test03() {
        if (System.currentTimeMillis() % 3 == 0)
        throw new IllegalArgumentException();
        else System.out.print("test03");
    }

    @Test
    void test01() {
        System.out.print("test01");
    }

    @BeforeEach
    void preTest01() {
        System.out.println("preTest01");
    }

    @BeforeEach
    void preTest03() {
        if ((int)(Math.random()*100) % 4 == 0)
            throw new NullPointerException();
        else System.out.println("preTest03");
    }
    @BeforeEach
    void preTest02() {
        if (System.currentTimeMillis() % 5 == 0)
            throw new IllegalArgumentException();
        else System.out.println("preTest02");
    }

    @AfterEach
    void postTest02() {
        if (System.currentTimeMillis() % 6 == 0)
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
            if (System.currentTimeMillis() % 5 == 0)
                throw new NullPointerException();
        System.out.println("beforeAllTest");
    }


}
