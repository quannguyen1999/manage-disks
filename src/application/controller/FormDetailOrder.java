package application.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
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
import application.controller.impl.OrderDetailImpl;
import application.controller.impl.OrderImpl;
import application.controller.services.TitleService;
import application.controller.services.ProductService;
import application.controller.services.SupplierService;
import application.controller.services.BillService;
import application.controller.services.CustomerService;
import application.controller.services.OrderDetailService;
import application.controller.services.OrderService;
import application.entities.Title;
import application.entities.Product;
import application.entities.Bill;
import application.entities.BillDetail;
import application.entities.Customer;
import application.entities.Order;
import application.entities.OrderDetail;
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
public class FormDetailOrder extends DialogBox implements Initializable{

	@FXML public BorderPane mainBd;

	@FXML Label lblTitle;

	@FXML JFXTextField txtOrderMa;

	@FXML JFXDatePicker txtOrderDate;
	
	@FXML JFXTextField txtCustomerId;
	
	@FXML JFXTextField txtCustomerPhone;

	@FXML JFXTextField txtCustomerName;

	@FXML JFXTextField txtCustomerAddress;

	@FXML JFXButton btn;

	@FXML Label lblTotal;

	public TitleService titlService = new TitleImpl();

	public OrderService orderService = new OrderImpl();
	
	public OrderDetailService orderDetailService = new OrderDetailImpl();

	public CustomerService customerService=new CustomerImpl();

	public ArrayList<Customer> listCustomer=new ArrayList<>();

	public ArrayList<Title> listTitle=new ArrayList<>();

	public ArrayList<Title> listTitleWantOrder=new ArrayList<>();

	@FXML BorderPane bdRight;
	private TableView<Title> tbl_viewOrderV;
	TableColumn<Title, String> colTitleIdV;
	TableColumn<Title, String> colNameV;
	TableColumn<Title, String> colStatusV;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		initTableInDb();
		
		txtOrderDate.setValue(LocalDate.now());
		
		txtOrderDate.setDisable(true);
		
		txtOrderDate.setEditable(false);
		
		txtOrderMa.setEditable(false);

		txtCustomerId.setEditable(false);
		
		txtCustomerPhone.setEditable(false);

		txtCustomerName.setEditable(false);

		txtCustomerAddress.setEditable(false);

		customerService.listCustomer().forEach(t->{
			listCustomer.add(t);
		});

	}
	
	public void loadListTitle(String orderId) {
		List<OrderDetail> listOrderDetail = orderDetailService.findAllOrderDetailByOrderId(orderId);
		if(listOrderDetail!=null && listOrderDetail.size()>=1) {
			listOrderDetail.forEach(t->{
				tbl_viewOrderV.getItems().add(t.getTitle());
			});
		}
	}


	public void handleRefersh(ActionEvent e) {
		tbl_viewOrderV.getItems().clear();
		tbl_viewOrderV.getSelectionModel().clearSelection();
	}

	public void initTableInDb() {
		tbl_viewOrderV = new TableView<Title>();

		colTitleIdV = new TableColumn<Title, String>("Mã title");
		colNameV = new TableColumn<Title, String>("tên title");
		colStatusV = new TableColumn<Title, String>("status");

		tbl_viewOrderV.getColumns().addAll(colTitleIdV,colNameV,colStatusV);

		bdRight.setCenter(tbl_viewOrderV);

		colTitleIdV.setCellValueFactory(cellData -> new SimpleStringProperty(
				cellData.getValue().getTitleId()));

		colNameV.setCellValueFactory(cellData -> new SimpleStringProperty(
				cellData.getValue().getName()));

		colStatusV.setCellValueFactory(cellData -> new SimpleStringProperty(
				String.valueOf(cellData.getValue().getStatus())));

	}



	public void CLickOK(ActionEvent e) throws IOException {
				((Node)(e.getSource())).getScene().getWindow().hide();  
	}


	public void btnCLoseWindow(ActionEvent e) throws IOException {

		((Node)(e.getSource())).getScene().getWindow().hide();  

	}

}
