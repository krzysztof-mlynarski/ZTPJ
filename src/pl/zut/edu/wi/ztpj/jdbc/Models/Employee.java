package pl.zut.edu.wi.ztpj.jdbc.Models;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlTransient;

@SuppressWarnings("serial")
@XmlTransient
public abstract class Employee implements Serializable 
{
	protected Integer id;
	protected String firstName;
	protected String lastName;
	protected Integer salary;
	protected String phoneNumber;
	
	public Employee() {}
	
	public Employee(String firstName, String lastName, Integer salary, String phoneNumber) 
	{
		this.firstName = firstName;
		this.lastName = lastName;
		this.salary = salary;
		this.phoneNumber = phoneNumber;
	}
    
	public Integer getId() 
	{
		return id;
	}
    
	public void setId(Integer id) 
	{
		this.id = id;
	}
    
	public String getFirstName() 
	{
		return firstName;
	}
    
	public void setFirstName(String firstName) 
	{
		this.firstName = firstName;
	}
    
	public String getLastName() 
	{
		return lastName;
	}
    
	public void setLastName(String lastName) 
	{
		this.lastName = lastName;
	}
    
	public Integer getSalary()
	{
		return salary;
	}
    
	public void setSalary(Integer salary) 
	{
		this.salary = salary;
	}
    
	public String getPhoneNumber()
	{
		return phoneNumber;
	}
    
	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}
}
