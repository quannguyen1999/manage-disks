package application.controller.impl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityTransaction;

import application.controller.DAO.Connect;
import application.controller.DAO.Repository;
import application.controller.services.BillService;
import application.controller.services.CustomerService;
import application.controller.services.LateFeeService;
import application.entities.Bill;
import application.entities.Customer;
import application.entities.LateFee;

public class  CustomerImpl extends Repository implements CustomerService{
	static Connection conn;
	
	static Connect connect;
	
	static BillService billService = new BillImpl();
	
	static LateFeeService lateFeeService = new LateFeeImpl();
	
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

	@Override
	public Customer findCustomerByPhone(String phone) {
		EntityTransaction transaction = null;
		List<Customer> accs=new ArrayList<>();
		try {
			transaction =  connect.getEntityManager().getTransaction();
			transaction.begin();
			accs=  connect.getEntityManager().createQuery("from Customer where phone='"+phone+"'",Customer.class).getResultList();
			transaction.commit();
		} catch (Exception ex) {
			if (transaction != null) {
				transaction.rollback();
			}
			connect.getEntityManager().close();
		}
		return accs.size()==0?null:accs.get(0);
	}

	@Override
	public List<Customer> listAllCustomerByNotReturnDisk() {
		List<Bill> listBill=billService.listBill();
		if(listBill==null) {
			return null;
		}
		ArrayList<Customer> listCustomer = new ArrayList<>();
		for(int i=0;i<listBill.size();i++) {
			if(listCustomer.contains(listBill.get(i).getCustomer().getCustomerId())==false) {
				listCustomer.add(listBill.get(i).getCustomer());
			}
		}
		return listCustomer.size()==0?null:listCustomer();
	}

	@Override
	public List<Customer> listAllCustomerByDebt() {
		List<LateFee> listLateFee=lateFeeService.listLateFee();
		if(listLateFee==null) {
			return null;
		}
		ArrayList<Customer> listCustomer = new ArrayList<>();
		for(int i=0;i<listLateFee.size();i++) {
			if(listCustomer.contains(listLateFee.get(i).getBill().getCustomer().getCustomerId())==false) {
				listCustomer.add(listLateFee.get(i).getBill().getCustomer());
			}
		}
		return listCustomer.size()==0?null:listCustomer();
	}
	
}
