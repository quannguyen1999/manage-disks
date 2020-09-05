package application.controller;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class DialogBox  implements Initializable{

	private double xOffset=0;

	private double YOffset=0;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	//text to set for label, btn to declined user click windows first
	public void Error(String text,JFXButton btnLogin) throws IOException {
		FXMLLoader loader= new FXMLLoader(getClass().getResource("../fxml/Error.fxml"));

		Parent root=loader.load();

		Error ctlMain=loader.getController();

		ctlMain.lblError.setText(text);

		loadFXML(root,btnLogin);
	}

	//text to set for label
	public void Success(String text,JFXButton btnLogin) throws IOException {
		FXMLLoader loader= new FXMLLoader(getClass().getResource("../fxml/Success.fxml"));

		Parent root=loader.load();

		Success ctlMain=loader.getController();

		ctlMain.lblSuccess.setText(text);

		loadFXML(root,btnLogin);
	}

	public void Help(JFXButton btnLogin) throws IOException {

		FXMLLoader loader= new FXMLLoader(getClass().getResource("../fxml/Help.fxml"));

		Parent root=loader.load();

		loadFXML(root,btnLogin);

	}
	
	public void changePassword(JFXButton btn) throws IOException{
		
		FXMLLoader loader= new FXMLLoader(getClass().getResource("../fxml/FormChangePassword.fxml"));

		Parent root=loader.load();

		loadFXML(root,btn);
		
	}

	//load file FXML
	public void loadFXML(Parent root,JFXButton btn) {
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

		stage.initModality(Modality.APPLICATION_MODAL);

		stage.show();
	}

	public ImageView getImageView(String nameIcon){

		File currentDirFile = new File("");

		String helper = currentDirFile.getAbsolutePath();

		URL url=null;
		try {

			url = new URL("file:////"+helper+"/src/application/image/"+nameIcon);

		} catch (MalformedURLException e) {

			System.out.println("FIle not found");

			e.printStackTrace();

		}

		Image imgUser = new Image("file:"+url.getFile());

		ImageView imaViewUser=new ImageView(imgUser);

		imaViewUser.setFitHeight(30);

		imaViewUser.setFitWidth(30);

		return imaViewUser;
	}

}
