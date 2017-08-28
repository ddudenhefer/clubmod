package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
	
    public static final String MYSQL_USERNAME = System.getenv("MYSQL_USER");
    public static final String MYSQL_PASSWORD = System.getenv("MYSQL_PASSWORD");
    public static final String MYSQL_DATABASE_HOST = System.getenv("MYSQL_SERVICE_HOST");
    public static final String MYSQL_DATABASE_PORT = System.getenv("MYSQL_SERVICE_PORT");
    public static final String MYSQL_DATABASE_NAME = "services";
    public static final String MYSQL_DATABASE_DRIVER = "com.mysql.jdbc.Driver";

	    public static Connection getConnection(){
	        String url = "";
	        Connection con = null;
            try {
                url = "jdbc:mysql://" + MYSQL_DATABASE_HOST + ":" + MYSQL_DATABASE_PORT + "/" + MYSQL_DATABASE_NAME;
                Class.forName(MYSQL_DATABASE_DRIVER);
                con = DriverManager.getConnection(url, MYSQL_USERNAME, MYSQL_PASSWORD);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        return con;
	    }
}
