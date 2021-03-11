import ca.study.purpose.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class UserDaoTester {
    private UserDao<HibUser> demo;

    @BeforeEach
    public void setUp() {
        demo = new UserDaoImpl<>();
    }

    @Test
    public void testCreateLoadUser() {
        HibUser user1 = new HibUser();
        user1.setAge(25);
        user1.setName("Simon");

        Address address1 = new Address();
        address1.setStreet("North Street");
        address1.setUser(user1);
        user1.setAddress(address1);

        Set<Phone> phones1 = new HashSet<>();
        Phone phone1 = new Phone();
        phone1.setNumber("123456789");
        phone1.setUser(user1);
        phones1.add(phone1);
        Phone phone2 = new Phone();
        phone2.setNumber("222333444");
        phone2.setUser(user1);
        phones1.add(phone2);
        user1.setPhones(phones1);
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
        HibUser user1 = new HibUser();
        user1.setAge(25);
        user1.setName("Simon");

        Address address1 = new Address();
        address1.setStreet("North Street");
        address1.setUser(user1);
        user1.setAddress(address1);

        Set<Phone> phones1 = new HashSet<>();
        Phone phone1 = new Phone();
        phone1.setNumber("123456789");
        phone1.setUser(user1);
        phones1.add(phone1);
        Phone phone2 = new Phone();
        phone2.setNumber("222333444");
        phone2.setUser(user1);
        phones1.add(phone2);
        user1.setPhones(phones1);
        demo.create(user1);

        HibUser hibUser = demo.load(1, HibUser.class).get();
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + user1);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + hibUser);


        user1.setName("Marry");
        Phone phone3 = new Phone();
        phone3.setNumber("898989898");
        phone3.setUser(user1);
        phones1.add(phone3);
        user1.setPhones(phones1);
        demo.update(user1);
        HibUser hibUser2 = demo.load(1, HibUser.class).get();

        System.out.println(">>>>>>>>>>>>>>>>>>>" + user1);
        System.out.println(">>>>>>>>>>>>>>>>>>>" + hibUser2);

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> javaUser hashcode:" + user1.hashCode());
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> retrievedUser hashcode:" + hibUser2.hashCode());

        assertEquals(hibUser2, user1);
    }
}
