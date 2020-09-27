package application.controller;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import animatefx.animation.BounceInDown;
import animatefx.animation.FadeIn;
import application.controller.impl.CustomerImpl;
import application.controller.services.CustomerService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class FormAdmin extends DialogBox implements Initializable{

	//JFXButton
	@FXML JFXButton btnCustomer;
	
	@FXML JFXButton btnProduct;
	
	@FXML JFXButton btnCategory;
	
	@FXML JFXButton btnBill;
	
	@FXML JFXButton btnSupplier;
	
	@FXML JFXButton btnTitle;
	
	@FXML JFXButton btnOrder;
	
	@FXML JFXButton btnStatistical;
	
	@FXML JFXButton btnLogOut;

	@FXML JFXButton btnUser;

	@FXML JFXButton btnHelp;

	@FXML JFXButton btnChangePass;

	@FXML BorderPane bd;
	
	private CustomerService customerService=new CustomerImpl();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		customerService.listCustomer();
		
		//set icon for button
		btnLogOut.setGraphic(getImageView("Logout.png"));
		btnUser.setGraphic(getImageView("IconUser.png"));
		btnHelp.setGraphic(getImageView("IconHelp.png"));
		btnChangePass.setGraphic(getImageView("IconPassworReset.png"));

		//init color
		Parent root=null;
		try {
			root = (Parent) FXMLLoader.load(getClass().getResource(loadWelcome));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		bd.setCenter(root);
	}

	public void btnClickChangePassword(ActionEvent e) throws IOException {

		changePassword(btnHelp);

	}

	public void btnClickCustomer(ActionEvent e) throws IOException {

		changeCssManage(btnCustomer);

		Parent root=(Parent) FXMLLoader.load(getClass().getResource(loadManageCustomer));

		bd.setCenter(root);

	}
	
	public void btnClickManageProducts(ActionEvent e) throws IOException {

		changeCssManage(btnProduct);

		Parent root=(Parent) FXMLLoader.load(getClass().getResource(loadManageProduct));

		bd.setCenter(root);

	}
	
	public void btnCLickManageCategories(ActionEvent e) throws IOException {
		changeCssManage(btnCategory);

		Parent root=(Parent) FXMLLoader.load(getClass().getResource(loadManageCategories));

		bd.setCenter(root);
	}
	
	public void btnCLickManageBill(ActionEvent e) throws IOException {
		changeCssManage(btnBill);

		Parent root=(Parent) FXMLLoader.load(getClass().getResource(loadManageBill));

		bd.setCenter(root);
	}
	
	public void btnCLickManageSupplier(ActionEvent e) throws IOException {
		changeCssManage(btnSupplier);

		Parent root=(Parent) FXMLLoader.load(getClass().getResource(loadManageSupplier));

		bd.setCenter(root);
	}
	
	public void btnCLickManageTitle(ActionEvent e) throws IOException {
		changeCssManage(btnTitle);

		Parent root=(Parent) FXMLLoader.load(getClass().getResource(loadManageTitle));

		bd.setCenter(root);
	}
	
	public void btnCLickManageOrder(ActionEvent e) throws IOException {
//		changeCssManage(btnOrder);
//
//		Parent root=(Parent) FXMLLoader.load(getClass().getResource(loadWelcome));
//
//		bd.setCenter(root);
	}
	
	public void btnCLickManageStastical(ActionEvent e) throws IOException {
//		changeCssManage(btnStatistical);
//
//		Parent root=(Parent) FXMLLoader.load(getClass().getResource(loadWelcome));
//
//		bd.setCenter(root);
	}
	
	

	//change css manage
	public void changeCssManage(JFXButton btn) {

		refreshBtnColor();

		btn.setStyle("-fx-background-color:#FF2926");

	}

	//refresh color btn manage to black
	public void refreshBtnColor() {

		 btnCustomer.setStyle("-fx-background-color:black");//.setStyle("-fx-background-color:white,-fx-border-width: 0px 0px 2px 0px,-fx-border-color:gray");
			
		 btnProduct.setStyle("-fx-background-color:black");//.setStyle("-fx-background-color:white,-fx-border-width: 0px 0px 2px 0px,-fx-border-color:gray");
		
		 btnCategory.setStyle("-fx-background-color:black");//.setStyle("-fx-background-color:white,-fx-border-width: 0px 0px 2px 0px,-fx-border-color:gray");
		
		 btnBill.setStyle("-fx-background-color:black");//.setStyle("-fx-background-color:white,-fx-border-width: 0px 0px 2px 0px,-fx-border-color:gray");
		
		 btnSupplier.setStyle("-fx-background-color:black");//.setStyle("-fx-background-color:white,-fx-border-width: 0px 0px 2px 0px,-fx-border-color:gray");
		
		 btnTitle.setStyle("-fx-background-color:black");//.setStyle("-fx-background-color:white,-fx-border-width: 0px 0px 2px 0px,-fx-border-color:gray");
		
		 btnOrder.setStyle("-fx-background-color:black");//.setStyle("-fx-background-color:white,-fx-border-width: 0px 0px 2px 0px,-fx-border-color:gray");
		
		 btnStatistical.setStyle("-fx-background-color:black");//.setStyle("-fx-background-color:white,-fx-border-width: 0px 0px 2px 0px,-fx-border-color:gray");

	}

	public void btnExit(ActionEvent e) throws IOException {

		areYouSure(btnLogOut);

	}

	public void clickBtnHelp(ActionEvent e) throws IOException {

		Help(btnCustomer);

	}

	public void btnHideWindow(ActionEvent e) {

		Stage stage=(Stage) ((Node)(e.getSource())).getScene().getWindow();  

		stage.setIconified(true);

	}

	@FXML
	private void btnLogOut(ActionEvent e) throws MalformedURLException, IOException {

		logOut(btnLogOut, e);

	}
}
