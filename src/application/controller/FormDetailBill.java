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
public class FormDetailBill extends DialogBox implements Initializable{

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
	public ArrayList<BillDetail> listBillDetail=new ArrayList<>();
	public ArrayList<Customer> listCustomer=new ArrayList<>();


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
		
		txtBillDatePay.setEditable(false);
		
		txtBillDateOrder.setEditable(false);
		
		rdBillDebtNo.setDisable(true);
		
		rdBillDebtYes.setDisable(true);
		
		cbcCustomerId.setDisable(true);
		
		cbcCustomerPhone.setDisable(true);
		
		txtCustomerName.setEditable(false);
		
		txtCustomerAddress.setEditable(false);
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

			bdRight.setCenter(tbl_viewOrder);

			colProductIdOrder.setCellValueFactory(new PropertyValueFactory<>("productId"));
			colNameOrder.setCellValueFactory(new PropertyValueFactory<>("name"));
			colQuantityOrder.setCellValueFactory(new PropertyValueFactory<>("quantity"));
			colStatusOrder.setCellValueFactory(new PropertyValueFactory<>("status"));
			colDateAddedOrder.setCellValueFactory(new PropertyValueFactory<>("dateAdded"));
			
			//set kích thước cho columns
			colProductIdOrder.setMinWidth(100);// .setCellValueFactory(new PropertyValueFactory<>("maKH"));
			colNameOrder.setMinWidth(180);//.setCellValueFactory(new PropertyValueFactory<>("diaChi"));
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


	//úp dự liệu dưới table lên bảng
	public void uploadDuLieuOrderLenBang() {
		tbl_viewOrder.getItems().clear();
		listProductOrder.forEach(t->{
			tbl_viewOrder.getItems().add(t);
		});
	}

	//khởi tạo search trong combobox
	private void loadDataSearchProductId() {
//		ObservableList<String> items = FXCollections.observableArrayList();
//
//		listProductInDb.forEach(t->{
//
//			items.add(t.getProductId());
//
//		});
//
//		FilteredList<String> filteredItems = new FilteredList<String>(items);
//
//		cbcProductId.getEditor().textProperty().addListener(new InputFilter(cbcProductId, filteredItems, false));
//
//		cbcProductId.setItems(filteredItems);
//
//		cbcProductId.setEditable(true);
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
//		String textFind=null;
//
//		try {
//
//			textFind=cbcProductId.getSelectionModel().getSelectedItem().toString().trim();
//
//		} catch (Exception e2) {
//
//			Error("Bạn chưa nhập tìm kiếm", btn);
//
//			cbcProductId.requestFocus();
//
//		}
//
//		if(textFind.isEmpty()) {
//
//			Error("Bạn chưa nhập tìm kiếm", btn);
//
//			cbcProductId.requestFocus();
//
//			return;
//
//		}

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

	//tính tổng tiền
	int total=0;
	public void countTotal() {

		total=0;

		listProductOrder.forEach(t->{
			total+=(t.getQuantity()*t.getTitle().getCategory().getPrice());
		});


		lblTotal.setText(String.valueOf(df.format(total)) +" $");

	}

	//click ok
	public void CLickOK(ActionEvent e) throws IOException {
				((Node)(e.getSource())).getScene().getWindow().hide();  
	}
	
	//đóng form
	public void btnCLoseWindow(ActionEvent e) throws IOException {

		((Node)(e.getSource())).getScene().getWindow().hide();  

	}

}
