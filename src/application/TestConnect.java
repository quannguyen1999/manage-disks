package application;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import application.controller.impl.BillImpl;
import application.controller.impl.CategoryImpl;
import application.controller.impl.CustomerImpl;
import application.controller.impl.OrderDetailImpl;
import application.controller.impl.OrderImpl;
import application.controller.impl.ProductImpl;
import application.controller.impl.SupplierImpl;
import application.controller.impl.TitleImpl;
import application.controller.services.BillService;
import application.controller.services.CategoryService;
import application.controller.services.CustomerService;
import application.controller.services.OrderDetailService;
import application.controller.services.OrderService;
import application.controller.services.ProductService;
import application.controller.services.SupplierService;
import application.controller.services.TitleService;
import application.entities.Bill;
import application.entities.BillDetail;
import application.entities.Category;
import application.entities.Customer;
import application.entities.Order;
import application.entities.OrderDetail;
import application.entities.Product;
import application.entities.Supplier;
import application.entities.Title;

public class TestConnect {
	public static void main(String[] args) {
//		Category category=new Category("CT101", "quân", "bao cao su", 10000);
//
//		Title title=new Title("T101", "abc", true, category);
//		
//		Customer customer=new Customer("C02", "slut", "34/16", "0708821227", true, LocalDate.of(1999, 2, 1));
//		
//		Order order=new Order("OD011", LocalDate.of(1999, 2, 1), customer);
//		
//		OrderDetail orderDetail=new OrderDetail("ODDT01", 10, 10, order, title);
//		
//		OrderService orderService=new OrderImpl();
//		
//		OrderDetailService orderDetailService=new OrderDetailImpl();
//		
//		System.out.println(orderDetailService.addOrderDetail(orderDetail));
		
//		CustomerService customerService=new CustomerImpl();
//		
//		System.out.println(customerService.findCustomerByPhone("008821227"));
		
//		NumberFormat formatter = new DecimalFormat("#000,000");     
//		System.out.println(formatter.format(400000000));
		
		BillService billService=new BillImpl();
		ArrayList<Bill> listBill=(ArrayList<Bill>) billService.findAllBillByIdCustomer("C01");
		billService.findAllBillDetailByIdBill(listBill).forEach(t->{
			System.out.println(t.getBillDetailId());
		});;
		
	}

	public void testBill() {

		Category category=new Category("CT101", "quân", "bao cao su", 10000);

		Title title=new Title("T101", "abc", true, category);

		Supplier supplier=new Supplier("S101", "0123337505", "61/12", "IT");

		Product product=new Product("P101", "Bao cao su", "S1.png", 10, "mô tả","on Hole",LocalDate.of(1999, 2, 1), title, supplier);

		Customer customer=new Customer("C02", "slut", "34/16", "0708821227", true, LocalDate.of(1999, 2, 1));

		Bill bill=new Bill("B01", LocalDate.of(1999, 2, 1), LocalDate.of(1999, 2, 1), customer, true);

		BillDetail billDetail=new BillDetail("BDT01", 10, 10, product, bill);

		BillService billService=new BillImpl();

		System.out.println(billService.addBill(bill));

	}

	public void testProduct() {

		Category category=new Category("CT101", "quân", "bao cao su", 10000);

		Title title=new Title("T101", "abc", true, category);

		Supplier supplier=new Supplier("S101", "0123337505", "61/12", "IT");

		Product product=new Product("P101", "Bao cao su", "S1.png", 10, "mô tả","on Hole",LocalDate.of(1999, 2, 1), title, supplier);

		ProductService productService=new ProductImpl();

		System.out.println(productService.addProduct(product));

		productService.listProduct().forEach(t->{
			System.out.println(t);
		});

	}

	public void testTitle() {
		Category category=new Category("CT101", "quân", "bao cao su", 10000);

		Title title=new Title("T101", "abc", true, category);

		TitleService titleService=new TitleImpl();

		System.out.println(titleService.addTitle(title));

		titleService.listTitle().forEach(t->{

			System.out.println(t);

		});


	}

	public void testSupplier() {


		Supplier supplier=new Supplier("S101", "0123337505", "61/12", "IT");

		SupplierService supplierService=new SupplierImpl();

		System.out.println(supplierService.addSupplier(supplier));

		supplierService.listSupplier().forEach(t->{

			System.out.println(t);

		});
	}

	public void testCategory() {

		Category category=new Category("CT101", "quân", "bao cao su", 10000);

		category.setDescription("fuck and slut");

		CategoryService categoryService=new CategoryImpl();

		System.out.println(categoryService.updateCategory(category, category.getCategoryId()));
		//		
		//		System.out.println(categoryService.addCategory(category));

		categoryService.listCategory().forEach(t->{
			System.out.println(t);
		});

	}

	public void testCustomer() {


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

		//		  int min = 0;
		//	      int max = 1000;
		//	      System.out.println("Random value in int from "+min+" to "+max+ ":");
		//	      int random_int = (int)(Math.random() * (max - min + 1) + min);

	}


}
