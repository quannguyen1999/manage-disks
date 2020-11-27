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

import application.controller.impl.CategoryImpl;
import application.controller.impl.CategoryImpl;
import application.controller.services.CategoryService;
import application.controller.services.CategoryService;
import application.entities.Category;
import application.entities.Category;
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

public class FormManageCategories extends DialogBox implements Initializable{
	private TableView<Category> tbl_view;

	TableColumn<Category, String> colCategoryId;
	TableColumn<Category, String> colName;
	TableColumn<Category, String> colDescription;
	TableColumn<Category, String> colPrice;
	TableColumn<Category, String> colTimeRent;

	DecimalFormat df = new DecimalFormat("#,###"); 

	@FXML BorderPane bd;

	public CategoryService categoryService=new CategoryImpl();

	@FXML ComboBox<String> cbc=new ComboBox<String>();

	@FXML JFXButton btnRefresh;

	List<Category> listCategory=new ArrayList<>();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initTable();

		loadDataSearch();

		cbc.setEditable(true);

		tbl_view.setOnMouseClicked(e->{
			if(e.getClickCount()==2) {
				int result=tbl_view.getSelectionModel().getSelectedIndex();
				if(result!=-1) {

					FXMLLoader loader= new FXMLLoader(getClass().getResource(loadFormAddCategory));

					Parent root=null;
					try {
						root = loader.load();
					} catch (IOException e1) {
						e1.printStackTrace();
					}

					FormAddCategory ctlMain=loader.getController();

					ctlMain.lblTitle.setText("Cập nhập mặt hàng");

					ctlMain.txtMa.setText(tbl_view.getItems().get(result).getCategoryId());

					ctlMain.txtName.setText(tbl_view.getItems().get(result).getName());

					ctlMain.txtPrice.setText(String.valueOf(Math.round(tbl_view.getItems().get(result).getPrice())));

					ctlMain.txtTimeRent.setText(String.valueOf(tbl_view.getItems().get(result).getTimeRent()));

					ctlMain.txtDescription.setText(tbl_view.getItems().get(result).getDescription());

					loadFXML(root,btnRefresh).setOnHidden(ev->{

						handleRefersh(new ActionEvent());

					});;
				}
			}
		});
	}


	public void btnXoaCategory(ActionEvent e) throws IOException{

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

					//					CategoryService.removeCategory(tbl_view.getItems().get(result).getCategoryId());

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
		tbl_view=new TableView<Category>();

		colCategoryId=new TableColumn<Category, String>("mã");
		colName=new TableColumn<Category, String>("Tên");
		colDescription=new TableColumn<Category, String>("Mô tả");
		colPrice=new TableColumn<Category, String>("Giá");
		colTimeRent = new TableColumn<Category, String>("Thời gian thuê");

		tbl_view.getColumns().addAll(colCategoryId,colName,colDescription,colPrice,colTimeRent);

		bd.setCenter(tbl_view);

		colCategoryId.setCellValueFactory(new PropertyValueFactory<>("CategoryId"));
		colName.setCellValueFactory(new PropertyValueFactory<>("name"));
		colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
		colPrice.setCellValueFactory( cellData->new SimpleStringProperty(String.valueOf(df.format(cellData.getValue().getPrice()))+" $"));
		colTimeRent.setCellValueFactory(new PropertyValueFactory<>("timeRent"));

		colCategoryId.setMinWidth(100);// .setCellValueFactory(new PropertyValueFactory<>("maKH"));
		colName.setMinWidth(180);//.setCellValueFactory(new PropertyValueFactory<>("diaChi"));
		colDescription.setMinWidth(120);//.setCellValueFactory(new PropertyValueFactory<>("CMND"));
		colPrice.setMinWidth(150);//.setCellValueFactory(new PropertyValueFactory<>("tenKH"));
		colTimeRent.setMinWidth(150);
		
		
		uploadDuLieuLenBang();
	}

	private void uploadDuLieuLenBang() {
		List<Category> cuss=categoryService.listCategory();
		cuss.forEach(t->{
			tbl_view.getItems().add(t);
			listCategory.add(t);
		});
	}

	public void handleRefersh(ActionEvent e) {
		//		cbc.getItems().clear();
		cbc.setValue("");
		tbl_view.getItems().clear();
		uploadDuLieuLenBang();
	}

	public void btnClickAdd(ActionEvent e) throws IOException {
		FXMLLoader loader= new FXMLLoader(getClass().getResource(loadFormAddCategory));

		Parent root=loader.load();

		FormAddCategory ctlMain=loader.getController();

		String id=null;

		do {

			id="C"+ranDomNumber();

			ctlMain.txtMa.setText(id);

		} while (categoryService.findCategoryById(id)!=null);

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

		Category CategoryFind=categoryService.findCategoryById(textFind);

		if(CategoryFind==null) {

			Error("Không tìm thấy", btnRefresh);

			cbc.requestFocus();

			return;

		}else {

			tbl_view.getItems().add(CategoryFind);

		}

	}



	private void loadDataSearch() {
		ObservableList<String> items = FXCollections.observableArrayList();
		List<Category> accs=categoryService.listCategory();

		accs.forEach(t->{

			items.add(t.getCategoryId());

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
		//					Category listnv = null;
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
		//						Category nv=listnv;
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
