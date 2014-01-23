package ru.org.icad.mishka.app.jdbc;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;

/**
 * User: Boss
 * Date: 9/22/13
 * Time: 1:02 AM
 */
public class JDBCTool {
    private DataSource ds;

    private static JDBCTool instance;

    private JDBCTool() {
    }

    private void init() {
        try {
            InitialContext context = new InitialContext();
            this.ds = (DataSource) context.lookup("jdbc/MishkaDS");
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }

    public static JDBCTool instance(){
        if(instance == null){
            synchronized (JDBCTool.class){
                if(instance == null){
                    JDBCTool newOne = new JDBCTool();
                    newOne.init();
                    instance = newOne;
                }
            }
        }
        return instance;
    }

    public<T> T executeQuery(String query, JDBCHandler<T> handler) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;
        try {
            connection = ds.getConnection();
            statement = connection.prepareStatement(query);
            handler.parametrize(statement);
            result = statement.executeQuery();
            return handler.onResultSet(result);
        } finally {
            try {
                if(result != null){
                    result.close();
                }
            } finally {
                try {
                    if(statement != null){
                        statement.close();
                    }
                } finally {
                    if(connection != null){
                        connection.close();
                    }
                }
            }
        }
    }

    public int executeUpdate(String query) throws SQLException {
        return executeUpdate(query, new ParamsProvider(){
            @Override
            public void parametrize(PreparedStatement statement) throws SQLException {

            }
        });
    }

    public int executeUpdate(String query, JDBCHandler handler) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;
        try {
            connection = ds.getConnection();
            statement = connection.prepareStatement(query);
            handler.parametrize(statement);
            return statement.executeUpdate();
        } finally {
            try {
                if(result != null){
                    result.close();
                }
            } finally {
                try {
                    if(statement != null){
                        statement.close();
                    }
                } finally {
                    if(connection != null){
                        connection.close();
                    }
                }
            }
        }
    }

    public int executeCommand(String query) throws SQLException {
        Connection connection = null;
        Statement statement = null;
        ResultSet result = null;
        try {
            connection = ds.getConnection();
            statement = connection.createStatement();
            return statement.executeUpdate(query);
        } finally {
            try {
                if(result != null){
                    result.close();
                }
            } finally {
                try {
                    if(statement != null){
                        statement.close();
                    }
                } finally {
                    if(connection != null){
                        connection.close();
                    }
                }
            }
        }
    }

    public Boolean selectBoolean(String query, final ParamsProvider params) throws SQLException {
        JDBCHandler<Boolean> handler = new JDBCHandler<Boolean>(){
            @Override
            public Boolean onResultSet(ResultSet rs) throws SQLException {
                if(rs.next()){
                    return rs.getBoolean(1);
                }
                return false;
            }

            @Override
            public void parametrize(PreparedStatement statement) throws SQLException {
                if(params !=null){
                    params.parametrize(statement);
                }
            }
        };
        return executeQuery(query, handler);
    }

    public String selectString(String query, final ParamsProvider params) throws SQLException {
        JDBCHandler<String> handler = new JDBCHandler<String>(){
            @Override
            public String onResultSet(ResultSet rs) throws SQLException {
                if(rs.next()){
                    return rs.getString(1);
                }
                return null;
            }

            @Override
            public void parametrize(PreparedStatement statement) throws SQLException {
                if(params !=null){
                    params.parametrize(statement);
                }
            }
        };
        return executeQuery(query, handler);
    }

    public Integer selectInt(String query, final ParamsProvider params) throws SQLException {
        JDBCHandler<Integer> handler = new JDBCHandler<Integer>(){
            @Override
            public Integer onResultSet(ResultSet rs) throws SQLException {
                if(rs.next()){
                    return rs.getInt(1);
                }
                return null;
            }

            @Override
            public void parametrize(PreparedStatement statement) throws SQLException {
                if(params !=null){
                    params.parametrize(statement);
                }
            }
        };
        return executeQuery(query, handler);
    }

}
