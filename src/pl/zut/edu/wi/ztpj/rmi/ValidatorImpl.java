package pl.zut.edu.wi.ztpj.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import pl.zut.edu.wi.ztpj.jdbc.Services.UserService;

@SuppressWarnings("serial")
public class ValidatorImpl extends UnicastRemoteObject implements Validator 
{
	private UserService daoUser = new UserService();
	
	public ValidatorImpl( ) throws RemoteException
	{}
	
	public boolean validate( String aUserName, String aPassword ) throws RemoteException 
	{
		System.out.println( "Username: " + aUserName + ", password: " + aPassword );
		
		return daoUser.getUser(aUserName, aPassword) != null;
	}
}
