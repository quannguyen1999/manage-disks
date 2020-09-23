package application.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;

import application.controller.impl.SupplierImpl;
import application.controller.impl.CustomerImpl;
import application.controller.services.SupplierService;
import application.controller.services.CustomerService;
import application.entities.Supplier;
import application.entities.Customer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Label;
public class FormAddSupplier extends DialogBox implements Initializable{
	@FXML public BorderPane mainBd;
	@FXML Label lblTitle;
	@FXML JFXTextField txtMa;
	@FXML JFXTextField txtPhone;
	@FXML JFXTextField txtAddress;
	@FXML JFXTextField txtNameCompany;

	@FXML JFXButton btn;

	public SupplierService SupplierService=new SupplierImpl();

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		txtMa.setEditable(false);

	}

	public boolean kiemTraDienThoai(ActionEvent e,String ma) throws IOException {
		String MaKT=ma.trim();
		if(MaKT.isEmpty()==false) {
			return true;
		}else {
			Error("Điện thoại không được để trống",btn);
			return false;
		}
	}

	public boolean kiemTraDiaChi(ActionEvent e,String ma) throws IOException {
		String MaKT=ma.trim();
		if(MaKT.isEmpty()==false) {
				return true;
		}else {
			Error("Địa chỉ không được để trống",btn);
			return false;
		}
	}

	public boolean kiemTraTenCongTy(ActionEvent e,String ma) throws IOException {
		String MaKT=ma.trim();
		if(MaKT.isEmpty()==false) {
			return true;
		}else {
			Error("Tên công ty không được để trống",btn);
			return false;
		}
	}


	public void CLickOK(ActionEvent e) throws IOException {
		System.out.println("ok");
		String ma=txtMa.getText().toString();
		String phone=txtPhone.getText().toString();
		String address=txtAddress.getText().toString();
		String nameCompany=txtNameCompany.getText().toString();
		boolean stillContunite=false;
		if(kiemTraDienThoai(e,phone)) {
			stillContunite=true;
		}else {
			txtPhone.requestFocus();
			stillContunite=false;
		}
		if(stillContunite==true) {
			if(kiemTraDiaChi(e,address)) {
				stillContunite=true;
			}else {
				txtAddress.requestFocus();
				stillContunite=false;
			}
		}
		if(stillContunite==true) {
			if(kiemTraTenCongTy(e,nameCompany)) {
				stillContunite=true;
			}else {
				txtNameCompany.requestFocus();
				stillContunite=false;
			}
		}

		if(stillContunite==true) {

			Supplier Supplier=new Supplier(ma, phone, address, nameCompany);

			if(lblTitle.getText().equals("Cập nhập nhà cung cấp")==false) {

				if(SupplierService.addSupplier(Supplier)==false) {

					Error("Lỗi thêm không thành công", btn);

				}else{

					((Node)(e.getSource())).getScene().getWindow().hide();  

				};

			}else {

				if(SupplierService.updateSupplier(Supplier,ma)==null) {

					Error("Lỗi cập nhập không thành công", btn);

				}else{

					((Node)(e.getSource())).getScene().getWindow().hide();  

				};


			}


		}
	}
	public void btnXoaRong(ActionEvent e) {
		txtPhone.setText("");
		txtAddress.setText("");
		txtNameCompany.setText("");

	}
	public void btnCLoseWindow(ActionEvent e) throws IOException {

		((Node)(e.getSource())).getScene().getWindow().hide();  

	}

}
