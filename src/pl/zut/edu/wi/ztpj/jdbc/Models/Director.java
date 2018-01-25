package pl.zut.edu.wi.ztpj.jdbc.Models;

import javax.xml.bind.annotation.XmlType;

@SuppressWarnings("serial")
@XmlType(name = "director")
public class Director extends Employee 
{
	private Integer salaryAddition;
	private String cardNumber;
	private Integer costsLimitPerMonth;
	
	public Director() 
	{
		super();
	}
	
	public Director(String firstName, String lastName, Integer salary, String phoneNumber, Integer salaryAddition, String cardNumber, Integer costsLimitPerMonth) 
	{
		super(firstName, lastName, salary, phoneNumber);
		this.salaryAddition = salaryAddition;
		this.cardNumber = cardNumber;
		this.costsLimitPerMonth = costsLimitPerMonth;
	}
	
	public Integer getSalaryAddition() 
	{
		return salaryAddition;
	}
	
	public void setSalaryAddition(Integer salaryAddition) 
	{
		this.salaryAddition = salaryAddition;
	}
	
	public String getCardNumber() 
	{
		return cardNumber;
	}
	
	public void setCardNumber(String cardNumber) 
	{
		this.cardNumber = cardNumber;
	}
	
	public Integer getCostsLimitPerMonth() 
	{
		return costsLimitPerMonth;
	}
	
	public void setCostsLimitPerMonth(Integer costsLimitPerMonth) 
	{
		this.costsLimitPerMonth = costsLimitPerMonth;
	}
}
