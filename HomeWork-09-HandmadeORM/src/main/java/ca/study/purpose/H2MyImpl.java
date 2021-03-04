package ca.study.purpose;

import ca.study.purpose.DBClasses.Account;
import ca.study.purpose.DBClasses.User;
import ca.study.purpose.JdbcTemplate.JdbcTemplate;
import ca.study.purpose.JdbcTemplate.MyDataSource;

import java.sql.*;
import java.util.Optional;


public class H2MyImpl {

    public static void main(String[] args) throws SQLException {
        H2MyImpl demo = new H2MyImpl();
        MyDataSource myDataSource = new MyDataSource();
        demo.createUserTable(new MyDataSource().getConnection());
        demo.createAccountTable(myDataSource.getConnection());

        JdbcTemplate<User> jdbcTemplate1 = new JdbcTemplate<>(myDataSource);
        jdbcTemplate1.create(new User(1, "Sam", 19));
        jdbcTemplate1.create(new User(2, "Mark", 26));
        jdbcTemplate1.create(new User(3, "Aaron", 38));
        jdbcTemplate1.create(new User(4, "Sally", 43));

        JdbcTemplate<Account> jdbcTemplate2 = new JdbcTemplate<>(myDataSource);
        jdbcTemplate2.create(new Account(1, "Credit", 657_000, true));
        jdbcTemplate2.create(new Account(2, "Debit", 100, false));
        jdbcTemplate2.create(new Account(3, "Credit", 1_243_256, true));
        jdbcTemplate2.create(new Account(4, "Credit/Debit", 235_367, false));
        jdbcTemplate2.create(new Account(5, "Debit", 69_889, true));

        Optional<Account> load1 = jdbcTemplate2.load(1, Account.class);
        Optional<User> load2 = jdbcTemplate1.load(3, User.class);
        Optional<Account> load3 = jdbcTemplate2.load(4, Account.class);

        System.out.println(load1);
        System.out.println(load2);
        System.out.println(load3);

        jdbcTemplate2.update(new Account(4, "Credit", -10_000, true));
        Optional<Account> load4 = jdbcTemplate2.load(4, Account.class);
        System.out.println(load4);
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
