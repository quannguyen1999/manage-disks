package application.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class Login extends DialogBox implements Initializable {

	@FXML 
	TextField txtAcc;

	@FXML PasswordField txtPass;

	@FXML JFXButton btnLogin;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}

	public void btnExit(ActionEvent e) {
		System.exit(0);
	}

	public void login(ActionEvent e) throws IOException {

		boolean resultTxtAcc=true;

		boolean resultPass=true;

		if(txtAcc.getText().trim().isEmpty()==true) {

			System.out.println("Please enter username");
			
			resultTxtAcc=false;
			
			Error("Please enter username",btnLogin);
			
			return;
		}

		if(resultTxtAcc==true) {



		}

		if(txtPass.getText().trim().isEmpty()==true) {

			System.out.println("Please enter pass");
			
			resultPass=false;
		
			Error("Please enter username",btnLogin);
			
			return;
		}

		if(resultPass==true) {


		}


		if(resultTxtAcc==true && resultPass==true) {

			if(txtPass.getText().toString().equals("123")) {

				Success("success",btnLogin);

			}else {
				
				Error("Password not correct",btnLogin);
				
			}

		}

	}
	
}
