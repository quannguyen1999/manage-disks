package application.controller;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;

import application.controller.impl.BillImpl;
import application.controller.impl.CustomerImpl;
import application.controller.impl.OrderDetailImpl;
import application.controller.impl.ProductImpl;
import application.controller.impl.TitleImpl;
import application.controller.services.BillService;
import application.controller.services.CustomerService;
import application.controller.services.OrderDetailService;
import application.controller.services.OrderService;
import application.controller.services.ProductService;
import application.controller.services.TitleService;
import application.entities.Bill;
import application.entities.BillDetail;
import application.entities.Customer;
import application.entities.Product;
import application.entities.Title;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

public class FormListCustomerOrder  extends DialogBox implements Initializable {
	//border
	@FXML BorderPane bdCustomer;
	@FXML BorderPane bdProductInStock;
	@FXML BorderPane bdProductWantOrder;

	//customer
	private TableView<Customer> tbl_view_customer;

	TableColumn<Customer, String> colName;
	TableColumn<Customer, String> colPhone;

	//title
	@FXML JFXTextField txtTitleId;
	@FXML JFXTextField txtTitleName;
	@FXML JFXTextField txtTitleStatus;
	@FXML  JFXDatePicker txtTitleDatePay;

	//customer
	@FXML JFXTextField txtCustomerId;
	@FXML JFXTextField txtCustomerPhone;
	@FXML JFXTextField txtCustomerName;

	//product
	@FXML JFXTextField txtProductId;
	@FXML JFXTextField txtProductName;
	@FXML JFXTextField txtProductQUantity;
	@FXML JFXTextField txtProductPrice;


	//table customer in stock
	private TableView<Product> tbl_view_product_instock;

	TableColumn<Product, String> col_Product_InStock_Id;
	TableColumn<Product, String> col_Product_InStock_Name;
	TableColumn<Product, String> col_Product_InStock_OnShelf;

	//table customer want order
	private TableView<Product> tbl_view_product_order;

	TableColumn<Product, String> col_Product_order_Id;
	TableColumn<Product, String> col_Product_order_Name;
	TableColumn<Product, String> col_Product_order_OnShelf;
	
	//service
	ProductService productService = new ProductImpl();
	TitleService titleService = new TitleImpl();
	OrderDetailService orderDetailService = new OrderDetailImpl();
	CustomerService customerService = new CustomerImpl();
	BillService billServices = new BillImpl();
	
	//button
	JFXButton btnReset;
	
	//
	//format value 100000 -> 100.000
	DecimalFormat df = new DecimalFormat("#,###"); 
	
	//array 
	List<Product> listProductWantOrder  = new ArrayList<>();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initTableCustomer();
		initProductInStock();
		initProductWantOrder();

		setDisableTextField();
		
