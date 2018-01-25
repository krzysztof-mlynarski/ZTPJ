package pl.zut.edu.wi.ztpj.jdbc.Services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import pl.zut.edu.wi.ztpj.jdbc.Jdbc;
import pl.zut.edu.wi.ztpj.jdbc.Models.User;

public class UserService
{
	private Jdbc jdbc = new Jdbc();
	
	public User getUser(String username, String password) 
	{
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";      
        
        try(Connection connection = jdbc.getConnection())
        {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, HashUtil.SHA256.hash(password));
			
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) 
            {
                return resultToObject(resultSet);
            }
        } 
        catch (Exception e) 
        {
			e.printStackTrace();
        }
        
        return null;
	}

	public List<User> getUsers() 
	{
    	List<User> result = new ArrayList<>();
    	String query = "SELECT * FROM users";
    	
        try(Connection connection = jdbc.getConnection())
        {
    		PreparedStatement preparedStatement = connection.prepareStatement(query);
    		
    		ResultSet resultSet = preparedStatement.executeQuery();
    		while(resultSet.next())
    		{
    			result.add(resultToObject(resultSet));
    		}
    	} 
    	catch (Exception e) 
    	{
			e.printStackTrace();
    	}
    	
    	return result;
	}

	public void addUser(String username, String password) 
	{
        String query = "INSERT INTO users (`username`, `password`) VALUES (?, ?)";
        
        try(Connection connection = jdbc.getConnection())
        {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, HashUtil.SHA256.hash(password));
                        
            preparedStatement.executeUpdate();
            
            connection.commit();
        } 
        catch (Exception e) 
        {
			e.printStackTrace();
        }
	}

	public void deleteUser(String username) 
	{
    	String query = "DELETE FROM users WHERE username = ?";
        
        try(Connection connection = jdbc.getConnection())
        {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
                        
            preparedStatement.executeUpdate();
            
            connection.commit();
        } 
        catch (Exception e) 
        {
			e.printStackTrace();
        }
	}
	
    private User resultToObject(ResultSet rs) 
    {
        User object = new User();
                
        try 
        {
            object.setId(rs.getInt("id"));
            object.setUsername(rs.getString("username"));
        }
        catch (SQLException e)
        {
			e.printStackTrace();
        }
        
        return object;
    }
}
