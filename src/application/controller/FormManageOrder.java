package application.controller;

import java.io.IOException;
import java.net.URL;
import java.text.spi.CollatorProvider;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import application.controller.impl.OrderImpl;
import application.controller.impl.OrderImpl;
import application.controller.services.OrderService;
import application.controller.services.OrderService;
import application.entities.Order;
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

	@FXML ComboBox<String> cbc=new ComboBox<String>();

	@FXML JFXButton btnRefresh;

	List<Order> listOrder=new ArrayList<>();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initTable();

		loadDataSearch();

		cbc.setEditable(true);

//		tbl_view.setOnMouseClicked(e->{
//			if(e.getClickCount()==2) {
//				int result=tbl_view.getSelectionModel().getSelectedIndex();
//				if(result!=-1) {
//
//					FXMLLoader loader= new FXMLLoader(getClass().getResource(loadFormAddOrder));
//
//					Parent root=null;
//					try {
//						root = loader.load();
//					} catch (IOException e1) {
//						e1.printStackTrace();
//					}
//
//					FormAddOrder ctlMain=loader.getController();
//
//					ctlMain.lblTitle.setText("Cập nhập mặt hàng");
//
//					ctlMain.txtMa.setText(tbl_view.getItems().get(result).getOrderId());
//
//					ctlMain.txtName.setText(tbl_view.getItems().get(result).getName());
//
//					ctlMain.txtPrice.setText(String.valueOf(tbl_view.getItems().get(result).getPrice()));
//
//					ctlMain.txtDescription.setText(tbl_view.getItems().get(result).getDescription());
//
//					loadFXML(root,btnRefresh).setOnHidden(ev->{
//
//						handleRefersh(new ActionEvent());
//
//					});;
//				}
//			}
//		});
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
				
//					OrderService.removeOrder(tbl_view.getItems().get(result).getOrderId());
					
					try {
						Success("Chưa code :v", btnRefresh);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
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
		//		cbc.getItems().clear();
		cbc.setValue("");
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

		Order OrderFind=OrderService.findOrderById(textFind);

		if(OrderFind==null) {

			Error("Không tìm thấy", btnRefresh);

			cbc.requestFocus();

			return;

		}else {

			tbl_view.getItems().add(OrderFind);

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

		//		try {
		//			cbc.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() { 
		//				public void changed(ObservableValue ov, Number value, Number new_value) 
		//				{ 
		//					System.out.println("ok");
		//					Order listnv = null;
		//					System.out.println(cbc.getSelectionModel().getSelectedItem().toString());
		//					try {
		//						for(int i=0;i<accs.size();i++) {
		//							
		//							if(accs.get(i).equals(cbc.getSelectionModel().getSelectedItem().toString())) {
		//								listnv=accs.get(i);
		// 							}
		//							
		//						}
		//					} catch (Exception e) {
		//						// TODO Auto-generated catch block
		//						e.printStackTrace();
		//					}
		//					if(listnv!=null) {
		//						Order nv=listnv;
		//						if(nv!=null) {
		//								tbl_view.getItems().clear();
		//								
		//						}else {
		//							System.out.println("error");
		//						}
		//
		//					}else {
		//						System.out.println("error");
		//					}
		//
		//				}
		//			});
		//		} catch (Exception ev) {
		//			// TODO: handle exception
		//			System.out.println(ev.getMessage());
		//		}

	}
}
