package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.http.HttpSession;

import utils.Constants;

public class Database {
	
    public static final String MYSQL_USERNAME = "adminGK9GXfL";	//System.getenv("MYSQL_USER");
    public static final String MYSQL_PASSWORD = "jLJwZbrJHGQ4";	//System.getenv("MYSQL_PASSWORD");
    public static final String MYSQL_DATABASE_HOST = System.getenv("MYSQL_SERVICE_HOST");
    public static final String MYSQL_DATABASE_PORT = System.getenv("MYSQL_SERVICE_PORT");
    public static final String MYSQL_DATABASE_NAME = "services";
    public static final String MYSQL_DATABASE_DRIVER = "com.mysql.jdbc.Driver";

	    public static Connection getConnection(HttpSession session){
	        String url = "";
	        Connection con = null;
	        
	        if (session.getAttribute(Constants.DB_CONNECTION) != null) {
	        	con = (Connection) session.getAttribute(Constants.DB_CONNECTION);
	        	System.out.println("Found connection in Session: " + con.toString());
	        }
	        else {
	            try {
	            	System.out.println("user: " + MYSQL_USERNAME);
	            	System.out.println("pass: " + MYSQL_PASSWORD);
	            	System.out.println("host: " + MYSQL_DATABASE_HOST);
	            	System.out.println("port: " + MYSQL_DATABASE_PORT);
	                url = "jdbc:mysql://" + MYSQL_DATABASE_HOST + ":" + MYSQL_DATABASE_PORT + "/" + MYSQL_DATABASE_NAME;
	                Class.forName(MYSQL_DATABASE_DRIVER);
	                con = DriverManager.getConnection(url, MYSQL_USERNAME, MYSQL_PASSWORD);
	            } catch (SQLException e) {
	                e.printStackTrace();
	            } catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	        return con;
	    }
}
