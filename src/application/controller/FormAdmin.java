package application.controller;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import animatefx.animation.BounceInDown;
import animatefx.animation.FadeIn;
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
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class FormAdmin extends DialogBox implements Initializable{

	@FXML JFXButton btnAccount;
	
	@FXML JFXButton btnCategory;

	@FXML JFXButton btnLogOut;

	@FXML JFXButton btnUser;

	@FXML JFXButton btnHelp;
	
	@FXML JFXButton btnChangePass;

	@FXML JFXButton btnProduct;

	@FXML Pane pnlCategory;

	@FXML Pane pnlProduct;

	@FXML Pane pnlAccounts;

	@FXML Pane pblAddCategoryGame;

	@FXML Label lblAccount;
	
	@FXML Label lblProduct;
	
	@FXML Label lblCateGory;

	private double xOffset=0;

	private double YOffset=0;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//set icon for button
		btnLogOut.setGraphic(getImageView("Logout.png"));
		btnUser.setGraphic(getImageView("IconUser.png"));
		btnHelp.setGraphic(getImageView("IconHelp.png"));
		btnChangePass.setGraphic(getImageView("IconPassworReset.png"));

		//init color
		btnProduct.setStyle("-fx-background-color:#FF8619");

	}

	public void btnClickChangePassword(ActionEvent e) throws IOException {
		
		changePassword(btnAccount);
		
	}
	
	public void btnClickManageAccount(ActionEvent e) {

		refreshBtnColor();

		btnAccount.setStyle("-fx-background-color:#FF2926");

		new BounceInDown(lblAccount).play();

		pnlAccounts.toFront();
	}

	public void btnClickManageProducts(ActionEvent e) {

		refreshBtnColor();

		btnProduct.setStyle("-fx-background-color:#FF2926");

		new BounceInDown(lblProduct).play();

		pnlProduct.toFront();

	}
	
	public void btnCLickManageCategories(ActionEvent e) {

		refreshBtnColor();

		btnCategory.setStyle("-fx-background-color:#FF2926");

		new BounceInDown(lblCateGory).play();

		pnlCategory.toFront();

	}

	//refresh color btn manage to black
	public void refreshBtnColor() {

		btnAccount.setStyle("-fx-background-color:black");

		btnProduct.setStyle("-fx-background-color:black");

		btnCategory.setStyle("-fx-background-color:black");
		
	}

	public void btnExit(ActionEvent e) throws IOException {

		System.exit(0);

	}
	
	public void clickBtnHelp(ActionEvent e) throws IOException {

		Help(btnAccount);

	}
	
	

	public void btnHideWindow(ActionEvent e) {

		Stage stage=(Stage) ((Node)(e.getSource())).getScene().getWindow();  

		stage.setIconified(true);

	}

	@FXML
	private void btnLogOut(ActionEvent e) throws MalformedURLException, IOException {
		
	}
}
