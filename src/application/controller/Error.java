package application.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
public class Error implements Initializable{
	
	@FXML Label lblError;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	
	public void btnExit(ActionEvent e) {
		
		((Node)(e.getSource())).getScene().getWindow().hide(); 
	
	}
}