		setActionForCustomer();
		setActionForProductInStock();
//		setActionForProductWa();
	}
	
	public void deleteOrderProduct(ActionEvent e) throws IOException {
		int result=tbl_view_product_order.getSelectionModel().getSelectedIndex();
		if(result!=-1) {
			String productId = tbl_view_product_order.getItems().get(result).getProductId();
			boolean resultFind = false;
			for(int i=0;i<listProductWantOrder.size();i++) {
				if(listProductWantOrder.get(i).getProductId().equalsIgnoreCase(productId)) {
					listProductWantOrder.remove(listProductWantOrder.get(i));
					resultFind = true;
					break;
				}
			}
			
			if(resultFind) {
				SuccessNoButton("Xóa thành công");
				return;
			}
			
		}else {
			ErrorNoButton("Chưa chọn bảng cần xóa");
			return;
		}
	}
	
	public void deleteCustomer(ActionEvent e) throws IOException {
		int result=tbl_view_customer.getSelectionModel().getSelectedIndex();
		if(result!=-1) {
			String customerId = tbl_view_customer.getItems().get(result).getCustomerId();
			orderDetailService.removeOrderDetailByCustomerId(customerId, txtTitleId.getText().toString());
			
			List<Customer> loadListCustomer = customerService.listALlCustomerByTitleId(txtTitleId.getText().toString());
			
			tbl_view_customer.getItems().clear();
			
			if(loadListCustomer!=null && loadListCustomer.size()>=1) {
				loadListCustomer.forEach(t->{
					tbl_view_customer.getItems().add(t);
				});
			}
			
			txtCustomerId.setText("");
			txtCustomerName.setText("");
			txtCustomerPhone.setText("");
			
			SuccessNoButton("Xóa khách hàng thành công");
			
		}else {
			ErrorNoButton("Chưa chọn bảng customer cần xóa");
			return;
		}
	}
	
	public void setActionForCustomer() {
		tbl_view_customer.setOnMouseClicked(e->{
			if(e.getClickCount()==2) {
				int result=tbl_view_customer.getSelectionModel().getSelectedIndex();
				if(result!=-1) {
					txtCustomerId.setText(tbl_view_customer.getItems().get(result).getCustomerId());
					txtCustomerPhone.setText(tbl_view_customer.getItems().get(result).getPhone());
					txtCustomerName.setText(tbl_view_customer.getItems().get(result).getName());
				}
			}
		});
	}
	
	public void setActionForProductInStock() {
		tbl_view_product_instock.setOnMouseClicked(e->{
			if(e.getClickCount()==2) {
				int result=tbl_view_product_instock.getSelectionModel().getSelectedIndex();
				if(result!=-1) {
					txtProductId.setText(tbl_view_product_instock.getItems().get(result).getProductId());
					txtProductName.setText(tbl_view_product_instock.getItems().get(result).getName());
					txtProductPrice.setText(df.format(tbl_view_product_instock.getItems().get(result).getTitle().getCategory().getPrice()));
//					txtProductQUantity.setText(String.valueOf(tbl_view_product_instock.getItems().get(result).getQuantity()));
				}
			}
		});
	}
	
	public void btnClickConfirmProduct(ActionEvent e) throws IOException {
		String idProduct = txtProductId.getText().toString();
		
		String idCustomer = txtCustomerId.getText().toString();
		
		if(idCustomer.isEmpty()) {
			ErrorNoButton("Vui lòng chọn khách hàng muốn đặt");
			return;
		}
		
		if(idProduct.isEmpty()) {
			ErrorNoButton("Vui lòng chọn sản phẩm muốn đặt");
			return;
		}
		
		int quantity = 0;
		
		if(txtProductQUantity.getText().toString().trim().isEmpty()) {
			ErrorNoButton("Vui lòng nhập số lượng");
			return;
		}
		
		try {
			quantity = Integer.parseInt(txtProductQUantity.getText().toString());
		} catch (Exception e2) {
			ErrorNoButton("Số lượng không hợp lệ");
			return;
		}
		
		if(quantity <= 0) {
			ErrorNoButton("Số lượng không hợp lệ");
			return;
		}
		
		List<Product> listProduct = productService.listAllProductByTitleIdInStock(txtTitleId.getText().toString());
		if(listProduct!=null) {
			for(int i=0;i<listProduct.size();i++) {
				if(listProduct.get(i).getProductId().equalsIgnoreCase(idProduct)) {
					if(quantity<=listProduct.get(i).getQuantityOnShelf()) {
						Product product = new Product(
								listProduct.get(i).getProductId(),
								listProduct.get(i).getName(),
								listProduct.get(i).getPicture(), 
								listProduct.get(i).getQuantity(), 
								listProduct.get(i).getDescription(),
								listProduct.get(i).getStatus(),
								listProduct.get(i).getDateAdded(), 
								listProduct.get(i).getTitle(), 
								listProduct.get(i).getSupplier(), 
								listProduct.get(i).getQuantityRentDisk(), 
								quantity, 
								listProduct.get(i).getQuantityOnHold());
						boolean result = false;
						for(int j=0;j<listProductWantOrder.size();j++) {
							if(listProductWantOrder.get(j).getProductId().equalsIgnoreCase(product.getProductId())) {
								listProductWantOrder.get(j).setQuantityRentDisk(quantity);
								result = true;
								break;
							}
						}
						if(result==false) {
							listProductWantOrder.add(product);
						}
						tbl_view_product_order.getItems().clear();
						listProductWantOrder.forEach(t->{
							tbl_view_product_order.getItems().add(t);
						});
						break;
					}
				}
				
			};
		}
	}
	
	public void btnThanhToan(ActionEvent e) throws IOException {
		String customerId = txtCustomerId.getText().toString().trim();
		
		LocalDate localDateDatePay = txtTitleDatePay.getValue();
		
		if(localDateDatePay==null) {
			ErrorNoButton("Vui lòng chọn ngày trả");
			txtTitleDatePay.requestFocus();
			return;
		}
		
		if(localDateDatePay.isBefore(LocalDate.now())) {
			ErrorNoButton("Ngày trả không hợp lệ");
			txtTitleDatePay.requestFocus();
			return;
		}
		
		if(customerId.isEmpty()) {
			ErrorNoButton("Bạn chưa chọn khách hàng");
			return;
		}
		if(listProductWantOrder.size()<=0) {
			ErrorNoButton("Chưa có sản phẩm nào");
			return;
		}
		
		Customer customer = customerService.findCustomerById(customerId);
		Bill bill = new Bill();
		bill.setBillId(returnIdBill());
		bill.setBillPay(localDateDatePay);
		bill.setCustomer(customer);
		bill.setDebt(KHONGCONO);
		bill.setLocalDate(LocalDate.now());
		billServices.addBill(bill);
		for(int i=0;i<listProductWantOrder.size();i++) {
			BillDetail billDetail = new BillDetail(returnIdBillDetail(),
					listProductWantOrder.get(i).getQuantityOnShelf(), 
					listProductWantOrder.get(i).getQuantityOnShelf()
					*Math.round(listProductWantOrder.get(i).getTitle().getCategory().getPrice()), 
					listProductWantOrder.get(i), bill);
			billServices.addBillDetail(billDetail);
		}
		
		for(int i=0;i<listProductWantOrder.size();i++) {
			Product product = productService.findProductById(listProductWantOrder.get(i).getProductId());
			product.setQuantityOnShelf(product.getQuantityOnShelf()-listProductWantOrder.get(i).getQuantityOnShelf());
			product.setQuantityRentDisk(product.getQuantityRentDisk()+listProductWantOrder.get(i).getQuantityOnShelf());
			productService.updateProduct(product, product.getProductId());
		}
		
		//delete customer from order 
		orderDetailService.removeOrderDetailByCustomerId(customerId, txtTitleId.getText().toString());
		
		List<Customer> loadListCustomer = customerService.listALlCustomerByTitleId(txtTitleId.getText().toString());
		
		tbl_view_customer.getItems().clear();
		
		//load customer
		if(loadListCustomer!=null && loadListCustomer.size()>=1) {
			loadListCustomer.forEach(t->{
				tbl_view_customer.getItems().add(t);
			});
		}
		
		txtCustomerId.setText("");
		txtCustomerName.setText("");
		txtCustomerPhone.setText("");
		
		txtProductId.setText("");
		txtProductName.setText("");
		txtProductPrice.setText("");
		txtProductQUantity.setText("");
		
		txtTitleDatePay.setValue(null);
		
		listProductWantOrder.clear();
		
		tbl_view_product_instock.getItems().clear();
		tbl_view_product_order.getItems().clear();
		//load product
		List<Product> loadProduct = productService.listAllProductByTitleIdInStock(txtTitleId.getText().toString());
		
		if(loadProduct!=null && loadProduct.size()>=1) {
			loadProduct.forEach(t->{
				tbl_view_product_instock.getItems().add(t);
			});
		}
		
		SuccessNoButton("đặt hàng khách hàng thành công");
		
	}
	
	public String returnIdBill() {
		String id="B000";
		do {

			id="B"+ranDomNumber();


		} while (billServices.findBillById(id)!=null);
		return id;
	}
	
	public String returnIdBillDetail() {
		String id="BD000";
		do {

			id="BD"+ranDomNumber();


		} while (billServices.findBillDetailById(id)!=null);
		return id;
	}
	
	public void btnResetForm(ActionEvent e) {
		txtCustomerId.setText("");
		txtCustomerName.setText("");
		txtCustomerPhone.setText("");
		
		txtProductId.setText("");
		txtProductName.setText("");
		txtProductPrice.setText("");
		txtProductQUantity.setText("");
		
		tbl_view_product_instock.getItems().clear();
		
		List<Product> listProduct = productService.listAllProductByTitleIdInStock(txtTitleId.getText().toString());
		if(listProduct!=null) {
			listProduct.forEach(t->{
				tbl_view_product_instock.getItems().add(t);
			});
		}
		
		listProductWantOrder.clear();
		
		tbl_view_product_order.getItems().clear();
	}
	
	public void btnHuy(ActionEvent e) {
		txtProductId.setText("");
		txtProductName.setText("");
		txtProductPrice.setText("");
		txtProductQUantity.setText("");
	}

	public void btnExit(ActionEvent e) {
		((Node)(e.getSource())).getScene().getWindow().hide();  
	}

	public void setDisableTextField() {

		//title
		txtTitleId.setEditable(false);
		txtTitleName.setEditable(false);
		txtTitleStatus.setEditable(false);
//		txtTitleDatePay.setEditable(false);
//		txtTitleDatePay.setDisable(true);

		//customer
		txtCustomerId.setEditable(false);
		txtCustomerPhone.setEditable(false);
		txtCustomerName.setEditable(false);

		//product
		txtProductId.setEditable(false);
		txtProductName.setEditable(false);
//		txtProductQUantity.setEditable(false);
		txtProductPrice.setEditable(false);
	}

	public void initTableCustomer() {
		tbl_view_customer=new TableView<Customer>();

		colName=new TableColumn<Customer, String>("Tên");
		colPhone=new TableColumn<Customer, String>("Điện thoại");

		tbl_view_customer.getColumns().addAll(colName,colPhone);

		bdCustomer.setCenter(tbl_view_customer);

		colName.setCellValueFactory(new PropertyValueFactory<>("name"));
		colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));

		colName.setMinWidth(180);//.setCellValueFactory(new PropertyValueFactory<>("diaChi"));
		colPhone.setMinWidth(100);//.setCellValueFactory(new PropertyValueFactory<>("soDT"));

	}

	public void initProductInStock() {
		tbl_view_product_instock=new TableView<Product>();

		col_Product_InStock_Id=new TableColumn<Product, String>("Mã product");
		col_Product_InStock_Name=new TableColumn<Product, String>("tên");
		col_Product_InStock_OnShelf=new TableColumn<Product, String>("Số lượng hàng trên kệ");

		tbl_view_product_instock.getColumns().addAll(col_Product_InStock_Id,
				col_Product_InStock_Name,
				col_Product_InStock_OnShelf);

		bdProductInStock.setCenter(tbl_view_product_instock);


		col_Product_InStock_Id.setCellValueFactory( cellData->new SimpleStringProperty(cellData.getValue().getProductId()));
		col_Product_InStock_Name.setCellValueFactory( cellData->new SimpleStringProperty(cellData.getValue().getName()));
		col_Product_InStock_OnShelf.setCellValueFactory( cellData->new SimpleStringProperty(String.valueOf(
				cellData.getValue().getQuantityOnShelf())));

	}

	public void initProductWantOrder() {
		tbl_view_product_order=new TableView<Product>();

		col_Product_order_Id=new TableColumn<Product, String>("Mã product");
		col_Product_order_Name=new TableColumn<Product, String>("tên");
		col_Product_order_OnShelf=new TableColumn<Product, String>("Số lượng hàng đặt");

		tbl_view_product_order.getColumns().addAll(col_Product_order_Id,
				col_Product_order_Name,
				col_Product_order_OnShelf);

		bdProductWantOrder.setCenter(tbl_view_product_order);


		col_Product_order_Id.setCellValueFactory( cellData->new SimpleStringProperty(cellData.getValue().getProductId()));
		col_Product_order_Name.setCellValueFactory( cellData->new SimpleStringProperty(cellData.getValue().getName()));
		col_Product_order_OnShelf.setCellValueFactory( cellData->new SimpleStringProperty(String.valueOf(
				cellData.getValue().getQuantityOnShelf())));

	}

	public void loadCustomer(List<Customer> listCustomer) {
		if(listCustomer!=null && listCustomer.size()>=1) {
			listCustomer.forEach(t->{
				tbl_view_customer.getItems().add(t);
			});
		}
	}
	
	public void loadTitleAndListProduct(String idTitle) throws IOException {
		Title title = titleService.findTitleById(idTitle);
		if(title==null) {
			Error("NUll title", btnReset);
			return;
		}else {
			txtTitleId.setText(title.getTitleId());
			txtTitleName.setText(title.getName());
			txtTitleStatus.setText(title.getStatus());
			
			List<Product> listProduct = productService.listAllProductByTitleIdInStock(idTitle);
			if(listProduct!=null) {
				listProduct.forEach(t->{
					tbl_view_product_instock.getItems().add(t);
				});
			}
		}
	}
}
