import ca.study.purpose.Account;
import ca.study.purpose.H2MyImpl;
import ca.study.purpose.JdbcTemplate;
import ca.study.purpose.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JdbcTemplateTester {
    private Connection connection;
    private JdbcTemplate jdbcTemplate;
    private H2MyImpl h2MyImpl;


    @BeforeEach
    public void setUp() {
        try {
            this.connection = DriverManager.getConnection("jdbc:h2:mem:");
            this.jdbcTemplate = new JdbcTemplate(connection);
            this.h2MyImpl = new H2MyImpl();
            h2MyImpl.createAccountTable(connection);
            h2MyImpl.createUserTable(connection);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    public void testLoadUser(){
        try {
            long natalia = jdbcTemplate.create(new User(1, "Natalia", 32));
            long alex = jdbcTemplate.create(new User(2, "Alex", 34));
            User user = (User)jdbcTemplate.load(2, User.class).get();
            assertEquals(2, user.getId());
            assertEquals("Alex", user.getName());
            assertEquals(34, user.getAge());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    public void testLoadAccount(){
        try {
            long acc1 = jdbcTemplate.create(new Account(1, "Credit", 100_000));
            long acc2 = jdbcTemplate.create(new Account(2, "Debit", 145_742));
            Account acc = (Account)jdbcTemplate.load(2, Account.class).get();
            assertEquals(2, acc.getNo());
            assertEquals("Debit", acc.getType());
            assertEquals(145_742, acc.getRest());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    public void testCreateUser(){
        try {
            long natalia = jdbcTemplate.create(new User(1, "Natalia", 32));
            long alex = jdbcTemplate.create(new User(2, "Alex", 34));
            User user = (User)jdbcTemplate.load(2, User.class).get();
            assertEquals(2, user.getId());
            assertEquals(34, user.getAge());
            assertEquals("Alex", user.getName());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    public void testCreateAccount(){
        try {
            long acc1 = jdbcTemplate.create(new Account(1, "Credit", 100_000));
            long acc2 = jdbcTemplate.create(new Account(2, "Debit", 145_742));
            Account acc = (Account)jdbcTemplate.load(2, Account.class).get();
            assertEquals(2, acc.getNo());
            assertEquals(145_742, acc.getRest());
            assertEquals("Debit", acc.getType());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    public void testUpdateAccount(){
        try {
            long acc1 = jdbcTemplate.create(new Account(1, "Credit", 100_000));
            jdbcTemplate.update(new Account(1, "Credit(VISA)", 97_632));
            Account acc = (Account)jdbcTemplate.load(1, Account.class).get();
            assertEquals(1, acc.getNo());
            assertEquals(97_632, acc.getRest());
            assertEquals("Credit(VISA)", acc.getType());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    public void testUpdateUser(){
        try {
            long natalia = jdbcTemplate.create(new User(1, "Natalia", 32));
            jdbcTemplate.update(new User(1, "Antony", 27));
            User user = (User)jdbcTemplate.load(1, User.class).get();
            assertEquals(1, user.getId());
            assertEquals(27, user.getAge());
            assertEquals("Antony", user.getName());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @AfterEach
    public void close() {
        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
