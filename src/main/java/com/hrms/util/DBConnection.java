package com.hrms.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DBConnection {
    public static Connection getConnection() throws Exception {
        Properties p = new Properties();
        try (InputStream in = DBConnection.class.getResourceAsStream("/db.properties")) {
            p.load(in);
        }
        return DriverManager.getConnection(
                p.getProperty("db.url"),
                p.getProperty("db.user"),
                p.getProperty("db.password"));
    }
}