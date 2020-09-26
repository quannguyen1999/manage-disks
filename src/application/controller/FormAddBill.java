package application.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import com.sun.javafx.webkit.ThemeClientImpl;

import application.controller.impl.TitleImpl;
import application.controller.impl.ProductImpl;
import application.controller.impl.SupplierImpl;
import application.controller.impl.BillImpl;
import application.controller.impl.CustomerImpl;
import application.controller.services.TitleService;
import application.controller.services.ProductService;
import application.controller.services.SupplierService;
import application.controller.services.BillService;
import application.controller.services.CustomerService;
import application.entities.Title;
import application.entities.Product;
import application.entities.Supplier;
import application.entities.Bill;
import application.entities.Customer;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
public class FormAddBill extends DialogBox implements Initializable{

	@FXML public BorderPane mainBd;

	@FXML Label lblTitle;

	@FXML JFXTextField txtBillMa;

	@FXML JFXTextField txtBillDateOrder;

	@FXML JFXDatePicker txtBillDatePay;

	@FXML JFXRadioButton rdBillDebtYes;

	@FXML JFXRadioButton rdBillDebtNo;

	@FXML JFXRadioButton rdFindCustomerById;

	@FXML JFXRadioButton rdFindCustomerByPhone;

	@FXML ComboBox<String> cbcCustomerId=new ComboBox<String>();

	@FXML ComboBox<String> cbcCustomerPhone=new ComboBox<String>();

	@FXML ComboBox<String> cbcProductId=new ComboBox<String>();

	@FXML JFXTextField txtCustomerName;

	@FXML JFXTextField txtCustomerAddress;

	@FXML JFXButton btn;

	@FXML Label lblTotal;

	public ProductService productService=new ProductImpl();

	public BillService billService=new BillImpl();

	public CustomerService customerService=new CustomerImpl();

	public ArrayList<Product> listProductOrder=new ArrayList<>();

	public ArrayList<Product> listProductInDb=new ArrayList<>();

	public ArrayList<Customer> listCustomer=new ArrayList<>();

	@FXML BorderPane bdLeft;
	private TableView<Product> tbl_view;
	TableColumn<Product, String> colProductId;
	TableColumn<Product, String> colName;
	TableColumn<Product, Integer> colQuantity;
	TableColumn<Product, String> colStatus;
	TableColumn<Product, String> colDateAdded;

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

		txtBillMa.setEditable(false);

		txtBillDateOrder.setEditable(false);

		cbcCustomerId.setDisable(true);

		txtCustomerName.setEditable(false);

		txtCustomerAddress.setEditable(false);

		customerService.listCustomer().forEach(t->{
			listCustomer.add(t);
		});

		productService.listProduct().forEach(t->{

			listProductInDb.add(t);

		});


		loadDataSearchCustomerId();

		loadDataSearchCustomerPhone();

		loadDataSearchProductId();

		initTableInDb();

