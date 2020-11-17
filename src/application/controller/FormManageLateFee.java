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
import application.controller.impl.CustomerImpl;
import application.controller.impl.LateFeeImpl;
import application.controller.impl.BillImpl;
import application.controller.services.BillService;
import application.controller.services.CustomerService;
import application.controller.services.LateFeeService;
import application.controller.services.BillService;
import application.entities.Bill;
import application.entities.Customer;
import application.entities.LateFee;
import application.entities.Bill;
import application.entities.Bill;
import javafx.beans.binding.Bindings;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class FormManageLateFee extends DialogBox implements Initializable{
	private TableView<LateFee> tbl_view;

	TableColumn<LateFee, String> colLateFeeId;
	TableColumn<LateFee, String> colPrice;
	TableColumn<LateFee, String> colDatePay;
	TableColumn<LateFee, String> colCustomerId;
	TableColumn<LateFee, String> colBillId;
	TableColumn<LateFee, String> colContent;
	TableColumn<LateFee, String> colNameCustomer;
	TableColumn<LateFee, String> colPhoneCustomer;


	@FXML BorderPane bd;

	public LateFeeService lateFeeService=new LateFeeImpl();
	public CustomerService customerService=new CustomerImpl();

	@FXML ComboBox<String> cbc=new ComboBox<String>();
	@FXML ComboBox<String> cbcIdKh=new ComboBox<String>();
	@FXML ComboBox<String> cbcPhoneKh=new ComboBox<String>();

	@FXML RadioButton rdOne;
	@FXML RadioButton rdTwo;
	@FXML RadioButton rdThree;


	@FXML JFXButton btnRefresh;

	List<LateFee> listFee=new ArrayList<>();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initTable();

		loadDataSearch();

		cbc.setEditable(false);
		cbcIdKh.setEditable(false);
		cbcPhoneKh.setEditable(true);

		cbcIdKh.setDisable(true);
		cbc.setDisable(true);

		tbl_view.setOnMouseClicked(e->{
			if(e.getClickCount()==2) {
				int result=tbl_view.getSelectionModel().getSelectedIndex();
				if(result!=-1) {

					FXMLLoader loader= new FXMLLoader(getClass().getResource(loadFormPay));

					Parent root=null;
					try {
						root = loader.load();
					} catch (IOException e1) {
						e1.printStackTrace();
					}

					FormPay ctlMain=loader.getController();
						
					ctlMain.txtLateFee.setText(tbl_view.getItems().get(result).getLateFeetId());
					
					ctlMain.txtBillMa.setText(tbl_view.getItems().get(result).getBill().getBillId());
					
					ctlMain.txtBillDateOrder.setValue(tbl_view.getItems().get(result).getBill().getLocalDate());
					
					ctlMain.txtBillDatePay.setValue(tbl_view.getItems().get(result).getDatePay());
					
					ctlMain.rdBillDebtYes.setSelected(true);
					
					ctlMain.cbcCustomerId.setValue(tbl_view.getItems().get(result).getBill().getCustomer().getCustomerId());
					
					ctlMain.cbcCustomerPhone.setValue(tbl_view.getItems().get(result).getBill().getCustomer().getPhone());
					
					ctlMain.txtCustomerName.setText(tbl_view.getItems().get(result).getBill().getCustomer().getName());
					
					ctlMain.txtCustomerAddress.setText(tbl_view.getItems().get(result).getBill().getCustomer().getAddress());
					
					ctlMain.bill = tbl_view.getItems().get(result).getBill();
					
					ctlMain.lateFee = tbl_view.getItems().get(result);
					
					ctlMain.total = Math.round(tbl_view.getItems().get(result).getPrice());
					
					ctlMain.uploadDuLieuLenBang(tbl_view.getItems().get(result).getBill().getBillId());
					
					loadFXML(root,btnRefresh).setOnHidden(ev->{
						handleRefersh(new ActionEvent());
						if(ctlMain.total == 0) {
							try {
								Success("Thanh toán thành công", btnRefresh);
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							
							return;
						}
					});;
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
					try {
						
						lateFeeService.removeLateFee(tbl_view.getItems().get(result).getLateFeetId());
						
						Success("Xóa thành công :v", btnRefresh);
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
		tbl_view=new TableView<LateFee>();

		colLateFeeId=new TableColumn<LateFee, String>("Mã");
		colPrice=new TableColumn<LateFee, String>("Phí");
		colDatePay=new TableColumn<LateFee, String>("Ngày phải thanh toán");
		colBillId=new TableColumn<LateFee, String>("Mã bill");
		colContent=new TableColumn<LateFee, String>("Nội dung");
		colNameCustomer=new TableColumn<LateFee, String>("Tên Kh");
		colPhoneCustomer=new TableColumn<LateFee, String>("Phone kh");


		tbl_view.getColumns().addAll(colLateFeeId, 
				colPrice,
				colDatePay,
				colBillId,
				colContent,
				colNameCustomer,
				colPhoneCustomer);

		bd.setCenter(tbl_view);

		colLateFeeId.setCellValueFactory(new PropertyValueFactory<>("lateFeetId"));
		colPrice.setCellValueFactory(cellData-> new SimpleStringProperty(
				String.valueOf(cellData.getValue().getPrice())));
		colDatePay.setCellValueFactory(new PropertyValueFactory<>("datePay"));
		colContent.setCellValueFactory(new PropertyValueFactory<>("content"));

		colBillId.setCellValueFactory(cellData-> new SimpleStringProperty(
				String.valueOf(cellData.getValue().getBill().getBillId())));

		colNameCustomer.setCellValueFactory(cellData-> new SimpleStringProperty(
				String.valueOf(cellData.getValue().getBill().getCustomer().getName())));

		colPhoneCustomer.setCellValueFactory(cellData-> new SimpleStringProperty(
				String.valueOf(cellData.getValue().getBill().getCustomer().getPhone())));


		colDatePay.setMinWidth(200);
		
		colContent.setMinWidth(200);
		
		colNameCustomer.setMinWidth(150);
		
		colPhoneCustomer.setMinWidth(120);
		
		uploadDuLieuLenBang();
	}

	private void uploadDuLieuLenBang() {
		List<LateFee> cuss=lateFeeService.listLateFee();
		cuss.forEach(t->{
			tbl_view.getItems().add(t);
			listFee.add(t);
		});
	}

	public void handleRefersh(ActionEvent e) {
		rdTwo.setSelected(true);
		cbcPhoneKh.setEditable(true);
		cbc.setEditable(false);
		cbcIdKh.setEditable(false);
		cbcIdKh.setValue("");
		cbcPhoneKh.setValue("");
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
		//			ctlMain.txtBillMa.setText(id);
		//			
		//			ctlMain.txtBillDateOrder.setText(String.valueOf(LocalDate.now()));
		//
		//		} while (BillService.findBillById(id)!=null);
		//
		//		loadFXML(root,btnRefresh).setOnHidden(ev->{
		//
		//			handleRefersh(e);
		//
		//		});;
	}

	public void findItemPhoneCustomerInTable(ActionEvent e) throws IOException {
		String textFind=null;

		if(rdOne.isSelected()) {
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
		}else if(rdTwo.isSelected()) {


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


		}else {

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

		}


		tbl_view.getItems().clear();

		if(rdOne.isSelected()) {
			List<LateFee> listLateFeeSpecific = new ArrayList<>();

			for(int i=0;i<listFee.size();i++) {
				if(listFee.get(i).getBill().getCustomer().getPhone().equalsIgnoreCase(textFind)) {
					listLateFeeSpecific.add(listFee.get(i));
				}
			}

			if(listLateFeeSpecific.size() == 0) {

				Error("Không tìm thấy", btnRefresh);

				cbc.requestFocus();

				return;

			}else {

				tbl_view.getItems().clear();

				listLateFeeSpecific.forEach(t->{

					tbl_view.getItems().add(t);

				});



			}

		}else if(rdTwo.isSelected()) {

			List<LateFee> listLateFeeFind = lateFeeService.findAllLteFeeByPhoneCustomer(textFind);

			if(listLateFeeFind == null) {

				Error("Không tìm thấy", btnRefresh);

				cbcPhoneKh.requestFocus();

				return;

			}else {
				tbl_view.getItems().clear();

				listLateFeeFind.forEach(t->{

					tbl_view.getItems().add(t);

				});
			}




		}else {
			List<LateFee> listLateFeeFind = lateFeeService.findAllLteFeeByIdCustomer(textFind);

			if(listLateFeeFind == null) {

				Error("Không tìm thấy", btnRefresh);

				cbcIdKh.requestFocus();

				return;

			}else {
				tbl_view.getItems().clear();

				listLateFeeFind.forEach(t->{

					tbl_view.getItems().add(t);

				});
			}


		}


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
		cbcIdKh.setEditable(false);
		cbcPhoneKh.setEditable(true);

		cbc.setDisable(true);
		cbcIdKh.setDisable(true);
		cbcPhoneKh.setDisable(false);
	}

	public void clickRdThree(ActionEvent e) {

		cbc.setEditable(false);
		cbcIdKh.setEditable(true);
		cbcPhoneKh.setEditable(false);

		cbc.setDisable(true);
		cbcIdKh.setDisable(false);
		cbcPhoneKh.setDisable(true);
	}


	private void loadDataSearch() {
		ObservableList<String> items = FXCollections.observableArrayList();

		List<LateFee> accs=lateFeeService.listLateFee();

		accs.forEach(t->{

			if(items.contains(t.getBill().getCustomer().getPhone())==false) {
				items.add(t.getBill().getCustomer().getPhone());
			}

		});

		FilteredList<String> filteredItems = new FilteredList<String>(items);

		cbc.getEditor().textProperty().addListener(new InputFilter(cbc, filteredItems, false));

		cbc.setItems(filteredItems);

		cbc.setEditable(true);

		ObservableList<String> itemsPhoneKH = FXCollections.observableArrayList();
		ObservableList<String> itemsIdKH = FXCollections.observableArrayList();
		List<Customer> listCusommer=customerService.listCustomer();

		listCusommer.forEach(t->{

			itemsPhoneKH.add(t.getPhone());
			itemsIdKH.add(t.getCustomerId());


		});

		FilteredList<String> filteredItemsPhoneKH = new FilteredList<String>(itemsPhoneKH);

		cbcPhoneKh.getEditor().textProperty().addListener(new InputFilter(cbcPhoneKh, filteredItemsPhoneKH, false));

		cbcPhoneKh.setItems(filteredItemsPhoneKH);

		cbcPhoneKh.setEditable(true);

		FilteredList<String> filteredItemsIdKH = new FilteredList<String>(itemsIdKH);

		cbcIdKh.getEditor().textProperty().addListener(new InputFilter(cbcIdKh, filteredItemsIdKH, false));

		cbcIdKh.setItems(filteredItemsIdKH);

		cbcIdKh.setEditable(true);



	}
}
