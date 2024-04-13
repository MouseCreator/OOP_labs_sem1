package mouse.project.lib.data.executor;

import mouse.project.lib.data.exception.ExecutorException;
import mouse.project.lib.data.orm.fill.ModelFill;
import mouse.project.lib.data.orm.map.OrmMap;
import mouse.project.lib.data.pool.ConnectionPool;
import mouse.project.lib.ioc.annotation.Auto;
import mouse.project.lib.ioc.annotation.Service;

import java.sql.*;

@Service
public class ExecutorImpl implements Executor {
    private final ModelFill fill;
    private final OrmMap map;
    private final ConnectionPool pool;
    @Auto
    public ExecutorImpl(ModelFill fill, OrmMap map, ConnectionPool pool) {
        this.fill = fill;
        this.map = map;
        this.pool = pool;
    }

    @Override
    public ExecutorResult executeQuery(String sql, Object... args) {
        if (args.length == 0) {
            return executePrepared(sql, args);
        }
        try(Connection connection = pool.getConnection()) {
            try(Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery(sql);
                return toResult(resultSet);
            }
        } catch (SQLException e) {
            throw new ExecutorException(e);
        }
    }

    private ExecutorResult executePrepared(String sql, Object... args) {
        int questionMarks = getQuestionMarks(sql);
        if (args.length != questionMarks) {
            String msg = String.format("Input sql has %d place holders, but received %d arguments", questionMarks, args.length);
            throw new ExecutorException(msg);
        }
        try (Connection connection = pool.getConnection()) {
            try(PreparedStatement ps = connection.prepareStatement(sql)) {
                for (int i = 0; i < args.length; i++) {
                    Object arg = args[i];
                    setParameter(ps, i, arg);
                }
                ResultSet resultSet = ps.executeQuery();
                return toResult(resultSet);
            }
        } catch (SQLException e) {
            throw new ExecutorException(e);
        }
    }

    private void setParameter(PreparedStatement ps, int i, Object arg) throws SQLException {
        ps.setObject(i, arg);
    }

    private int getQuestionMarks(String sql) {
        long count = sql.chars().filter(ch -> ch == '?').count();
        return (int) count;
    }

    public ExecutorResult toResult(ResultSet resultSet) {
        return new ExecutorResultImpl(resultSet, fill, map);
    }
}
