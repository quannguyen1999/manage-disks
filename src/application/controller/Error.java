package application.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
//form error
public class Error implements Initializable{
	
	//ánh xạ
	@FXML Label lblError;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	//xử lý form khi bấm nút thoát
	public void btnExit(ActionEvent e) {
		
		((Node)(e.getSource())).getScene().getWindow().hide(); 
	
	}
	
	//lắng nghe sự kiện
	public void handleKeyEvents(KeyEvent e) throws IOException {
		//nếu enter thì thực thi
		if(e.getCode()==KeyCode.ENTER) {
			
			((Node)(e.getSource())).getScene().getWindow().hide(); 
			
		}
	}
}
