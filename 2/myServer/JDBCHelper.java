package myServer;
import java.sql.*;

public class JDBCHelper {
    private Connection connection;
    private Statement statement;
    private PreparedStatement preparedStatement;

    public JDBCHelper(String url, String username, String password) throws SQLException {
        connection = DriverManager.getConnection(url, username, password);
    }

    public void executeQuery(String query) throws SQLException {
        statement = connection.createStatement();
        statement.execute(query);
    }

    public ResultSet executeQueryWithResult(String query) throws SQLException {
        statement = connection.createStatement();
        return statement.executeQuery(query);
    }

    public void close() {
        try {
            if (statement != null)
                statement.close();
            if (preparedStatement != null)
                preparedStatement.close();
            if (connection != null)
                connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
