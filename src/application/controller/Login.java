package application.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
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

		if(txtAcc.getText().trim().isEmpty()==true) {

			System.out.println("Please enter username");

			Error("Please enter username",btnLogin);

			return;
		}


		if(txtPass.getText().trim().isEmpty()==true) {

			System.out.println("Please enter pass");

			Error("Please enter username",btnLogin);

			return;
		}

		if(txtAcc.getText().toString().equals("admin") && txtPass.getText().toString().equals("123")) {

			loadInterfaceAdmin(btnLogin,e);

		}else if(txtAcc.getText().toString().equals("employ") && txtPass.getText().toString().equals("123")){
			
			loadInterfaceEmploy(btnLogin,e);
			
		}else {
			
			Error("username don't exists", btnLogin);
			
		}

	}
	
	public void loadInterfaceAdmin(JFXButton btn,ActionEvent e) throws IOException{

		((Node)(e.getSource())).getScene().getWindow().hide(); 
		
		FXMLLoader loader= new FXMLLoader(getClass().getResource("../fxml/FormAdmin.fxml"));

		Parent root=loader.load();

		loadFXML(root,btn);

	}
	
	public void loadInterfaceEmploy(JFXButton btn,ActionEvent e) throws IOException{

		((Node)(e.getSource())).getScene().getWindow().hide(); 
		
		FXMLLoader loader= new FXMLLoader(getClass().getResource("../fxml/FormEmployee.fxml"));

		Parent root=loader.load();

		loadFXML(root,btn);

	}

}
