package application.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
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
import application.controller.impl.CustomerImpl;
import application.controller.services.TitleService;
import application.controller.services.ProductService;
import application.controller.services.SupplierService;
import application.controller.services.CustomerService;
import application.entities.Title;
import application.entities.Product;
import application.entities.Supplier;
import application.entities.Customer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
public class FormAddProduct extends DialogBox implements Initializable{

	@FXML public BorderPane mainBd;

	@FXML Label lblTitle;

	@FXML JFXTextField txtMa;

	@FXML JFXTextField txtName;

	@FXML JFXTextField txtQuantity;

	@FXML JFXTextField txtDescription;

	@FXML JFXTextField txtStatus;

	@FXML JFXDatePicker txtDateAdded;

	@FXML TextField txtImage;


	@FXML ComboBox<String> cbcSupplier=new ComboBox<String>();

	@FXML JFXTextField txtPhoneSupplier;

	@FXML JFXTextField txtCompanySupplier;


	@FXML ComboBox<String> cbcTitle=new ComboBox<String>();

	@FXML JFXTextField txtNameTitle;

	@FXML JFXTextField txtStatusTitle;

	@FXML JFXButton btn;
	
	@FXML ImageView img;

	public TitleService titleService=new TitleImpl();

	public ProductService productService=new ProductImpl();

	public SupplierService supplierService=new SupplierImpl();

	public String maProductRemember="";

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		txtImage.setEditable(false);
		
		txtMa.setEditable(false);
		
		txtDateAdded.setEditable(false);
		
		txtDateAdded.setValue(LocalDate.now());
		

		txtPhoneSupplier.setEditable(false);

		txtCompanySupplier.setEditable(false);

		txtNameTitle.setEditable(false);

		txtStatusTitle.setEditable(false);

		loadDataSearchSupplier();

