package pl.zut.edu.wi.ztpj.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Jdbc 
{
	public Jdbc() {}
	
	private Connection connection = null;
		
	public Connection getConnection() throws ClassNotFoundException 
	{
		try 
		{
			if (connection == null || connection.isClosed()) 
			{			
				Class.forName("com.mysql.jdbc.Driver");
				connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ZTPJ?user=root&password=12345");
	            connection.setAutoCommit(false);
			}
		}
		catch(SQLException ex) 
		{
			ex.printStackTrace();
		} 

		return connection;
	}
}
