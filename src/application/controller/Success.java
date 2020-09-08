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

public class Success implements Initializable{
	
	@FXML Label lblSuccess;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	public void btnExit(ActionEvent e) {
		
		((Node)(e.getSource())).getScene().getWindow().hide(); 
		
	}
	
	public void handleKeyEvents(KeyEvent e) throws IOException {
		if(e.getCode()==KeyCode.ENTER) {
			
			((Node)(e.getSource())).getScene().getWindow().hide(); 
			
		}
	}
}
