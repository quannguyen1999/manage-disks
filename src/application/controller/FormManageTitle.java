package application.controller;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.spi.CollatorProvider;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import application.controller.impl.OrderDetailImpl;
import application.controller.impl.TitleImpl;
import application.controller.impl.TitleImpl;
import application.controller.services.OrderDetailService;
import application.controller.services.TitleService;
import application.controller.services.TitleService;
import application.entities.Category;
import application.entities.OrderDetail;
import application.entities.Title;
import application.entities.Title;
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

public class FormManageTitle extends DialogBox implements Initializable{
	private TableView<Title> tbl_view;

	TableColumn<Title, String> colTitleId;
	TableColumn<Title, String> colName;
	TableColumn<Title, String> colStatus;
	TableColumn<Title, String> colcategoryId;
	TableColumn<Title, String> colcategoryName;

	DecimalFormat df = new DecimalFormat("#,###"); 

	@FXML BorderPane bd;

	TitleService titleService=new TitleImpl();

	OrderDetailService orderDetailService = new OrderDetailImpl();

	@FXML ComboBox<String> cbc=new ComboBox<String>();

	@FXML ComboBox<String> cbcNameTitle=new ComboBox<String>();

	@FXML JFXButton btnRefresh;

	List<Title> listTitle=new ArrayList<>();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initTable();

		loadDataSearch();

		cbc.setEditable(true);

		cbcNameTitle.setEditable(true);

