import ca.study.purpose.DBClasses.Account;
import ca.study.purpose.H2MyImpl;
import ca.study.purpose.JdbcTemplate.JdbcTemplateImpl;
import ca.study.purpose.DBClasses.User;
import ca.study.purpose.JdbcTemplate.MyDataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class JdbcTemplateImplTester {
    private JdbcTemplateImpl<User> userJdbcTemplateImpl;
    private JdbcTemplateImpl<Account> accJdbcTemplateImpl;
    private Connection connection;

    @BeforeEach
    public void setUp() {
        MyDataSource myDataSource = new MyDataSource();
        try {
            this.connection = myDataSource.getConnection();
            userJdbcTemplateImpl = new JdbcTemplateImpl<>(myDataSource);
            accJdbcTemplateImpl = new JdbcTemplateImpl<>(myDataSource);
            H2MyImpl demo = new H2MyImpl();
            demo.createAccountTable(connection);
            demo.createUserTable(connection);
        } catch (SQLException ignored) {
        }
    }

    @Test
    public void testCreateLoadUser() {
        userJdbcTemplateImpl.create(new User(2, "Natalia", 32));
        userJdbcTemplateImpl.create(new User(3, "Alex", 34));
        Optional<User> load = userJdbcTemplateImpl.load(3, User.class);
        if (load.isPresent()) {
            User user = load.get();
            assertEquals(3, user.getId());
            assertEquals("Alex", user.getName());
            assertEquals(34, user.getAge());
        }
    }

    @Test
    public void testCreateLoadAccount() {
        accJdbcTemplateImpl.create(new Account(2, "Credit", 100_000, true));
        accJdbcTemplateImpl.create(new Account(3, "Debit", 145_742, false));
        Optional<Account> load = accJdbcTemplateImpl.load(3, Account.class);
        if (load.isPresent()) {
            Account acc = load.get();
            assertEquals(3, acc.getNo());
            assertEquals("Debit", acc.getType());
            assertEquals(145_742, acc.getRest());
            assertFalse(acc.isLocked());
        }
    }

    @Test
    public void testUpdateAccount() {
        accJdbcTemplateImpl.create(new Account(1, "Credit", 100_000, true));
        accJdbcTemplateImpl.update(new Account(1, "Credit/Debit", 200_000, false));
        Optional<Account> load = accJdbcTemplateImpl.load(1, Account.class);
        if (load.isPresent()) {
            Account acc = load.get();
            assertEquals(1, acc.getNo());
            assertEquals("Credit/Debit", acc.getType());
            assertEquals(200_000, acc.getRest());
            assertFalse(acc.isLocked());
        }
    }

    @Test
    public void testUpdateUser() {
        userJdbcTemplateImpl.create(new User(1, "Emma", 35));
        userJdbcTemplateImpl.update(new User(1, "Nelson", 19));
        Optional<User> load = userJdbcTemplateImpl.load(1, User.class);
        if (load.isPresent()) {
            User user = load.get();
            assertEquals(1, user.getId());
            assertEquals("Nelson", user.getName());
            assertEquals(19, user.getAge());
        }
    }

    @AfterEach
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
