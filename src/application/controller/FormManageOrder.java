package application.controller;

import java.io.IOException;
import java.net.URL;
import java.text.spi.CollatorProvider;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;

import application.controller.impl.CustomerImpl;
import application.controller.impl.OrderDetailImpl;
import application.controller.impl.OrderImpl;
import application.controller.impl.OrderImpl;
import application.controller.services.CustomerService;
import application.controller.services.OrderDetailService;
import application.controller.services.OrderService;
import application.controller.services.OrderService;
import application.entities.Customer;
import application.entities.Order;
import application.entities.OrderDetail;
import application.entities.Order;
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

public class FormManageOrder extends DialogBox implements Initializable{
	private TableView<Order> tbl_view;

	TableColumn<Order, String> colOrderId;
	TableColumn<Order, String> colOrderDate;
	TableColumn<Order, String> colCustomerId;
	TableColumn<Order, String> colCustomerPhone;
	TableColumn<Order, String> colCustomerName;
	TableColumn<Order, String> colCustomrAddress;


	@FXML BorderPane bd;

	public OrderService OrderService=new OrderImpl();
	
	public OrderDetailService orderDetailService = new OrderDetailImpl();
	
	public CustomerService customerService = new CustomerImpl();

	@FXML ComboBox<String> cbc=new ComboBox<String>();
	@FXML ComboBox<String> cbcPhoneKh=new ComboBox<String>();
	@FXML ComboBox<String> cbcIdKh=new ComboBox<String>();

	@FXML JFXButton btnRefresh;

	List<Order> listOrder=new ArrayList<>();
	
	@FXML JFXRadioButton rdIdOrder;
	
	@FXML JFXRadioButton rdIdKh;
	
	@FXML JFXRadioButton rdIdPhone;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
		initTable();

		loadDataSearch();
		
		loadDataSearchKh();
		
		setActionForTable();

		cbc.setDisable(true);
		cbcIdKh.setDisable(true);
		cbcPhoneKh.setDisable(false);
		
