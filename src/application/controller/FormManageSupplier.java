package application.controller;

import java.io.IOException;
import java.net.URL;
import java.text.spi.CollatorProvider;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import application.controller.impl.SupplierImpl;
import application.controller.impl.SupplierImpl;
import application.controller.services.SupplierService;
import application.controller.services.SupplierService;
import application.entities.Supplier;
import application.entities.Supplier;
import application.entities.Supplier;
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

public class FormManageSupplier extends DialogBox implements Initializable{
	private TableView<Supplier> tbl_view;

	TableColumn<Supplier, String> colSupplierId;
	TableColumn<Supplier, String> colPhone;
	TableColumn<Supplier, String> colAddress;
	TableColumn<Supplier, String> colCompanyName;

	@FXML BorderPane bd;

	public SupplierService SupplierService=new SupplierImpl();

	@FXML ComboBox<String> cbc=new ComboBox<String>();

	@FXML JFXButton btnRefresh;

	List<Supplier> listSupplier=new ArrayList<>();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initTable();

		loadDataSearch();

		cbc.setEditable(true);

		tbl_view.setOnMouseClicked(e->{
			if(e.getClickCount()==2) {
				int result=tbl_view.getSelectionModel().getSelectedIndex();
				if(result!=-1) {

					FXMLLoader loader= new FXMLLoader(getClass().getResource(loadFormAddSupplier));

					Parent root=null;
					try {
						root = loader.load();
					} catch (IOException e1) {
						e1.printStackTrace();
					}

					FormAddSupplier ctlMain=loader.getController();

					ctlMain.lblTitle.setText("Cập nhập nhà cung cấp");

					ctlMain.txtMa.setText(tbl_view.getItems().get(result).getSupplierId());

					ctlMain.txtPhone.setText(tbl_view.getItems().get(result).getPhone());

					ctlMain.txtAddress.setText(String.valueOf(tbl_view.getItems().get(result).getAddress()));

					ctlMain.txtNameCompany.setText(tbl_view.getItems().get(result).getCompanyName());

					loadFXML(root,btnRefresh).setOnHidden(ev->{

						handleRefersh(new ActionEvent());

					});;
				}
			}
		});
	}


	public void btnXoaSupplier(ActionEvent e) throws IOException{

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
				
//					SupplierService.removeSupplier(tbl_view.getItems().get(result).getSupplierId());
					
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
		tbl_view=new TableView<Supplier>();

		colSupplierId=new TableColumn<Supplier, String>("Mã");
		colPhone=new TableColumn<Supplier, String>("Điện thoại");
		colAddress=new TableColumn<Supplier, String>("Địa chỉ");
		colCompanyName=new TableColumn<Supplier, String>("Tên công ty");

		tbl_view.getColumns().addAll(colSupplierId,colPhone,colAddress,colCompanyName);

		bd.setCenter(tbl_view);

		colSupplierId.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
		colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
		colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
		colCompanyName.setCellValueFactory(new PropertyValueFactory<>("companyName"));

		colSupplierId.setMinWidth(100);// .setCellValueFactory(new PropertyValueFactory<>("maKH"));
		colPhone.setMinWidth(180);//.setCellValueFactory(new PropertyValueFactory<>("diaChi"));
		colAddress.setMinWidth(120);//.setCellValueFactory(new PropertyValueFactory<>("CMND"));
		colCompanyName.setMinWidth(150);//.setCellValueFactory(new PropertyValueFactory<>("tenKH"));

		uploadDuLieuLenBang();
	}

	private void uploadDuLieuLenBang() {
		List<Supplier> cuss=SupplierService.listSupplier();
		cuss.forEach(t->{
			tbl_view.getItems().add(t);
			listSupplier.add(t);
		});
	}

	public void handleRefersh(ActionEvent e) {
		//		cbc.getItems().clear();
		cbc.setValue("");
		tbl_view.getItems().clear();
		uploadDuLieuLenBang();
	}

	public void btnClickAdd(ActionEvent e) throws IOException {
		FXMLLoader loader= new FXMLLoader(getClass().getResource(loadFormAddSupplier));

		Parent root=loader.load();

		FormAddSupplier ctlMain=loader.getController();

		String id=null;

		do {

			id="C"+ranDomNumber();

			ctlMain.txtMa.setText(id);

		} while (SupplierService.findSupplierById(id)!=null);

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

		Supplier SupplierFind=SupplierService.findSupplierById(textFind);

		if(SupplierFind==null) {

			Error("Không tìm thấy", btnRefresh);

			cbc.requestFocus();

			return;

		}else {

			tbl_view.getItems().add(SupplierFind);

		}

	}



	private void loadDataSearch() {
		ObservableList<String> items = FXCollections.observableArrayList();
		List<Supplier> accs=SupplierService.listSupplier();

		accs.forEach(t->{

			items.add(t.getSupplierId());

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
		//					Supplier listnv = null;
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
		//						Supplier nv=listnv;
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
