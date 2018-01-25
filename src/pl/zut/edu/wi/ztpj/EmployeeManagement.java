package pl.zut.edu.wi.ztpj;

import java.io.DataInputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import pl.zut.edu.wi.ztpj.jdbc.Models.Director;
import pl.zut.edu.wi.ztpj.jdbc.Models.Employee;
import pl.zut.edu.wi.ztpj.jdbc.Models.Employees;
import pl.zut.edu.wi.ztpj.jdbc.Models.Trader;
import pl.zut.edu.wi.ztpj.jdbc.Models.User;
import pl.zut.edu.wi.ztpj.jdbc.Services.DirectorService;
import pl.zut.edu.wi.ztpj.jdbc.Services.TraderService;
import pl.zut.edu.wi.ztpj.jdbc.Services.UserService;
import pl.zut.edu.wi.ztpj.rmi.Validator;

public class EmployeeManagement 
{
	private List<Employee> employeeList;
	private Scanner scanner = new Scanner(System.in);
	
	DirectorService directorService = new DirectorService();
	TraderService traderService = new TraderService();
	UserService userService = new UserService();
	
	public EmployeeManagement()
	{
		employeeList = new ArrayList<>();
	}
	
	public void menu() throws ClassNotFoundException 
	{
		String pressedKey = "";
		do 
		{
			System.out.println("MENU");
			System.out.println("\t1. Lista pracownikow");
			System.out.println("\t2. Dodaj pracownika");
			System.out.println("\t3. Edytuj pracownika");
			System.out.println("\t4. Usun pracownika");
			System.out.println("\t5. Pobierz dane z sieci");
			System.out.println("\t6. Zarz¹dzaj uprawnieniami transferu danych");
			System.out.println("\t\n\n7. Wyjdz z programu");
			
			pressedKey = scanner.nextLine();
			
			switch (pressedKey) 
			{
				case "1":
					retrieveData();
					printAllEmployees(employeeList);
					break;
				case "2":
					addAnEmployee();
					break;
				case "3":
					//editAnEmployee();
					break;
				case "4":
					removeAnEmployee();
					break;
				case "5":
					retrieveDataFromNetwork();
					break;
				case "6":
					permissionsManagement();
				default:
					break;
			}
		} 
		while (!pressedKey.equals("7"));
	}
	
	public List<Employee> getEmployeeList() 
	{
		return employeeList;
	}
	
	private void retrieveData() throws ClassNotFoundException 
	{
		employeeList.clear();
		employeeList.addAll(directorService.getDirectors());
		employeeList.addAll(traderService.getTraders());
	}
	
	private void printInformation(Employee employee) 
	{
		System.out.println("\tIdentyfikator       	:   " + employee.getId());
		System.out.println("\tImiê                	:   " + employee.getFirstName());
		System.out.println("\tNazwisko            	:   " + employee.getLastName());
		System.out.println("\tWynagrodzenie      	:   " + employee.getSalary());
		System.out.println("\tStanowisko          	:   " + employee.getClass().getSimpleName());
		System.out.println("\tTelefon             	:   " + employee.getPhoneNumber());
		
		if (employee instanceof Director) 
		{
			System.out.println("\tDodatek sluzbowy        :   " + ((Director) employee).getSalaryAddition());
			System.out.println("\tKarta sluzbowa nr       :   " + ((Director) employee).getCardNumber());
			System.out.println("\tLimit kosztow/miesiac   :   " + ((Director) employee).getCostsLimitPerMonth());
		} 
		else if (employee instanceof Trader) 
		{
			System.out.println("\tProwizja %              :   " + ((Trader) employee).getProvision());
			System.out.println("\tLimit prowizji/miesiac  :   " + ((Trader) employee).getProvisionLimit());
		}
	}