		tbl_view.setOnMouseClicked(e->{
			if(e.getClickCount()==2) {
				int result=tbl_view.getSelectionModel().getSelectedIndex();
				if(result!=-1) {

					FXMLLoader loader= new FXMLLoader(getClass().getResource(loadFormAddTitle));

					Parent root=null;
					try {
						root = loader.load();
					} catch (IOException e1) {
						e1.printStackTrace();
					}

					FormAddTitle ctlMain=loader.getController();

					ctlMain.lblTitle.setText("Cập nhập title");

//					ctlMain.txtTimeRent.setText(String.valueOf(tbl_view.getItems().get(result).getCategory().getPrice()));
					
					ctlMain.txtMa.setText(tbl_view.getItems().get(result).getTitleId());

					ctlMain.cbc.setValue(tbl_view.getItems().get(result).getCategory().getName());

					ctlMain.maCategoryRemember=tbl_view.getItems().get(result).getCategory().getCategoryId();

					ctlMain.txtNameTitle.setText(tbl_view.getItems().get(result).getName());

					String status=tbl_view.getItems().get(result).getStatus();

//					ctlMain.txtNameCategory.setText(tbl_view.getItems().get(result).getCategory().getName());
//
//					ctlMain.txtDescriptionCategory.setText(tbl_view.getItems().get(result).getCategory().getDescription());
//
//					ctlMain.txtPriceCategory.setText(String.valueOf(tbl_view.getItems().get(result).getCategory().getPrice()));

					ctlMain.titleOld=tbl_view.getItems().get(result);

					if(status.equalsIgnoreCase(DAT)) {
						ctlMain.rdTrue.setSelected(true);
					}else {
						ctlMain.rdFalse.setSelected(true);
					}

					loadFXML(root,btnRefresh).setOnHidden(ev->{

						handleRefersh(new ActionEvent());

					});;
				}
			}
		});
	}


	public void btnXoaTitle(ActionEvent e) throws IOException{

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

					//					TitleService.removeTitle(tbl_view.getItems().get(result).getTitleId());

					List<OrderDetail> listOrderDetail = 
							orderDetailService.findAllOrderDetailByTitleId(tbl_view.getItems().get(result).getTitleId());

					if(listOrderDetail!=null && listOrderDetail.size()>=1) {
						try {
							Error("Đang có khách hàng đặt title", btnRefresh);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

						return;
					}

					titleService.removeTitle(tbl_view.getItems().get(result).getTitleId());

					handleRefersh(e);
					
					loadDataSearch();

				}else {

				}
			});

		}else {

			Error("bạn chưa chọn bảng cần xóa", btnRefresh);

		}

	}


	public void initTable() {
		tbl_view=new TableView<Title>();

		colTitleId=new TableColumn<Title, String>("mã");
		colName=new TableColumn<Title, String>("Tên");
		colStatus=new TableColumn<Title, String>("status");
		colcategoryId=new TableColumn<Title, String>("mã mặt hàng");
		colcategoryName=new TableColumn<Title, String>("tên mặt hàng");

		tbl_view.getColumns().addAll(colTitleId,colName,colStatus,colcategoryId,colcategoryName);

		bd.setCenter(tbl_view);

		colTitleId.setCellValueFactory(new PropertyValueFactory<>("titleId"));
		colName.setCellValueFactory(new PropertyValueFactory<>("name"));
		colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
		colcategoryId.setCellValueFactory(cellData->new SimpleStringProperty(cellData.getValue().getCategory().getCategoryId()));
		colcategoryName.setCellValueFactory(cellData->new SimpleStringProperty(cellData.getValue().getCategory().getName()));

		colTitleId.setMinWidth(100);// .setCellValueFactory(new PropertyValueFactory<>("maKH"));
		colName.setMinWidth(180);//.setCellValueFactory(new PropertyValueFactory<>("diaChi"));
		colStatus.setMinWidth(120);//.setCellValueFactory(new PropertyValueFactory<>("CMND"));
		colcategoryId.setMinWidth(150);//.setCellValueFactory(new PropertyValueFactory<>("tenKH"));
		colcategoryName.setMinWidth(150);//.setCellValueFactory(new PropertyValueFactory<>("tenKH"));

		uploadDuLieuLenBang();
	}

	private void uploadDuLieuLenBang() {
		List<Title> cuss=titleService.listTitle();
		cuss.forEach(t->{
			tbl_view.getItems().add(t);
			listTitle.add(t);
		});
	}

	public void handleRefersh(ActionEvent e) {
		//		cbc.getItems().clear();
		cbc.setValue("");
		cbcNameTitle.setValue("");
		tbl_view.getItems().clear();
		uploadDuLieuLenBang();
	}

	public void btnClickAdd(ActionEvent e) throws IOException {
		FXMLLoader loader= new FXMLLoader(getClass().getResource(loadFormAddTitle));

		Parent root=loader.load();

		FormAddTitle ctlMain=loader.getController();

		String id=null;

		do {

			id="C"+ranDomNumber();

			ctlMain.txtMa.setText(id);


		} while (titleService.findTitleById(id)!=null);

		loadFXML(root,btnRefresh).setOnHidden(ev->{

			handleRefersh(e);

			loadDataSearch();
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

		Title TitleFind=titleService.findTitleById(textFind);

		if(TitleFind==null) {

			Error("Không tìm thấy", btnRefresh);

			cbc.requestFocus();

			return;

		}else {

			tbl_view.getItems().add(TitleFind);

		}

	}

	public void findItemNameTitleInTable(ActionEvent e) throws IOException {
		String textFind=null;

		try {

			textFind=cbcNameTitle.getSelectionModel().getSelectedItem().toString().trim();

		} catch (Exception e2) {

			Error("Bạn chưa nhập tìm kiếm", btnRefresh);

			cbcNameTitle.requestFocus();

		}

		if(textFind.isEmpty()) {

			Error("Bạn chưa nhập tìm kiếm", btnRefresh);

			cbcNameTitle.requestFocus();

			return;

		}

		tbl_view.getItems().clear();

		List<Title> TitleFind=titleService.findTitleByName(textFind);

		if(TitleFind == null) {

			Error("Không tìm thấy", btnRefresh);

			cbcNameTitle.requestFocus();

			return;

		}else {

			tbl_view.getItems().clear();

			TitleFind.forEach(t->{

				tbl_view.getItems().add(t);

			});


		}

	}

	private void loadDataSearch() {
		ObservableList<String> items = FXCollections.observableArrayList();

		ObservableList<String> itemsNameTitle = FXCollections.observableArrayList();

		List<Title> accs=titleService.listTitle();

		accs.forEach(t->{
			items.add(t.getTitleId());

			itemsNameTitle.add(t.getName());
		});

		FilteredList<String> filteredItems = new FilteredList<String>(items);

		cbc.getEditor().textProperty().addListener(new InputFilter(cbc, filteredItems, false));

		cbc.setItems(filteredItems);

		cbc.setEditable(true);

		FilteredList<String> filteredNameItems = new FilteredList<String>(itemsNameTitle);

		cbcNameTitle.getEditor().textProperty().addListener(new InputFilter(cbcNameTitle, filteredNameItems, false));

		cbcNameTitle.setItems(filteredNameItems);

		cbcNameTitle.setEditable(true);
	}
}
