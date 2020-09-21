package application;
	
import java.io.File;

import animatefx.animation.FadeInUpBig;
import application.controller.DAO.Connect;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;


public class Main extends Application {
	//pull your mouse to your location x, y
	private double xOffset=0;
	
	private double YOffset=0;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			//load file fxml
			Parent root=(Parent) FXMLLoader.load(getClass().getResource("fxml/LoginV2.fxml"));
			
			//set move mouse
			root.setOnMousePressed((MouseEvent event)->{
				xOffset=event.getSceneX();
				YOffset=event.getSceneY();
			});
			
			root.setOnMouseDragged((MouseEvent event)->{
				primaryStage.setX(event.getScreenX()-xOffset);
				primaryStage.setY(event.getScreenY()-YOffset);
			});
			
			//set shadow for windows
			root.setEffect(new DropShadow(10, Color.PURPLE));
			
			Scene scene = new Scene(root);
			
			//set transparent for windows
			scene.setFill(Color.TRANSPARENT);
			
			
			primaryStage.setScene(scene);
			
			//animateFX
			new FadeInUpBig(root).play();
			
			primaryStage.setResizable(true);
			
			new animatefx.animation.FadeIn(root).play();
			
			//delete toolbar
			primaryStage.initStyle(StageStyle.TRANSPARENT);
			
			File currentDirFile = new File("");

			String helper = currentDirFile.getAbsolutePath();
			
			primaryStage.getIcons().add(new Image("file:////"+helper+"/src/application/image/title.png"));
			
			primaryStage.show();
		
		} catch(Exception e) {
			
			e.printStackTrace();
			
		}
	}

	public static void main(String[] args) {
		
		launch(args);
		
	}
}
