package application.controller;

import java.awt.TextField;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

import javax.persistence.Column;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.criterion.CountProjection;
import org.hibernate.query.criteria.internal.expression.function.AggregationFunction.COUNT;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
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

public class ReturnDiskV2  extends DialogBox implements Initializable{

	//Label 
	@FXML Label txtPriceLateFee;

	//radio button
	@FXML JFXRadioButton rdBuaSauTT;
	@FXML JFXRadioButton rdThanhToanLuon;

	@FXML JFXButton btnThanhToan;
	@FXML JFXButton btnXoaRong;
	@FXML JFXButton btnExit;

	//Bill
	//	@FXML JFXDatePicker txtDatePayBill;

	//customer
	@FXML ComboBox<String>  cbcIdCustomer=new ComboBox<String>();;
	@FXML ComboBox<String>  cbcPhoneCustomer=new ComboBox<String>();;
	@FXML JFXTextField txtNameCustomer;
	@FXML JFXTextField txtAddressCustomer;
	@FXML JFXDatePicker txtDatePickerCustomer;

	//product
	//	@FXML ComboBox<String> cbcIdProduct=new ComboBox<String>();;
	@FXML JFXTextField txtProductId;
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
	LateFeeService lateFeeService = new LateFeeImpl();


	//table 
	@FXML BorderPane bd;
	@FXML BorderPane bdBillDetailDebt;

	private TableView<BillDetail> tbl_view;

	TableColumn<BillDetail, String> colBIllDetailId;
	TableColumn<BillDetail, String> colQuantity;
	TableColumn<BillDetail, String> colTotalAmmount;
	TableColumn<BillDetail, String> colIdProduct;
	TableColumn<BillDetail, String> colNameProduct;

	private TableView<BillDetail> tbl_view_billdetailDebt;

	TableColumn<BillDetail, String> colBIllDetailId_billdetailDebt;
	TableColumn<BillDetail, String> colQuantity_billdetailDebt;
	TableColumn<BillDetail, String> colTotalAmmount_billdetailDebt;
	TableColumn<BillDetail, String> colIdProduct_billdetailDebt;
	TableColumn<BillDetail, String> colNameProduct_billdetailDebt;

	ProductService ProductService=new ProductImpl();

	BillService billService=new BillImpl();

	List<Product> listProduct=new ArrayList<>();

	List<Product> arrayOrderProduct=new ArrayList<>();

	List<Bill> listBill =new ArrayList<>();

	List<BillDetail> listBillDetailWantReturn = new ArrayList<>();

	DecimalFormat df = new DecimalFormat("#,###"); 

	Customer customer;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		rdBuaSauTT.setDisable(true);
		rdThanhToanLuon.setDisable(true);

		//load data 
		loadDataSearchIdProduct();
		loadDataSearchCustomer();

		//init customer
		txtNameCustomer.setEditable(false);
		txtAddressCustomer.setEditable(false);
		txtDatePickerCustomer.setEditable(false);

		//init product
		txtProductId.setEditable(false);
		txtNameProduct.setEditable(false);
		txtQuantityProduct.setEditable(true);
		txtDescriptionProduct.setEditable(false);
		txtStatusProduct.setEditable(false);

		//init table 
		initTable();
		initTableBillDetailDebt();

		//init form customer 
		rdPhoneCustomer.setSelected(true);
		cbcPhoneCustomer.setEditable(true);
		cbcPhoneCustomer.setDisable(false);
		btnFindPhoneCustomer.setDisable(false);

		cbcIdCustomer.setEditable(false);
		cbcIdCustomer.setDisable(true);
		btnFindIdCustomer.setDisable(true);

		txtPriceProduct.setEditable(false);

