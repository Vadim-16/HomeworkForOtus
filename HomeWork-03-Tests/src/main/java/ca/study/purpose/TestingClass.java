package ca.study.purpose;

public class TestingClass {
    @Test
    public void test02() {
        System.out.println("Running: test02");
    }

    @Test
    public void test03() {
        System.out.println("Running: test03");
    }

    @Test
    public void test01() {
        System.out.println("Running: test01");
    }

    @BeforeEach
    public void preTest01() {
        System.out.println("Running: preTest01");
    }
    @BeforeEach
    public void preTest03() {
        System.out.println("Running: preTest03");
    }
    @BeforeEach
    public void preTest02() {
        System.out.println("Running: preTest02");
    }

    @AfterEach
    public void postTest02() {
        System.out.println("Running: postTest02");
    }

    @AfterEach
    public void postTest01() {
        System.out.println("Running: postTest01");
    }

    @AfterAll
    public void afterAllTest01() {
        System.out.println("Running: afterAllTest01");
    }

    @AfterAll
    public void afterAllTest02() {
        System.out.println("Running: afterAllTest02");
    }

    @BeforeAll
    public void beforeAllTest() {
        System.out.println("Running: beforeAllTest");
    }


}