	private void printAllEmployees(List<Employee> employees) 
	{
		String pressedKey = "n";
		
		System.out.println("1.1 Lista pracownikow\n");
		for(Employee employee : employees) 
		{
			if (pressedKey.equalsIgnoreCase("N"))
			{
				printInformation(employee);
			}
			else if (pressedKey.equalsIgnoreCase("Q"))
			{
				break;
			}
			
			System.out.println("\t-----------------------------------------------");
			System.out.println("[N] - nastepny");
			System.out.println("[Q] - powrot");
			pressedKey = scanner.nextLine();
		}
	}
	
	private void addAnEmployee() throws ClassNotFoundException 
	{
		Employee newEmployee = null;

		System.out.print("[D]yrektor/[H]andlowiec:\t");
		String readPosition = scanner.nextLine();
		
		if (readPosition.equalsIgnoreCase("D"))
		{
			newEmployee = new Director();
		}
		else if (readPosition.equalsIgnoreCase("H")) 
		{
			newEmployee = new Trader();
		}
		
		System.out.print("Imie:\t\t\t");
		String tForname = scanner.nextLine();
		System.out.print("Nazwisko:\t\t");
		String tSurname = scanner.nextLine();
		System.out.print("Wynagrodzenie:\t\t");
		String tSalary = scanner.nextLine();
		System.out.print("Telefon:\t\t");
		String tPhone = scanner.nextLine();
		
		if (newEmployee instanceof Director) 
		{
			System.out.print("Dodatek sluzbowy:\t");
			String tDutyAddition = scanner.nextLine();
			System.out.print("Karta sluzbowa:\t\t");
			String tDutyCard = scanner.nextLine();
			System.out.print("Limit kosztow/miesiac:\t");
			String tCostsLimit = scanner.nextLine();
			
			newEmployee = new Director(tForname, tSurname, Integer.parseInt(tSalary), tPhone, Integer.parseInt(tDutyAddition), tDutyCard, Integer.parseInt(tCostsLimit));
		} 
		else 
		{
			System.out.print("Prowizja %:\t\t");
			String tProvision = scanner.nextLine();
			System.out.print("Limit prowizji/miesiac:\t");
			String tProvisionLimit = scanner.nextLine();
			
			newEmployee = new Trader(tForname, tSurname, Integer.parseInt(tSalary), tPhone, Integer.parseInt(tProvision), Integer.parseInt(tProvisionLimit));
		}
		System.out.println("\t-----------------------------------------------");
		System.out.println("[A] - dodaj");
		System.out.println("[Q] - porzuc");

		String pressedKey = scanner.nextLine();
		while (!pressedKey.equalsIgnoreCase("Q")) 
		{
			if (pressedKey.equalsIgnoreCase("A")) 
			{
				if (newEmployee instanceof Director)
				{
					try 			
					{					
						directorService.addDirector(newEmployee);			
					} 
					catch (Exception e) 
					{
						e.printStackTrace();
					}
				}
				else 
				{
					traderService.addTrader(newEmployee);
				}
				break;
			}
			System.out.println("Nie rozpoznano klawisza");
			
			pressedKey = scanner.nextLine();
		}
	}
		
	private void removeAnEmployee() 
	{
		System.out.println("4.1 Usun pracownika\n");
		System.out.print("\tPodaj identyfikator    :        ");
		int index = scanner.nextInt();
		System.out.println("\t-----------------------------------------------");
		
		if (index != 0) 
		{			
			Employee employee = directorService.getDirector(index);
			if (employee == null)
			{
				employee = traderService.getTrader(index);
			}
			
			if (employee != null) 
			{
				printInformation(employee);
				
				System.out.println("\t-----------------------------------------------");
				System.out.println("[R] - usun");
				System.out.println("[Q] - porzuc");

				String pressedKey = scanner.nextLine();
				while (!pressedKey.equalsIgnoreCase("Q")) 
				{
					if (pressedKey.equalsIgnoreCase("R")) 
					{
						try 
						{
							if (employee instanceof Director)
							{
								directorService.deleteDirector(index);
							} 
							else 
							{
								traderService.deleteTrader(index);
							}
						} 
						catch(Exception e) 
						{
							e.printStackTrace();
						}
						break;
					}
					else 
					{
						pressedKey = scanner.nextLine();
					}
				}
			}
		}
	}
	
