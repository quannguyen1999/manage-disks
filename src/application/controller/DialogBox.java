package application.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import com.jfoenix.controls.JFXButton;

import animatefx.animation.FadeIn;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sun.misc.BASE64Decoder;

public class DialogBox  implements Initializable{
	
	static File currentDirFile = new File("");
	static String helper = currentDirFile.getAbsolutePath();
	
	static final String loadFormAddCustomer="../fxml/FormAddCustomer.fxml";
	
	static final String loadFormRentDisk="../fxml/FormRentDisk.fxml";
	
	static final String loadFormReturnDisk="../fxml/ReturnDisk.fxml";
	
	static final String loadFormAddCategory="../fxml/FormAddCategory.fxml";
	
	static final String loadFormAddSupplier="../fxml/FormAddSupplier.fxml";
	
	static final String loadFormAddTitle="../fxml/FormAddTitle.fxml";
	
	static final String loadFormAddProduct="../fxml/FormAddProduct.fxml";
	
	static final String loadFormAddBill="../fxml/FormAddBill.fxml";
	
	static final String loadFormPay="../fxml/FormPay.fxml";
	
	
	static final String loadWelcome="../fxml/Welcome.fxml";
	static final String loadManageCustomer="../fxml/ManageCustomer.fxml";
	static final String loadManageProduct="../fxml/ManageProduct.fxml";
	static final String loadManageTitle="../fxml/ManageTitle.fxml";
	static final String loadManageLateFee="../fxml/ManageLateFee.fxml";
	static final String loadManageCategories="../fxml/ManageCategory.fxml";
	static final String loadManageBill="../fxml/ManageBill.fxml";
	static final String loadManageSupplier="../fxml/ManageSupplier.fxml";
//	static final String loadManageOrder="../fxml/FormAddBill.fxml";
//	static final String loadManageStatistical="../fxml/FormAddBill.fxml";
	
	static final String loadError="../fxml/Error.fxml";
	
	static final String loadSuccess="../fxml/Success.fxml";
	
	static final String loadHelp="../fxml/Help.fxml";
	
	static final String loadChangePassword="../fxml/FormChangePassword.fxml";
	
	static final String loadAreYouSure="../fxml/AreYouSure.fxml";
	
	static final String loadLogin="file:////"+helper+"/src/application/fxml/LoginV2.fxml";
	
	static final String loadTitlePng="file:////"+helper+"/src/application/image/title.png";
	
	private double xOffset=0;

	private double YOffset=0;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}
	
	public WritableImage getImage(String base64String) throws IOException {
		BASE64Decoder decoder = new BASE64Decoder();

		byte[] decodedBytes = decoder.decodeBuffer(base64String);

		String uploadFile = "/tmp/test.png";

		BufferedImage image = ImageIO.read(new ByteArrayInputStream(decodedBytes));
		if (image == null) {

		}
		// write the image
		return SwingFXUtils.toFXImage(image, null);
	}
	
	public int ranDomNumber() {
		  int min = 0;
	      int max = 10000;
	      System.out.println("Random value in int from "+min+" to "+max+ ":");
	      int random_int = (int)(Math.random() * (max - min + 1) + min);
	      return random_int;
	}

	//text to set for label, btn to declined user click windows first
	public void Error(String text,JFXButton btnLogin) throws IOException {
		FXMLLoader loader= new FXMLLoader(getClass().getResource(loadError));

		Parent root=loader.load();

		Error ctlMain=loader.getController();

		ctlMain.lblError.setText(text);

		loadFXML(root,btnLogin);
	}

	//text to set for label
	public void Success(String text,JFXButton btnLogin) throws IOException {
		FXMLLoader loader= new FXMLLoader(getClass().getResource(loadSuccess));

		Parent root=loader.load();

		Success ctlMain=loader.getController();

		ctlMain.lblSuccess.setText(text);

		loadFXML(root,btnLogin);
	}

	public void Help(JFXButton btnLogin) throws IOException {

		FXMLLoader loader= new FXMLLoader(getClass().getResource(loadHelp));

		Parent root=loader.load();

		loadFXML(root,btnLogin);

	}

	public void changePassword(JFXButton btn) throws IOException{

		FXMLLoader loader= new FXMLLoader(getClass().getResource(loadChangePassword));

		Parent root=loader.load();

		loadFXML(root,btn);

	}
	

	public void areYouSure(JFXButton btn) throws IOException{
		FXMLLoader loader= new FXMLLoader(getClass().getResource(loadAreYouSure));
		
		Parent root=loader.load();
		
		AreYouSure ctlMain=loader.getController();
		
		new animatefx.animation.FadeIn(root).play();
		
		Stage stage=new Stage();
		
		stage.initOwner(btn.getScene().getWindow());
		
		stage.setScene(new Scene(root));
		
		stage.initStyle(StageStyle.UNDECORATED);
		
		stage.initModality(Modality.APPLICATION_MODAL);
		
		stage.show();
		
		stage.setOnHidden(efg->{
		
			if(ctlMain.result==true) {
			
				System.exit(0);
			
			}else {

			}
		});
	}
	

	//load file FXML
	public Stage loadFXML(Parent root,JFXButton btn) {
		Stage stage=new Stage();

		root.setOnMousePressed((MouseEvent event)->{
			xOffset=event.getSceneX();
			YOffset=event.getSceneY();
		});
		root.setOnMouseDragged((MouseEvent event)->{
			stage.setX(event.getScreenX()-xOffset);
			stage.setY(event.getScreenY()-YOffset);
		});

		stage.initOwner(btn.getScene().getWindow());

		new animatefx.animation.FadeIn(root).play();

		stage.setScene(new Scene(root));

		stage.initStyle(StageStyle.UNDECORATED);
		
		stage.getIcons().add(new Image(loadTitlePng));

		stage.initModality(Modality.APPLICATION_MODAL);

		stage.show();
		
		return stage;
	}

	//load image
	public ImageView getImageView(String nameIcon){

		File currentDirFile = new File("");

		String helper = currentDirFile.getAbsolutePath();

		URL url=null;
		try {

			url = new URL("file:////"+helper+"/src/application/image/"+nameIcon);

		} catch (MalformedURLException e) {

			e.printStackTrace();

		}

		Image imgUser = new Image("file:"+url.getFile());

		ImageView imaViewUser=new ImageView(imgUser);

		imaViewUser.setFitHeight(30);

		imaViewUser.setFitWidth(30);

		return imaViewUser;
	}

	public void logOut(JFXButton btn,ActionEvent e) throws MalformedURLException, IOException {
		
		Parent root;
		
		((Node)(e.getSource())).getScene().getWindow().hide(); 
		
		root=(Parent) FXMLLoader.load(new URL(loadLogin));
		
		loadFXML(root,btn);
		
	}

}
