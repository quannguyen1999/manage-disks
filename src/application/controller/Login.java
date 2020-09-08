package application.controller;

import java.io.IOException;
import java.net.URL;
import java.util.EventObject;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

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
	
	public void handleKeyEvents(KeyEvent e) throws IOException {
		if(e.getCode()==KeyCode.ENTER) {
			
			if(handleFieldLogin()==false) {
				return;
			};
			
			if(txtAcc.getText().toString().equals("admin") && txtPass.getText().toString().equals("123")) {

				loadInterfaceAdmin(btnLogin,e);

			}else if(txtAcc.getText().toString().equals("employ") && txtPass.getText().toString().equals("123")){
				
				loadInterfaceEmploy(btnLogin,e);
				
			}else {
				
				Error("username don't exists", btnLogin);
				
			}
			
		}
	}

	public void login(ActionEvent e) throws IOException {

		if(handleFieldLogin()==false) {
			return;
		};

		if(txtAcc.getText().toString().equals("admin") && txtPass.getText().toString().equals("123")) {

			loadInterfaceAdmin(btnLogin,e);

		}else if(txtAcc.getText().toString().equals("employ") && txtPass.getText().toString().equals("123")){
			
			loadInterfaceEmploy(btnLogin,e);
			
		}else {
			
			Error("username don't exists", btnLogin);
			
		}

	}
	
	public boolean handleFieldLogin() throws IOException {
		
		if(txtAcc.getText().trim().isEmpty()==true) {

			System.out.println("Please enter username");

			Error("Please enter username",btnLogin);

			return false;
		}


		if(txtPass.getText().trim().isEmpty()==true) {

			System.out.println("Please enter pass");

			Error("Please enter username",btnLogin);

			return false;
		}
		
		return true;
		
	}
	
	
	public <T> void loadInterfaceAdmin(JFXButton btn,T e) throws IOException{

		((Node)(((EventObject) e).getSource())).getScene().getWindow().hide(); 
		
		FXMLLoader loader= new FXMLLoader(getClass().getResource("../fxml/FormAdmin.fxml"));

		Parent root=loader.load();

		loadFXML(root,btn);

	}
	
	public <T> void loadInterfaceEmploy(JFXButton btn,T e) throws IOException{

		((Node)(((EventObject) e).getSource())).getScene().getWindow().hide(); 
		
		FXMLLoader loader= new FXMLLoader(getClass().getResource("../fxml/FormEmployee.fxml"));

		Parent root=loader.load();

		loadFXML(root,btn);

	}

}
