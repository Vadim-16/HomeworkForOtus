import ca.study.purpose.HibernateExample.HibUser;
import ca.study.purpose.HibernateExample.HibUserDaoImpl;
import ca.study.purpose.HibernateExample.Phone;
import ca.study.purpose.MyCache.MyCacheEngineImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class TesterHibUserWithCache {
    private HibUserDaoImpl demo;


    @BeforeEach
    public void setUp() {
        MyCacheEngineImpl<Long, HibUser> cache = new MyCacheEngineImpl<>(5, 0, 0, true);
        demo = new HibUserDaoImpl(cache);
    }

    @Test
    public void testCreateLoadUser() {
        HibUser user1 = demo.buildUser2();
        demo.create(user1);
        HibUser hibUser = demo.load(1, HibUser.class).get();

        System.out.println(user1);
        System.out.println(hibUser);

        System.out.println("javaUser hashcode:" + user1.hashCode());
        System.out.println("retrievedUser hashcode:" + hibUser.hashCode());

        assertEquals(hibUser, user1);
    }

    @Test
    public void testUpdateLoadUser() {
        HibUser user1 = demo.buildUser1();
        demo.create(user1);

        HibUser hibUser = demo.load(1, HibUser.class).get();
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + user1);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + hibUser);


        user1.setName("Marry");
        Phone phone3 = new Phone();
        phone3.setNumber("898989898");
        phone3.setUser(user1);
        user1.getPhones().add(phone3);
        demo.update(user1);
        HibUser hibUser2 = demo.load(1, HibUser.class).get();

        System.out.println(">>>>>>>>>>>>>>>>>>>" + user1);
        System.out.println(">>>>>>>>>>>>>>>>>>>" + hibUser2);

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> javaUser hashcode:" + user1.hashCode());
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> retrievedUser hashcode:" + hibUser2.hashCode());

        assertEquals(hibUser2, user1);
    }

    @Test
    public void testIsCacheFaster() {
        HibUser user1 = demo.buildUser1();
        HibUser user2 = demo.buildUser2();

        demo.create(user1);
        long beginLoad1 = System.currentTimeMillis();
        System.out.println(">>>>>>>>>>>>>>>>>>>" + demo.load(1, HibUser.class).get());
        long loadTime1 = System.currentTimeMillis() - beginLoad1;
        System.out.println(">>>>>>>>>>>>>>>>>>>Load time: " + loadTime1 + "ms");

        demo.create(user2);

        long beginLoad2 = System.currentTimeMillis();
        System.out.println(">>>>>>>>>>>>>>>>>>>" + demo.load(2, HibUser.class).get());
        long loadTime2 = System.currentTimeMillis() - beginLoad2;
        System.out.println(">>>>>>>>>>>>>>>>>>>Load time with cache: " + loadTime2 + "ms");
        assertTrue(loadTime1 > loadTime2);

        user2.setName("James Bond");
        user2.setAge(51);
        Phone phone = new Phone();
        phone.setUser(user2);
        phone.setNumber("777-777-888");
        user2.getPhones().add(phone);
        demo.update(user2);

        HibUser hibUser2 = demo.load(2, HibUser.class).get();
        System.out.println(">>>>>>>>>>>>>>>>>>>" + user2);
        System.out.println(">>>>>>>>>>>>>>>>>>>" + hibUser2);

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> javaUser hashcode:" + user2.hashCode());
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>> retrievedUser hashcode:" + hibUser2.hashCode());
        assertEquals(hibUser2, user2);

        long beginLoad3 = System.currentTimeMillis();
        System.out.println(">>>>>>>>>>>>>>>>>>>" + demo.load(2, HibUser.class).get());
        long loadTime3 = System.currentTimeMillis() - beginLoad3;
        System.out.println(">>>>>>>>>>>>>>>>>>>Load time with cache: " + loadTime3 + "ms");
        assertTrue(loadTime3 < loadTime1);
    }
}
