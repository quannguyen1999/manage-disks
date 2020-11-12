package application.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;

import application.controller.impl.CustomerImpl;
import application.controller.services.CustomerService;
import application.entities.Customer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Label;
public class FormAddCustomer extends DialogBox implements Initializable{
	@FXML public BorderPane mainBd;
	@FXML Label lblTitle;
	@FXML JFXTextField txtMa;
	@FXML JFXTextField txtDiaChi;
	@FXML JFXTextField txtDienThoai;
	@FXML JFXTextField txtTenKH;
	@FXML JFXDatePicker txtNgaySinh;

	@FXML JFXButton btn;

	Customer customer=null;
	
	private CustomerService customerService=new CustomerImpl();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		txtMa.setEditable(false);

	}
	public boolean kiemTraMa(ActionEvent e,String text) throws IOException {
		if(text.isEmpty()==false) {
			
			return true;
			
		}else {

			Error("mã khách hàng không được để trống",btn);

		}
		return false;
	}
	public boolean kiemTraDiaChi(ActionEvent e,String ma) throws IOException {
		String MaKT=ma.trim();
		if(MaKT.isEmpty()==false) {
			//			if(MaKT.matches("^[A-Za-z\\sÀ�?ÂÃÈÉÊÌ�?ÒÓÔÕÙÚ�?àáâãèéêìíòóôõùúýĂă�?đĨĩŨũƠơƯưẠ-ỹ]+$")==true) {
			return true;
			//			}else {
			//				Error("địa chỉ không được nhập ký tự đặc biệt",btn);
			//				return false;
			//			}
		}else {
			Error("địa chỉ không được để trống",btn);
			return false;
		}
	}
	
	
	public boolean kiemTraDienThoai(ActionEvent e,String ma) throws IOException {
		String MaKT=ma.trim();
		if(MaKT.isEmpty()==false) {
			if(MaKT.length()==10) {
				if(MaKT.matches("[0-9]{10}+")==true) {
					return true;
				}else {
					Error("số điện thoại chỉ nhập số",btn);
					return false;
				}
			}else {
				Error("số điện thoại phải 10 số",btn);
				return false;
			}
		}else {
			Error("Số điện thoại không được để trống",btn);
			return false;
		}
	}
	public boolean kiemTraTenKhachHang(ActionEvent e,String ma) throws IOException {
		String MaKT=ma.trim();
		if(MaKT.isEmpty()==false) {
			if(MaKT.matches("^[A-Za-z\\sÀ�?ÂÃÈÉÊÌ�?ÒÓÔÕÙÚ�?àáâãèéêìíòóôõùúýĂă�?đĨĩŨũƠơƯưẠ-ỹ]+$")==true) {
				return true;
			}else {
				Error("Tên khách hàng không được nhập ký tự đặc biệt",btn);
				return false;
			}
		}else {
			Error("Tên khách hàng không được để trống",btn);
			return false;
		}
	}
	public void CLickOK(ActionEvent e) throws IOException {
		String ma=txtMa.getText().toString();
		String diaChi=txtDiaChi.getText().toString();
		String DienThoai=txtDienThoai.getText().toString();
		String ten=txtTenKH.getText().toString();
		LocalDate lc=txtNgaySinh.getValue();
		boolean stillContunite=true;
		if(kiemTraMa(e, ma)==true) {
			stillContunite=true;
		}else {
			txtMa.requestFocus();
			stillContunite=false;
		}
		if(stillContunite==true) {
			if(kiemTraDiaChi(e,diaChi)) {
				stillContunite=true;
			}else {
				txtDiaChi.requestFocus();
				stillContunite=false;
			}
		}
		if(stillContunite==true) {
			if(kiemTraDienThoai(e,DienThoai)) {
				stillContunite=true;
			}else {
				txtDienThoai.requestFocus();
				stillContunite=false;
				return;
			}
		}
		if(stillContunite==true) {
			if(kiemTraTenKhachHang(e,ten)) {
				stillContunite=true;
			}else {
				txtTenKH.requestFocus();
				stillContunite=false;
			}
		}
		if(stillContunite==true) {
			if(txtNgaySinh.getValue()==null) {
				stillContunite=false;
				Error("Ngày sinh chưa ch�?n",btn);
				txtNgaySinh.requestFocus();
			}
			
			if(txtNgaySinh.getValue().getYear()>=2010) {
				
				stillContunite=false;
				
				Error("Ngày sinh không hợp lệ", btn);
				
				txtNgaySinh.requestFocus();
				
			}
			
			
		}
		
		if(stillContunite==true) {

			if(lblTitle.getText().equals("Cập nhập khách hàng")==true) {
				
				if(customer.getPhone().equals(DienThoai)==false) {
					
					if(	customerService.findCustomerByPhone(DienThoai)!=null) {
						
						Error("Điện thoại đã tồn tại", btn);
						
						txtDienThoai.requestFocus();
						
						return;
						
					}
					
				}
				
			}else {
				if(	customerService.findCustomerByPhone(DienThoai)!=null) {
					
					Error("Điện thoại đã tồn tại", btn);
					
					txtDienThoai.requestFocus();
					
					return;
					
				}
			}
			
			
			
		}
		
		if(stillContunite==true) {

			if(lblTitle.getText().equals("Cập nhập khách hàng")==false) {

				if(customerService.addCustomer(new Customer(ma, ten, diaChi,DienThoai, true, lc))==false) {

					Error("Lỗi thêm không thành công", btn);

				}else{
//					Success("Thêm khách hàng thành công")
					((Node)(e.getSource())).getScene().getWindow().hide();  

				};

			}else {

				if(customerService.updateCustomer(new Customer(ma, ten, diaChi,DienThoai, true, lc),ma)==null) {

					Error("Lỗi cập nhập không thành công", btn);

				}else{

					((Node)(e.getSource())).getScene().getWindow().hide();  

				};
			}


		}
	}
	public void btnXoaRong(ActionEvent e) {
		if(customer==null) {
			txtDiaChi.setText("");
			txtDienThoai.setText("");
			txtTenKH.setText("");
			txtNgaySinh.setValue(LocalDate.of(1999, 11, 24));
		}else {
			txtDiaChi.setText(customer.getAddress());
			
			txtDienThoai.setText(customer.getPhone());
			
			txtTenKH.setText(customer.getName());
			
			txtNgaySinh.setValue(customer.getDateOfBirth());
		}
		

	}
	public void btnCLoseWindow(ActionEvent e) throws IOException {
		
		((Node)(e.getSource())).getScene().getWindow().hide();  
	
	}

}
