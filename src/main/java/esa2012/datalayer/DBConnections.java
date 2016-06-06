package esa2012.datalayer;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;


public class DBConnections {
    private static DataSource dataSource = null;

    static {
        try {
            Context initContext = new InitialContext();
            Context context = (Context) initContext.lookup("java:/comp/env");
            dataSource = (DataSource) context.lookup("jdbc/TestWorkDS");
        } catch (NamingException e) {

        }
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

}
