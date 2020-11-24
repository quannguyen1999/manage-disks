package application.controller;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import application.controller.impl.ProductImpl;
import application.controller.impl.BillImpl;
import application.controller.impl.CustomerImpl;
import application.controller.services.ProductService;
import application.controller.services.BillService;
import application.controller.services.CustomerService;
import application.entities.Product;
import application.entities.Bill;
import application.entities.BillDetail;
import application.entities.Customer;
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
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
public class FormAddBill extends DialogBox implements Initializable{

	//ánh xạ
	//border
	@FXML public BorderPane mainBd;

	//label
	@FXML Label lblTitle;

	//text field
	@FXML JFXTextField txtBillMa;
	@FXML JFXTextField txtBillDateOrder;
	@FXML JFXTextField txtCustomerName;
	@FXML JFXTextField txtCustomerAddress;

	//date picker
	@FXML JFXDatePicker txtBillDatePay;

	//radio button
	@FXML JFXRadioButton rdBillDebtYes;
	@FXML JFXRadioButton rdBillDebtNo;
	@FXML JFXRadioButton rdFindCustomerById;
	@FXML JFXRadioButton rdFindCustomerByPhone;

	//combobox
	@FXML ComboBox<String> cbcCustomerId=new ComboBox<String>();
	@FXML ComboBox<String> cbcCustomerPhone=new ComboBox<String>();
	@FXML ComboBox<String> cbcProductId=new ComboBox<String>();
	

	//jfx button
	@FXML JFXButton btn;

	//label
	@FXML Label lblTotal;

	//format số 
	DecimalFormat df = new DecimalFormat("#,###"); 

	
	//service
	public ProductService productService=new ProductImpl();
	public BillService billService=new BillImpl();
	public CustomerService customerService=new CustomerImpl();

	//array list
	public ArrayList<Product> listProductOrder=new ArrayList<>();
	public ArrayList<Product> listProductInDb=new ArrayList<>();
	public ArrayList<BillDetail> listBillDetail=new ArrayList<>();
	public ArrayList<Customer> listCustomer=new ArrayList<>();

	//border left
	@FXML BorderPane bdLeft;
	private TableView<Product> tbl_view;
	TableColumn<Product, String> colProductId;
	TableColumn<Product, String> colName;
	TableColumn<Product, Integer> colQuantity;
	TableColumn<Product, String> colStatus;
	TableColumn<Product, String> colDateAdded;
	TableColumn<Product, String> colPrice;

	//border right
	@FXML BorderPane bdRight;
	private TableView<Product> tbl_viewOrder;
	TableColumn<Product, String> colProductIdOrder;
	TableColumn<Product, String> colNameOrder;
	TableColumn<Product, String> colPictureOrder;
	TableColumn<Product, Integer> colQuantityOrder;
	TableColumn<Product, String> colDescriptionOrder;
	TableColumn<Product, String> colStatusOrder;
	TableColumn<Product, String> colDateAddedOrder;
	TableColumn<Product, String> colTitleIdOrder;
	TableColumn<Product, String> colSupplierIdOrder;
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		//set don't allow edit in text firled
		txtBillMa.setEditable(false);

		txtBillDateOrder.setEditable(false);
		
		txtCustomerName.setEditable(false);

		txtCustomerAddress.setEditable(false);

		//turn off combobox
		cbcCustomerId.setDisable(true);

		//add all list to this class
		customerService.listCustomer().forEach(t->{
			listCustomer.add(t);
		});

		productService.listProduct().forEach(t->{
			listProductInDb.add(t);
		});


		//load data search
		loadDataSearchCustomerId();

		loadDataSearchCustomerPhone();

		loadDataSearchProductId();

		//init table
		initTableInDb();

