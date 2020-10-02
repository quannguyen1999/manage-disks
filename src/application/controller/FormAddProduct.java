package application.controller;

import java.io.ByteArrayInputStream;
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
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;

import javax.imageio.ImageIO;

import sun.misc.BASE64Decoder;

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
import javafx.embed.swing.SwingFXUtils;
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
		fc.getExtensionFilters().add(new ExtensionFilter("PNG Files", "*.PNG","*.png","*.jpg"));
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
			// this is not the real stream just for example
			String base64String= "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";

			BASE64Decoder decoder = new BASE64Decoder();
			
			byte[] decodedBytes = decoder.decodeBuffer(base64String);



			String uploadFile = "/tmp/test.png";

			BufferedImage image = ImageIO.read(new ByteArrayInputStream(decodedBytes));
			if (image == null) {
				
			}
			File f = new File(uploadFile);

			// write the image
			img.setImage(SwingFXUtils.toFXImage(image, null));


			//			Product product2=new Product(ma, nameProduct,imageProduct,
			//					Integer.parseInt(quantityProduct)	, descriptionProduct, statusProduct,
			//					dateAddedProduct, new Title(idTitle), new Supplier(idSupplier));
			//
			//
			//			if(lblTitle.getText().equals("Cập nhập sản phẩm")==false) {
			//
			//				if(productService.addProduct(product2)==false) {
			//
			//					Error("Lỗi thêm không thành công", btn);
			//
			//				}else{
			//
			//					((Node)(e.getSource())).getScene().getWindow().hide();  
			//
			//				};
			//
			//			}else {
			//
			//				if(productService.updateProduct(product2,ma)==null) {
			//
			//					Error("Lỗi cập nhập không thành công", btn);
			//
			//				}else{
			//
			//					((Node)(e.getSource())).getScene().getWindow().hide();  
			//
			//				};
			//
			//			}

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
