package application.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
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
public class FormPay extends DialogBox implements Initializable{

	@FXML public BorderPane mainBd;

	@FXML Label lblTitle;

	@FXML JFXTextField txtLateFee;

	@FXML JFXTextField txtBillMa;

	@FXML JFXDatePicker txtBillDateOrder;

	@FXML JFXDatePicker txtBillDatePay;

	@FXML JFXRadioButton rdBillDebtYes;

	@FXML JFXRadioButton rdBillDebtNo;

	@FXML ComboBox<String> cbcCustomerId=new ComboBox<String>();

	@FXML ComboBox<String> cbcCustomerPhone=new ComboBox<String>();

	@FXML JFXTextField txtCustomerName;

	@FXML JFXTextField txtCustomerAddress;

	@FXML JFXButton btn;

	@FXML Label lblTotal;

	@FXML JFXTextField txtPayWant;

	public ProductService productService=new ProductImpl();

	public BillService billService=new BillImpl();

	public LateFeeService lateFeeService = new LateFeeImpl();

	public CustomerService customerService=new CustomerImpl();

	public ArrayList<BillDetail> listBillDetailX=new ArrayList<>();


	@FXML BorderPane bdLeft;
	private TableView<BillDetail> tbl_view;
	TableColumn<BillDetail, String> colBillDetail;
	TableColumn<BillDetail, String> colQuantity;
	TableColumn<BillDetail, String> colTotalAmmount;
	TableColumn<BillDetail, String> colProductId;
	TableColumn<BillDetail, String> colName;
	TableColumn<BillDetail, String> colPrice;

	Bill bill = null;
	
	LateFee lateFee = null;


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		txtLateFee.setEditable(false);


		txtBillMa.setEditable(false);

		txtBillDatePay.setEditable(false);

		txtBillDateOrder.setDisable(true);

		txtBillDatePay.setEditable(true);

		txtBillDateOrder.setDisable(true);


		rdBillDebtNo.setDisable(true);

		rdBillDebtYes.setDisable(true);


		cbcCustomerId.setDisable(true);

		cbcCustomerPhone.setDisable(true);

		txtCustomerName.setEditable(false);

		txtCustomerAddress.setEditable(false);

		initTableInOrder();

		//		uploadDuLieuLenBang(bill.getBillId());
	}



	public void handleRefersh(ActionEvent e) {
		tbl_view.getItems().clear();
		tbl_view.getSelectionModel().clearSelection();
		//		uploadDuLieuLenBang();
	}



	public void initTableInOrder() {
		tbl_view=new TableView<BillDetail>();

		colBillDetail=new TableColumn<BillDetail, String>("mã bill detail");
		colQuantity=new TableColumn<BillDetail, String>("số lượng");
		colTotalAmmount=new TableColumn<BillDetail, String>("Tổng");
		colProductId=new TableColumn<BillDetail, String>("Mả sản phẩm");
		colName=new TableColumn<BillDetail, String>("tên sản phẩm");

		tbl_view.getColumns().addAll(
				colBillDetail,
				colQuantity,
				colTotalAmmount,
				colProductId,
				colName);

		bdLeft.setCenter(tbl_view);

		colBillDetail.setCellValueFactory(celldata-> new SimpleStringProperty(
				celldata.getValue().getBillDetailId()));

		colQuantity.setCellValueFactory(celldata-> new SimpleStringProperty(
				String.valueOf(celldata.getValue().getQuantity())));

		colTotalAmmount.setCellValueFactory(celldata-> new SimpleStringProperty(
				String.valueOf(celldata.getValue().getTotalAmmount())));

		colProductId.setCellValueFactory(celldata-> new SimpleStringProperty(
				celldata.getValue().getProduct().getProductId()));

		colName.setCellValueFactory(celldata-> new SimpleStringProperty(
				celldata.getValue().getProduct().getName()));

		//		colProductIdOrder.setMinWidth(100);// .setCellValueFactory(new PropertyValueFactory<>("maKH"));
		//		colNameOrder.setMinWidth(180);//.setCellValueFactory(new PropertyValueFactory<>("diaChi"));
	}



	public  void uploadDuLieuLenBang(String idBill) {

		ArrayList<Bill> listBill = new ArrayList<>();
		listBill.add(bill);
		List<BillDetail> listBillDetail = billService.findAllBillDetailByIdBill(listBill);
		if(listBillDetail != null) {
			listBillDetail.forEach(t->{
				listBillDetailX.add(t);
				tbl_view.getItems().add(t);
			});
		}

		countTotal();
	}


	int total=0;
	public void countTotal() {

//		listBillDetailX.forEach(t->{
//			total+=(t.getTotalAmmount());
//		});

		lblTotal.setText(String.valueOf(total) +" $");

	}




	public void xoaRong(ActionEvent e) {
		//		txtBillDatePay.setValue(LocalDate.now());
		//
		//		rdBillDebtNo.setSelected(true);
		//
		//		cbcCustomerId.setValue("");
		//
		//		cbcCustomerPhone.setValue("");
		//
		//		txtCustomerName.setText("");
		//
		//		txtCustomerAddress.setText("");
		//
		//		rdFindCustomerByPhone.setSelected(true);
		//
		//		cbcCustomerId.setEditable(false);
		//
		//		listProductOrder.clear();
		//
		//		uploadDuLieuOrderLenBang();

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
		if(txtPayWant.getText().toString().isEmpty()) {

			Error("Vui lòng nhập số tiền muốn trả", btn);

			return;
		}

		int number = 0;

		try {
			number = Integer.parseInt(txtPayWant.getText().toString());

		} catch (Exception e2) {
			// TODO: handle exception


			Error("Số tiền không hợp lệ", btn);

			return;
		}

		if(number>total) {
			Error("Số tiền không hợp lệ", btn);

			return;
		}
		
		total = total - number;
		
		lateFee.setPrice((float) total);
		
		lateFeeService.updateLateFee(lateFee, lateFee.getLateFeetId());
		
		lblTotal.setText(String.valueOf(total));

		if(total <=0 ) {

			lateFeeService.removeLateFee(lateFee.getLateFeetId());
			
			((Node)(e.getSource())).getScene().getWindow().hide();  
			
			return;
		}else {
			
			txtPayWant.setText("");
			
			Success("Thanh toán thành công, số tiền còn lại là "+ total, btn);
		}
		
		
		
	}

	public void btnCLoseWindow(ActionEvent e) throws IOException {

		((Node)(e.getSource())).getScene().getWindow().hide();  

	}

}
