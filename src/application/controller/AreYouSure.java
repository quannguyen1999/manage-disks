package application.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;

public class AreYouSure implements Initializable{
	boolean result=true;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
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
