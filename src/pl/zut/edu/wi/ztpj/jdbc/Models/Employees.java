package pl.zut.edu.wi.ztpj.jdbc.Models;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "employees")
public class Employees
{
	private List<Director> directors;
	private List<Trader> traders;
	
	
	public List<Director> getDirectors() 
	{
		return directors;
	}

	public void setDirectors(List<Director> directors) 
	{
		this.directors = directors;
	}

	public List<Trader> getTraders()
	{
		return traders;
	}

	public void setTraders(List<Trader> traders) 
	{
		this.traders = traders;
	}
}
