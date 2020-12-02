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
public class FormAddOrder extends DialogBox implements Initializable{

	@FXML public BorderPane mainBd;

	@FXML Label lblTitle;

	@FXML JFXTextField txtOrderMa;

	@FXML JFXDatePicker txtOrderDate;

	@FXML JFXRadioButton rdFindCustomerById;

	@FXML JFXRadioButton rdFindCustomerByPhone;

	@FXML ComboBox<String> cbcCustomerId=new ComboBox<String>();

	@FXML ComboBox<String> cbcCustomerPhone=new ComboBox<String>();

	@FXML ComboBox<String> cbcTitleId=new ComboBox<String>();

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

	@FXML BorderPane bdLeft;
	private TableView<Title> tbl_view;
	TableColumn<Title, String> colTitleId;
	TableColumn<Title, String> colName;
	TableColumn<Title, String> colStatus;

	@FXML BorderPane bdRight;
	private TableView<Title> tbl_viewOrderV;
	TableColumn<Title, String> colTitleIdV;
	TableColumn<Title, String> colNameV;
	TableColumn<Title, String> colStatusV;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		txtOrderDate.setValue(LocalDate.now());
		
		txtOrderDate.setDisable(true);
		
		txtOrderDate.setEditable(false);
		
		txtOrderMa.setEditable(false);

		cbcTitleId.setEditable(true);

		cbcCustomerId.setDisable(true);

		txtCustomerName.setEditable(false);

		txtCustomerAddress.setEditable(false);

		customerService.listCustomer().forEach(t->{
			listCustomer.add(t);
		});


		loadDataSearchCustomerId();

		loadDataSearchCustomerPhone();

		initTableInOrder();

		initTableInDb();

