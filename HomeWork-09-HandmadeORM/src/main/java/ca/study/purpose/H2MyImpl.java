package ca.study.purpose;

import java.sql.*;

public class H2MyImpl  {
    private static final String URL = "jdbc:h2:mem:";

    public static void main(String[] args) throws SQLException, IllegalAccessException {
        H2MyImpl demo = new H2MyImpl();
        Connection connection = demo.getConnection();
        demo.createUserTable(connection);

        demo.createAccountTable(connection);


        JdbcTemplate<User> jdbcTemplate = new JdbcTemplate<>(connection);

        long userId1 = jdbcTemplate.createUser(new User(0, "Samuel", 29));
        System.out.println("created user:" + userId1);

        long userId2 = jdbcTemplate.createUser(new User(0, "Jim", 21));
        System.out.println("created user:" + userId2);

        long userId3 = jdbcTemplate.createUser(new User(0, "Samantha", 36));
        System.out.println("created user:" + userId3);

        connection.commit();

//        Optional<User> user = executor.selectRecord("select id, name from user where id  = ?", userId, resultSet -> {
//            try {
//                if (resultSet.next()) {
//                    return new User(resultSet.getLong("id"), resultSet.getString("name"), resultSet.getLong("age"));
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//            return null;
//        });
//        System.out.println(user);

        connection.close();
    }

    private Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(URL);
        connection.setAutoCommit(false);
        return connection;
    }

    private void createUserTable(Connection connection) throws SQLException {
        try (PreparedStatement pst = connection.prepareStatement("create table User(id bigint(20) NOT NULL auto_increment, name varchar(255), age int(3))")) {
            pst.executeUpdate();
        }
    }

    private void createAccountTable(Connection connection) throws SQLException {
        try (PreparedStatement pst = connection.prepareStatement("create table Account(no bigint(20) NOT NULL auto_increment, type varchar(255), rest number)")) {
            pst.executeUpdate();
        }
    }
}
