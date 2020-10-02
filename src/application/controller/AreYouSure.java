package application.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;

public class AreYouSure extends DialogBox implements Initializable{
	boolean result=true;
	
	@FXML JFXButton btnExit;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		btnExit.setGraphic(getImageView("CLoseWindows.png"));
		
	}
	
	public void btnYes(ActionEvent e) {
		
		result=true;
		
		System.out.println(result);
		
		((Node)(e.getSource())).getScene().getWindow().hide(); 
		
	}
	
	public void btnNo(ActionEvent e) {
		
		result=false;
		
		System.out.println(result);
		
		((Node)(e.getSource())).getScene().getWindow().hide(); 
		
	}
	

}
