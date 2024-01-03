package com.example.mkt.exceptions;

import java.sql.SQLException;

public class DataBaseException extends SQLException {
    public DataBaseException(Throwable cause) {
        super(cause);
    }
}