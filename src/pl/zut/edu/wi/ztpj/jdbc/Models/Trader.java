package pl.zut.edu.wi.ztpj.jdbc.Models;

import javax.xml.bind.annotation.XmlType;

@SuppressWarnings("serial")
@XmlType(name = "trader")
public class Trader extends Employee 
{	
	private Integer provision;
	private Integer provisionLimit;
	
	public Trader() 
	{
		super();
	}
	
	public Trader(String firstName, String lastName, Integer salary, String phoneNumber, Integer provision, Integer provisionLimit) 
	{
		super(firstName, lastName, salary, phoneNumber);
		this.provision = provision;
		this.provisionLimit = provisionLimit;
	}
	
	public Integer getProvision() 
	{
		return provision;
	}
	
	public void setProvision(Integer provision) 
	{
		this.provision = provision;
	}
	
	public Integer getProvisionLimit()
	{
		return provisionLimit;
	}
	
	public void setProvisionLimit(Integer provisionLimit) 
	{
		this.provisionLimit = provisionLimit;
	}
}
