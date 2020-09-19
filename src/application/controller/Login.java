package application.controller;

import java.io.IOException;
import java.net.URL;
import java.util.EventObject;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXButton;

import javafx.animation.TranslateTransition;
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
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class Login extends DialogBox implements Initializable {

	@FXML StackPane pnlSign;
	@FXML Pane pnlSignIn;
	@FXML Pane pnlSignIn2;
	@FXML Pane pnlSignOut;
	@FXML Pane pnlSignOut2;
	@FXML StackPane pnlFill;
	
	@FXML TextField txtAcc;

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

		}else {
			
			Error("username don't exists", btnLogin);
			
		}

	}
	
	public void enter(ActionEvent e) throws IOException {
		loadInterfaceEmploy(btnLogin,e);
	}
	
	private boolean handleFieldLogin() throws IOException {
		
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
	
	public void clickSignOut(ActionEvent e) {
		pnlSignOut.toFront();
		pnlSignOut2.toFront();
		
		TranslateTransition slide=new TranslateTransition();
		slide.setAutoReverse(true);
		slide.setDuration(Duration.seconds(0.7));
		slide.setNode(pnlFill);
		slide.setToX(-644);
		slide.play();

		TranslateTransition slide2=new TranslateTransition();
		slide2.setAutoReverse(true);
		slide2.setDuration(Duration.seconds(0.7));
		slide2.setNode(pnlSign);
		slide2.setToX(322);
		slide2.play();
		
		
	}
	
	public void clickSignIn(ActionEvent e) {
		pnlSignIn.toFront();
		pnlSignIn2.toFront();
		
		TranslateTransition slide=new TranslateTransition();
		slide.setAutoReverse(true);
		slide.setDuration(Duration.seconds(0.7));
		slide.setNode(pnlFill);
		slide.setToX(0);
		slide.play();

		TranslateTransition slide2=new TranslateTransition();
		slide2.setAutoReverse(true);
		slide2.setDuration(Duration.seconds(0.7));
		slide2.setNode(pnlSign);
		slide2.setToX(0);
		slide2.play();

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
