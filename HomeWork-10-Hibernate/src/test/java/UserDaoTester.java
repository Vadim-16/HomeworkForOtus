import ca.study.purpose.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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

        List<Phone> phones1 = new ArrayList<>();
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

        System.out.println(user1.hashCode());
        System.out.println(hibUser.hashCode());

        assertEquals(true, Objects.equals(user1, hibUser));
    }

    @Test
    public void testUpdateUser() {

    }
}
