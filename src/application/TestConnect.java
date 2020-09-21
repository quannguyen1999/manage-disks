package application;
import java.time.LocalDate;
import application.controller.impl.CustomerImpl;
import application.controller.services.CustomerService;
import application.entities.Customer;

public class TestConnect {
	public static void main(String[] args) {
//		CustomerService customerService=new CustomerImpl();
//		//add customer
//		Customer customer=new Customer("C02", "slut", "34/16", "0708821227", true, LocalDate.of(1999, 11, 23));
//		customer.setAddress("abcxyz");
//		System.out.println(customerService.addCustomer(customer));
//		//		System.out.println(customerService.findCustomerById("C02"));
//
//		//		System.out.println(Customer.class.getSimpleName().toString());
//
//
//		//		System.out.println(customerService.removeCustomer("C02"));
//
//				System.out.println(customerService.updateCustomer(customer, customer.getCustomerId()));
//
//		//list customer
//		customerService.listCustomer().forEach(t->{
//			System.out.println(t);
//		});
		
		  int min = 0;
	      int max = 1000;
	      System.out.println("Random value in int from "+min+" to "+max+ ":");
	      int random_int = (int)(Math.random() * (max - min + 1) + min);
	}
}
