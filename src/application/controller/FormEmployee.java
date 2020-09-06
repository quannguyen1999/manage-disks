package application.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import animatefx.animation.BounceInDown;
import animatefx.animation.BounceInLeft;
import animatefx.animation.FadeInRight;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class FormEmployee extends DialogBox implements Initializable{
	@FXML JFXButton btnLogOut;

	@FXML JFXButton btnUser;

	@FXML JFXButton btnHelp;

	@FXML JFXButton btnChangePass;

	@FXML Pane pnlDiscs;

	@FXML Pane pnlCustomer;

	@FXML Label lblCustomer;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//set icon for button
		btnLogOut.setGraphic(getImageView("Logout.png"));
		btnUser.setGraphic(getImageView("IconUser.png"));
		btnHelp.setGraphic(getImageView("IconHelp.png"));
		btnChangePass.setGraphic(getImageView("IconPassworReset.png"));

	}

	public void btnClickChangePassword(ActionEvent e) throws IOException {

		changePassword(btnLogOut);

	}

	public void btnExit(ActionEvent e) throws IOException {

		System.exit(0);

	}

	public void clickBtnHelp(ActionEvent e) throws IOException {

		Help(btnLogOut);

	}

	public void btnHideWindow(ActionEvent e) {

		Stage stage=(Stage) ((Node)(e.getSource())).getScene().getWindow();  

		stage.setIconified(true);

	}

	@FXML
	private void btnLogOut(ActionEvent e) throws MalformedURLException, IOException {

		logOut(btnLogOut,e);
		
	}

	public void btnCLickDiscs(ActionEvent e) {

		new BounceInLeft(pnlCustomer).play();

		pnlCustomer.toFront();

	}

	public void btnClickCustomer(ActionEvent e) {
		
		new BounceInDown(lblCustomer).play();

		pnlDiscs.toFront();

	}





}