	private void retrieveDataFromNetwork() 
	{
		String host = "127.0.0.1";
		
		System.out.println("5.1 Pobierz dane z sieci\n");
		System.out.print("\tAdres      : ");
		host = scanner.nextLine();
		System.out.print("\tPort       : ");
		int port = scanner.nextInt();
		scanner.nextLine();
		
		System.out.print("\tU¿ytkownik : ");
		String username = scanner.nextLine();
		System.out.print("\tHas³o      : ");
		String password = scanner.nextLine();
				
		if (System.getSecurityManager() == null) 
		{
	        System.setSecurityManager(new SecurityManager());
	    }
		
		try 
		{
			Context context = new InitialContext( );
			Validator remote = (Validator)context.lookup("rmi://" + host + "/validator");
			if (remote.validate(username, password)) 
			{
				if (!host.isEmpty() && port != 0) 
				{
					try (Socket echoSocket = new Socket(host, port);
						DataInputStream in = new DataInputStream(echoSocket.getInputStream())) {
						System.out.println("\t-----------------------------------------------");
						System.out.println("\tPo³¹czenie :        Sukces");
						System.out.println("\t-----------------------------------------------");
						
						System.out.println("Czy jesteœ pewien? [T]/[N]");

						String pressedKey = scanner.nextLine();
						while (!pressedKey.equalsIgnoreCase("N")) 
						{
							if (pressedKey.equalsIgnoreCase("T")) 
							{
								
								try 
								{
									JAXBContext jaxbContext = JAXBContext.newInstance(Employees.class);
									Unmarshaller um = jaxbContext.createUnmarshaller();

									Employees employees = (Employees)um.unmarshal(in);
									List<Employee> employeesList = new ArrayList<Employee>();
									
									for (Trader trader : employees.getTraders()) 
									{
										employeesList.add(trader);
									}
									
									for (Director director : employees.getDirectors()) {
										employeesList.add(director);
									}								
									
									printAllEmployees(employeesList);
								} 
								catch (Exception e)
								{
									e.printStackTrace();
								}
								break;
							} 
							else
							{
								pressedKey = scanner.nextLine();
							}
						}
					} 
					catch (Exception e)
					{
						throw e;
					}
				}
			} 
			else 
			{
				throw new Exception();
			}
		} 
		catch(Exception ex) 
		{
			ex.printStackTrace();
			System.out.println("\t-----------------------------------------------");
			System.out.println("\tPo³¹czenie :        Niepowodzenie");
			System.out.println("\t-----------------------------------------------");
		}
	}
	
	private void permissionsManagement() 
	{
		System.out.println("6.1 Zarz¹dzaj uprawnieniami transferu danych\n");
		for (User user : userService.getUsers()) 
		{
			System.out.println("\tU¿ytkownik :        " + user.getUsername());
			System.out.println("\t-----------------------------------------------");
		}
		
		System.out.println("[A] - dodaj");
		System.out.println("[R] - usun");
		System.out.println("[Q] - powrot");
		
		String username, password;;
		String pressedKey = scanner.nextLine();
		while (!pressedKey.equalsIgnoreCase("Q")) 
		{
			if (pressedKey.equalsIgnoreCase("A")) 
			{
				System.out.print("\tU¿ytkownik :        ");
				username = scanner.nextLine();
				System.out.print("\tHaslo      :        ");
				password = scanner.nextLine();
				System.out.println("\t-----------------------------------------------");
				
				userService.addUser(username, password);
				
				break;
			} 
			else if (pressedKey.equalsIgnoreCase("R")) 
			{
				System.out.print("\tU¿ytkownik :        ");
				username = scanner.nextLine();
				System.out.println("\t-----------------------------------------------");
				
				userService.deleteUser(username);
				
				break;
			} 
			else 
			{
				pressedKey = scanner.nextLine();
			}
		}
	}
}

