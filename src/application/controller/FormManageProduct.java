package application.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.spi.CollatorProvider;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import com.jfoenix.controls.JFXButton;

import application.controller.impl.BillImpl;
import application.controller.impl.ProductImpl;
import application.controller.impl.ProductImpl;
import application.controller.services.BillService;
import application.controller.services.ProductService;
import application.controller.services.ProductService;
import application.entities.BillDetail;
import application.entities.Product;
import application.entities.Product;
import application.entities.Product;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sun.misc.BASE64Decoder;

public class FormManageProduct extends DialogBox implements Initializable{

	private TableView<Product> tbl_view;

	TableColumn<Product, String> colProductId;
	TableColumn<Product, String> colName;
	TableColumn<Product, String> colPicture;
	TableColumn<Product, Integer> colQuantity;
	TableColumn<Product, String> colDescription;
	TableColumn<Product, String> colStatus;
	TableColumn<Product, String> colDateAdded;
	TableColumn<Product, String> colNameTitle;
	TableColumn<Product, String> colNameSupplier;

	@FXML BorderPane bd;

	@FXML Label lblName;
	@FXML Label lblPath;
	@FXML ImageView img;

	public ProductService productService=new ProductImpl();

	public BillService billService = new BillImpl();

	@FXML ComboBox<String> cbc=new ComboBox<String>();

	@FXML JFXButton btnRefresh;

	List<Product> listProduct=new ArrayList<>();

	DecimalFormat df = new DecimalFormat("#,###"); 

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initTable();

		loadDataSearch();

		cbc.setEditable(true);

