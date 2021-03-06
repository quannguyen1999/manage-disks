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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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

	//	@FXML JFXButton btnStatistical;
	@FXML JFXButton btnReportKh;
	@FXML JFXButton btnReportTitle;


	@FXML JFXButton btnLogOut;

	@FXML JFXButton btnLateFee;

	//	@FXML JFXButton btnUser;

	@FXML JFXButton btnHelp;

	@FXML JFXButton btnChangePass;

	@FXML JFXButton btnCloseWindow;

	@FXML JFXButton btnMinium;

	@FXML BorderPane bd;

	private CustomerService customerService=new CustomerImpl();

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		customerService.listCustomer();

		//set icon for button
		File currentDirFile = new File("");

		String helper = currentDirFile.getAbsolutePath();

		URL url=null;
		try {

			url = new URL("file:////"+helper+"/src/application/image/"+"Logout.png");

		} catch (MalformedURLException e) {

			e.printStackTrace();

		}

		Image imgUser = new Image("file:"+url.getFile());

		ImageView imaViewUser=new ImageView(imgUser);

		imaViewUser.setFitHeight(50);

		imaViewUser.setFitWidth(50);
		
		btnLogOut.setGraphic(imaViewUser);



		btnHelp.setGraphic(getImageView("IconHelp.png"));
		btnChangePass.setGraphic(getImageView("IconPassworReset.png"));
		btnCustomer.setGraphic(getImageView("customers.png"));
		btnProduct.setGraphic(getImageView("product.png"));
		btnCategory.setGraphic(getImageView("category.png"));
		btnBill.setGraphic(getImageView("bill.png"));
		btnSupplier.setGraphic(getImageView("supplier.png"));
		btnTitle.setGraphic(getImageView("Mtitle.png"));
		btnOrder.setGraphic(getImageView("order.png"));
		btnReportKh.setGraphic(getImageView("statistical.png"));
		btnReportTitle.setGraphic(getImageView("statistical.png"));
		btnLateFee.setGraphic(getImageView("lateFee.png"));


		btnMinium.setGraphic(getImageView("MiniumWindows.png"));
		btnCloseWindow.setGraphic(getImageView("CLoseWindows.png"));

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

	public void btnCLickManageLateFee(ActionEvent e) throws IOException {
		changeCssManage(btnLateFee);

		Parent root=(Parent) FXMLLoader.load(getClass().getResource(loadManageLateFee));

		bd.setCenter(root);
	}

	public void btnClickReportCustomer(ActionEvent e) throws IOException {
		changeCssManage(btnReportKh);

		Parent root=(Parent) FXMLLoader.load(getClass().getResource(loadManageReportCustomer));

		bd.setCenter(root);
	}

	public void btnClickReportTitle(ActionEvent e) throws IOException {
		changeCssManage(btnReportTitle);

		Parent root=(Parent) FXMLLoader.load(getClass().getResource(loadManageReportTitle));

		bd.setCenter(root);
	}

	public void btnCLickManageOrder(ActionEvent e) throws IOException {

		changeCssManage(btnOrder);

		Parent root=(Parent) FXMLLoader.load(getClass().getResource(loadManageOrder));

		bd.setCenter(root);
	}

	public void btnCLickManageStastical(ActionEvent e) throws IOException {
	}

	public void handleKeyPress(KeyEvent e) throws IOException {
		if(e.getCode()==KeyCode.F1) {

			changeCssManage(btnCustomer);

			Parent root=(Parent) FXMLLoader.load(getClass().getResource(loadManageCustomer));

			bd.setCenter(root);

			root.setFocusTraversable(true);

		}else if(e.getCode()==KeyCode.F2) {
			changeCssManage(btnProduct);

			Parent root=(Parent) FXMLLoader.load(getClass().getResource(loadManageProduct));

			bd.setCenter(root);
			root.setFocusTraversable(true);
		}else if(e.getCode()==KeyCode.F3) {
			changeCssManage(btnCategory);

			Parent root=(Parent) FXMLLoader.load(getClass().getResource(loadManageCategories));

			bd.setCenter(root);
		}else if(e.getCode()==KeyCode.F4) {
			changeCssManage(btnBill);

			Parent root=(Parent) FXMLLoader.load(getClass().getResource(loadManageBill));

			bd.setCenter(root);
		}else if(e.getCode()==KeyCode.F5) {
			changeCssManage(btnLateFee);

			Parent root=(Parent) FXMLLoader.load(getClass().getResource(loadManageLateFee));

			bd.setCenter(root);
		}else if(e.getCode()==KeyCode.F6) {
			changeCssManage(btnSupplier);

			Parent root=(Parent) FXMLLoader.load(getClass().getResource(loadManageSupplier));

			bd.setCenter(root);
		}else if(e.getCode()==KeyCode.F7) {
			changeCssManage(btnReportTitle);

			Parent root=(Parent) FXMLLoader.load(getClass().getResource(loadManageReportTitle));

			bd.setCenter(root);
		}else if(e.getCode()==KeyCode.F8) {
			changeCssManage(btnOrder);

			Parent root=(Parent) FXMLLoader.load(getClass().getResource(loadManageOrder));

			bd.setCenter(root);
		}else if(e.getCode()==KeyCode.F9) {
			changeCssManage(btnReportKh);

			Parent root=(Parent) FXMLLoader.load(getClass().getResource(loadManageReportCustomer));

			bd.setCenter(root);
		}else if(e.getCode()==KeyCode.F10) {
			changeCssManage(btnReportTitle);

			Parent root=(Parent) FXMLLoader.load(getClass().getResource(loadManageReportTitle));

			bd.setCenter(root);
		}
	}



	//change css manage
	public void changeCssManage(JFXButton btn) {

		refreshBtnColor();

		btn.setStyle("-fx-background-color:#FF2926");

	}

	//refresh color btn manage to black
	public void refreshBtnColor() {
		btnCustomer.setStyle("-fx-background-color:black");//.setStyle("-fx-background-color:white,-fx-border-width: 0px 0px 2px 0px,-fx-border-color:gray");

		btnLateFee.setStyle("-fx-background-color:black");//.setStyle("-fx-background-color:white,-fx-border-width: 0px 0px 2px 0px,-fx-border-color:gray");

		btnProduct.setStyle("-fx-background-color:black");//.setStyle("-fx-background-color:white,-fx-border-width: 0px 0px 2px 0px,-fx-border-color:gray");

		btnCategory.setStyle("-fx-background-color:black");//.setStyle("-fx-background-color:white,-fx-border-width: 0px 0px 2px 0px,-fx-border-color:gray");

		btnBill.setStyle("-fx-background-color:black");//.setStyle("-fx-background-color:white,-fx-border-width: 0px 0px 2px 0px,-fx-border-color:gray");

		btnSupplier.setStyle("-fx-background-color:black");//.setStyle("-fx-background-color:white,-fx-border-width: 0px 0px 2px 0px,-fx-border-color:gray");

		btnTitle.setStyle("-fx-background-color:black");//.setStyle("-fx-background-color:white,-fx-border-width: 0px 0px 2px 0px,-fx-border-color:gray");

		btnOrder.setStyle("-fx-background-color:black");//.setStyle("-fx-background-color:white,-fx-border-width: 0px 0px 2px 0px,-fx-border-color:gray");

		btnReportKh.setStyle("-fx-background-color:black");//.setStyle("-fx-background-color:white,-fx-border-width: 0px 0px 2px 0px,-fx-border-color:gray");

		btnReportTitle.setStyle("-fx-background-color:black");//.setStyle("-fx-background-color:white,-fx-border-width: 0px 0px 2px 0px,-fx-border-color:gray");

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
