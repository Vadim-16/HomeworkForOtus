package ca.study.purpose;

import java.sql.*;
import java.util.Optional;

public class H2MyImpl  {
    private static final String URL = "jdbc:h2:mem:";

    public static void main(String[] args) throws SQLException{
        H2MyImpl demo = new H2MyImpl();
        Connection connection = demo.getConnection();
        demo.createUserTable(connection);
        demo.createAccountTable(connection);


        User user1 = new User(0, "Samuel", 29);
        User user2 = new User(0, "Jim", 21);
        User user3 = new User(0, "Samantha", 36);

        JdbcTemplate<User> jdbcTemplate = new JdbcTemplate<>(connection);
        long userId1 = jdbcTemplate.create(user1);
        user1.setId(userId1);
        long userId2 = jdbcTemplate.create(user2);
        user2.setId(userId2);
        long userId3 = jdbcTemplate.create(user3);
        user3.setId(userId3);

        Optional<User> load1 = jdbcTemplate.load(2, User.class);
        System.out.println(load1);
        Optional<User> load2 = jdbcTemplate.load(3, User.class);
        System.out.println(load2);

        jdbcTemplate.update(new User(3, "Monica", 56));
        Optional<User> load5 = jdbcTemplate.load(3, User.class);
        System.out.println(load5);

        System.out.println("----------------------------");

        Account account1 = new Account(0, "Credit", 14_000);
        Account account2 = new Account(0, "Debit", 9_500);
        Account account3 = new Account(0, "Debit", 1_632_233);

        JdbcTemplate<Account> jdbcTemplate2 = new JdbcTemplate<>(connection);

        long accountNo1 = jdbcTemplate2.create(account1);
        account1.setNo(accountNo1);
        long accountNo2 = jdbcTemplate2.create(account2);
        account2.setNo(accountNo2);
        long accountNo3 = jdbcTemplate2.create(account3);
        account3.setNo(accountNo3);

        Optional<Account> load3 = jdbcTemplate2.load(3, Account.class);
        System.out.println(load3);
        Optional<Account> load4 = jdbcTemplate2.load(1, Account.class);
        System.out.println(load4);

        jdbcTemplate2.update(new Account(3, "Credit(VISA)", 554_846));
        Optional<Account> load6 = jdbcTemplate2.load(3, Account.class);
        System.out.println(load6);

        connection.close();
    }

    public Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(URL);
        connection.setAutoCommit(false);
        return connection;
    }

    public void createUserTable(Connection connection) throws SQLException {
        try (PreparedStatement pst = connection.prepareStatement("create table User(id bigint(20) NOT NULL auto_increment, name varchar(255), age int(3))")) {
            pst.executeUpdate();
        }
    }

    public void createAccountTable(Connection connection) throws SQLException {
        try (PreparedStatement pst = connection.prepareStatement("create table Account(no bigint(20) NOT NULL auto_increment, type varchar(255), rest number)")) {
            pst.executeUpdate();
        }
    }
}
