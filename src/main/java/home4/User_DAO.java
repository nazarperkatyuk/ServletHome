package home4;

import java.util.Optional;
import java.sql.*;

public class User_DAO {
    private Connection connection;

    public static final String SELECT = "SELECT * FROM user;";
    private static final String INSERT = "insert (first_name, last_name, email, password) values (?, ?, ?, ?)";


    public void insert(String firstName, String lastName, String email, String password) throws SQLException {
        connection = ConnectionUtil.getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement(INSERT);

        preparedStatement.setString(1, firstName);
        preparedStatement.setString(2, lastName);
        preparedStatement.setString(3, email);
        preparedStatement.setString(4, password);

        preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
    }

    public Optional<User> getUserByEmail(String email) {
        connection = ConnectionUtil.getConnection();

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement(SELECT);
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();

            resultSet.next();
            Optional <User> user = Optional.of(User.of(resultSet));

            connection.close();
            preparedStatement.close();
            resultSet.close();

            return user;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Can`t get a user my email");
        }
    }
}