		loadDataTitle();

	}

	public void btnXoaProduct(ActionEvent e) throws IOException{

		int result=tbl_view.getSelectionModel().getSelectedIndex();

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

					for(int i=0;i<listTitle.size();i++) {
						
						if(listTitleWantOrder.get(i).getTitleId().equals(tbl_view.getItems().get(result).getTitleId())){

							listTitleWantOrder.remove(i);
							
							tbl_view.getItems().clear();
							
							tbl_viewOrderV.getItems().clear();
							
							uploadDuLieuOrderLenBang();
							
							List<Title> listTitleX = titlService.listTitle();
							if(listTitleX!=null) {
								listTitleX.forEach(t->{
									tbl_viewOrderV.getItems().add(t);
								});
							}

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
		tbl_viewOrderV.getItems().clear();
		tbl_viewOrderV.getSelectionModel().clearSelection();
		uploadDuLieuLenBang();
	}



	public void initTableInOrder() {
		tbl_view = new TableView<Title>();

		colTitleId = new TableColumn<Title, String>("Mã title");
		colName = new TableColumn<Title, String>("tên title");
		colStatus = new TableColumn<Title, String>("status");

		tbl_view.getColumns().addAll(colTitleId,colName,colStatus);

		bdLeft.setCenter(tbl_view);

		colTitleId.setCellValueFactory(cellData -> new SimpleStringProperty(
				cellData.getValue().getTitleId()));

		colName.setCellValueFactory(cellData -> new SimpleStringProperty(
				cellData.getValue().getName()));

		colStatus.setCellValueFactory(cellData -> new SimpleStringProperty(
				String.valueOf(cellData.getValue().getStatus())));
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

		uploadDuLieuLenBang();
	}

	private void uploadDuLieuLenBang() {
		List<Title> listTitleX = titlService.listTitle();
		if(listTitleX!=null) {
			listTitleX.forEach(t->{
				tbl_viewOrderV.getItems().add(t);
				listTitle.add(t);
			});
		}
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


	public void findItemInTable(ActionEvent e) throws IOException {

		String textFind=null;

		try {

			textFind=cbcTitleId.getSelectionModel().getSelectedItem().toString().trim();

		} catch (Exception e2) {

			Error("Bạn chưa nhập tìm kiếm", btn);

			cbcTitleId.requestFocus();

		}

		if(textFind.isEmpty()) {

			Error("Bạn chưa nhập tìm kiếm", btn);

			cbcTitleId.requestFocus();

			return;

		}

		tbl_view.getItems().clear();

		for(int i=0;i<listTitleWantOrder.size();i++) {
			if(listTitleWantOrder.get(i).getTitleId().equals(textFind)) {
				
				Error("Đã đặt trong danh sách", btn);
				
				return;
			}
		}
		
		List<Title> titleFind=titlService.findTitleByName(textFind);

		if(titleFind==null) {

			Error("Không tìm thấy", btn);

			cbcTitleId.requestFocus();

			return;
		}else {
			
			
			tbl_viewOrderV.getItems().clear();

			titleFind.forEach(t->{
				tbl_viewOrderV.getItems().add(t);
			});
			
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

	private void loadDataTitle() {
		ObservableList<String> items = FXCollections.observableArrayList();


		listTitle.forEach(t->{

			items.add(t.getName());

		});

		FilteredList<String> filteredItems = new FilteredList<String>(items);

		cbcTitleId.getEditor().textProperty().addListener(new InputFilter(cbcTitleId, filteredItems, false));

		cbcTitleId.setItems(filteredItems);

		cbcTitleId.setEditable(true);
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

		int result=tbl_viewOrderV.getSelectionModel().getSelectedIndex();

		if(result!=-1) {

			Title title = tbl_viewOrderV.getItems().get(result);

			if(listTitleWantOrder!=null && listTitleWantOrder.size()>=1) {
				for(int i=0;i<listTitleWantOrder.size();i++) {
					if(listTitleWantOrder.get(i).getTitleId().equalsIgnoreCase(title.getTitleId())) {

						Error("Title đã đặt", btn);

						return;
					}
				}
			}

			listTitleWantOrder.add(title);

			tbl_view.getItems().clear();

			listTitleWantOrder.forEach(t->{

				tbl_view.getItems().add(t);

			});

		}else {

			Error("bạn chưa chọn title muốn đặt", btn);

		}

	}

	public void xoaRong(ActionEvent e) {
		cbcTitleId.setValue(null);

		listTitleWantOrder.clear();

		txtOrderDate.setValue(null);

		cbcCustomerId.setValue("");

		cbcCustomerPhone.setValue("");

		txtCustomerName.setText("");

		txtCustomerAddress.setText("");

		rdFindCustomerByPhone.setSelected(true);

		cbcCustomerId.setEditable(false);

		uploadDuLieuOrderLenBang();

		listTitleWantOrder.clear();

		tbl_viewOrderV.getItems().clear();

	}

	public void uploadDuLieuOrderLenBang() {
		if(listTitleWantOrder!=null && listTitleWantOrder.size()>=1) {
			listTitleWantOrder.forEach(t->{
				tbl_view.getItems().add(t);
			});
		}
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
		String orderId=txtOrderMa.getText().toString();
		LocalDate orderDateOrder=txtOrderDate.getValue();
		String txtCustomerId=cbcCustomerId.getSelectionModel().getSelectedItem().toString();

		if(orderDateOrder==null) {
			Error("Bạn chưa nhập ngày", btn);

			txtOrderDate.requestFocus();

			return;
		}

		if(checkCustomer(e,txtCustomerId)==false) {
			return;
		};

		if(listTitleWantOrder.size()<=0) {

			Error("Bạn chưa chọn title", btn);

			return;

		}

		Customer customer=customerService.findCustomerById(txtCustomerId);

		Order order = new Order(orderId, orderDateOrder, customer);

		if(lblTitle.getText().equals("Cập nhập order")==false) {

			if(orderService.addOrder(order)==true) {

				listTitleWantOrder.forEach(t->{
					OrderDetail orderDetail = new 
							OrderDetail(checkIdBIllDetail(),0, 0, order, t);
					
					orderDetailService.addOrderDetail(orderDetail);

				});
				
				listTitleWantOrder.forEach(t->{
					t.setStatus(DAT);
					
					titlService.updateTitle(t, t.getTitleId());
					
				});

				((Node)(e.getSource())).getScene().getWindow().hide();  

			}else{

				Error("Lỗi thêm không thành công", btn);

			};

		}else {

			if(orderService.updateOrder(order,order.getOrderId())==null) {

				Error("Lỗi cập nhập không thành công", btn);

			}else{

				((Node)(e.getSource())).getScene().getWindow().hide();  

			};


		}
	}

	public String checkIdBIllDetail() {
		String id=null;

				do {
		
					id="OD"+ranDomNumber();
		
				} while (orderService.findOrderById(id)!=null);

		return id;
	}

	public void btnCLoseWindow(ActionEvent e) throws IOException {

		((Node)(e.getSource())).getScene().getWindow().hide();  

	}

}
