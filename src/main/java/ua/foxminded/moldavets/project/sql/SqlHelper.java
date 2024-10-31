package ua.foxminded.moldavets.project.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {

    private final ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public void execute(String sqlQuery) {
        execute(sqlQuery, PreparedStatement::execute);
    }

    public <T> T execute(String sqlQuery, SqlExecutor<T> executor) {
        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            return executor.execute(preparedStatement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T transactionalExecute(SqlTransaction<T> executor) {
        try (Connection connection = connectionFactory.getConnection()) {
            try {
                connection.setAutoCommit(false);
                T result = executor.execute(connection);
                connection.commit();
                return result;
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
