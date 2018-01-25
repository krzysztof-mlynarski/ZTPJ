package pl.zut.edu.wi.ztpj.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Validator extends Remote
{
	public boolean validate(String aUserName, String aPassword) throws RemoteException;
}
