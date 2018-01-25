package pl.zut.edu.wi.ztpj.net;

import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import pl.zut.edu.wi.ztpj.jdbc.Models.Employees;
import pl.zut.edu.wi.ztpj.jdbc.Services.DirectorService;
import pl.zut.edu.wi.ztpj.jdbc.Services.TraderService;
import pl.zut.edu.wi.ztpj.rmi.ValidatorImpl;

public class Server extends Thread
{
	private static DirectorService daoDirector = new DirectorService();
	private static TraderService daoTrader = new TraderService();
	static int port = 8080;
		
	@Override
	public void run()
	{
		ServerSocket mainSocket = null;
		
		try 
		{		
			ValidatorImpl object = new ValidatorImpl(); 
			Context context = new InitialContext();
			context.rebind("rmi:validator", object); 
			System.out.println("RMI started...");
		
			mainSocket = new ServerSocket(port);
			System.out.println("Server is listening at " + InetAddress.getLocalHost() + " on port " + mainSocket.getLocalPort());

			while(true) 
			{
				Socket clientSocket = mainSocket.accept();
				
				DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());				
				JAXBContext jaxbContext = JAXBContext.newInstance(Employees.class);
				Marshaller m = jaxbContext.createMarshaller();
				
				Employees employees = new Employees();
				employees.setTraders(daoTrader.getTraders());
				employees.setDirectors(daoDirector.getDirectors());
				
				m.marshal(employees, out);
				out.close();
			}
		} 
		catch(Exception ex) 
		{ 
			ex.printStackTrace();
		} 
		finally 
		{
			try 
			{
				if (mainSocket != null)
					mainSocket.close();
			}
			catch (Exception exc) 
			{}
		}
	}
}

