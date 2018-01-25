package pl.zut.edu.wi.ztpj.jdbc.Services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import pl.zut.edu.wi.ztpj.jdbc.Jdbc;
import pl.zut.edu.wi.ztpj.jdbc.Models.Employee;
import pl.zut.edu.wi.ztpj.jdbc.Models.Trader;

public class TraderService
{
	private Jdbc jdbc = new Jdbc();

	public Trader getTrader(Integer id)
	{
        String query = "SELECT * FROM employees WHERE position = ? AND id = ?";
        
        try(Connection connection = jdbc.getConnection())
        {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "Trader");
            preparedStatement.setInt(2, id);
            
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

	public List<Trader> getTraders() 
	{
        List<Trader> result = new ArrayList<>();
        String query = "SELECT * FROM employees WHERE position = ?";
        
        try(Connection connection = jdbc.getConnection())
        {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "Trader");
            
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

	public void addTrader(Employee trader) 
	{
        String query = "INSERT INTO employees (`firstName`, `lastName`, `salary`, `phoneNumber`, `position`, `provision`, `provisionLimit`) "
        			 + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try(Connection connection = jdbc.getConnection())
        {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, trader.getFirstName());
            preparedStatement.setString(2, trader.getLastName());
            preparedStatement.setInt(3, trader.getSalary());
            preparedStatement.setString(4, trader.getPhoneNumber());
            preparedStatement.setString(5, trader.getClass().getSimpleName());
            preparedStatement.setInt(6, ((Trader)trader).getProvision());
            preparedStatement.setInt(7, ((Trader)trader).getProvisionLimit());
                        
            preparedStatement.executeUpdate();
            
            connection.commit();
        } 
        catch (Exception e) 
        {
			e.printStackTrace();
        }
	}

	public void deleteTrader(Integer id) 
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

    private Trader resultToObject(ResultSet rs) {
        Trader object = new Trader();
                
        try
        {
            object.setId(rs.getInt("id"));
            object.setFirstName(rs.getString("firstName"));
            object.setLastName(rs.getString("lastName"));
            object.setSalary(rs.getInt("salary"));
            object.setPhoneNumber(rs.getString("phoneNumber"));
            object.setProvision(rs.getInt("provision"));
            object.setProvisionLimit(rs.getInt("provisionLimit"));
        } 
        catch (Exception e) 
        {
			e.printStackTrace();
        }
        
        return object;
    }
}
