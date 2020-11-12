package application.controller;

import java.awt.TextField;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;

import application.controller.impl.BillImpl;
import application.controller.impl.CustomerImpl;
import application.controller.impl.ProductImpl;
import application.controller.services.BillService;
import application.controller.services.CustomerService;
import application.controller.services.ProductService;
import application.entities.Bill;
import application.entities.BillDetail;
import application.entities.Customer;
import application.entities.Product;
import application.entities.Supplier;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class FormRentDisk  extends DialogBox implements Initializable{

	@FXML JFXButton btnThanhToan;
	@FXML JFXButton btnXoaRong;
	@FXML JFXButton btnExit;

	//Bill
	@FXML JFXTextField txtIdBill;
	@FXML JFXDatePicker txtDatePayBill;

	//customer
	@FXML ComboBox<String>  cbcIdCustomer=new ComboBox<String>();;
	@FXML ComboBox<String>  cbcPhoneCustomer=new ComboBox<String>();;
	@FXML JFXTextField txtNameCustomer;
	@FXML JFXTextField txtAddressCustomer;
	@FXML JFXDatePicker txtDatePickerCustomer;

	//product
	@FXML ComboBox<String> cbcIdProduct=new ComboBox<String>();;
	@FXML JFXTextField txtNameProduct;
	@FXML JFXTextField txtQuantityProduct;
	@FXML JFXTextField txtDescriptionProduct;
	@FXML JFXTextField txtStatusProduct;
	@FXML JFXTextField txtPriceProduct;
	@FXML RadioButton rdIdCustomer;
	@FXML RadioButton rdPhoneCustomer;
	@FXML JFXButton btnFindIdCustomer;
	@FXML JFXButton btnFindPhoneCustomer;

	//
	@FXML Label lblTotal;

	ProductService productService = new ProductImpl();
	CustomerService customerService = new CustomerImpl();


	//table 
	@FXML BorderPane bd;

	private TableView<Product> tbl_view;

	TableColumn<Product, String> colProductId;
	TableColumn<Product, String> colName;
	TableColumn<Product, Integer> colQuantity;
	TableColumn<Product, String> colStatus;
	TableColumn<Product, String> colPrice;

	ProductService ProductService=new ProductImpl();

	BillService billService=new BillImpl();

	List<Product> listProduct=new ArrayList<>();

	List<Product> arrayOrderProduct=new ArrayList<>();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//load data 
		loadDataSearchIdProduct();
		loadDataSearchCustomer();

		//init customer
		txtNameCustomer.setEditable(false);
		txtAddressCustomer.setEditable(false);
		txtDatePickerCustomer.setEditable(false);

		//init product
		txtNameProduct.setEditable(false);
		txtQuantityProduct.setEditable(false);
		txtDescriptionProduct.setEditable(false);
		txtStatusProduct.setEditable(false);

		//init table 
		initTable();

		//init form customer 
		rdPhoneCustomer.setSelected(true);
		cbcPhoneCustomer.setEditable(true);
		cbcPhoneCustomer.setDisable(false);
		btnFindPhoneCustomer.setDisable(false);

		cbcIdCustomer.setEditable(false);
		cbcIdCustomer.setDisable(true);
		btnFindIdCustomer.setDisable(true);

		txtPriceProduct.setEditable(false);
		txtIdBill.setEditable(false);
	}

	public void initTable() {
		tbl_view=new TableView<Product>();

		colProductId=new TableColumn<Product, String>("mã");
		colName=new TableColumn<Product, String>("tên");
		colQuantity=new TableColumn<Product, Integer>("số lượng");
		colStatus=new TableColumn<Product, String>("tình trạng");
		colPrice=new TableColumn<Product, String>("giá");

		tbl_view.getColumns().addAll(colProductId,
				colName,
				colQuantity,
				colPrice);

		bd.setCenter(tbl_view);

		colProductId.setCellValueFactory(new PropertyValueFactory<>("productId"));
		colName.setCellValueFactory(new PropertyValueFactory<>("name"));
		colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
		//		colPrice.setCellValueFactory( cellData->new SimpleStringProperty(String.valueOf(cellData.getValue().getTitle().getCategory().getPrice())));

		NumberFormat formatter = new DecimalFormat("#000,000");   
		colPrice.setCellValueFactory( cellData->
		new SimpleStringProperty(String.valueOf(formatter.format(cellData.getValue().getTitle().getCategory().getPrice()))));


		colProductId.setMinWidth(100);// .setCellValueFactory(new PropertyValueFactory<>("maKH"));
		colName.setMinWidth(180);//.setCellValueFactory(new PropertyValueFactory<>("diaChi"));

	}
	//	private void uploadDuLieuLenBang() {
	//		List<Product> cuss=ProductService.listProduct();
	//		cuss.forEach(t->{
	//			tbl_view.getItems().add(t);
	//			listProduct.add(t);
	//		});
	//	}

	public void clickChooseFindByIdCustomer(ActionEvent e) {
		rdIdCustomer.setSelected(true);
		cbcIdCustomer.setEditable(true);
		cbcIdCustomer.setDisable(false);
		btnFindIdCustomer.setDisable(false);

		cbcPhoneCustomer.setEditable(false);
		cbcPhoneCustomer.setDisable(true);
		btnFindPhoneCustomer.setDisable(true);

		cbcPhoneCustomer.setValue("");
		cbcIdCustomer.setValue("");
		txtNameCustomer.setText("");
		txtAddressCustomer.setText("");
		txtDatePickerCustomer.setValue(null);
	}

	public void clickChooseFindByPhoneCustomer(ActionEvent e) {
		rdPhoneCustomer.setSelected(true);
		cbcPhoneCustomer.setEditable(true);
		cbcPhoneCustomer.setDisable(false);
		btnFindPhoneCustomer.setDisable(false);

		cbcIdCustomer.setEditable(false);
		cbcIdCustomer.setDisable(true);
		btnFindIdCustomer.setDisable(true);

		cbcPhoneCustomer.setValue("");
		cbcIdCustomer.setValue("");
		txtNameCustomer.setText("");
		txtAddressCustomer.setText("");
		txtDatePickerCustomer.setValue(null);
	}

	boolean result = false;
	int total = 0;
	public void clickChooseProduct(ActionEvent e) throws IOException {
		String textFind=null;

		try {

			textFind=cbcIdProduct.getSelectionModel().getSelectedItem().toString().trim();

		} catch (Exception e2) {

			Error("Bạn chưa nhập tìm kiếm", btnThanhToan);

			cbcIdProduct.requestFocus();

		}

		if(textFind.isEmpty()) {

			Error("Bạn chưa nhập tìm kiếm", btnExit);

			cbcIdProduct.requestFocus();

			return;

		}


		Product product = productService.findProductById(textFind);

		if(product==null) {

			Error("Không tìm thấy", btnExit);

			cbcIdProduct.requestFocus();

			return;

		}else {
			result = false;
			arrayOrderProduct.forEach(t->{
				if(t.getProductId().equals(product.getProductId()) 
						&& t.getQuantity()>=product.getQuantity() && t.getQuantity()>0) {
					try {
						Error("Không đủ hàng chỉ còn "+product.getQuantity(), btnExit);
						result=true;
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					return;
				}else if(t.getProductId().equals(product.getProductId())){
					t.setQuantity(t.getQuantity()+1);
					result=true;
					return;
				}
			});
			if(result == false) {
				if(product.getQuantity()<=0) {
					Error("Không đủ hàng chỉ còn "+product.getQuantity(), btnExit);
				}else {
					arrayOrderProduct.add(new Product(product.getProductId(), product.getName(),
							product.getPicture(), 1,
							product.getDescription(), product.getStatus(), 
							product.getDateAdded(), product.getTitle(), product.getSupplier()));
				}

			}
			arrayOrderProduct.forEach(t->{

				total+=(t.getQuantity()*t.getTitle().getCategory().getPrice());

			});

			if(total != 0) {

				NumberFormat formatter = new DecimalFormat("#000,000");   
				lblTotal.setText(String.valueOf(formatter.format(total))+" $");

			}


			tbl_view.getItems().clear();

			arrayOrderProduct.forEach(t->{
				tbl_view.getItems().add(t);
			});

		}
	}

	public void clickDeleteOrderInTable(ActionEvent e) throws IOException {
		int result=tbl_view.getSelectionModel().getSelectedIndex();

		if(result!=-1) {

			for(int i=0;i<arrayOrderProduct.size() ;i++) {
				if(tbl_view.getItems().get(result).getProductId().equalsIgnoreCase(arrayOrderProduct.get(i).getProductId())) {
					arrayOrderProduct.remove(i);
					break;
				}
			}

			tbl_view.getItems().clear();


			total = 0;

			arrayOrderProduct.forEach(t->{

				tbl_view.getItems().add(t);

				total+=(t.getQuantity()*t.getTitle().getCategory().getPrice());

			});

			System.out.println(total);

			if(total != 0) {

				NumberFormat formatter = new DecimalFormat("#000,000");   
				lblTotal.setText(String.valueOf(formatter.format(total))+" $");

			} else if (total == 0) {

				lblTotal.setText("0 $");

			}
		}else {

			Error("bạn chưa chọn bảng cần xóa", btnExit);

		}

	}

	public void removefieldInProduct(ActionEvent e) throws IOException {
		cbcIdProduct.setValue("");
		txtNameProduct.setText("");
		txtQuantityProduct.setText("");
		txtDescriptionProduct.setText("");
		txtStatusProduct.setText("");
		txtPriceProduct.setText("");
	}

	public void resetAllField(ActionEvent e) throws IOException {

		arrayOrderProduct.clear();

		total = 0;

		lblTotal.setText("0 $");

		cbcIdProduct.setValue("");
		txtNameProduct.setText("");
		txtQuantityProduct.setText("");
		txtDescriptionProduct.setText("");
		txtStatusProduct.setText("");
		txtPriceProduct.setText("");

		tbl_view.getItems().clear();

		cbcIdCustomer.setValue("");
		cbcPhoneCustomer.setValue("");
		txtNameCustomer.setText("");
		txtAddressCustomer.setText("");
		txtDatePickerCustomer.setValue(null);

	}

	public void findItemInTableIdCustomer(ActionEvent e) throws IOException {
		String textFind=null;

		try {

			textFind=cbcIdCustomer.getSelectionModel().getSelectedItem().toString().trim();

		} catch (Exception e2) {

			Error("Bạn chưa nhập tìm kiếm", btnThanhToan);

			cbcIdCustomer.requestFocus();

		}

		if(textFind.isEmpty()) {

			Error("Bạn chưa nhập tìm kiếm", btnExit);

			cbcIdCustomer.requestFocus();

			return;

		}


		Customer customerFind=customerService.findCustomerById(textFind);

		if(customerFind==null) {

			Error("Không tìm thấy", btnExit);

			cbcIdCustomer.requestFocus();

			return;

		}else {
			cbcPhoneCustomer.setValue(customerFind.getPhone());
			txtNameCustomer.setText(customerFind.getName());
			txtAddressCustomer.setText(customerFind.getAddress());
			txtDatePickerCustomer.setValue(customerFind.getDateOfBirth());
		}

	}

	public void findItemInTablePhoneCustomer(ActionEvent e) throws IOException {
		String textFind=null;

		try {

			textFind=cbcPhoneCustomer.getSelectionModel().getSelectedItem().toString().trim();

		} catch (Exception e2) {

			Error("Bạn chưa nhập tìm kiếm", btnThanhToan);

			cbcPhoneCustomer.requestFocus();

		}

		if(textFind.isEmpty()) {

			Error("Bạn chưa nhập tìm kiếm", btnExit);

			cbcPhoneCustomer.requestFocus();

			return;

		}


		Customer customerFind=customerService.findCustomerByPhone(textFind);

		if(customerFind==null) {

			Error("Không tìm thấy", btnExit);

			cbcPhoneCustomer.requestFocus();

			return;

		}else {
			cbcIdCustomer.setValue(customerFind.getCustomerId());
			txtNameCustomer.setText(customerFind.getName());
			txtAddressCustomer.setText(customerFind.getAddress());
			txtDatePickerCustomer.setValue(customerFind.getDateOfBirth());
		}

	}

	public void findItemInTableIdProduct(ActionEvent e) throws IOException {
		String textFind=null;

		try {

			textFind=cbcIdProduct.getSelectionModel().getSelectedItem().toString().trim();

		} catch (Exception e2) {

			Error("Bạn chưa nhập tìm kiếm", btnThanhToan);

			cbcIdProduct.requestFocus();

		}

		if(textFind.isEmpty()) {

			Error("Bạn chưa nhập tìm kiếm", btnExit);

			cbcIdProduct.requestFocus();

			return;

		}


		//		Customer customerFind=customerService.findCustomerById(textFind);

		Product product = productService.findProductById(textFind);

		if(product==null) {

			Error("Không tìm thấy", btnExit);

			cbcIdProduct.requestFocus();

			return;

		}else {
			cbcIdProduct.setValue(product.getProductId());
			txtNameProduct.setText(product.getName());
			txtQuantityProduct.setText(String.valueOf(product.getQuantity()));
			txtDescriptionProduct.setText(product.getDescription());
			txtStatusProduct.setText(product.getStatus());

			NumberFormat formatter = new DecimalFormat("#000,000");     
			txtPriceProduct.setText(String.valueOf(formatter.format(product.getTitle().getCategory().getPrice()))+" $");
		}

	}

	private void loadDataSearchIdProduct() {
		ObservableList<String> items = FXCollections.observableArrayList();

		List<Product> accs=productService.listProduct();

		accs.forEach(t->{

			items.add(t.getProductId());

		});

		FilteredList<String> filteredItems = new FilteredList<String>(items);

		cbcIdProduct.getEditor().textProperty().addListener(new InputFilter(cbcIdProduct, filteredItems, false));

		cbcIdProduct.setItems(filteredItems);

		cbcIdProduct.setEditable(true);
	}

	private void loadDataSearchCustomer() {
		ObservableList<String> items = FXCollections.observableArrayList();
		ObservableList<String> itemsPhone = FXCollections.observableArrayList();

		List<Customer> accs=customerService.listCustomer();

		accs.forEach(t->{
			items.add(t.getCustomerId());
			itemsPhone.add(t.getPhone());
		});

		FilteredList<String> filteredItems = new FilteredList<String>(items);
		FilteredList<String> filteredItemsPhone = new FilteredList<String>(itemsPhone);

		cbcIdCustomer.getEditor().textProperty().addListener(new InputFilter(cbcIdCustomer, filteredItems, false));

		cbcIdCustomer.setItems(filteredItems);

		cbcIdCustomer.setEditable(true);

		cbcPhoneCustomer.getEditor().textProperty().addListener(new InputFilter(cbcPhoneCustomer, filteredItemsPhone, false));

		cbcPhoneCustomer.setItems(filteredItemsPhone);

		cbcPhoneCustomer.setEditable(true);
	}



	public void btnCLoseWindow(ActionEvent e) throws IOException {

		((Node)(e.getSource())).getScene().getWindow().hide();  

	}

	public void btnOKRenDisk(ActionEvent e) throws IOException {

		String textFind=null;

		if(rdIdCustomer.isSelected()) {
			try {

				textFind=cbcIdCustomer.getSelectionModel().getSelectedItem().toString().trim();

			} catch (Exception e2) {

				Error("Bạn chưa nhập id khách hàng muốn order", btnThanhToan);

				cbcIdCustomer.requestFocus();

			}

			if(textFind.isEmpty()) {

				Error("Bạn chưa nhập tìm kiếm", btnExit);

				cbcIdCustomer.requestFocus();

				return;

			}
		}else {
			try {

				textFind=cbcPhoneCustomer.getSelectionModel().getSelectedItem().toString().trim();

			} catch (Exception e2) {

				Error("Bạn chưa nhập phone khách hàng muốn order", btnThanhToan);

				cbcPhoneCustomer.requestFocus();

			}

			if(textFind.isEmpty()) {

				Error("Bạn chưa nhập tìm kiếm", btnExit);

				cbcPhoneCustomer.requestFocus();

				return;

			}
		}

		if(tbl_view.getItems().size()<=0) {

			Error("Chưa có đĩa nào", btnThanhToan);

			cbcIdProduct.requestFocus();

			return;

		}

		if(txtDatePayBill.getValue() == null) {

			Error("Chưa nhập ngày trả", btnExit);

			txtDatePayBill.requestFocus();

			return;
		}else if(txtDatePayBill.getValue().isBefore(LocalDate.now())) {

			Error("Ngày không hợp lệ", btnExit);

			txtDatePayBill.requestFocus();

			return;

		}
		
		Customer customerFind = customerService.findCustomerById(cbcIdCustomer.getSelectionModel()
				.getSelectedItem().toString().trim());

		Bill bil = new Bill(txtIdBill.getText().toString(), LocalDate.now(), txtDatePayBill.getValue(), customerFind, true);

		if(billService.addBill(bil)==true) {
			for(int i=0;i<arrayOrderProduct.size();i++) {

				BillDetail billDetail = new BillDetail("BD"+ranDomNumber(),
						arrayOrderProduct.get(i).getQuantity(), 
						Math.round(arrayOrderProduct.get(i).getTitle().getCategory().getPrice())
						, arrayOrderProduct.get(i),
						bil);


				billService.addBillDetail(billDetail);

			}

			Success("Thuê đĩa thành công", btnExit);

			resetAllField(e);
		}else{

			Error("Lỗi thêm không thành công", btnExit);

		};

	}

	public int ranDomNumber() {
		int min = 0;
		int max = 1000000;
		System.out.println("Random value in int from "+min+" to "+max+ ":");
		int random_int = (int)(Math.random() * (max - min + 1) + min);
		return random_int;
	}
}
