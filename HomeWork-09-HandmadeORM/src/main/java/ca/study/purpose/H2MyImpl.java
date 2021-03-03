package ca.study.purpose;

import ca.study.purpose.DBClasses.Account;
import ca.study.purpose.DBClasses.User;
import ca.study.purpose.Executor.MyDbExecutor;
import ca.study.purpose.JdbcTemplate.JdbcTemplate;
import ca.study.purpose.JdbcTemplate.MyDataSource;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class H2MyImpl {

    public static void main(String[] args) throws SQLException, IllegalAccessException {
        H2MyImpl demo = new H2MyImpl();
        MyDataSource myDataSource = new MyDataSource();
        MyDbExecutor<User> userExecutor = new MyDbExecutor<>(myDataSource.getConnection());
        MyDbExecutor<Account> accExecutor = new MyDbExecutor<>(myDataSource.getConnection());
        demo.createUserTable(new MyDataSource().getConnection());
        demo.createAccountTable(myDataSource.getConnection());

        List<Object> list = new ArrayList<>();
        list.add("Jenifer");
        list.add(25F);
        List<Object> list2 = new ArrayList<>();
        list2.add("Osvald");
        list2.add(65D);
        userExecutor.executeDBStatement("INSERT INTO User(Name, Age) VALUES (?, ?)", list);
        userExecutor.executeDBStatement("INSERT INTO User(Name, Age) VALUES (?, ?)", list);
        userExecutor.executeDBStatement("INSERT INTO User(Name, Age) VALUES (?, ?)", list2);
        Optional<User> user = userExecutor.selectRecord("SELECT * FROM User Where Id = ?", 3, resultSet -> {
            try {
                if (resultSet.next()) {
                    return new User(resultSet.getLong(1), resultSet.getString(2), resultSet.getLong(3));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        });
        System.out.println(user);

        JdbcTemplate<User> jdbcTemplate1 = new JdbcTemplate<>(myDataSource);
        jdbcTemplate1.create(new User(2, "Sam", 33));

        JdbcTemplate<Account> jdbcTemplate2 = new JdbcTemplate<>(myDataSource);
        jdbcTemplate2.create(new Account(2, "Credit", 657_000, false));

        jdbcTemplate2.load(1, Account.class);

//        User user1 = new User(0, "Samuel", 29);
//        User user2 = new User(0, "Jim", 21);
//        User user3 = new User(0, "Samantha", 36);

//        JdbcTemplate<User> jdbcTemplate = new JdbcTemplate<User>(new MyDataSource());
//        long userId1 = jdbcTemplate.create(user1);
//        user1.setId(userId1);
//        long userId2 = jdbcTemplate.create(user2);
//        user2.setId(userId2);
//        long userId3 = jdbcTemplate.create(user3);
//        user3.setId(userId3);
//
//        Optional<User> load1 = jdbcTemplate.load(2, User.class);
//        System.out.println(load1);
//        Optional<User> load2 = jdbcTemplate.load(3, User.class);
//        System.out.println(load2);
//
//        jdbcTemplate.update(new User(3, "Monica", 56));
//        Optional<User> load5 = jdbcTemplate.load(3, User.class);
//        System.out.println(load5);
//
//        System.out.println("----------------------------");
//
//        Account account1 = new Account(0, "Credit", 14_000);
//        Account account2 = new Account(0, "Debit", 9_500);
//        Account account3 = new Account(0, "Debit", 1_632_233);
//
//        JdbcTemplate<Account> jdbcTemplate2 = new JdbcTemplate<>(new MyDataSource());
//
//        long accountNo1 = jdbcTemplate2.create(account1);
//        account1.setNo(accountNo1);
//        long accountNo2 = jdbcTemplate2.create(account2);
//        account2.setNo(accountNo2);
//        long accountNo3 = jdbcTemplate2.create(account3);
//        account3.setNo(accountNo3);
//
//        Optional<Account> load3 = jdbcTemplate2.load(3, Account.class);
//        System.out.println(load3);
//        Optional<Account> load4 = jdbcTemplate2.load(1, Account.class);
//        System.out.println(load4);
//
//        jdbcTemplate2.update(new Account(3, "Credit(VISA)", 554_846));
//        Optional<Account> load6 = jdbcTemplate2.load(3, Account.class);
//        System.out.println(load6);

    }


    public void createUserTable(Connection connection) throws SQLException {
        try (PreparedStatement pst = connection.prepareStatement("create table User(id bigint(20) NOT NULL auto_increment, name varchar(255), age int(3))")) {
            pst.executeUpdate();
        }
    }

    public void createAccountTable(Connection connection) throws SQLException {
        try (PreparedStatement pst = connection.prepareStatement("create table Account(no bigint(20) NOT NULL auto_increment, type varchar(255), rest number, locked boolean)")) {
            pst.executeUpdate();
        }
    }
}
