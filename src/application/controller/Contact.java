package application.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;

public class Contact extends DialogBox  implements Initializable{

	@FXML JFXButton btnExit;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		btnExit.setGraphic(getImageView("CloseWindows.png"));
	
	}
	
	public void btnExit(ActionEvent e) {
	
		((Node)(e.getSource())).getScene().getWindow().hide(); 
	
	}

}