		initTableInOrder();
	}

	//xóa product
	public void btnXoaProduct(ActionEvent e) throws IOException{

		int result=tbl_viewOrder.getSelectionModel().getSelectedIndex();

		if(result!=-1) {

			FXMLLoader loader= new FXMLLoader(getClass().getResource(loadAreYouSure));

			Parent root=loader.load();

			AreYouSure ctlMain=loader.getController();

			new animatefx.animation.FadeIn(root).play();

			Stage stage=new Stage();

			stage.initOwner(btn.getScene().getWindow());

			stage.setScene(new Scene(root));

			stage.initStyle(StageStyle.UNDECORATED);

			stage.initModality(Modality.APPLICATION_MODAL);

			stage.show();

			stage.setOnHidden(efg->{

				if(ctlMain.result==true) {

					for(int i=0;i<listProductOrder.size();i++) {
						if(listProductOrder.get(i).getProductId().equals(tbl_viewOrder.getItems().get(result).getProductId())){
							
							listProductOrder.remove(i);

							uploadDuLieuOrderLenBang();

							countTotal();

							break;
						}
					}


				}else {

				}
			});

		}else {

			Error("bạn chưa chọn bảng cần xóa", btn);

		}

	}

	//reset bảng
	public void handleRefersh(ActionEvent e) {
		cbcProductId.setValue("");
		tbl_view.getItems().clear();
		tbl_view.getSelectionModel().clearSelection();
		uploadDuLieuLenBang();
	}

	//khỏi tạo table và ánh xạ
	public void initTableInOrder() {
		tbl_viewOrder=new TableView<Product>();

		colProductIdOrder=new TableColumn<Product, String>("mã");
		colNameOrder=new TableColumn<Product, String>("tên");
		colQuantityOrder=new TableColumn<Product, Integer>("số lượng");
		colStatusOrder=new TableColumn<Product, String>("status");
		colDateAddedOrder=new TableColumn<Product, String>("ngày thêm");

		tbl_viewOrder.getColumns().addAll(colProductIdOrder,
				colNameOrder,
				colQuantityOrder,
				colStatusOrder,
				colDateAddedOrder);

		bdLeft.setCenter(tbl_viewOrder);

		colProductIdOrder.setCellValueFactory(new PropertyValueFactory<>("productId"));
		colNameOrder.setCellValueFactory(new PropertyValueFactory<>("name"));
		colQuantityOrder.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		colStatusOrder.setCellValueFactory(new PropertyValueFactory<>("status"));
		colDateAddedOrder.setCellValueFactory(new PropertyValueFactory<>("dateAdded"));
		
		//set kích thước cho columns
		colProductIdOrder.setMinWidth(100);// .setCellValueFactory(new PropertyValueFactory<>("maKH"));
		colNameOrder.setMinWidth(180);//.setCellValueFactory(new PropertyValueFactory<>("diaChi"));
	}


	//Khởi tạo bảng
	public void initTableInDb() {
		tbl_view=new TableView<Product>();

		colProductId=new TableColumn<Product, String>("mã");
		colName=new TableColumn<Product, String>("tên");
		colQuantity=new TableColumn<Product, Integer>("số lượng");
		colStatus=new TableColumn<Product, String>("status");
		colDateAdded=new TableColumn<Product, String>("ngày thêm");
		colPrice=new TableColumn<Product, String>("giá");

		tbl_view.getColumns().addAll(colProductId,
				colPrice,
				colName,
				colQuantity,
				colStatus,
				colDateAdded);

		bdRight.setCenter(tbl_view);

		colProductId.setCellValueFactory(new PropertyValueFactory<>("productId"));
		colName.setCellValueFactory(new PropertyValueFactory<>("name"));
		colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
		colDateAdded.setCellValueFactory(new PropertyValueFactory<>("dateAdded"));
		colPrice.setCellValueFactory(
				cellData-> new SimpleStringProperty(String.valueOf(cellData.getValue().getTitle().getCategory().getPrice()) +" $"));
	
		//set kích thước columns
		colProductId.setMinWidth(100);// .setCellValueFactory(new PropertyValueFactory<>("maKH"));
		colName.setMinWidth(180);//.setCellValueFactory(new PropertyValueFactory<>("diaChi"));

		uploadDuLieuLenBang();
	}

	//úp dự liệu dưới table lên bảng
	private void uploadDuLieuLenBang() {
		listProductInDb.forEach(t->{
			tbl_view.getItems().add(t);
		});
	}

	//úp dự liệu dưới table lên bảng
	private void uploadDuLieuOrderLenBang() {
		tbl_viewOrder.getItems().clear();
		listProductOrder.forEach(t->{
			tbl_viewOrder.getItems().add(t);
		});
	}

	//khởi tạo search trong combobox
	private void loadDataSearchCustomerId() {
		ObservableList<String> items = FXCollections.observableArrayList();

		listCustomer.forEach(t->{

			items.add(t.getCustomerId());

		});

		FilteredList<String> filteredItems = new FilteredList<String>(items);

		cbcCustomerId.getEditor().textProperty().addListener(new InputFilter(cbcCustomerId, filteredItems, false));

		cbcCustomerId.setItems(filteredItems);

		cbcCustomerId.setEditable(true);
	}

	//khởi tạo search trong combobox
	private void loadDataSearchProductId() {
		ObservableList<String> items = FXCollections.observableArrayList();

		listProductInDb.forEach(t->{

			items.add(t.getProductId());

		});

		FilteredList<String> filteredItems = new FilteredList<String>(items);

		cbcProductId.getEditor().textProperty().addListener(new InputFilter(cbcProductId, filteredItems, false));

		cbcProductId.setItems(filteredItems);

		cbcProductId.setEditable(true);
	}
	
	//khởi tạo search trong combobox
	private void loadDataSearchCustomerPhone() {
		ObservableList<String> items = FXCollections.observableArrayList();


		listCustomer.forEach(t->{

			items.add(t.getPhone());

		});

		FilteredList<String> filteredItems = new FilteredList<String>(items);

		cbcCustomerPhone.getEditor().textProperty().addListener(new InputFilter(cbcCustomerPhone, filteredItems, false));

		cbcCustomerPhone.setItems(filteredItems);

		cbcCustomerPhone.setEditable(true);

		txtCustomerAddress.setText("");

		txtCustomerName.setText("");

		cbcCustomerId.setValue("");

		cbcCustomerPhone.setValue("");
	}


	//click find tìm kiếm
	public void findItemInTable(ActionEvent e) throws IOException {
		String textFind=null;

		try {

			textFind=cbcProductId.getSelectionModel().getSelectedItem().toString().trim();

		} catch (Exception e2) {

			Error("Bạn chưa nhập tìm kiếm", btn);

			cbcProductId.requestFocus();

		}

		if(textFind.isEmpty()) {

			Error("Bạn chưa nhập tìm kiếm", btn);

			cbcProductId.requestFocus();

			return;

		}

		tbl_view.getItems().clear();

		Product ProductFind=productService.findProductById(textFind);

		if(ProductFind==null) {

			Error("Không tìm thấy", btn);

			cbcProductId.requestFocus();

			return;

		}else {

			tbl_view.getItems().add(ProductFind);

		}

	}


	
	public void clickFindByCustomerId(ActionEvent e) {

		rdFindCustomerById.setSelected(true);

		cbcCustomerId.setDisable(false);

		cbcCustomerPhone.setDisable(true);

		txtCustomerAddress.setText("");

		txtCustomerName.setText("");

		cbcCustomerId.setValue("");

		cbcCustomerPhone.setValue("");

	}

	public void clickFindByCustomerPhone(ActionEvent e) {
		rdFindCustomerByPhone.setSelected(true);

		cbcCustomerId.setDisable(true);

		cbcCustomerPhone.setDisable(false);
	}

	public void findCustomerById(ActionEvent e) {

		listCustomer.forEach(t->{

			if(cbcCustomerId.getSelectionModel().getSelectedItem().toString().equals(	t.getCustomerId())) {
				cbcCustomerPhone.setValue(t.getPhone());
				txtCustomerName.setText(t.getName());
				txtCustomerAddress.setText(t.getAddress());
				return;
			}

		});
	}

	public void findCustomerByPhone(ActionEvent e) {

		listCustomer.forEach(t->{

			if(cbcCustomerPhone.getSelectionModel().getSelectedItem().toString().equals(	t.getPhone())) {
				cbcCustomerId.setValue(t.getCustomerId());
				txtCustomerName.setText(t.getName());
				txtCustomerAddress.setText(t.getAddress());
				return;
			}

		});
	}

	//chọn product
	public void btnChonProduct(ActionEvent e) throws IOException{

		int result=tbl_view.getSelectionModel().getSelectedIndex();

		if(result!=-1) {

			Product product=new Product();

			product=tbl_view.getItems().get(result);

			for(int i=0;i<listProductOrder.size();i++) {

				if(listProductOrder.get(i).getProductId().equals(product.getProductId())) {

					if(listProductOrder.get(i).getQuantity()>=product.getQuantity()) {

						Error("Không đủ hàng", btn);

						return;
					}


					listProductOrder.get(i).setQuantity(listProductOrder.get(i).getQuantity()+1);

					uploadDuLieuOrderLenBang();

					countTotal();

					return;
				}

			}

			if(product.getQuantity()<=0) {

				Error("Hết hàng", btn);

				return;
			}

			Product product2=new Product(product.getProductId(),
					product.getName(),
					product.getPicture(),1,product.getDescription(), product.getStatus(), product.getDateAdded()
					, product.getTitle(), product.getSupplier());

			listProductOrder.add(product2);

			uploadDuLieuOrderLenBang();

			countTotal();

			return;




		}else {

			Error("bạn chưa chọn sản phẩm", btn);

		}

	}

	
	//tính tổng tiền
	int total=0;
	public void countTotal() {

		total=0;

		listProductOrder.forEach(t->{
			total+=(t.getQuantity()*t.getTitle().getCategory().getPrice());
		});


		lblTotal.setText(String.valueOf(df.format(total)) +" $");

	}

	public void xoaRong(ActionEvent e) {
		txtBillDatePay.setValue(LocalDate.now());

		rdBillDebtNo.setSelected(true);

		cbcCustomerId.setValue("");

		cbcCustomerPhone.setValue("");

		txtCustomerName.setText("");

		txtCustomerAddress.setText("");

		rdFindCustomerByPhone.setSelected(true);

		cbcCustomerId.setEditable(false);

		listProductOrder.clear();

		uploadDuLieuOrderLenBang();

	}

	//kiểm tra ngày hợp lệ
	public boolean checkPayDate(ActionEvent e,String ma) throws IOException {
		String MaKT=ma.trim();
		if(MaKT.isEmpty()==false) {
			return true;
		}else {
			Error("Bạn chưa nhập ngày thanh toán",btn);
			return false;
		}
	}

	//kiểm tra mã customer
	public boolean checkCustomer(ActionEvent e,String ma) throws IOException {
		String MaKT=ma.trim();
		if(MaKT.isEmpty()==false) {
			return true;
		}else {
			Error("Bạn chưa chọn khách hàng",btn);
			return false;
		}
	}

	//click ok
	public void CLickOK(ActionEvent e) throws IOException {
		String billId=txtBillMa.getText().toString();
		String billDateOrder=txtBillDateOrder.getText().toString();
		LocalDate billPay=txtBillDatePay.getValue();
		String txtCustomerId=cbcCustomerId.getSelectionModel().getSelectedItem().toString();

		if(billPay==null) {
			Error("Bạn chưa nhập ngày trả", btn);

			txtBillDatePay.requestFocus();

			return;
		}

		if(checkCustomer(e,txtCustomerId)==false) {
			return;
		};

		if(listProductOrder.size()<=0) {

			Error("Bạn chưa chọn đĩa", btn);

			return;

		}

		Customer customer=customerService.findCustomerById(txtCustomerId);

		Bill bill=new Bill(billId, LocalDate.now(), billPay,customer , KHONGCONO);

		if(rdBillDebtYes.isSelected()==true) {

			bill.setDebt(NO);

		}

		if(lblTitle.getText().equals("Cập nhập bill")==false) {

			if(billService.addBill(bill)==true) {

				listProductOrder.forEach(t->{
					Product product = productService.findProductById(t.getProductId());

					Bill billFind = billService.findBillById(txtBillMa.getText().toString());

					BillDetail billDetail = 
							new BillDetail(checkIdBIllDetail(), t.getQuantity(), 
									t.getQuantity()*Math.round(t.getTitle().getCategory().getPrice()), product, billFind);
					billService.addBillDetail(billDetail);

				});

				listProductOrder.forEach(t->{

					Product product=productService.findProductById(t.getProductId());

					product.setQuantity(product.getQuantity()-t.getQuantity());

					productService.updateProduct(product, product.getProductId());

				});

				((Node)(e.getSource())).getScene().getWindow().hide();  

			}else{

				Error("Lỗi thêm không thành công", btn);

			};

		}else {

			if(billService.updateBill(bill,bill.getBillId())==null) {

				Error("Lỗi cập nhập không thành công", btn);

			}else{

				((Node)(e.getSource())).getScene().getWindow().hide();  

			};


		}
	}
	
	//kiểm tra id bill detail coi có trùng hay không
	public String checkIdBIllDetail() {
		String id=null;

		do {

			id="BD"+ranDomNumber();

		} while (billService.findBillDetailById(id)!=null);

		return id;
	}
	
	//đóng form
	public void btnCLoseWindow(ActionEvent e) throws IOException {

		((Node)(e.getSource())).getScene().getWindow().hide();  

	}

}
