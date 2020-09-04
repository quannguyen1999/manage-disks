package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;


public class Main extends Application {
	private double xOffset=0;
	
	private double YOffset=0;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root=(Parent) FXMLLoader.load(getClass().getResource("fxml/Login.fxml"));
			
			root.setOnMousePressed((MouseEvent event)->{
				xOffset=event.getSceneX();
				YOffset=event.getSceneY();
			});
			root.setOnMouseDragged((MouseEvent event)->{
				primaryStage.setX(event.getScreenX()-xOffset);
				primaryStage.setY(event.getScreenY()-YOffset);
			});
			
			root.setEffect(new DropShadow(10, Color.PURPLE));
			
			Scene scene = new Scene(root);
			scene.setFill(Color.TRANSPARENT);
			primaryStage.setScene(scene);
//			new FadeInUp(root).play();
			primaryStage.setResizable(true);
//			new animatefx.animation.FadeIn(root).play();
			primaryStage.initStyle(StageStyle.TRANSPARENT);
			
			
			primaryStage.show();
		
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