		loadDataSearchTitle();
	}
	
	String fileHinhCapNhap="";
	String fileHinh="";
	public void btnChonHinh(ActionEvent e) {
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters().add(new ExtensionFilter("PNG Files", "*.PNG","*.png"));
		List<File> f=fc.showOpenMultipleDialog(null);
		for(File file:f) {
			fileHinh=file.getAbsolutePath();
			Image image = new Image("file:///"+file.getAbsolutePath());
			img.setImage(image);
			img.setFitHeight(150);
			txtImage.setText(fileHinh);
		}
	}

	private void loadDataSearchSupplier() {
		ObservableList<String> items = FXCollections.observableArrayList();

		List<Supplier> accs=supplierService.listSupplier();

		accs.forEach(t->{

			items.add(t.getSupplierId());

		});

		FilteredList<String> filteredItems = new FilteredList<String>(items);

		cbcSupplier.getEditor().textProperty().addListener(new InputFilter(cbcSupplier, filteredItems, false));

		cbcSupplier.setItems(filteredItems);

		cbcSupplier.setEditable(true);
	}

	private void loadDataSearchTitle() {
		ObservableList<String> items = FXCollections.observableArrayList();

		List<Title> accs=titleService.listTitle();

		accs.forEach(t->{

			items.add(t.getTitleId());

		});

		FilteredList<String> filteredItems = new FilteredList<String>(items);

		cbcTitle.getEditor().textProperty().addListener(new InputFilter(cbcTitle, filteredItems, false));

		cbcTitle.setItems(filteredItems);

		cbcTitle.setEditable(true);
	}

	public void findItemSupplier(ActionEvent e) throws IOException{
		Supplier listnv = null;
		List<Supplier> accs=supplierService.listSupplier();
		String cbcTextFind=null;

		try {

			cbcTextFind=cbcSupplier.getSelectionModel().getSelectedItem().toString();

		} catch (Exception e2) {

			Error("Vui lòng không để trống", btn);
			xoaRongSUpplier();
			return;

		};

		if(cbcTextFind==null) {

			Error("Vui lòng không để trống", btn);

			cbcSupplier.requestFocus();
			xoaRongSUpplier();
			return;

		}
		try {
			for(int i=0;i<accs.size();i++) {
				if(accs.get(i).getSupplierId().equals(cbcTextFind)) {
					listnv=accs.get(i);
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		if(listnv!=null) {
			Supplier cate=listnv;
			if(cate!=null) {

				txtPhoneSupplier.setText(cate.getPhone());

				txtCompanySupplier.setText(cate.getCompanyName());

			}
		}else {

			Error("Không tìm thấy mã này", btn);

			cbcSupplier.requestFocus();

			xoaRongSUpplier();

			return;

		}
	}

	public void xoaRongSUpplier() {

		txtPhoneSupplier.setText("");

		txtCompanySupplier.setText("");


	}

	public void findItemTitle(ActionEvent e) throws IOException{
		Title listnv = null;
		List<Title> accs=titleService.listTitle();
		String cbcTextFind=null;

		try {

			cbcTextFind=cbcTitle.getSelectionModel().getSelectedItem().toString();

		} catch (Exception e2) {

			Error("Vui lòng không để trống", btn);
			xoaRongTitle();
			return;

		};

		if(cbcTextFind==null) {

			Error("Vui lòng không để trống", btn);

			cbcTitle.requestFocus();
			xoaRongTitle();
			return;

		}
		try {
			for(int i=0;i<accs.size();i++) {
				if(accs.get(i).getTitleId().equals(cbcTextFind)) {
					listnv=accs.get(i);
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		if(listnv!=null) {
			Title cate=listnv;
			if(cate!=null) {

				txtNameTitle.setText(cate.getName());

				if(cate.isStatus()==true) {

					txtStatusTitle.setText("Còn hàng");

				}else {

					txtStatusTitle.setText("Hết hàng");

				}


			}
		}else {

			Error("Không tìm thấy mã này", btn);

			cbcSupplier.requestFocus();

			xoaRongTitle();

			return;

		}
	}

	public void xoaRongTitle() {
		txtNameTitle.setText("");

		txtStatusTitle.setText("");

	}


	public boolean checkNameProduct(ActionEvent e,String ma) throws IOException {
		String MaKT=ma.trim();
		if(MaKT.isEmpty()==false) {
			return true;
		}else {
			Error("tên sản phẩm không được để trống",btn);
			return false;
		}
	}
	
	public boolean checkQuanTityProduct(ActionEvent e,String ma) throws IOException {
		String MaKT=ma.trim();
		if(MaKT.isEmpty()==false) {
			try {
				Integer.parseInt(MaKT);
			} catch (Exception e2) {
				// TODO: handle exception
				Error("Số lượng không hợp lệ",btn);
				return false;
			}
			
			return true;
			
		}else {
			Error("Số lượng không được để trống",btn);
			return false;
		}
	}
	
	public boolean checkDescriptionProduct(ActionEvent e,String ma) throws IOException {
		String MaKT=ma.trim();
		if(MaKT.isEmpty()==false) {
			return true;
		}else {
			Error("Mô tả không được để trống",btn);
			return false;
		}
	}
	
	public boolean checkStatusProduct(ActionEvent e,String ma) throws IOException {
		String MaKT=ma.trim();
		if(MaKT.isEmpty()==false) {
			return true;
		}else {
			Error("Tình trạng sản phẩm không được để trống",btn);
			return false;
		}
	}
	
	public boolean checkSupplierID(ActionEvent e,String ma) throws IOException {
		String MaKT=ma.trim();
		if(MaKT.isEmpty()==false) {
			return true;
		}else {
			Error("Bạn chưa chọn nhà cung cấp",btn);
			return false;
		}
	}
	
	public boolean checkTitleID(ActionEvent e,String ma) throws IOException {
		String MaKT=ma.trim();
		if(MaKT.isEmpty()==false) {
			return true;
		}else {
			Error("Bạn chưa chọn title",btn);
			return false;
		}
	}
	
	public boolean checkImage(ActionEvent e,String ma) throws IOException {
		String MaKT=ma.trim();
		if(MaKT.isEmpty()==false) {
			return true;
		}else {
			Error("Bạn chưa chọn hình",btn);
			return false;
		}
	}
	
	
	
	public void CLickOK(ActionEvent e) throws IOException {
		String ma=txtMa.getText().toString();
		String nameProduct=txtName.getText().toString();
		String quantityProduct=txtQuantity.getText().toString();
		String descriptionProduct=txtDescription.getText().toString();
		String statusProduct=txtStatus.getText().toString();
		LocalDate dateAddedProduct=txtDateAdded.getValue();
		String imageProduct=txtImage.getText().toString();
		
		String idSupplier=cbcSupplier.getSelectionModel().getSelectedItem().toString();
		
		String idTitle=cbcTitle.getSelectionModel().getSelectedItem().toString();
		
		boolean stillContunite=false;
		if(checkNameProduct(e,nameProduct)) {
			stillContunite=true;
		}else {
			txtName.requestFocus();
			stillContunite=false;
		}
		if(stillContunite==true) {
			if(checkQuanTityProduct(e,quantityProduct)) {
				stillContunite=true;
			}else {
				txtQuantity.requestFocus();
				stillContunite=false;
			}
		}
		if(stillContunite==true) {
			if(checkDescriptionProduct(e, descriptionProduct)) {
				stillContunite=true;
			}else {
				txtDescription.requestFocus();
				stillContunite=false;
			}
		}
		if(stillContunite==true) {
			if(checkStatusProduct(e,statusProduct)) {
				stillContunite=true;
			}else {
				txtStatus.requestFocus();
				stillContunite=false;
			}
		}
		
		if(stillContunite==true) {
			if(checkImage(e,imageProduct)) {
				stillContunite=true;
			}else {
				txtImage.requestFocus();
				stillContunite=false;
			}
		}
		
		if(stillContunite==true) {
			if(checkSupplierID(e,idSupplier)) {
				stillContunite=true;
			}else {
				cbcSupplier.requestFocus();
				stillContunite=false;
			}
		}
		
		if(stillContunite==true) {
			if(checkTitleID(e,idTitle)) {
				stillContunite=true;
			}else {
				cbcTitle.requestFocus();
				stillContunite=false;
			}
		}
		
		if(stillContunite==true) {
			

			Product product2=new Product(ma, nameProduct,imageProduct,
					Integer.parseInt(quantityProduct)	, descriptionProduct, statusProduct,
					dateAddedProduct, new Title(idTitle), new Supplier(idSupplier));


			if(lblTitle.getText().equals("Cập nhập sản phẩm")==false) {

				if(productService.addProduct(product2)==false) {

					Error("Lỗi thêm không thành công", btn);

				}else{

					((Node)(e.getSource())).getScene().getWindow().hide();  

				};

			}else {

				if(productService.updateProduct(product2,ma)==null) {

					Error("Lỗi cập nhập không thành công", btn);

				}else{

					((Node)(e.getSource())).getScene().getWindow().hide();  

				};

			}

		}
	}
	public void btnXoaRong(ActionEvent e) {
		if(maProductRemember.isEmpty()==false) {
			Product product=productService.findProductById(maProductRemember);
			txtMa.setText(product.getProductId());
			txtName.setText(product.getName());
			txtQuantity.setText(String.valueOf(product.getQuantity()));
			txtDescription.setText(product.getDescription());
			txtStatus.setText(product.getStatus());
			txtDateAdded.setValue(product.getDateAdded());
			
			Image image = new Image("file:///"+product.getPicture());
			img.setImage(image);
			
			
			txtImage.setText(product.getPicture());
			cbcSupplier.setValue(product.getSupplier().getSupplierId());
			cbcTitle.setValue(product.getTitle().getTitleId());
			
			txtCompanySupplier.setText(product.getSupplier().getCompanyName());
			txtPhoneSupplier.setText(product.getSupplier().getPhone());
			
			txtNameTitle.setText(product.getTitle().getName());
			if(product.getTitle().isStatus()==true) {
				txtStatusTitle.setText("Còn hàng");
			}else {
				txtStatusTitle.setText("Hết hàng");
			}
		}else {
			System.out.println();
			Image image=new Image(getClass().getResource("../image/Login-2.jpg").toString());
			img.setImage(image);
			txtName.setText("");
			txtQuantity.setText("");
			txtDescription.setText("");
			txtStatus.setText("");
			txtDateAdded.setValue(LocalDate.now());
			txtImage.setText("");
			cbcSupplier.setValue("");
			cbcTitle.setValue("");

		}


	}

	public void btnCLoseWindow(ActionEvent e) throws IOException {

		((Node)(e.getSource())).getScene().getWindow().hide();  

	}

}