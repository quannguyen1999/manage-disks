package application.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;

//form 'are you sure'
public class AreYouSure extends DialogBox implements Initializable{
	boolean result=true;
	
	//ánh xạ
	@FXML JFXButton btnExit;
	
	//khởi tạo
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		btnExit.setGraphic(getImageView("CLoseWindows.png"));
		
	}
	
	//Xử lý button yes
	public void btnYes(ActionEvent e) {
		
		result=true;
		
		((Node)(e.getSource())).getScene().getWindow().hide(); 
		
	}
	
	//Xử lý button no
	public void btnNo(ActionEvent e) {
		
		result=false;
		
		((Node)(e.getSource())).getScene().getWindow().hide(); 
		
	}
	

}
