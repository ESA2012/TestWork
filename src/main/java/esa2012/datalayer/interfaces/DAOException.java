package esa2012.datalayer.interfaces;

import java.sql.SQLException;

public class DAOException extends Exception {

    private int errorCode;

    public DAOException(Throwable cause) {
        super(cause);
        if (cause instanceof SQLException) {
            errorCode = ((SQLException)cause).getErrorCode();
        }
    }

    public DAOException(String message) {
        super(message);
    }

    public DAOException(String message, Throwable cause) {
        super(message, cause);
        if (cause instanceof SQLException) {
            errorCode = ((SQLException)cause).getErrorCode();
        }

    }

    public int getSQLErrorCode() {
        return errorCode;
    }



}
