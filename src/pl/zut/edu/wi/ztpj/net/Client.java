package pl.zut.edu.wi.ztpj.net;

import pl.zut.edu.wi.ztpj.EmployeeManagement;

public class Client 
{
	public static void main(String[] args) throws ClassNotFoundException 
	{
		Server server = new Server();
		server.setDaemon(true);
		server.start();
		EmployeeManagement employeeManagement = new EmployeeManagement();
		employeeManagement.menu();
	}
}
