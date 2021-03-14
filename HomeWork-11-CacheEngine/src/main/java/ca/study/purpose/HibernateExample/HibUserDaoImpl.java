package ca.study.purpose.HibernateExample;


import ca.study.purpose.MyCache.MyCacheEngine;
import ca.study.purpose.MyCache.MyCacheEngineImpl;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class HibUserDaoImpl implements HibUserDao {
    private final SessionFactory sessionFactory;
    private MyCacheEngine<Long, HibUser> cache;

    public static void main(String[] args) {
        MyCacheEngineImpl<Long, HibUser> cache = new MyCacheEngineImpl<>(5, 0, 0, true);
        HibUserDaoImpl demo = new HibUserDaoImpl(cache);

        HibUser user1 = demo.buildUser1();
        HibUser user2 = demo.buildUser2();

        demo.create(user1);
        long beginTime1 = System.currentTimeMillis();
        System.out.println("====================>" + demo.load(1, HibUser.class));
        System.out.println("====================>Load time: " + (System.currentTimeMillis() - beginTime1) + "ms");

        demo.create(user2);
        long beginTime2 = System.currentTimeMillis();
        System.out.println("====================>" + demo.load(2, HibUser.class));
        System.out.println("====================>Load time with cache: " + (System.currentTimeMillis() - beginTime2) + "ms");

        user2.setName("James Bond");
        user2.setAge(51);
        demo.update(user2);
        long beginTime3 = System.currentTimeMillis();
        System.out.println("====================>" + demo.load(2, HibUser.class));
        System.out.println("====================>Load time with cache: " + (System.currentTimeMillis() - beginTime3) + "ms");
        demo.cache.dispose();
    }

    public HibUserDaoImpl(MyCacheEngine<Long, HibUser> cache) {
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
        this.cache = cache;
    }

    @Override
    public void create(HibUser hibUser) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(hibUser);
            transaction.commit();
            cache.put(hibUser.getId(), hibUser);
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void update(HibUser hibUser) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.update(hibUser);
            transaction.commit();
            cache.put(hibUser.getId(), hibUser);
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public Optional<HibUser> load(long id, Class<HibUser> clazz) {
        HibUser user = cache.get(id);
        if (user != null) return Optional.of(user);
        HibUser selected = null;
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            selected = session.get(clazz, id);
            if (cache != null) cache.put(id, selected);
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
        return Optional.ofNullable(selected);
    }

    public HibUser buildUser1() {
        HibUser user1 = new HibUser();
        user1.setId(1);
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
        return user1;
    }

    public HibUser buildUser2() {
        HibUser user2 = new HibUser();
        user2.setId(2);
        user2.setAge(25);
        user2.setName("Simon");

        Address address2 = new Address();
        address2.setStreet("South Street");
        address2.setUser(user2);
        user2.setAddress(address2);

        Set<Phone> phones2 = new HashSet<>();
        Phone phone3 = new Phone();
        phone3.setNumber("999888777");
        phone3.setUser(user2);
        phones2.add(phone3);
        Phone phone4 = new Phone();
        phone4.setNumber("555555555");
        phone4.setUser(user2);
        phones2.add(phone4);
        user2.setPhones(phones2);
        return user2;
    }
}
