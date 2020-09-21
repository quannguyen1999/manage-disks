package application.controller.services;

import java.util.List;

import application.entities.Customer;

public interface CustomerService {
	public boolean addCustomer(Customer customer);
	
	public boolean removeCustomer(String id);
	
	public Customer updateCustomer(Customer customerUpdate,String id);
	
	public Customer findCustomerById(String id);
	
	public List<Customer> listCustomer();
}
