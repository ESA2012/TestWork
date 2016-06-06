package esa2012.datalayer;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by snake on 06.06.16.
 */
public class DBUtils {

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

}
