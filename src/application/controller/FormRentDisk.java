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
import application.controller.impl.LateFeeImpl;
import application.controller.impl.ProductImpl;
import application.controller.services.BillService;
import application.controller.services.CustomerService;
import application.controller.services.LateFeeService;
import application.controller.services.ProductService;
import application.entities.Bill;
import application.entities.BillDetail;
import application.entities.Customer;
import application.entities.LateFee;
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
	@FXML Label txtProductCurrentInShelf;

	ProductService productService = new ProductImpl();
	CustomerService customerService = new CustomerImpl();
	LateFeeService lateFeeService = new LateFeeImpl();


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

	DecimalFormat df = new DecimalFormat("#,###"); 

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
		txtQuantityProduct.setEditable(true);
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
//		txtIdBill.setEditable(false);
		
		tbl_view.setOnMouseClicked(e->{
			if(e.getClickCount()==2) {
				int result=tbl_view.getSelectionModel().getSelectedIndex();
				if(result!=-1) {
					
					cbcIdProduct.setValue(tbl_view.getItems().get(result).getProductId());
					txtNameProduct.setText(tbl_view.getItems().get(result).getName());
					txtQuantityProduct.setText(String.valueOf(tbl_view.getItems().get(result).getQuantity()));
					txtDescriptionProduct.setText(tbl_view.getItems().get(result).getDescription());
					txtStatusProduct.setText(tbl_view.getItems().get(result).getStatus());
					txtPriceProduct.setText(String.valueOf(df.format(tbl_view.getItems().get(result).getTitle().getCategory().getPrice()))+" $");
				}
			}
		});

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

		colPrice.setCellValueFactory( cellData->
		new SimpleStringProperty(String.valueOf(df.format(cellData.getValue().getTitle().getCategory().getPrice()))));

	}

	public void clickChooseFindByIdCustomer(ActionEvent e) {
		cbcPhoneCustomer.setValue("");
		cbcIdCustomer.setValue("");
		txtNameCustomer.setText("");
		txtAddressCustomer.setText("");
		rdIdCustomer.setSelected(true);
		cbcIdCustomer.setEditable(true);
		cbcIdCustomer.setDisable(false);
		btnFindIdCustomer.setDisable(false);

		cbcPhoneCustomer.setEditable(false);
		cbcPhoneCustomer.setDisable(true);
		btnFindPhoneCustomer.setDisable(true);




		txtDatePickerCustomer.setValue(null);

		loadDataSearchCustomer();
	}

	public void clickChooseFindByPhoneCustomer(ActionEvent e) {
		cbcPhoneCustomer.setValue("");
		cbcIdCustomer.setValue("");
		txtNameCustomer.setText("");
		txtAddressCustomer.setText("");
		rdPhoneCustomer.setSelected(true);
		cbcPhoneCustomer.setEditable(true);
		cbcPhoneCustomer.setDisable(false);
		btnFindPhoneCustomer.setDisable(false);

		cbcIdCustomer.setEditable(false);
		cbcIdCustomer.setDisable(true);
		btnFindIdCustomer.setDisable(true);


		txtDatePickerCustomer.setValue(null);

		loadDataSearchCustomer();
	}

	boolean result = false;
	int total = 0;
	int quantity = 0 ;
	public void clickChooseProduct(ActionEvent e) throws IOException {
		if(txtQuantityProduct.getText().toString().isEmpty()==true) {
			Error("Vui lòng nhập số lượng", btnExit);

			txtQuantityProduct.requestFocus();

			return;
		}
		try {
			quantity = Integer.parseInt(txtQuantityProduct.getText().toString());
		} catch (Exception e2) {
			Error("Số lượng không hợp lệ", btnExit);

			txtQuantityProduct.requestFocus();
			return;
		}
		
		if(quantity<=0) {
			Error("Số lượng không hợp lệ", btnExit);
			
			return;
		}


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
				if(t.getProductId().equalsIgnoreCase(product.getProductId())){ 
					if(t.getQuantity()>product.getQuantityOnShelf()) {
						try {
							Error("Không đủ hàng chỉ còn "+product.getQuantityOnShelf(), btnExit);
							result=true;
							return;
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						return;
					}
				}else if(t.getProductId().equals(product.getProductId())){
					t.setQuantity(quantity);
					result=true;
					return;
				}
			});
			if(result == false) {
				if(product.getQuantityOnShelf()<=0) {
					Error("Không đủ hàng chỉ còn "+product.getQuantityOnShelf(), btnExit);

					return;
				}else {
					if(quantity>product.getQuantityOnShelf()) {
						Error("Không đủ hàng chỉ còn :"+product.getQuantityOnShelf(), btnExit);

						return;
					}

					boolean checkExistst = false;

					for(int i=0;i<arrayOrderProduct.size();i++) {
						if(arrayOrderProduct.get(i).getProductId().equalsIgnoreCase(product.getProductId())) {
							arrayOrderProduct.get(i).setQuantity(quantity);
							checkExistst = true;
						}
					}
					if(checkExistst == false) {
						arrayOrderProduct.add(new Product(product.getProductId(), product.getName(),
								product.getPicture(), quantity,
								product.getDescription(), product.getStatus(), 
								product.getDateAdded(), product.getTitle(), product.getSupplier(),
								product.getQuantityRentDisk(),
								product.getQuantityOnShelf(),
								product.getQuantityOnHold()));
					}

				}

			}

			arrayOrderProduct.forEach(t->{

				total=0;

				total+=(t.getQuantity()*t.getTitle().getCategory().getPrice());

			});

			if(total != 0) {

				lblTotal.setText(String.valueOf(df.format(total))+" $");

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

				lblTotal.setText(String.valueOf(df.format(total))+" $");

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
		txtProductCurrentInShelf.setText("(Hiện có : ....)");

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
			List<LateFee> lateFee = lateFeeService.findAllLteFeeByIdCustomer(customerFind.getCustomerId());
			if(lateFee!=null && lateFee.size()>=1) {
				Error("Khách hàng có phí trễ hạn", btnExit);
			}
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
			List<LateFee> lateFee = lateFeeService.findAllLteFeeByIdCustomer(customerFind.getCustomerId());
			if(lateFee!=null && lateFee.size()>=1) {
				Error("Khách hàng có phí trễ hạn", btnExit);
			}
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
			//			txtQuantityProduct.setText(String.valueOf(product.getQuantity()));
			txtDescriptionProduct.setText(product.getDescription());
			txtStatusProduct.setText(product.getStatus());

			txtPriceProduct.setText(String.valueOf(df.format(product.getTitle().getCategory().getPrice()))+" $");
			
			txtProductCurrentInShelf.setText("( Hiện có trên kệ:"+String.valueOf(product.getQuantityOnShelf())+")");
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

		
		Bill bil = new Bill(txtIdBill.getText().toString(), LocalDate.now(), txtDatePayBill.getValue(), customerFind,KHONGCONO);

		if(billService.addBill(bil)==true) {
			for(int i=0;i<arrayOrderProduct.size();i++) {

				BillDetail billDetail = new BillDetail("BD"+ranDomNumber(),
						arrayOrderProduct.get(i).getQuantity(), 
						Math.round(arrayOrderProduct.get(i).getTitle().getCategory().getPrice())*arrayOrderProduct.get(i).getQuantity()
						, arrayOrderProduct.get(i),
						bil);


				billService.addBillDetail(billDetail);

			}
			
			arrayOrderProduct.forEach(t->{
				
				Product product = productService.findProductById(t.getProductId());
				product.setQuantityRentDisk(product.getQuantityRentDisk() + t.getQuantity());
				//product.getQuantityOnShelf() = 0
				//
				product.setQuantityOnShelf(product.getQuantityOnShelf()-t.getQuantity());
				
				if(product.getQuantityOnShelf()==0) {
					product.setStatus(CHOTHUE);
				}
				
				productService.updateProduct(product, t.getProductId());//
			});
			
			List<LateFee> lateFees =lateFeeService.findAllLteFeeByIdCustomer(customerFind.getCustomerId());
			
			if(lateFees!=null && lateFees.size()>=1) {
				Error("Khách hàng đang có phí trễ hạn (mã:"+customerFind.getCustomerId()+")", btnExit);
				resetAllField(e);
				return;
			}else {
				Success("Thuê đĩa thành công", btnExit);
				resetAllField(e);
				return;
			}
		
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

	public void btnClickAdd(ActionEvent e) throws IOException {
		FXMLLoader loader= new FXMLLoader(getClass().getResource(loadFormAddCustomer));

		Parent root=loader.load();

		FormAddCustomer ctlMain=loader.getController();

		String id=null;

		do {

			id="C"+ranDomNumber();

			ctlMain.txtMa.setText(id);

			ctlMain.txtDiaChi.requestFocus();

			ctlMain.txtNgaySinh.setValue(LocalDate.of(1999, 11, 23));

		} while (customerService.findCustomerById(id)!=null);

		loadFXML(root,btnExit).setOnHidden(ev->{



		});;

	}
}
