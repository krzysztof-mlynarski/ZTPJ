package pl.zut.edu.wi.ztpj.jdbc.Services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import pl.zut.edu.wi.ztpj.jdbc.Jdbc;
import pl.zut.edu.wi.ztpj.jdbc.Models.Director;
import pl.zut.edu.wi.ztpj.jdbc.Models.Employee;

public class DirectorService
{
	private Jdbc jdbc = new Jdbc();
	
	public Director getDirector(Integer id) 
	{
		String query = "SELECT * FROM employees WHERE position = ? AND id = ?";
		
        try(Connection connection = jdbc.getConnection())
        {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, "Director");
			preparedStatement.setInt(2, id);

			ResultSet resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next())
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

	public List<Director> getDirectors() throws ClassNotFoundException 
	{
        List<Director> result = new ArrayList<>();
        String query = "SELECT * FROM employees WHERE position = ?";
        
        try(Connection connection = jdbc.getConnection())
        {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "Director");
            
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

	public void addDirector(Employee director) throws ClassNotFoundException 
	{
        String query = "INSERT INTO employees (`firstName`, `lastName`, `salary`, `phoneNumber`, `position`, `salaryAddition`, `cardNumber`, `costsLimitPerMonth`) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";

        try(Connection connection = jdbc.getConnection())
        {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, director.getFirstName());
            preparedStatement.setString(2, director.getLastName());
            preparedStatement.setInt(3, director.getSalary());
		    preparedStatement.setString(4, director.getPhoneNumber());
		    preparedStatement.setString(5, director.getClass().getSimpleName());
		    preparedStatement.setInt(6, ((Director)director).getSalaryAddition());
		    preparedStatement.setString(7, ((Director)director).getCardNumber());
		    preparedStatement.setInt(8, ((Director)director).getCostsLimitPerMonth());
		                
		    preparedStatement.executeUpdate();
		    
		    connection.commit();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

	public void deleteDirector(Integer id) throws ClassNotFoundException 
	{
    	String query = "DELETE FROM employees WHERE id = ?";
        
        try(Connection connection = jdbc.getConnection())
        {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
                   
            preparedStatement.executeUpdate();
            
            connection.commit();
        } 
        catch (Exception e) 
        {
			e.printStackTrace();
        }
	}
	
    private Director resultToObject(ResultSet rs) 
    {
        Director object = new Director();
                
        try 
        {
            object.setId(rs.getInt("id"));
            object.setFirstName(rs.getString("firstName"));
            object.setLastName(rs.getString("lastName"));
            object.setSalary(rs.getInt("salary"));
            object.setPhoneNumber(rs.getString("phoneNumber"));
            object.setSalaryAddition(rs.getInt("salaryAddition"));
            object.setCardNumber(rs.getString("cardNumber"));
            object.setCostsLimitPerMonth(rs.getInt("costsLimitPerMonth"));
        } 
        catch (Exception e) 
        {
			e.printStackTrace();
        }
        
        return object;
    }
}
