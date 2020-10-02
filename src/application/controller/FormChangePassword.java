package application.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.PasswordField;

public class FormChangePassword extends DialogBox implements Initializable{

	@FXML PasswordField txtPassOld;


	@FXML PasswordField txtPassNew;

	@FXML PasswordField txtPassNewAgin;

	@FXML JFXButton btnE;
	
	@FXML JFXButton btnExit;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		btnExit.setGraphic(getImageView("CLoseWindows.png"));

	}

	public void btnExit(ActionEvent e) {

		((Node)(e.getSource())).getScene().getWindow().hide(); 

	}

	public void btnRemoveField(ActionEvent e) {

		txtPassOld.setText("");

		txtPassNewAgin.setText("");

		txtPassNew.setText("");

	}
	
	public void btnOK(ActionEvent e) throws IOException {
		if(txtPassOld.getText().isEmpty()==true) {

			System.out.println("please insert passold");

			Error("please insert pass old",btnE);

			return;
		}


		if(txtPassNew.getText().isEmpty()==true) {

			System.out.println("please insert passold");

			Error("please insert pass new",btnE);
			
			return;

		}
		
		if(txtPassNewAgin.getText().isEmpty()==true) {
			
			System.out.println("please insert pass new again");
			
			Error("please insert pass new again",btnE);
			
			return;
			
		}
		if(txtPassNew.getText().equals(txtPassNewAgin.getText())==false) {
			
			System.out.println("passNew not equal passNewAgain");
			
			Error("passNew not equal passNewAgain",btnE);
			
			return;
			
		}
		
		Success("success",btnE);
	}

}
