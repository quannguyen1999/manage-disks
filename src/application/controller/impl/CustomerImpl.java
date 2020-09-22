package application.controller.impl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import application.controller.DAO.Connect;
import application.controller.DAO.Repository;
import application.controller.services.CustomerService;
import application.entities.Customer;

public class  CustomerImpl extends Repository implements CustomerService{
	static Connection conn;
	static Connect connect;
	
	@Override
	public boolean removeCustomer(String id) {
		return deleteById(id,new Customer());
	}

	@Override
	public Customer updateCustomer(Customer customerUpdate, String id) {
		Customer customerFind=findCustomerById(id);
		if(customerFind==null) {
			
			return null;
			
		}
		customerUpdate.setCustomerId(id);
		return (Customer) update(customerUpdate);
	}

	@Override
	public Customer findCustomerById(String id) {
		// TODO Auto-generated method stub
		return (Customer) findById(id,new Customer());
	}

	@Override
	public List<Customer> listCustomer() {
		List<Object> listObject=list(new Customer(),Customer.class.getSimpleName().toString());
		List<Customer> listCustomer=new ArrayList<>();
		for(int i=0;i<listObject.size();i++) {
			listCustomer.add((Customer)listObject.get(i));
		}
		return listCustomer;
	}

	@Override
	public boolean addCustomer(Customer customer) {
		return add(customer);
	}
	
}