		tbl_view.setOnMouseClicked(e->{
			if(e.getClickCount()==2) {
				int result=tbl_view.getSelectionModel().getSelectedIndex();
				if(result!=-1) {

					FXMLLoader loader= new FXMLLoader(getClass().getResource(loadFormAddProduct));

					Parent root=null;
					try {
						root = loader.load();
					} catch (IOException e1) {
						e1.printStackTrace();
					}

					FormAddProduct ctlMain=loader.getController();

					ctlMain.product = tbl_view.getItems().get(result);

					ctlMain.lblTitle.setText("Cập nhập sản phẩm");

					ctlMain.maProductRemember=tbl_view.getItems().get(result).getProductId();

					ctlMain.txtMa.setText(tbl_view.getItems().get(result).getProductId());

					ctlMain.txtName.setText(tbl_view.getItems().get(result).getName());

					ctlMain.txtQuantity.setText(String.valueOf(tbl_view.getItems().get(result).getQuantity()));

					ctlMain.txtDescription.setText(tbl_view.getItems().get(result).getDescription());

					String status = tbl_view.getItems().get(result).getStatus();
					if(status.equalsIgnoreCase(CHOTHUE)) {
						ctlMain.rdChoThue.setSelected(true);
					}else if(status.equalsIgnoreCase(TRENKE)) {
						ctlMain.rdTrenKe.setSelected(true);
					}else {
						ctlMain.rdGiuLai.setSelected(true);
					}

					ctlMain.txtDateAdded.setValue(tbl_view.getItems().get(result).getDateAdded());

					try {
						ctlMain.img.setImage(getImage(tbl_view.getItems().get(result).getPicture()));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					ctlMain.txtImage.setText("...");

					ctlMain.cbcSupplier.setValue(tbl_view.getItems().get(result).getSupplier().getPhone());

					ctlMain.txtPhoneSupplier.setText(tbl_view.getItems().get(result).getSupplier().getSupplierId());

					ctlMain.txtCompanySupplier.setText(tbl_view.getItems().get(result).getSupplier().getCompanyName());

					ctlMain.cbcTitle.setValue(tbl_view.getItems().get(result).getTitle().getName());

					ctlMain.txtNameTitle.setText(tbl_view.getItems().get(result).getTitle().getTitleId());

					if(tbl_view.getItems().get(result).getTitle().getStatus().equalsIgnoreCase(DAT)) {
						ctlMain.txtStatusTitle.setText("Hết hàng");
					}else {
						ctlMain.txtStatusTitle.setText("Còn hàng");
					}



					loadFXML(root,btnRefresh).setOnHidden(ev->{

						handleRefersh(new ActionEvent());

					});;
				}

			}else if(e.getClickCount() == 1) {
				int resultX=tbl_view.getSelectionModel().getSelectedIndex();
				lblName.setText(tbl_view.getItems().get(resultX).getName());
				lblPath.setText(tbl_view.getItems().get(resultX).getStatus());
				try {
					img.setImage(getImage(tbl_view.getItems().get(resultX).getPicture()));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
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

					List<BillDetail> listBillDetail = billService.findAllBillDetailByProductId(
							tbl_view.getItems().get(result).getProductId());
					if(listBillDetail!=null && listBillDetail.size()>=1) {

						try {
							Error("Đang có khách hàng đặt bill", btnRefresh);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

						return;

					}

					System.out.println(productService.removeProduct(tbl_view.getItems().get(result).getProductId()));

					lblName.setText("");
					lblPath.setText("");
					img.setImage(null);

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
		tbl_view=new TableView<Product>();

		colProductId=new TableColumn<Product, String>("mã");
		colName=new TableColumn<Product, String>("tên");
		colQuantity=new TableColumn<Product, Integer>("số lượng");
		colDescription=new TableColumn<Product, String>("mô tả");
		colStatus=new TableColumn<Product, String>("status");
		colDateAdded=new TableColumn<Product, String>("ngày thêm");
		colNameTitle=new TableColumn<Product, String>("tên title");
		colNameSupplier=new TableColumn<Product, String>("tên supplier");

		tbl_view.getColumns().addAll(colProductId,
				colName,
				colQuantity,
				colStatus,
				colDateAdded,
				colNameTitle,
				colNameSupplier,
				colDescription);

		bd.setCenter(tbl_view);

		colProductId.setCellValueFactory(new PropertyValueFactory<>("productId"));
		colName.setCellValueFactory(new PropertyValueFactory<>("name"));

		colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
		colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
		colDateAdded.setCellValueFactory(new PropertyValueFactory<>("dateAdded"));
		colNameTitle.setCellValueFactory( cellData->new SimpleStringProperty(cellData.getValue().getTitle().getName()));
		colNameSupplier.setCellValueFactory(cellData->new SimpleStringProperty(cellData.getValue().getSupplier().getCompanyName()));

		colProductId.setMinWidth(100);// .setCellValueFactory(new PropertyValueFactory<>("maKH"));
		colName.setMinWidth(180);//.setCellValueFactory(new PropertyValueFactory<>("diaChi"));
		colDescription.setMinWidth(120);//.setCellValueFactory(new PropertyValueFactory<>("CMND"));
		colDateAdded.setMinWidth(120);
		colNameTitle.setMinWidth(150);
		colNameSupplier.setMinWidth(120);

		uploadDuLieuLenBang();
	}

	private void uploadDuLieuLenBang() {
		List<Product> cuss=productService.listProduct();
		cuss.forEach(t->{
			tbl_view.getItems().add(t);
			listProduct.add(t);
		});
	}

	public void handleRefersh(ActionEvent e) {
		//		cbc.getItems().clear();
		lblName.setText("");
		lblPath.setText("");
		img.setImage(null);
		cbc.setValue("");
		tbl_view.getItems().clear();
		uploadDuLieuLenBang();
	}

	public void btnClickAdd(ActionEvent e) throws IOException {
		FXMLLoader loader= new FXMLLoader(getClass().getResource(loadFormAddProduct));

		Parent root=loader.load();

		FormAddProduct ctlMain=loader.getController();

		String id=null;

		do {

			id="C"+ranDomNumber();

			ctlMain.txtMa.setText(id);

			ctlMain.txtDateAdded.setValue(LocalDate.now());

		} while (productService.findProductById(id)!=null);

		loadFXML(root,btnRefresh).setOnHidden(ev->{

			handleRefersh(e);
			
//			cbc.getItems().clear();
			
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

		Product ProductFind=productService.findProductById(textFind);

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
		List<Product> accs=productService.listProduct();

		accs.forEach(t->{

			items.add(t.getProductId());

		});

		FilteredList<String> filteredItems = new FilteredList<String>(items);

		cbc.getEditor().textProperty().addListener(new InputFilter(cbc, filteredItems, false));

		cbc.setItems(filteredItems);

		cbc.setEditable(true);

	}
}
