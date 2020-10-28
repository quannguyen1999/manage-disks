package application.controller;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
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
import application.entities.Category;
import application.entities.Customer;
import application.entities.Product;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ReturnDisk extends DialogBox implements Initializable{

	//customer
	@FXML ComboBox<String>  cbcIdCustomer=new ComboBox<String>();;
	@FXML ComboBox<String>  cbcPhoneCustomer=new ComboBox<String>();;
	@FXML JFXTextField txtNameCustomer;
	@FXML JFXTextField txtAddressCustomer;
	@FXML JFXDatePicker txtDatePickerCustomer;

	//product
	@FXML RadioButton rdIdCustomer;
	@FXML RadioButton rdPhoneCustomer;
	@FXML JFXButton btnFindIdCustomer;
	@FXML JFXButton btnFindPhoneCustomer;

	ProductService productService = new ProductImpl();
	CustomerService customerService = new CustomerImpl();
	BillService billService=new BillImpl();

	//table 
	@FXML BorderPane bd;

	@FXML BorderPane bdLeft;

	private TableView<BillDetail> tbl_view;

	TableColumn<BillDetail, String> colIdBillDetail;
	TableColumn<BillDetail, String> colQuantity;
	TableColumn<BillDetail, String> colTotalAmmoun;
	TableColumn<BillDetail, String> colProductId;
	TableColumn<BillDetail, String> colProductNamProduct0;

	//left
	private TableView<BillDetail> tbl_viewLeft;

	TableColumn<BillDetail, String> colIdBillDetailLEft;
	TableColumn<BillDetail, String> colQuantityLeft;
	TableColumn<BillDetail, String> colTotalAmmountLeft;
	TableColumn<BillDetail, String> colProductIdLeft;
	TableColumn<BillDetail, String> colProductNamProduct;

	ArrayList<BillDetail> listAllBillDetail = new ArrayList<>();
	ArrayList<BillDetail> listAllBillDetailWantReturn = new ArrayList<>();

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		//load data 
		loadDataSearchCustomer();

		//init customer
		txtNameCustomer.setEditable(false);
		txtAddressCustomer.setEditable(false);
		txtDatePickerCustomer.setEditable(false);

		//init table 
		initTable();
		initTableLeft();

		//init form customer 
		rdPhoneCustomer.setSelected(true);
		cbcPhoneCustomer.setEditable(true);
		cbcPhoneCustomer.setDisable(false);
		btnFindPhoneCustomer.setDisable(false);

		cbcIdCustomer.setEditable(false);
		cbcIdCustomer.setDisable(true);
		btnFindIdCustomer.setDisable(true);

	}

	public void initTable() {
		tbl_view=new TableView<BillDetail>();

		colIdBillDetail=new TableColumn<BillDetail, String>("mã");
		colQuantity=new TableColumn<BillDetail, String>("số lượng");
		colTotalAmmoun=new TableColumn<BillDetail, String>("Tổng");
		colProductId=new TableColumn<BillDetail, String>("mã sp");
		colProductNamProduct0=new TableColumn<BillDetail, String>("tên sp");

		tbl_view.getColumns().addAll(colIdBillDetail,
				colQuantity,
				colTotalAmmoun,
				colProductId,
				colProductNamProduct0);

		bd.setCenter(tbl_view);

		colIdBillDetail.setCellValueFactory(new PropertyValueFactory<>("billDetailId"));
		colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		colTotalAmmoun.setCellValueFactory(new PropertyValueFactory<>("totalAmmount"));

		colProductId.setCellValueFactory( cellData->
		new SimpleStringProperty(cellData.getValue().getProduct().getProductId()));

		colProductNamProduct0.setCellValueFactory( cellData->
		new SimpleStringProperty(cellData.getValue().getProduct().getName()));

		colProductId.setMinWidth(100);// .setCellValueFactory(new PropertyValueFactory<>("maKH"));
		colProductNamProduct0.setMinWidth(180);//.setCellValueFactory(new PropertyValueFactory<>("diaChi"));

	}

	public void initTableLeft() {
		tbl_viewLeft=new TableView<BillDetail>();

		colIdBillDetailLEft=new TableColumn<BillDetail, String>("mã");
		colQuantityLeft=new TableColumn<BillDetail, String>("số lượng");
		colTotalAmmountLeft=new TableColumn<BillDetail, String>("Tổng");
		colProductIdLeft=new TableColumn<BillDetail, String>("mã sp");
		colProductNamProduct=new TableColumn<BillDetail, String>("tên sp");

		tbl_viewLeft.getColumns().addAll(colIdBillDetailLEft,
				colQuantityLeft,
				colTotalAmmountLeft,
				colProductIdLeft,
				colProductNamProduct);

		bdLeft.setCenter(tbl_viewLeft);

		colIdBillDetailLEft.setCellValueFactory(new PropertyValueFactory<>("billDetailId"));
		colQuantityLeft.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		colTotalAmmountLeft.setCellValueFactory(new PropertyValueFactory<>("totalAmmount"));

		colProductIdLeft.setCellValueFactory( cellData->
		new SimpleStringProperty(cellData.getValue().getProduct().getProductId()));

		colProductNamProduct.setCellValueFactory( cellData->
		new SimpleStringProperty(cellData.getValue().getProduct().getName()));

		colProductIdLeft.setMinWidth(100);// .setCellValueFactory(new PropertyValueFactory<>("maKH"));
		colProductNamProduct.setMinWidth(180);//.setCellValueFactory(new PropertyValueFactory<>("diaChi"));
	}

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


	public void resetAllField(ActionEvent e) throws IOException {
		tbl_view.getItems().clear();

		tbl_viewLeft.getItems().clear();

		listAllBillDetail.clear();

		listAllBillDetailWantReturn.clear();

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

			Error("Bạn chưa nhập tìm kiếm", btnFindIdCustomer);

			cbcIdCustomer.requestFocus();

		}

		if(textFind.isEmpty()) {

			Error("Bạn chưa nhập tìm kiếm", btnFindIdCustomer);

			cbcIdCustomer.requestFocus();

			return;

		}


		Customer customerFind=customerService.findCustomerById(textFind);

		if(customerFind==null) {

			Error("Không tìm thấy", btnFindIdCustomer);

			cbcIdCustomer.requestFocus();

			return;

		}else {
			cbcPhoneCustomer.setValue(customerFind.getPhone());
			txtNameCustomer.setText(customerFind.getName());
			txtAddressCustomer.setText(customerFind.getAddress());
			txtDatePickerCustomer.setValue(customerFind.getDateOfBirth());

			ArrayList<Bill> listBill=(ArrayList<Bill>) 
					billService.findAllBillByIdCustomer(customerFind.getCustomerId());

			List<BillDetail> listBillDetail=billService.findAllBillDetailByIdBill(listBill);

			listAllBillDetail.clear();

			tbl_viewLeft.getItems().clear();

			listBillDetail.forEach(t->{
				tbl_viewLeft.getItems().add(t);
				listAllBillDetail.add(t);
			});
		}

	}

	public void findItemInTablePhoneCustomer(ActionEvent e) throws IOException {
		String textFind=null;

		try {

			textFind=cbcPhoneCustomer.getSelectionModel().getSelectedItem().toString().trim();

		} catch (Exception e2) {

			Error("Bạn chưa nhập tìm kiếm", btnFindIdCustomer);

			cbcPhoneCustomer.requestFocus();

		}

		if(textFind.isEmpty()) {

			Error("Bạn chưa nhập tìm kiếm", btnFindIdCustomer);

			cbcPhoneCustomer.requestFocus();

			return;

		}


		Customer customerFind=customerService.findCustomerByPhone(textFind);

		if(customerFind==null) {

			Error("Không tìm thấy", btnFindIdCustomer);

			cbcPhoneCustomer.requestFocus();

			return;

		}else {
			cbcIdCustomer.setValue(customerFind.getCustomerId());
			txtNameCustomer.setText(customerFind.getName());
			txtAddressCustomer.setText(customerFind.getAddress());
			txtDatePickerCustomer.setValue(customerFind.getDateOfBirth());
		}

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

	public void clickChooseDiskINTable(ActionEvent e) throws IOException {

		int result=tbl_viewLeft.getSelectionModel().getSelectedIndex();

		if(result!=-1) {

			for(int i=0;i<listAllBillDetail.size();i++) {
				System.out.println(result);
				System.out.println(tbl_viewLeft.getSelectionModel().getSelectedItem());

				if(listAllBillDetail.get(i).getBillDetailId().equalsIgnoreCase(
						tbl_viewLeft.getSelectionModel().getSelectedItem().getBillDetailId())) {

					listAllBillDetailWantReturn.add(listAllBillDetail.get(i));

					tbl_view.getItems().add(listAllBillDetail.get(i));

					listAllBillDetail.remove(i);

					break;
				}

			}


			listAllBillDetail.forEach(t->{
				System.out.println(t);
			});

			tbl_viewLeft.getItems().clear();

			listAllBillDetail.forEach(t->{
				tbl_viewLeft.getItems().add(t); 
			});



		}else {

			Error("bạn chưa chọn bảng cần xóa", btnFindIdCustomer);

		}

	}

	public void clickDeleteReturnDiskInTable(ActionEvent e) throws IOException {
		int result=tbl_view.getSelectionModel().getSelectedIndex();

		if(result!=-1) {

			for(int i=0;i<listAllBillDetailWantReturn.size();i++) {

				if(tbl_view.getSelectionModel().getSelectedItem().getBillDetailId()
						.equalsIgnoreCase(
								listAllBillDetailWantReturn.get(i).getBillDetailId())) {
					listAllBillDetail.add(listAllBillDetailWantReturn.get(i));

					listAllBillDetailWantReturn.remove(i);

					break;

				}
			}

			tbl_view.getItems().clear();

			tbl_viewLeft.getItems().clear();

			listAllBillDetail.forEach(t->{
				tbl_viewLeft.getItems().add(t); 
			});

			listAllBillDetailWantReturn.forEach(t->{
				tbl_view.getItems().add(t); 
			});

		}else {

			Error("bạn chưa chọn bảng cần xóa", btnFindIdCustomer);

		}

	}



	public void btnCLoseWindow(ActionEvent e) throws IOException {

		((Node)(e.getSource())).getScene().getWindow().hide();  

	}

	public void btnOKReturnDisk(ActionEvent e) throws IOException {
		String textFind=null;

		if(rdIdCustomer.isSelected()) {
			try {

				textFind=cbcIdCustomer.getSelectionModel().getSelectedItem().toString().trim();

			} catch (Exception e2) {

				Error("Bạn chưa nhập id khách hàng muốn trả đĩa", btnFindIdCustomer);

				cbcIdCustomer.requestFocus();

			}

			if(textFind.isEmpty()) {

				Error("Bạn chưa nhập tìm kiếm", btnFindIdCustomer);

				cbcIdCustomer.requestFocus();

				return;

			}
		}else {
			try {

				textFind=cbcPhoneCustomer.getSelectionModel().getSelectedItem().toString().trim();

			} catch (Exception e2) {

				Error("Bạn chưa nhập phone khách hàng muốn trả", btnFindPhoneCustomer);

				cbcPhoneCustomer.requestFocus();

			}

			if(textFind.isEmpty()) {

				Error("Bạn chưa nhập tìm kiếm", btnFindIdCustomer);

				cbcPhoneCustomer.requestFocus();

				return;

			}
		}

		if(tbl_view.getItems().size()<=0) {

			Error("Chưa có đĩa muốn trả nào", btnFindIdCustomer);

			return;

		}

		for(int i=0;i<listAllBillDetailWantReturn.size();i++) {

			billService.removeBillDetail(listAllBillDetailWantReturn.get(i).getBillDetailId());

		}

		tbl_view.getItems().clear();

		FXMLLoader loader= new FXMLLoader(getClass().getResource(loadSuccess));

		Parent root=loader.load();

		Success ctlMain=loader.getController();

		ctlMain.lblSuccess.setText("Trả đĩa thành công");

		new animatefx.animation.FadeIn(root).play();

		Stage stage=new Stage();

		stage.initOwner(btnFindIdCustomer.getScene().getWindow());

		stage.setScene(new Scene(root));

		stage.initStyle(StageStyle.UNDECORATED);

		stage.initModality(Modality.APPLICATION_MODAL);

		stage.show();

	}
}
