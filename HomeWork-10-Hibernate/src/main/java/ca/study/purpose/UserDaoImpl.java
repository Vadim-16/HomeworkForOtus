package ca.study.purpose;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl<T> implements UserDao<T> {
    private static final String URL = "jdbc:h2:mem:testDB;DB_CLOSE_DELAY=-1";
    private final SessionFactory sessionFactory;

    public static void main(String[] args) {
        UserDaoImpl<HibUser> demo = new UserDaoImpl<>();

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


        HibUser user2 = new HibUser();
        user2.setAge(25);
        user2.setName("Simon");

        Address address2 = new Address();
        address2.setStreet("South Street");
        address2.setUser(user2);
        user2.setAddress(address2);

        List<Phone> phones2 = new ArrayList<>();
        Phone phone3 = new Phone();
        phone3.setNumber("999888777");
        phone3.setUser(user2);
        phones2.add(phone3);
        Phone phone4 = new Phone();
        phone4.setNumber("555555555");
        phone4.setUser(user2);
        phones2.add(phone4);
        user2.setPhones(phones2);


        demo.create(user1);
        System.out.println("====================>" + demo.load(1, HibUser.class));
        demo.create(user2);
        System.out.println("====================>" + demo.load(2, HibUser.class));

        user2.setName("James Bond");
        user2.setAge(51);
        demo.update(user2);
        System.out.println("====================>" + demo.load(2, HibUser.class));
    }

    public UserDaoImpl() {
        Configuration configuration = new Configuration()
                .configure("hibernate.cfg.xml");

        StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties()).build();

        Metadata metadata = new MetadataSources(serviceRegistry)
                .addAnnotatedClass(HibUser.class)
                .addAnnotatedClass(Address.class)
                .addAnnotatedClass(Phone.class)
                .getMetadataBuilder()
                .build();

        sessionFactory = metadata.getSessionFactoryBuilder().build();
    }

    @Override
    public void create(T obj) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(obj);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void update(T obj) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.update(obj);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public Optional<T> load(long id, Class<T> clazz) {
        T selected = null;
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            selected = session.get(clazz, id);
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
        return Optional.ofNullable(selected);
    }
}
