package application.controller;

import java.io.IOException;
import java.net.URL;
import java.text.spi.CollatorProvider;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import application.controller.impl.BillImpl;
import application.controller.impl.BillImpl;
import application.controller.services.BillService;
import application.controller.services.BillService;
import application.entities.Bill;
import application.entities.Bill;
import application.entities.Bill;
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

public class FormManageBill extends DialogBox implements Initializable{
	private TableView<Bill> tbl_view;

	TableColumn<Bill, String> colBillId;
	TableColumn<Bill, String> colLocalDate;
	TableColumn<Bill, String> colBillPay;
	TableColumn<Bill, String> colCustomerId;
	TableColumn<Bill, String> colNameCustomer;
	TableColumn<Bill,Boolean> colDebt;

	@FXML BorderPane bd;

	public BillService BillService=new BillImpl();

	@FXML ComboBox<String> cbc=new ComboBox<String>();

	@FXML JFXButton btnRefresh;

	List<Bill> listBill=new ArrayList<>();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initTable();

		loadDataSearch();

		cbc.setEditable(true);

		tbl_view.setOnMouseClicked(e->{
			if(e.getClickCount()==2) {
				int result=tbl_view.getSelectionModel().getSelectedIndex();
				if(result!=-1) {
//
//					FXMLLoader loader= new FXMLLoader(getClass().getResource(loadFormAddBill));
//
//					Parent root=null;
//					try {
//						root = loader.load();
//					} catch (IOException e1) {
//						e1.printStackTrace();
//					}
//
//					FormAddBill ctlMain=loader.getController();
//
//					ctlMain.lblTitle.setText("Cập nhập mặt hàng");
//
//					ctlMain.txtMa.setText(tbl_view.getItems().get(result).getBillId());
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

//					});;
				}
			}
		});
	}


	public void btnXoaBill(ActionEvent e) throws IOException{

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
				
//					BillService.removeBill(tbl_view.getItems().get(result).getBillId());
					
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
		tbl_view=new TableView<Bill>();

		colBillId=new TableColumn<Bill, String>("mã");
		colLocalDate=new TableColumn<Bill, String>("ngày đặt");
		colBillPay=new TableColumn<Bill, String>("Phí thanh toán");
		colCustomerId=new TableColumn<Bill, String>("mã customerId");
		colNameCustomer=new TableColumn<Bill, String>("tên khách hàng");
		colDebt=new TableColumn<Bill, Boolean>("Tình trạng");
		

		tbl_view.getColumns().addAll(colBillId,colLocalDate,colBillPay,colCustomerId,colNameCustomer,colDebt);

		bd.setCenter(tbl_view);

		colBillId.setCellValueFactory(new PropertyValueFactory<>("billId"));
		colLocalDate.setCellValueFactory(new PropertyValueFactory<>("localDate"));
		colBillPay.setCellValueFactory(new PropertyValueFactory<>("billPay"));
		colCustomerId.setCellValueFactory(cellData->new SimpleStringProperty(cellData.getValue().getCustomer().getCustomerId()));
		colNameCustomer.setCellValueFactory(cellData->new SimpleStringProperty(cellData.getValue().getCustomer().getName()));
		colDebt.setCellValueFactory(new PropertyValueFactory<>("BillId"));
		

		uploadDuLieuLenBang();
	}

	private void uploadDuLieuLenBang() {
		List<Bill> cuss=BillService.listBill();
		cuss.forEach(t->{
			tbl_view.getItems().add(t);
			listBill.add(t);
		});
	}

	public void handleRefersh(ActionEvent e) {
		//		cbc.getItems().clear();
		cbc.setValue("");
		tbl_view.getItems().clear();
		uploadDuLieuLenBang();
	}

	public void btnClickAdd(ActionEvent e) throws IOException {
//		FXMLLoader loader= new FXMLLoader(getClass().getResource(loadFormAddBill));
//
//		Parent root=loader.load();
//
//		FormAddBill ctlMain=loader.getController();
//
//		String id=null;
//
//		do {
//
//			id="C"+ranDomNumber();
//
//			ctlMain.txtMa.setText(id);
//
//		} while (BillService.findBillById(id)!=null);
//
//		loadFXML(root,btnRefresh).setOnHidden(ev->{
//
//			handleRefersh(e);
//
//		});;


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

		Bill BillFind=BillService.findBillById(textFind);

		if(BillFind==null) {

			Error("Không tìm thấy", btnRefresh);

			cbc.requestFocus();

			return;

		}else {

			tbl_view.getItems().add(BillFind);

		}

	}



	private void loadDataSearch() {
		ObservableList<String> items = FXCollections.observableArrayList();
		List<Bill> accs=BillService.listBill();

		accs.forEach(t->{

			items.add(t.getBillId());

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
		//					Bill listnv = null;
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
		//						Bill nv=listnv;
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
