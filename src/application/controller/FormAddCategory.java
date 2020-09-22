package application.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;

import application.controller.impl.CategoryImpl;
import application.controller.impl.CustomerImpl;
import application.controller.services.CategoryService;
import application.controller.services.CustomerService;
import application.entities.Category;
import application.entities.Customer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Label;
public class FormAddCategory extends DialogBox implements Initializable{
	@FXML public BorderPane mainBd;
	@FXML Label lblTitle;
	@FXML JFXTextField txtMa;
	@FXML JFXTextField txtName;
	@FXML JFXTextField txtPrice;
	@FXML JFXTextField txtDescription;

	@FXML JFXButton btn;

	public CategoryService categoryService=new CategoryImpl();

	@Override
	public void initialize(URL location, ResourceBundle resources) {

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


	public void CLickOK(ActionEvent e) throws IOException {
		System.out.println("ok");
		String ma=txtMa.getText().toString();
		String tenMatHang=txtName.getText().toString();
		String gia=txtPrice.getText().toString();
		String moTa=txtDescription.getText().toString();
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

			Category category=new Category(ma, tenMatHang, moTa, Float.parseFloat(gia));

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

	}
	public void btnCLoseWindow(ActionEvent e) throws IOException {

		((Node)(e.getSource())).getScene().getWindow().hide();  

	}

}
