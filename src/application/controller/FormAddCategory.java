package application.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.persistence.Column;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import application.controller.impl.CategoryImpl;
import application.controller.services.CategoryService;
import application.entities.Category;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Label;

//form add category
public class FormAddCategory extends DialogBox implements Initializable{
	//border
	@FXML public BorderPane mainBd;
	
	
	//label
	@FXML Label lblTitle;
	
	//text field
	@FXML JFXTextField txtMa;
	@FXML JFXTextField txtName;
	@FXML JFXTextField txtPrice;
	@FXML JFXTextField txtDescription;
	@FXML JFXTextField txtTimeRent;
	@FXML JFXTextField txtPriceLateFee;

	//jfx button
	@FXML JFXButton btn;

	//service
	public CategoryService categoryService=new CategoryImpl();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//set don't allow edit in text field
		txtMa.setEditable(false);

	}

	public boolean kiemTraTenMatHang(ActionEvent e,String ma) throws IOException {
		String MaKT=ma.trim();
		if(MaKT.isEmpty()==false) {
			return true;
		}else {
			Error("Tên mặt hàng không được để trống",btn);
			return false;
		}
	}

	public boolean kiemTraGia(ActionEvent e,String ma) throws IOException {
		String MaKT=ma.trim();
		if(MaKT.isEmpty()==false) {
			try {
				int getNumber=Integer.parseInt(MaKT);
				return true;
			} catch (Exception e2) {
				Error("giá không hợp lệ",btn);
				return false;
			}
		}else {
			Error("giá không được để trống",btn);
			return false;
		}
	}

	public boolean kiemTraMoTa(ActionEvent e,String ma) throws IOException {
		String MaKT=ma.trim();
		if(MaKT.isEmpty()==false) {
			return true;
		}else {
			Error("mô tả không được để trống",btn);
			return false;
		}
	}
	
	public boolean kiemTraThoiGianThue(ActionEvent e,String ma) throws IOException {
		int tgThue = 0;
		String MaKT = ma.trim();
		if(MaKT.isEmpty()==true) {
			Error("thời gian thuê không được để trống",btn);
			return false;
		}
		
		try {
			tgThue = Integer.parseInt(ma);
		} catch (Exception e2) {
			Error("thời gian thuê không hợp lệ",btn);
			return false;
		}
		
		if(tgThue<=0) {
			Error("Thời gian thuê không hợp lệ", btn);
			return false;
		}
		
		return true;
	}
	
	public boolean kiemTraGiaThue(ActionEvent e,String ma) throws IOException {
		int tgThue = 0;
		String MaKT = ma.trim();
		if(MaKT.isEmpty()==true) {
			Error("giá thuê không được để trống",btn);
			return false;
		}
		
		try {
			tgThue = Integer.parseInt(ma);
		} catch (Exception e2) {
			Error("gián thuê không hợp lệ",btn);
			return false;
		}
		
		if(tgThue<=0) {
			Error("giá thuê không hợp lệ", btn);
			return false;
		}
		
		return true;
	}
	
	public void CLickOK(ActionEvent e) throws IOException {
		String ma=txtMa.getText().toString();
		String tenMatHang=txtName.getText().toString();
		String gia=txtPrice.getText().toString();
		System.out.println(gia);
		String moTa=txtDescription.getText().toString();
		String timeRent=txtTimeRent.getText().toString();
		String priveLateFee=txtPriceLateFee.getText().toString();
		boolean stillContunite=false;
		if(kiemTraTenMatHang(e,tenMatHang)) {
			stillContunite=true;
		}else {
			txtName.requestFocus();
			stillContunite=false;
		}
		if(stillContunite==true) {
			if(kiemTraGia(e,gia)) {
				stillContunite=true;
			}else {
				txtPrice.requestFocus();
				stillContunite=false;
			}
		}
		if(stillContunite==true) {
			if(kiemTraMoTa(e,moTa)) {
				stillContunite=true;
			}else {
				txtDescription.requestFocus();
				stillContunite=false;
			}
		}
		
		if(stillContunite==true) {
			if(kiemTraThoiGianThue(e, timeRent)) {
				stillContunite=true;
			}else {
				txtTimeRent.requestFocus();
				stillContunite=false;
			}
		}
		

		if(stillContunite==true) {
			if(kiemTraGiaThue(e, priveLateFee)) {
				stillContunite=true;
			}else {
				txtPriceLateFee.requestFocus();
				stillContunite=false;
			}
		}

		if(stillContunite==true) {
			
//			private String categoryId;
//			
//			@Column(columnDefinition = "nvarchar(500)")
//			private String name;
//			
//			@Column(columnDefinition = "nvarchar(1000)")
//			private String description;
//			
//			@Column
//			private float price;
//			
//			@Column
//			private float priceLateFee;
//			
//			@Column
//			private int timeRent;

			Category category=new Category(ma, tenMatHang, moTa, Float.parseFloat(gia),Integer.parseInt(priveLateFee),Integer.parseInt(timeRent));

			if(lblTitle.getText().equals("Cập nhập mặt hàng")==false) {

				if(categoryService.addCategory(category)==false) {

					Error("Lỗi thêm không thành công", btn);

				}else{

					((Node)(e.getSource())).getScene().getWindow().hide();  

				};

			}else {

				if(categoryService.updateCategory(category,ma)==null) {

					Error("Lỗi cập nhập không thành công", btn);

				}else{

					((Node)(e.getSource())).getScene().getWindow().hide();  

				};


			}

		}
	}
	public void btnXoaRong(ActionEvent e) {
		txtName.setText("");
		txtPrice.setText("");
		txtDescription.setText("");
		txtTimeRent.setText("");

	}
	public void btnCLoseWindow(ActionEvent e) throws IOException {

		((Node)(e.getSource())).getScene().getWindow().hide();  

	}

}
