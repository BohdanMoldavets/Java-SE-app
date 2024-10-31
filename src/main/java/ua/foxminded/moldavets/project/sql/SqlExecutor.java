package ua.foxminded.moldavets.project.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface SqlExecutor<T> {

    T execute(PreparedStatement ps) throws SQLException;

}