		tbl_view.setOnMouseClicked(e->{
			if(e.getClickCount()==2) {
				int result=tbl_view.getSelectionModel().getSelectedIndex();
				if(result!=-1) {

					//					cbcIdProduct.setValue(tbl_view.getItems().get(result).getProductId());
					//					txtNameProduct.setText(tbl_view.getItems().get(result).getName());
					//					txtQuantityProduct.setText(String.valueOf(tbl_view.getItems().get(result).getQuantity()));
					//					txtDescriptionProduct.setText(tbl_view.getItems().get(result).getDescription());
					//					txtStatusProduct.setText(tbl_view.getItems().get(result).getStatus());
					//					txtPriceProduct.setText(String.valueOf(df.format(tbl_view.getItems().get(result).getTitle().getCategory().getPrice()))+" $");
				}
			}
		});

		setActionForProductInDebt();

	}

	public void initTable() {
		tbl_view=new TableView<BillDetail>();

		colBIllDetailId=new TableColumn<BillDetail, String>("mã bill detail");
		colQuantity=new TableColumn<BillDetail, String>("Số lượng");
		colTotalAmmount=new TableColumn<BillDetail, String>("Tổng");
		colIdProduct=new TableColumn<BillDetail, String>("mã product");
		colNameProduct=new TableColumn<BillDetail, String>("tên product");


		tbl_view.getColumns().addAll(colBIllDetailId,colQuantity
				,colTotalAmmount,colIdProduct,colNameProduct);

		bd.setCenter(tbl_view);

		colBIllDetailId.setCellValueFactory(new PropertyValueFactory<>("billDetailId"));
		colQuantity.setCellValueFactory(  cellData->new SimpleStringProperty(String.valueOf(cellData.getValue().getQuantity())));
		colTotalAmmount.setCellValueFactory( cellData->new SimpleStringProperty(String.valueOf(cellData.getValue().getTotalAmmount())));
		colIdProduct.setCellValueFactory( cellData->new SimpleStringProperty(String.valueOf(cellData.getValue().getProduct().getProductId())));
		colNameProduct.setCellValueFactory( cellData->new SimpleStringProperty(String.valueOf(cellData.getValue().getProduct().getName())));
	}

	public void initTableBillDetailDebt() {
		tbl_view_billdetailDebt=new TableView<BillDetail>();

		colBIllDetailId_billdetailDebt=new TableColumn<BillDetail, String>("mã bill detail");
		colQuantity_billdetailDebt=new TableColumn<BillDetail, String>("Số lượng");
		colTotalAmmount_billdetailDebt=new TableColumn<BillDetail, String>("Tổng");
		colIdProduct_billdetailDebt=new TableColumn<BillDetail, String>("mã product");
		colNameProduct_billdetailDebt=new TableColumn<BillDetail, String>("tên product");


		tbl_view_billdetailDebt.getColumns().addAll(colBIllDetailId_billdetailDebt,colQuantity_billdetailDebt
				,colTotalAmmount_billdetailDebt,colIdProduct_billdetailDebt,colNameProduct_billdetailDebt);

		bdBillDetailDebt.setCenter(tbl_view_billdetailDebt);

		colBIllDetailId_billdetailDebt.setCellValueFactory(new PropertyValueFactory<>("billDetailId"));
		colQuantity_billdetailDebt.setCellValueFactory(  cellData->new SimpleStringProperty(String.valueOf(cellData.getValue().getQuantity())));
		colTotalAmmount_billdetailDebt.setCellValueFactory( cellData->new SimpleStringProperty(String.valueOf(cellData.getValue().getTotalAmmount())));
		colIdProduct_billdetailDebt.setCellValueFactory( cellData->new SimpleStringProperty(String.valueOf(cellData.getValue().getProduct().getProductId())));
		colNameProduct_billdetailDebt.setCellValueFactory( cellData->new SimpleStringProperty(String.valueOf(cellData.getValue().getProduct().getName())));
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
		Customer customerFind= null;
		if(rdPhoneCustomer.isSelected()) {
			String phone = cbcPhoneCustomer.getValue();

			if(phone==null || phone.isEmpty()) {
				Error("Vui lòng chọn khách hàng", btnExit);
				return;
			}

			customerFind=customerService.findCustomerByPhone(phone);

			if(customerFind==null) {

				Error("Không tìm thấy", btnExit);

				cbcPhoneCustomer.requestFocus();

				return;

			}
		}else {
			String phone = cbcIdCustomer.getValue();

			if(phone.isEmpty()) {
				Error("Vui lòng chọn khách hàng", btnExit);
				return;
			}

			customerService.findCustomerById(phone);

			if(customerFind==null) {

				Error("Không tìm thấy", btnExit);

				cbcIdCustomer.requestFocus();

				return;

			}
		}






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

			textFind=txtProductId.getText().toString();//.getSelectionModel().getSelectedItem().toString().trim();

		} catch (Exception e2) {

			Error("Bạn chưa nhập tìm kiếm", btnThanhToan);

			txtProductId.requestFocus();

		}

		if(textFind.isEmpty()) {

			Error("Bạn chưa nhập tìm kiếm", btnExit);

			txtProductId.requestFocus();

			return;

		}


		Product product = productService.findProductById(textFind);

		if(product==null) {

			Error("Không tìm thấy", btnExit);

			txtProductId.requestFocus();

			return;

		}else {
			result = false;

			List<BillDetail> listBillDetail = billService.findAllBillDetailByIdBill((ArrayList<Bill>) listBill);

			if(listBillDetail!=null && listBillDetail.size()>=1) {
				listBillDetail.forEach(t->{
					if(t.getProduct().getProductId().equalsIgnoreCase(product.getProductId())) {
						if(t.getQuantity()<=quantity) {

						}
						return;
					}
				});

				int countX =0;
				int number =-1;
				for(int i=0;i<listBillDetail.size();i++) {

					if(listBillDetail.get(i).getProduct().getProductId().equalsIgnoreCase(product.getProductId())){
						countX+=1;
					}

				}
				for(int i=0;i<listBillDetail.size();i++) {

					if(listBillDetail.get(i).getProduct().getProductId().equalsIgnoreCase(product.getProductId())){
						if(listBillDetail.get(i).getQuantity()<quantity) {
							if(countX==1) {
								Error("Số lượng thuê product "+product.getProductId()+" có "+listBillDetail.get(i).getQuantity(), btnExit);
								break;
							}
							countX-=1;

						}else {
							BillDetail billDetail = new BillDetail();
							billDetail.setBill(listBillDetail.get(i).getBill());
							billDetail.setBillDetailId(listBillDetail.get(i).getBillDetailId());
							billDetail.setProduct(listBillDetail.get(i).getProduct());
							billDetail.setQuantity(quantity);
							billDetail.setTotalAmmount(quantity*Math.round(product.getTitle().getCategory().getPrice()));
							boolean result = false;
							for(int j=0; j<listBillDetailWantReturn.size();j++) {
								if(listBillDetailWantReturn.get(j).getProduct().getProductId().equalsIgnoreCase(billDetail.getProduct().getProductId())) {
									listBillDetailWantReturn.get(j).setQuantity(quantity);
									listBillDetailWantReturn.get(j).setTotalAmmount(billDetail.getTotalAmmount());
									result=true;
									break;
								}
							}
							if(result==false) {
								listBillDetailWantReturn.add(billDetail);
							}
							number = i;
							break;
						}
					}
				}
			}

			tbl_view.getItems().clear();

			int count =0;

			for(int i=0;i<listBillDetailWantReturn.size();i++) {
				count+=(listBillDetailWantReturn.get(i).getQuantity()*product.getTitle().getCategory().getPrice());
				tbl_view.getItems().add(listBillDetailWantReturn.get(i));
			}
			
//			Error(String.valueOf(count), btnExit);

			if(rdThanhToanLuon.isSelected()) {

				List<LateFee> lateFee = lateFeeService.findAllLteFeeByIdCustomer(customerFind.getCustomerId());
				if(lateFee!=null && lateFee.size()>=1) {

					rdBuaSauTT.setDisable(false);
					
					rdThanhToanLuon.setDisable(false);

					rdThanhToanLuon.setSelected(true);

					int countPriceFee = 0;

					for(int i=0;i<lateFee.size();i++) {
						countPriceFee+=lateFee.get(i).getPrice();
					}
					
					count=count+countPriceFee;

					return;
				}

			}

			lblTotal.setText(String.valueOf(df.format(count))+" $");
		}
	}
	
	public void actionBuaSauThanhToan(ActionEvent e) {
		
		int total = 0 ;
		for(int i=0;i<listBillDetailWantReturn.size();i++) {
			total+=(listBillDetailWantReturn.get(i).getQuantity()*listBillDetailWantReturn.get(i).getProduct().getTitle().getCategory().getPrice());
		}
		
		lblTotal.setText(df.format(total));
		
	
	}
	
	public void actionThanhToanLuon(ActionEvent e) {
		int total = 0 ;
		for(int i=0;i<listBillDetailWantReturn.size();i++) {
			total+=(listBillDetailWantReturn.get(i).getQuantity()*listBillDetailWantReturn.get(i).getProduct().getTitle().getCategory().getPrice());
		}
		if(cbcIdCustomer.getValue()!=null) {
			List<LateFee> lateFee = lateFeeService.findAllLteFeeByIdCustomer(cbcIdCustomer.getValue());
			if(lateFee!=null && lateFee.size()>=1) {

				rdBuaSauTT.setDisable(false);
				rdThanhToanLuon.setDisable(false);

				rdThanhToanLuon.setSelected(true);

				int countPriceFee = 0;

				for(int i=0;i<lateFee.size();i++) {
					countPriceFee+=lateFee.get(i).getPrice();
				}
				
				total+=countPriceFee;

			}
		}
		
		
		lblTotal.setText(df.format(total));
	}

	public void clickDeleteOrderInTable(ActionEvent e) throws IOException {
		int result=tbl_view.getSelectionModel().getSelectedIndex();

		if(result!=-1) {

			for(int i=0;i<listBillDetailWantReturn.size() ;i++) {
				if(tbl_view.getItems().get(result).getBillDetailId().equalsIgnoreCase(listBillDetailWantReturn.get(i).getBillDetailId())) {
					listBillDetailWantReturn.remove(i);
					break;
				}
			}

			tbl_view.getItems().clear();

			total = 0;

			listBillDetailWantReturn.forEach(t->{

				tbl_view.getItems().add(t);

				total+=(t.getTotalAmmount());

			});

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
		txtProductId.setText("");
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

		txtProductId.setText("");
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
		
		rdBuaSauTT.setDisable(true);
		rdThanhToanLuon.setDisable(true);
		lblTotal.setText("....");
		txtPriceLateFee.setText("....");

		setActionForProductInDebt();

	}

	public void setActionForProductInDebt() {
		tbl_view_billdetailDebt.setOnMouseClicked(e->{
			if(e.getClickCount()==2) {
				int result=tbl_view_billdetailDebt.getSelectionModel().getSelectedIndex();
				txtProductId.setText(tbl_view_billdetailDebt.getItems().get(result).getProduct().getProductId());
				txtNameProduct.setText(tbl_view_billdetailDebt.getItems().get(result).getProduct().getName());
				txtPriceProduct.setText(String.valueOf(Math.round(tbl_view_billdetailDebt.getItems().get(result).getProduct().getTitle().getCategory().getPrice())));
				txtDescriptionProduct.setText(tbl_view_billdetailDebt.getItems().get(result).getProduct().getDescription());
				txtStatusProduct.setText(tbl_view_billdetailDebt.getItems().get(result).getProduct().getStatus());
			}
		});
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

		customer = customerFind;

		if(customerFind==null) {

			Error("Không tìm thấy", btnExit);

			cbcIdCustomer.requestFocus();

			return;

		}else {
			resetListBill(customerFind.getCustomerId());

			tbl_view_billdetailDebt.getItems().clear();

			if(listBill.size()>=1) {
				listBill.forEach(t->{
					ArrayList<Bill> listBill = new ArrayList<>();
					listBill.add(t);
					ArrayList<BillDetail> listBillDetail=(ArrayList<BillDetail>) billService.findAllBillDetailByIdBill(listBill);
					if(listBillDetail!=null && listBillDetail.size()>=1) {
						for(int i=0;i<listBillDetail.size();i++) {
							tbl_view_billdetailDebt.getItems().add(listBillDetail.get(i));
						}
					}
				});
			}

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

		customer =customerFind;

		if(customerFind==null) {

			Error("Không tìm thấy", btnExit);

			cbcPhoneCustomer.requestFocus();

			return;

		}else {

			resetListBill(customerFind.getCustomerId());

			tbl_view_billdetailDebt.getItems().clear();

			if(listBill.size()>=1) {
				listBill.forEach(t->{
					ArrayList<Bill> listBill = new ArrayList<>();
					listBill.add(t);
					ArrayList<BillDetail> listBillDetail=(ArrayList<BillDetail>) billService.findAllBillDetailByIdBill(listBill);
					if(listBillDetail!=null && listBillDetail.size()>=1) {
						for(int i=0;i<listBillDetail.size();i++) {
							tbl_view_billdetailDebt.getItems().add(listBillDetail.get(i));
						}
					}
				});
			}


			cbcIdCustomer.setValue(customerFind.getCustomerId());
			txtNameCustomer.setText(customerFind.getName());
			txtAddressCustomer.setText(customerFind.getAddress());
			txtDatePickerCustomer.setValue(customerFind.getDateOfBirth());


			List<LateFee> lateFee = lateFeeService.findAllLteFeeByIdCustomer(customerFind.getCustomerId());
			if(lateFee!=null && lateFee.size()>=1) {

				rdBuaSauTT.setDisable(false);
				rdThanhToanLuon.setDisable(false);

				rdThanhToanLuon.setSelected(true);

				int countPriceFee = 0;

				for(int i=0;i<lateFee.size();i++) {
					countPriceFee+=lateFee.get(i).getPrice();
				}

				lblTotal.setText(df.format(countPriceFee)+" $");
				
				txtPriceLateFee.setText(df.format(countPriceFee)+" $");

				return;
			}

		}

	}

	public void resetListBill(String customerId) {
		listBill.clear();
		listBill=billService.findAllBillByIdCustomer(customerId);
	}

	public void findItemInTableIdProduct(ActionEvent e) throws IOException {
		String textFind=null;

		try {

			textFind=txtProductId.getText().toString();//.getSelectionModel().getSelectedItem().toString().trim();

		} catch (Exception e2) {

			Error("Bạn chưa nhập tìm kiếm", btnThanhToan);

			txtProductId.requestFocus();

		}

		if(textFind.isEmpty()) {

			Error("Bạn chưa nhập tìm kiếm", btnExit);

			txtProductId.requestFocus();

			return;

		}


		Product product = productService.findProductById(textFind);

		if(product==null) {

			Error("Không tìm thấy", btnExit);

			txtProductId.requestFocus();

			return;

		}else {
			txtProductId.setText(product.getProductId());
			txtNameProduct.setText(product.getName());
			txtDescriptionProduct.setText(product.getDescription());
			txtStatusProduct.setText(product.getStatus());

			txtPriceProduct.setText(String.valueOf(df.format(product.getTitle().getCategory().getPrice()))+" $");
		}

	}

	private void loadDataSearchIdProduct() {
		//		ObservableList<String> items = FXCollections.observableArrayList();
		//
		//		List<Product> accs=productService.listProduct();
		//
		//		accs.forEach(t->{
		//
		//			items.add(t.getProductId());
		//
		//		});
		//
		//		FilteredList<String> filteredItems = new FilteredList<String>(items);
		//
		//		txtProductId.getEditor().textProperty().addListener(new InputFilter(cbcIdProduct, filteredItems, false));
		//
		//		txtProductId.setItems(filteredItems);
		//
		//		txtProductId.setEditable(true);
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

			txtProductId.requestFocus();

			return;

		}

		Customer customerFind = customer;

		for(int i=0;i<listBillDetailWantReturn.size();i++) {

			Bill bill = listBillDetailWantReturn.get(i).getBill();

			if(bill.getBillPay().isBefore(LocalDate.now())) {
				LateFee lateFee = new LateFee("LF"+ranDomNumber(), listBillDetailWantReturn.get(i).getProduct().getTitle().getCategory().getPriceLateFee()*listBillDetailWantReturn.get(i).getQuantity(), LocalDate.now(), bill, "");
				lateFeeService.addLateFee(lateFee);
			}

		}

		for(int i=0;i<listBillDetailWantReturn.size();i++) {
			BillDetail billDetail = billService.findBillDetailById(listBillDetailWantReturn.get(i).getBillDetailId());
			if(listBillDetailWantReturn.get(i).getQuantity() == billDetail.getQuantity()) {
				billService.removeBillDetail(listBillDetailWantReturn.get(i).getBillDetailId());
			}else if(listBillDetailWantReturn.get(i).getQuantity()<billDetail.getQuantity()) {
				billDetail.setQuantity(billDetail.getQuantity()-listBillDetailWantReturn.get(i).getQuantity());
				billDetail.setTotalAmmount(billDetail.getTotalAmmount()-listBillDetailWantReturn.get(i).getTotalAmmount());
				billService.updateBillDetail(billDetail, billDetail.getBillDetailId());
			}
			Product product = listBillDetailWantReturn.get(i).getProduct();
			product.setQuantityRentDisk(product.getQuantityRentDisk()-listBillDetailWantReturn.get(i).getQuantity());
			product.setQuantityOnShelf(product.getQuantityOnShelf()+listBillDetailWantReturn.get(i).getQuantity());
			productService.updateProduct(product, product.getProductId());
		}

		txtProductId.setText("");//.setValue(null);

		txtNameProduct.setText("");

		txtQuantityProduct.setText("");

		txtDescriptionProduct.setText("");

		txtStatusProduct.setText("");

		txtPriceProduct.setText("");

		tbl_view.getItems().clear();

		listBillDetailWantReturn.clear();

		listBill.clear();

		resetListBill(customerFind.getCustomerId());

		cbcIdCustomer.setValue(customerFind.getCustomerId());
		txtNameCustomer.setText(customerFind.getName());
		txtAddressCustomer.setText(customerFind.getAddress());
		txtDatePickerCustomer.setValue(customerFind.getDateOfBirth());

		lblTotal.setText("");

		tbl_view_billdetailDebt.getItems().clear();

		if(listBill.size()>=1) {
			listBill.forEach(t->{
				ArrayList<Bill> listBill = new ArrayList<>();
				listBill.add(t);
				ArrayList<BillDetail> listBillDetail=(ArrayList<BillDetail>) billService.findAllBillDetailByIdBill(listBill);
				if(listBillDetail!=null && listBillDetail.size()>=1) {
					for(int i=0;i<listBillDetail.size();i++) {
						tbl_view_billdetailDebt.getItems().add(listBillDetail.get(i));
					}
				}
			});
		}
		
		if(rdThanhToanLuon.isSelected()) {
			
			List<LateFee> lateFee = lateFeeService.findAllLteFeeByIdCustomer(customerFind.getCustomerId());
			if(lateFee!=null && lateFee.size()>=1) {
				lateFee.forEach(t->{
					lateFeeService.removeLateFee(t.getLateFeetId());
				});
			}
			
			txtPriceLateFee.setText("");
			rdBuaSauTT.setDisable(true);
			rdThanhToanLuon.setDisable(true);
			
		}else {
			int countPriceFee = 0 ;
			List<LateFee> lateFee = lateFeeService.findAllLteFeeByIdCustomer(customerFind.getCustomerId());
			if(lateFee!=null && lateFee.size()>=1) {
				for(int i=0;i<lateFee.size();i++) {
					countPriceFee+=lateFee.get(i).getPrice();
				}
				txtPriceLateFee.setText(df.format(countPriceFee));
				rdBuaSauTT.setSelected(true);
			}
		}
		
		

		Success("Trả đĩa thành công", btnExit);

	}

	public int ranDomNumber() {
		int min = 0;
		int max = 1000000000;
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
