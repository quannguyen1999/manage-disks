package application.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;

//Form contact
public class Contact extends DialogBox  implements Initializable{

	//ánh xạ
	@FXML JFXButton btnExit;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		//set image
		btnExit.setGraphic(getImageView("CloseWindows.png"));
	
	}
	
	//thoát form contact
	public void btnExit(ActionEvent e) {
	
		((Node)(e.getSource())).getScene().getWindow().hide(); 
	
	}

}
