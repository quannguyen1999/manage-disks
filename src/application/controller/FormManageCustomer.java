package application.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import application.controller.impl.CustomerImpl;
import application.controller.services.CustomerService;
import application.entities.Customer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class FormManageCustomer extends DialogBox implements Initializable{
	private TableView<Customer> tbl_view;

	TableColumn<Customer, String> colCustomerId;
	TableColumn<Customer, String> colName;
	TableColumn<Customer, String> colAddress;
	TableColumn<Customer, String> colPhone;
	TableColumn<Customer, LocalDate> colDateOfBirth;

	@FXML BorderPane bd;

	private CustomerService customerService=new CustomerImpl();

	@FXML ComboBox<String> cbc=new ComboBox<String>();
	
	@FXML ComboBox<String> cbcPhone=new ComboBox<String>();
	
	@FXML JFXButton btnRefresh;

	List<Customer> listCustomer=new ArrayList<>();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initTable();

		loadDataSearch();

		cbc.setEditable(true);
		
		cbcPhone.setEditable(true);

		tbl_view.setOnMouseClicked(e->{
			if(e.getClickCount()==2) {
				int result=tbl_view.getSelectionModel().getSelectedIndex();
				if(result!=-1) {

					FXMLLoader loader= new FXMLLoader(getClass().getResource(loadFormAddCustomer));

					Parent root=null;
					try {
						root = loader.load();
					} catch (IOException e1) {
						e1.printStackTrace();
					}

					FormAddCustomer ctlMain=loader.getController();

					ctlMain.lblTitle.setText("Cập nhập khách hàng");

					ctlMain.txtMa.setText(tbl_view.getItems().get(result).getCustomerId());

					ctlMain.txtTenKH.setText(tbl_view.getItems().get(result).getName());
					
					ctlMain.txtDiaChi.setText(tbl_view.getItems().get(result).getAddress());

					ctlMain.txtDienThoai.setText(tbl_view.getItems().get(result).getPhone());

					ctlMain.txtNgaySinh.setValue(tbl_view.getItems().get(result).getDateOfBirth());
					
					ctlMain.btn.setText("Reset");
					
					ctlMain.customer=tbl_view.getItems().get(result);

					loadFXML(root,btnRefresh).setOnHidden(ev->{

						handleRefersh(new ActionEvent());

					});;
				}
			}
		});
	}


	public void btnXoaCustomer(ActionEvent e) throws IOException{

		int result=tbl_view.getSelectionModel().getSelectedIndex();
		
		if(result!=-1) {

			FXMLLoader loader= new FXMLLoader(getClass().getResource(loadAreYouSure));
			
			Parent root=loader.load();
			
			AreYouSure ctlMain=loader.getController();
			
			new animatefx.animation.FadeIn(root).play();
			
			Stage stage=new Stage();
			
			stage.initOwner(btnRefresh.getScene().getWindow());
			
			stage.setScene(new Scene(root));
			
			stage.initStyle(StageStyle.UNDECORATED);
			
			stage.initModality(Modality.APPLICATION_MODAL);
			
			stage.show();
			
			stage.setOnHidden(efg->{
			
				if(ctlMain.result==true) {
				
					customerService.removeCustomer(tbl_view.getItems().get(result).getCustomerId());
					
					handleRefersh(e);
				
				}else {

				}
			});
			
		}else {

			Error("bạn chưa chọn bảng cần xóa", btnRefresh);

		}

	}


	public void initTable() {
		tbl_view=new TableView<Customer>();

		colCustomerId=new TableColumn<Customer, String>("mã");
		colName=new TableColumn<Customer, String>("Tên");
		colAddress=new TableColumn<Customer, String>("Địa chỉ");
		colPhone=new TableColumn<Customer, String>("Điện thoại");
		colDateOfBirth=new TableColumn<Customer, LocalDate>("ngày sinh");

		tbl_view.getColumns().addAll(colCustomerId,colName,colAddress,colPhone,colDateOfBirth);

		bd.setCenter(tbl_view);

		colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
		colName.setCellValueFactory(new PropertyValueFactory<>("name"));
		colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
		colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
		colDateOfBirth.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));

		colCustomerId.setMinWidth(100);// .setCellValueFactory(new PropertyValueFactory<>("maKH"));
		colName.setMinWidth(180);//.setCellValueFactory(new PropertyValueFactory<>("diaChi"));
		colAddress.setMinWidth(120);//.setCellValueFactory(new PropertyValueFactory<>("CMND"));
		colPhone.setMinWidth(100);//.setCellValueFactory(new PropertyValueFactory<>("soDT"));
		colDateOfBirth.setMinWidth(150);//.setCellValueFactory(new PropertyValueFactory<>("tenKH"));

		uploadDuLieuLenBang();
	}

	private void uploadDuLieuLenBang() {
		List<Customer> cuss=customerService.listCustomer();
		cuss.forEach(t->{
			tbl_view.getItems().add(t);
			listCustomer.add(t);
		});
	}

	public void handleRefersh(ActionEvent e) {
		//		cbc.getItems().clear();
		cbc.setValue("");
		tbl_view.getItems().clear();
		uploadDuLieuLenBang();
		
		cbcPhone.setValue("");
	}

	public void btnClickAdd(ActionEvent e) throws IOException {
		FXMLLoader loader= new FXMLLoader(getClass().getResource(loadFormAddCustomer));

		Parent root=loader.load();

		FormAddCustomer ctlMain=loader.getController();

		String id=null;

		do {

			id="C"+ranDomNumber();

			ctlMain.txtMa.setText(id);

		} while (customerService.findCustomerById(id)!=null);

		loadFXML(root,btnRefresh).setOnHidden(ev->{

			handleRefersh(e);

		});;


	}

	public void findItemInTable(ActionEvent e) throws IOException {
		String textFind=null;

		try {

			textFind=cbc.getSelectionModel().getSelectedItem().toString().trim();

		} catch (Exception e2) {

			Error("Bạn chưa nhập tìm kiếm", btnRefresh);

			cbc.requestFocus();

		}

		if(textFind.isEmpty()) {

			Error("Bạn chưa nhập tìm kiếm", btnRefresh);

			cbc.requestFocus();

			return;

		}

		tbl_view.getItems().clear();

		Customer customerFind=customerService.findCustomerById(textFind);

		if(customerFind==null) {

			Error("Không tìm thấy", btnRefresh);

			cbc.requestFocus();

			return;

		}else {

			tbl_view.getItems().add(customerFind);

		}

	}
	
	public void findItemPhoneInTable(ActionEvent e) throws IOException {
		String textFind=null;

		try {

			textFind=cbcPhone.getSelectionModel().getSelectedItem().toString().trim();

		} catch (Exception e2) {

			Error("Bạn chưa nhập tìm kiếm", btnRefresh);

			cbcPhone.requestFocus();

		}

		if(textFind.isEmpty()) {

			Error("Bạn chưa nhập tìm kiếm", btnRefresh);

			cbcPhone.requestFocus();

			return;

		}

		tbl_view.getItems().clear();

		Customer customerFind=customerService.findCustomerByPhone(textFind);

		if(customerFind==null) {

			Error("Không tìm thấy", btnRefresh);

			cbcPhone.requestFocus();

			return;

		}else {

			tbl_view.getItems().add(customerFind);

		}

	}



	private void loadDataSearch() {
		ObservableList<String> items = FXCollections.observableArrayList();
		ObservableList<String> itemsPhone = FXCollections.observableArrayList();
		
		
		List<Customer> accs=customerService.listCustomer();

		accs.forEach(t->{

			items.add(t.getCustomerId());
			
			itemsPhone.add(t.getPhone());

		});

		FilteredList<String> filteredItems = new FilteredList<String>(items);

		cbc.getEditor().textProperty().addListener(new InputFilter(cbc, filteredItems, false));

		cbc.setItems(filteredItems);

		cbc.setEditable(true);
		
		FilteredList<String> filteredItemsPhone = new FilteredList<String>(itemsPhone);

		cbcPhone.getEditor().textProperty().addListener(new InputFilter(cbcPhone, filteredItemsPhone, false));

		cbcPhone.setItems(filteredItemsPhone);

		cbcPhone.setEditable(true);


	}
}
