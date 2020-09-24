package application.controller;

import java.io.IOException;
import java.net.URL;
import java.text.spi.CollatorProvider;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import application.controller.impl.ProductImpl;
import application.controller.impl.ProductImpl;
import application.controller.services.ProductService;
import application.controller.services.ProductService;
import application.entities.Product;
import application.entities.Product;
import application.entities.Product;
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

public class FormManageProduct extends DialogBox implements Initializable{
	private TableView<Product> tbl_view;

	TableColumn<Product, String> colProductId;
	TableColumn<Product, String> colName;
	TableColumn<Product, String> colPicture;
	TableColumn<Product, Integer> colQuantity;
	TableColumn<Product, String> colDescription;
	TableColumn<Product, String> colStatus;
	TableColumn<Product, String> colDateAdded;
	TableColumn<Product, String> colTitleId;
	TableColumn<Product, String> colSupplierId;

	@FXML BorderPane bd;

	public ProductService ProductService=new ProductImpl();

	@FXML ComboBox<String> cbc=new ComboBox<String>();

	@FXML JFXButton btnRefresh;

	List<Product> listProduct=new ArrayList<>();

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
//					FXMLLoader loader= new FXMLLoader(getClass().getResource(loadFormAddProduct));
//
//					Parent root=null;
//					try {
//						root = loader.load();
//					} catch (IOException e1) {
//						e1.printStackTrace();
//					}
//
//					FormAddProduct ctlMain=loader.getController();
//
//					ctlMain.lblTitle.setText("Cập nhập mặt hàng");
//
//					ctlMain.txtMa.setText(tbl_view.getItems().get(result).getProductId());
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
				}
			}
		});
	}


	public void btnXoaProduct(ActionEvent e) throws IOException{

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
				
//					ProductService.removeProduct(tbl_view.getItems().get(result).getProductId());
					
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
		tbl_view=new TableView<Product>();

		 colProductId=new TableColumn<Product, String>("mã");
		 colName=new TableColumn<Product, String>("tên");
		 colPicture=new TableColumn<Product, String>("hình");
		colQuantity=new TableColumn<Product, Integer>("số lượng");
		 colDescription=new TableColumn<Product, String>("mô tả");
		 colStatus=new TableColumn<Product, String>("status");
		 colDateAdded=new TableColumn<Product, String>("ngày thêm");
		 colTitleId=new TableColumn<Product, String>("mã title");
		 colSupplierId=new TableColumn<Product, String>("mã ncc");

		tbl_view.getColumns().addAll(colProductId,
				colName,
				colPicture,
				colQuantity,
				colDescription,
				colStatus,
				colDateAdded,
				colTitleId,
				colSupplierId);

		bd.setCenter(tbl_view);

		colProductId.setCellValueFactory(new PropertyValueFactory<>("productId"));
		colName.setCellValueFactory(new PropertyValueFactory<>("name"));
		colPicture.setCellValueFactory(new PropertyValueFactory<>("picture"));
		colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
		colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
		colDateAdded.setCellValueFactory(new PropertyValueFactory<>("dateAdded"));
		colTitleId.setCellValueFactory( cellData->new SimpleStringProperty(cellData.getValue().getTitle().getTitleId()));
		colSupplierId.setCellValueFactory(cellData->new SimpleStringProperty(cellData.getValue().getSupplier().getSupplierId()));

		colProductId.setMinWidth(100);// .setCellValueFactory(new PropertyValueFactory<>("maKH"));
		colName.setMinWidth(180);//.setCellValueFactory(new PropertyValueFactory<>("diaChi"));
		colDescription.setMinWidth(120);//.setCellValueFactory(new PropertyValueFactory<>("CMND"));
//		colPrice.setMinWidth(150);//.setCellValueFactory(new PropertyValueFactory<>("tenKH"));

		uploadDuLieuLenBang();
	}

	private void uploadDuLieuLenBang() {
		List<Product> cuss=ProductService.listProduct();
		cuss.forEach(t->{
			tbl_view.getItems().add(t);
			listProduct.add(t);
		});
	}

	public void handleRefersh(ActionEvent e) {
		//		cbc.getItems().clear();
		cbc.setValue("");
		tbl_view.getItems().clear();
		uploadDuLieuLenBang();
	}

	public void btnClickAdd(ActionEvent e) throws IOException {
//		FXMLLoader loader= new FXMLLoader(getClass().getResource(loadFormAddProduct));
//
//		Parent root=loader.load();
//
//		FormAddProduct ctlMain=loader.getController();
//
//		String id=null;
//
//		do {
//
//			id="C"+ranDomNumber();
//
//			ctlMain.txtMa.setText(id);
//
//		} while (ProductService.findProductById(id)!=null);
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

		Product ProductFind=ProductService.findProductById(textFind);

		if(ProductFind==null) {

			Error("Không tìm thấy", btnRefresh);

			cbc.requestFocus();

			return;

		}else {

			tbl_view.getItems().add(ProductFind);

		}

	}



	private void loadDataSearch() {
		ObservableList<String> items = FXCollections.observableArrayList();
		List<Product> accs=ProductService.listProduct();

		accs.forEach(t->{

			items.add(t.getProductId());

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
		//					Product listnv = null;
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
		//						Product nv=listnv;
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
