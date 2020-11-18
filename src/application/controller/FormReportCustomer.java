package application.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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
import application.controller.impl.LateFeeImpl;
import application.controller.services.TitleService;
import application.controller.services.ProductService;
import application.controller.services.SupplierService;
import application.controller.services.BillService;
import application.controller.services.CustomerService;
import application.controller.services.LateFeeService;
import application.entities.Title;
import application.entities.Product;
import application.entities.Supplier;
import application.entities.Bill;
import application.entities.BillDetail;
import application.entities.Customer;
import application.entities.LateFee;
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
public class FormReportCustomer extends DialogBox implements Initializable{

	@FXML public BorderPane mainBd;

	@FXML Label lblTitle;

	@FXML ComboBox<String> cbcCustomerId=new ComboBox<String>();

	@FXML ComboBox<String> cbcCustomerPhone=new ComboBox<String>();

	@FXML JFXTextField txtCustomerName;

	@FXML JFXTextField txtCustomerAddress;

	@FXML JFXButton btn;

	@FXML Label lblTotal;

	@FXML ComboBox<String> cbcBillId= new ComboBox<String>();

	public ProductService productService=new ProductImpl();

	public BillService billService=new BillImpl();

	public LateFeeService lateFeeService = new LateFeeImpl();

	public CustomerService customerService=new CustomerImpl();

	public ArrayList<BillDetail> listBillDetail=new ArrayList<>();
	
	private ArrayList<Bill> listBillDaTa = new ArrayList<Bill>();

	@FXML BorderPane bdLeft;
	private TableView<Bill> tbl_view_bill;
	TableColumn<Bill, String> colBillId;
	TableColumn<Bill, String> colLocalDate;
	TableColumn<Bill, String> colBillPay;
	TableColumn<Bill, String> colDebt;

	@FXML BorderPane bdRight;
	private TableView<LateFee> tbl_view_lateFee;
	TableColumn<LateFee, String> colLateFeeId;
	TableColumn<LateFee, String> colPrice;
	TableColumn<LateFee, String> colDatePay;

	Customer customer = null;


	@Override
	public void initialize(URL location, ResourceBundle resources) {

		cbcCustomerId.setDisable(true);

		cbcCustomerPhone.setDisable(true);

		txtCustomerName.setEditable(false);

		txtCustomerAddress.setEditable(false);

		initTableInDbLateFee();

		initTableInBill();
		
		
	}

	public void loadDataSearchBillId() {
		ObservableList<String> items = FXCollections.observableArrayList();
		listBillDaTa.forEach(t->{
			System.out.println(t.getBillId());
			items.add(t.getBillId());

		});

		FilteredList<String> filteredItems = new FilteredList<String>(items);

		cbcBillId.getEditor().textProperty().addListener(new InputFilter(cbcBillId, filteredItems, false));

		cbcBillId.setItems(filteredItems);

		cbcBillId.setEditable(true);
	}

	public void handleRefersh(ActionEvent e) {

	}

	public void findAllLateFee(ActionEvent e) {
		List<LateFee> listLateFee = lateFeeService.findAllLteFeeByIdCustomer(customer.getCustomerId());
		if(listLateFee!=null) {
			listLateFee.forEach(t->{
				tbl_view_lateFee.getItems().add(t);
			});
		}

	}



	public void initTableInBill() {
		tbl_view_bill=new TableView<Bill>();

		colBillId=new TableColumn<Bill, String>("Mã bill");
		colLocalDate=new TableColumn<Bill, String>("Ngày đặt");
		colBillPay=new TableColumn<Bill, String>("Ngày trả");
		colDebt=new TableColumn<Bill, String>("nợ");

		tbl_view_bill.getColumns().addAll(colBillId,
				colLocalDate,
				colBillPay,
				colDebt);

		bdRight.setCenter(tbl_view_bill);

		colBillId.setCellValueFactory(cellData->new SimpleStringProperty(cellData
				.getValue().getBillId()));

		colLocalDate.setCellValueFactory(cellData-> new SimpleStringProperty(cellData.getValue().getLocalDate().toString()));

		colBillPay.setCellValueFactory(cellData-> new SimpleStringProperty(cellData.getValue().getBillPay().toString()));

		colDebt.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getDebt())));

		tbl_view_bill.setOnMouseClicked(e->{
			if(e.getClickCount()==1) {
				int result=tbl_view_bill.getSelectionModel().getSelectedIndex();
				if(result!=-1) {
					tbl_view_lateFee.getItems().clear();
					
					List<LateFee> listLateFeeFind = lateFeeService.findAllLateFeeByBillId(tbl_view_bill.getItems().get(result).getBillId());
					
					if(listLateFeeFind!=null) {
						listLateFeeFind.forEach(t->{
							tbl_view_lateFee.getItems().add(t);
						});
					}
					
				}
			}
		});
		
	}


	public void initTableInDbLateFee() {
		tbl_view_lateFee=new TableView<LateFee>();

		colLateFeeId=new TableColumn<LateFee, String>("Mã phí");
		colPrice=new TableColumn<LateFee, String>("Giá");
		colDatePay=new TableColumn<LateFee, String>("Ngày phải thanh toán");

		tbl_view_lateFee.getColumns().addAll(
				colLateFeeId,
				colPrice,
				colDatePay);

		bdLeft.setCenter(tbl_view_lateFee);

		colLateFeeId.setCellValueFactory(cellData-> new SimpleStringProperty(cellData.getValue().getLateFeetId()));

		colPrice.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getPrice())));

		colDatePay.setCellValueFactory(cellData-> new SimpleStringProperty(cellData.getValue().getDatePay().toString()));

	}


	public void uploadDuLieuBilLenBang(ActionEvent e) {
		upload();
	}
	
	public void upload() {
		List<Bill> listBill = billService.findAllBillByIdCustomer(customer.getCustomerId());
		tbl_view_bill.getItems().clear();
		listBillDaTa.clear();
		if(listBill!=null) {
			listBill.forEach(t->{
				listBillDaTa.add(t);
				tbl_view_bill.getItems().add(t);
			});
		}
	}
	
	
	

	public void findItemInTable(ActionEvent e) throws IOException {
		String textFind=null;

		try {

			textFind=cbcBillId.getSelectionModel().getSelectedItem().toString().trim();

		} catch (Exception e2) {

			Error("Bạn chưa nhập tìm kiếm", btn);

			cbcBillId.requestFocus();

		}

		if(textFind.isEmpty()) {

			Error("Bạn chưa nhập tìm kiếm", btn);

			cbcBillId.requestFocus();

			return;

		}

		tbl_view_bill.getItems().clear();

		Bill billFind=billService.findBillById(textFind);//.findProductById(textFind);

		if(billFind==null) {

			Error("Không tìm thấy", btn);

			cbcBillId.requestFocus();

			return;

		}else {

			tbl_view_bill.getItems().add(billFind);

		}

	}

	public void xoaRong(ActionEvent e) {
		findAllLateFee(e);
		uploadDuLieuBilLenBang(e);

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

	public void btnCLoseWindow(ActionEvent e) throws IOException {

		((Node)(e.getSource())).getScene().getWindow().hide();  

	}

}
