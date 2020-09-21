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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class FormAdmin extends DialogBox implements Initializable{

	//JFXButton
	@FXML JFXButton btnAccount;

	@FXML JFXButton btnCategory;

	@FXML JFXButton btnLogOut;

	@FXML JFXButton btnUser;

	@FXML JFXButton btnHelp;

	@FXML JFXButton btnChangePass;

	@FXML JFXButton btnProduct;

	@FXML JFXButton btnStatistical;

	@FXML JFXButton btnCustomer;

	@FXML BorderPane bd;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//set icon for button
		btnLogOut.setGraphic(getImageView("Logout.png"));
		btnUser.setGraphic(getImageView("IconUser.png"));
		btnHelp.setGraphic(getImageView("IconHelp.png"));
		btnChangePass.setGraphic(getImageView("IconPassworReset.png"));

		//init color
		Parent root=null;
		try {
			root = (Parent) FXMLLoader.load(getClass().getResource("../fxml/Welcome.fxml"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		bd.setCenter(root);
	}

	public void btnClickChangePassword(ActionEvent e) throws IOException {

		changePassword(btnAccount);

	}

	public void btnCLickManageStatistical(ActionEvent e) {

		//		changeCssManage(btnStatistical, lblStatisical, pnlStatisical);

	}

	public void btnClickManageAccount(ActionEvent e) {

		//		changeCssManage(btnAccount, lblAccount, pnlAccounts);

	}

	public void btnClickManageProducts(ActionEvent e) {

		//		changeCssManage(btnProduct, lblProduct, pnlProduct);

	}

	public void btnCLickManageCategories(ActionEvent e) {

		//		changeCssManage(btnCategory, lblCateGory, pnlCategory);

	}

	public void btnClickCustomer(ActionEvent e) throws IOException {

		changeCssManage(btnCustomer);

		Parent root=(Parent) FXMLLoader.load(getClass().getResource("../fxml/ManageCustomer.fxml"));

		bd.setCenter(root);

	}

	//change css manage
	public void changeCssManage(JFXButton btn) {

		refreshBtnColor();

		btn.setStyle("-fx-background-color:#FF2926");

	}

	//refresh color btn manage to black
	public void refreshBtnColor() {

		btnStatistical.setStyle("-fx-background-color:black");

		btnCustomer.setStyle("-fx-background-color:black");

		btnAccount.setStyle("-fx-background-color:black");

		btnProduct.setStyle("-fx-background-color:black");

		btnCategory.setStyle("-fx-background-color:black");

	}

	public void btnExit(ActionEvent e) throws IOException {

		areYouSure(btnLogOut);

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

		logOut(btnLogOut, e);

	}
}