		initTableInOrder();
	}

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
						System.out.println("Begin");
						if(listProductOrder.get(i).getProductId().equals(tbl_viewOrder.getItems().get(result).getProductId())){
							System.out.println(listProductOrder.size());

							listProductOrder.remove(i);

							System.out.println(listProductOrder.size());

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

	public void handleRefersh(ActionEvent e) {
		cbcProductId.setValue("");
		tbl_view.getItems().clear();
		tbl_view.getSelectionModel().clearSelection();
		uploadDuLieuLenBang();
	}



	public void initTableInOrder() {
		tbl_viewOrder=new TableView<Product>();

		colProductIdOrder=new TableColumn<Product, String>("mã");
		colNameOrder=new TableColumn<Product, String>("tên");
		//		 colPicture=new TableColumn<Product, String>("hình");
		colQuantityOrder=new TableColumn<Product, Integer>("số lượng");
		//		 colDescription=new TableColumn<Product, String>("mô tả");
		colStatusOrder=new TableColumn<Product, String>("status");
		colDateAddedOrder=new TableColumn<Product, String>("ngày thêm");
		//		 colTitleId=new TableColumn<Product, String>("mã title");
		//		 colSupplierId=new TableColumn<Product, String>("mã ncc");

		tbl_viewOrder.getColumns().addAll(colProductIdOrder,
				colNameOrder,
				//				colPicture,
				colQuantityOrder,
				//				colDescription,
				colStatusOrder,
				colDateAddedOrder);
		//				colTitleId,
		//				colSupplierId);

		bdLeft.setCenter(tbl_viewOrder);

		colProductIdOrder.setCellValueFactory(new PropertyValueFactory<>("productId"));
		colNameOrder.setCellValueFactory(new PropertyValueFactory<>("name"));
		//		colPicture.setCellValueFactory(new PropertyValueFactory<>("picture"));
		colQuantityOrder.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		//		colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
		colStatusOrder.setCellValueFactory(new PropertyValueFactory<>("status"));
		colDateAddedOrder.setCellValueFactory(new PropertyValueFactory<>("dateAdded"));
		//		colTitleId.setCellValueFactory( cellData->new SimpleStringProperty(cellData.getValue().getTitle().getTitleId()));
		//		colSupplierId.setCellValueFactory(cellData->new SimpleStringProperty(cellData.getValue().getSupplier().getSupplierId()));

		colProductIdOrder.setMinWidth(100);// .setCellValueFactory(new PropertyValueFactory<>("maKH"));
		colNameOrder.setMinWidth(180);//.setCellValueFactory(new PropertyValueFactory<>("diaChi"));
		//		colDescription.setMinWidth(120);//.setCellValueFactory(new PropertyValueFactory<>("CMND"));
	}


	public void initTableInDb() {
		tbl_view=new TableView<Product>();

		colProductId=new TableColumn<Product, String>("mã");
		colName=new TableColumn<Product, String>("tên");
		//		 colPicture=new TableColumn<Product, String>("hình");
		colQuantity=new TableColumn<Product, Integer>("số lượng");
		//		 colDescription=new TableColumn<Product, String>("mô tả");
		colStatus=new TableColumn<Product, String>("status");
		colDateAdded=new TableColumn<Product, String>("ngày thêm");
		//		 colTitleId=new TableColumn<Product, String>("mã title");
		//		 colSupplierId=new TableColumn<Product, String>("mã ncc");

		tbl_view.getColumns().addAll(colProductId,
				colName,
				//				colPicture,
				colQuantity,
				//				colDescription,
				colStatus,
				colDateAdded);
		//				colTitleId,
		//				colSupplierId);

		bdRight.setCenter(tbl_view);

		colProductId.setCellValueFactory(new PropertyValueFactory<>("productId"));
		colName.setCellValueFactory(new PropertyValueFactory<>("name"));
		//		colPicture.setCellValueFactory(new PropertyValueFactory<>("picture"));
		colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		//		colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
		colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
		colDateAdded.setCellValueFactory(new PropertyValueFactory<>("dateAdded"));
		//		colTitleId.setCellValueFactory( cellData->new SimpleStringProperty(cellData.getValue().getTitle().getTitleId()));
		//		colSupplierId.setCellValueFactory(cellData->new SimpleStringProperty(cellData.getValue().getSupplier().getSupplierId()));

		colProductId.setMinWidth(100);// .setCellValueFactory(new PropertyValueFactory<>("maKH"));
		colName.setMinWidth(180);//.setCellValueFactory(new PropertyValueFactory<>("diaChi"));
		//		colDescription.setMinWidth(120);//.setCellValueFactory(new PropertyValueFactory<>("CMND"));

		uploadDuLieuLenBang();
	}

	private void uploadDuLieuLenBang() {
		listProductInDb.forEach(t->{
			tbl_view.getItems().add(t);
		});
	}

	private void uploadDuLieuOrderLenBang() {
		tbl_viewOrder.getItems().clear();
		listProductOrder.forEach(t->{
			tbl_viewOrder.getItems().add(t);
		});
	}

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

	public void btnChonProduct(ActionEvent e) throws IOException{

		int result=tbl_view.getSelectionModel().getSelectedIndex();

		if(result!=-1) {

			Product product=new Product();

			product=tbl_view.getItems().get(result);

			System.out.println(product.getQuantity());

			for(int i=0;i<listProductOrder.size();i++) {

				if(listProductOrder.get(i).getProductId().equals(product.getProductId())) {

					System.out.println(product.getQuantity());

					System.out.println(listProductOrder.get(i).getQuantity());

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

	int total=0;
	public void countTotal() {

		total=0;

		listProductOrder.forEach(t->{
			total+=t.getQuantity();
		});

		lblTotal.setText(String.valueOf(total));

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




	public boolean checkPayDate(ActionEvent e,String ma) throws IOException {
		String MaKT=ma.trim();
		if(MaKT.isEmpty()==false) {
			return true;
		}else {
			Error("Bạn chưa nhập ngày thanh toán",btn);
			return false;
		}
	}

	public boolean checkCustomer(ActionEvent e,String ma) throws IOException {
		String MaKT=ma.trim();
		if(MaKT.isEmpty()==false) {
			return true;
		}else {
			Error("Bạn chưa chọn khách hàng",btn);
			return false;
		}
	}


	public void CLickOK(ActionEvent e) throws IOException {
		String billId=txtBillMa.getText().toString();
		String billDateOrder=txtBillDateOrder.getText().toString();
		LocalDate billPay=txtBillDatePay.getValue();
		String txtCustomerId=cbcCustomerId.getSelectionModel().getSelectedItem().toString();

		if(billPay==null) {
			Error("Bạn chưa nhập ngày", btn);

			txtBillDateOrder.requestFocus();

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

		Bill bill=new Bill(billId, LocalDate.now(), billPay,customer , false);

		if(rdBillDebtYes.isSelected()==true) {

			bill.setDebt(true);

		}

		if(lblTitle.getText().equals("Cập nhập bill")==false) {

			if(billService.addBill(bill)==false) {

				
				
				Error("Lỗi thêm không thành công", btn);

			}else{
				
				listProductOrder.forEach(t->{
					
					Product product=productService.findProductById(t.getProductId());
					
					product.setQuantity(product.getQuantity()-t.getQuantity());
					
					productService.updateProduct(product, product.getProductId());
					
				});
				
				((Node)(e.getSource())).getScene().getWindow().hide();  

			};

		}else {

			if(billService.updateBill(bill,bill.getBillId())==null) {

				Error("Lỗi cập nhập không thành công", btn);

			}else{

				((Node)(e.getSource())).getScene().getWindow().hide();  

			};


		}
	}

	public void btnCLoseWindow(ActionEvent e) throws IOException {

		((Node)(e.getSource())).getScene().getWindow().hide();  

	}

}