		cbcPhoneKh.setEditable(true);

	}
	
	public void setActionForTable() {
		tbl_view.setOnMouseClicked(e->{
			if(e.getClickCount()==2) {
				int result=tbl_view.getSelectionModel().getSelectedIndex();
				FXMLLoader loader= new FXMLLoader(getClass().getResource(loadFormDetailOrder));

				Parent root=null;
				try {
					root = loader.load();
				} catch (IOException e1) {
					e1.printStackTrace();
				}

				FormDetailOrder ctlMain=loader.getController();
				
				ctlMain.txtCustomerId.setText(tbl_view.getItems().get(result).getCustomer().getCustomerId());
				
				ctlMain.txtCustomerName.setText(tbl_view.getItems().get(result).getCustomer().getName());
				
				ctlMain.txtCustomerPhone.setText(tbl_view.getItems().get(result).getCustomer().getPhone());
				
				ctlMain.txtCustomerAddress.setText(tbl_view.getItems().get(result).getCustomer().getAddress());
				
				ctlMain.txtOrderDate.setValue(tbl_view.getItems().get(result).getOrderDate());
				
				ctlMain.txtOrderMa.setText(tbl_view.getItems().get(result).getOrderId());
				
				ctlMain.loadListTitle(tbl_view.getItems().get(result).getOrderId());

				loadFXML(root,btnRefresh).setOnHidden(ev->{


				});;
			}
		});
	}

	public void clickRdOne(ActionEvent e) {
		
		cbc.setEditable(true);
		cbcIdKh.setEditable(false);
		cbcPhoneKh.setEditable(false);
		
		cbc.setDisable(false);
		cbcIdKh.setDisable(true);
		cbcPhoneKh.setDisable(true);
	}
	
	public void clickRdTwo(ActionEvent e) {
		
		cbc.setEditable(false);
		cbcIdKh.setEditable(true);
		cbcPhoneKh.setEditable(false);
		
		cbc.setDisable(true);
		cbcIdKh.setDisable(false);
		cbcPhoneKh.setDisable(true);
	}
	
	public void clickRdThree(ActionEvent e) {
		cbc.setEditable(false);
		cbcIdKh.setEditable(false);
		cbcPhoneKh.setEditable(true);
		
		cbc.setDisable(true);
		cbcIdKh.setDisable(true);
		cbcPhoneKh.setDisable(false);
	}

	public void btnXoaOrder(ActionEvent e) throws IOException{

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
					List<OrderDetail> listOrderDetails = orderDetailService.findAllOrderDetailByOrderId(
							tbl_view.getItems().get(result).getOrderId());
					if(listOrderDetails!=null) {
						listOrderDetails.forEach(t->{
							orderDetailService.removeOrderDetail(t.getOrderDetailsId());
						});
					}
					OrderService.removeOrder(tbl_view.getItems().get(result).getOrderId());

					handleRefersh(e);

				}else {

				}
			});

		}else {

			Error("bạn chưa chọn bảng cần xóa", btnRefresh);

		}

	}


	public void initTable() {
		tbl_view=new TableView<Order>();

		colOrderId=new TableColumn<Order, String>("mã");
		colOrderDate=new TableColumn<Order, String>("Ngày đặt");

		colCustomerId=new TableColumn<Order, String>("Mã khách hàng");

		colCustomerName=new TableColumn<Order, String>("Tên khách hàng");
		colCustomerPhone=new TableColumn<Order, String>("sdt");
		colCustomrAddress=new TableColumn<Order, String>("Địa chỉ");

		tbl_view.getColumns().addAll(colOrderId,colOrderDate,colCustomerId,colCustomerName,
				colCustomerPhone,colCustomrAddress);

		bd.setCenter(tbl_view);

		colOrderId.setCellValueFactory(new PropertyValueFactory<>("OrderId"));
		colOrderDate.setCellValueFactory(new PropertyValueFactory<>("orderDate"));

		colCustomerId.setCellValueFactory(cellData->new SimpleStringProperty(cellData.getValue().getOrderId()));
		colCustomerPhone.setCellValueFactory(cellData->new SimpleStringProperty(cellData.getValue().getCustomer().getPhone()));
		colCustomerName.setCellValueFactory(cellData->new SimpleStringProperty(cellData.getValue().getCustomer().getName()));
		colCustomrAddress.setCellValueFactory(cellData->new SimpleStringProperty(cellData.getValue().getCustomer().getAddress()));

		colOrderDate.setMinWidth(120);
		
		colCustomerId.setMinWidth(120);
		
		colCustomerName.setMinWidth(150);
		
		colCustomerPhone.setMinWidth(120);
		
		colCustomrAddress.setMinWidth(200);
		
		uploadDuLieuLenBang();
	}

	private void uploadDuLieuLenBang() {
		List<Order> cuss=OrderService.listOrder();
		cuss.forEach(t->{
			tbl_view.getItems().add(t);
			listOrder.add(t);
		});
	}

	public void handleRefersh(ActionEvent e) {
		rdIdPhone.setSelected(true);
		cbc.setValue("");
		cbcIdKh.setValue("");
		cbcPhoneKh.setValue("");
		tbl_view.getItems().clear();
		uploadDuLieuLenBang();
	}

	public void btnClickAdd(ActionEvent e) throws IOException {
		FXMLLoader loader= new FXMLLoader(getClass().getResource(loadFormAddOrder));

		Parent root=loader.load();

		FormAddOrder ctlMain=loader.getController();

		String id=null;

		do {

			id="C"+ranDomNumber();

			ctlMain.txtOrderMa.setText(id);

		} while (OrderService.findOrderById(id)!=null);

		loadFXML(root,btnRefresh).setOnHidden(ev->{

			handleRefersh(e);

		});;
	}

	public void findItemInTable(ActionEvent e) throws IOException {
		String textFind=null;

		if(rdIdOrder.isSelected()) {
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

		}else if(rdIdKh.isSelected()) {
			try {

				textFind=cbcIdKh.getSelectionModel().getSelectedItem().toString().trim();

			} catch (Exception e2) {

				Error("Bạn chưa nhập tìm kiếm", btnRefresh);

				cbcIdKh.requestFocus();

			}

			if(textFind.isEmpty()) {

				Error("Bạn chưa nhập tìm kiếm", btnRefresh);

				cbcIdKh.requestFocus();

				return;

			}
			
			
		}else if(rdIdPhone.isSelected()){
			
			try {

				textFind=cbcPhoneKh.getSelectionModel().getSelectedItem().toString().trim();

			} catch (Exception e2) {

				Error("Bạn chưa nhập tìm kiếm", btnRefresh);

				cbcPhoneKh.requestFocus();

			}

			if(textFind.isEmpty()) {

				Error("Bạn chưa nhập tìm kiếm", btnRefresh);

				cbcPhoneKh.requestFocus();

				return;

			}
			
		}
		
		tbl_view.getItems().clear();

		if(rdIdOrder.isSelected()) {
			
			Order OrderFind=OrderService.findOrderById(textFind);

			if(OrderFind==null) {

				Error("Không tìm thấy", btnRefresh);

				cbc.requestFocus();

				return;

			}else {

				tbl_view.getItems().add(OrderFind);

			}
		}else if(rdIdKh.isSelected()) {
			
			List<Order> listOrders = OrderService.findAllOrderByIdCustomer(textFind);

			if(listOrders==null) {

				Error("Không tìm thấy", btnRefresh);

				cbcIdKh.requestFocus();

				return;

			}else {
				listOrders.forEach(t->{
					tbl_view.getItems().add(t);
				});
			}
		}else if(rdIdPhone.isSelected()) {
			List<Order> listOrders = OrderService.findAllOrderByPhoneCustomer(textFind);

			if(listOrders==null) {

				Error("Không tìm thấy", btnRefresh);

				cbcPhoneKh.requestFocus();

				return;

			}else {
				listOrders.forEach(t->{
					tbl_view.getItems().add(t);
				});
			}
			
			
		}
	

	}



	private void loadDataSearch() {
		ObservableList<String> items = FXCollections.observableArrayList();
		List<Order> accs=OrderService.listOrder();

		accs.forEach(t->{

			items.add(t.getOrderId());

		});

		FilteredList<String> filteredItems = new FilteredList<String>(items);

		cbc.getEditor().textProperty().addListener(new InputFilter(cbc, filteredItems, false));

		cbc.setItems(filteredItems);

		cbc.setEditable(true);
	}
	
	private void loadDataSearchKh() {
		ObservableList<String> itemsId = FXCollections.observableArrayList();
		ObservableList<String> itemsPhone = FXCollections.observableArrayList();
		List<Customer> accs=customerService.listCustomer();

		accs.forEach(t->{

			itemsId.add(t.getCustomerId());
			itemsPhone.add(t.getPhone());

		});

		FilteredList<String> filteredItems = new FilteredList<String>(itemsId);

		cbcIdKh.getEditor().textProperty().addListener(new InputFilter(cbcIdKh, filteredItems, false));

		cbcIdKh.setItems(filteredItems);

		cbcIdKh.setEditable(true);
		
		FilteredList<String> filteredItemsPhone = new FilteredList<String>(itemsPhone);

		cbcPhoneKh.getEditor().textProperty().addListener(new InputFilter(cbcPhoneKh, filteredItemsPhone, false));

		cbcPhoneKh.setItems(filteredItemsPhone);

		cbcPhoneKh.setEditable(true);
		
	}
	
	
}
