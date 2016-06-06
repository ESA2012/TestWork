package esa2012.datalayer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DBService {
    private static DataSource dataSource = null;
    private static final Logger logger = LogManager.getLogger(DBService.class);

    private static DBService instance;

    private DBService() {
        try {
            Context initContext = new InitialContext();
            Context context = (Context) initContext.lookup("java:/comp/env");
            dataSource = (DataSource) context.lookup("jdbc/TestWorkDS");
            logger.info("Data source context lookup OK.");
        } catch (NamingException e) {
            logger.error("Data source lookup failed. "+e.getMessage());
        }
    }


    public static synchronized DBService getInstance() {
        if (instance == null) {
            instance = new DBService();
        }
        return instance;
    }


    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }


    public static void close(Statement statement, ResultSet resultSet) throws SQLException {
        if (resultSet !=null && !resultSet.isClosed()) {
            resultSet.close();
        }
        if (statement != null && !statement.isClosed()) {
            statement.close();
        }
    }


    public static void close(Connection connection) throws SQLException{
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }


    public static void executeSQLfile(File file, Connection connection) throws SQLException, IOException {
        BufferedReader in = Files.newBufferedReader(file.toPath(), Charset.forName("UTF-8"));
        String sql = "";
        String s;
        while ((s = in.readLine()) != null) {
            sql = sql.concat(s);
        }
        in.close();
        String[] st = sql.split(";");
        Statement statement = connection.createStatement();
        for(String s1: st) {
            if(!s1.trim().equals("")) {
                statement.executeUpdate(s1.trim());
            }
        }
        close(statement, null);
        close(connection);
    }

}
