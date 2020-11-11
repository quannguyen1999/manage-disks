package application.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import org.mindrot.jbcrypt.BCrypt;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.PasswordField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class FormChangePassword extends DialogBox implements Initializable{

	@FXML PasswordField txtPassOld;


	@FXML PasswordField txtPassNew;

	@FXML PasswordField txtPassNewAgin;

	@FXML JFXButton btnE;

	@FXML JFXButton btnExit;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		btnExit.setGraphic(getImageView("CLoseWindows.png"));

	}

	public void btnExit(ActionEvent e) {

		((Node)(e.getSource())).getScene().getWindow().hide(); 

	}

	public void btnRemoveField(ActionEvent e) {

		txtPassOld.setText("");

		txtPassNewAgin.setText("");

		txtPassNew.setText("");

	}
	
	public void handleKeyEvents(KeyEvent e) throws IOException {
		if(e.getCode()==KeyCode.ENTER) {
			checkButtonOrEnter();
		}
	}

	public void btnOK(ActionEvent e) throws IOException {
		checkButtonOrEnter();
	}
	
	public void checkButtonOrEnter() throws IOException {
		if(txtPassOld.getText().isEmpty()==true) {

			System.out.println("please insert passold");

			Error("please insert pass old",btnE);
			
			txtPassOld.requestFocus();

			return;
		}


		if(txtPassNew.getText().isEmpty()==true) {

			System.out.println("please insert passold");

			Error("please insert pass new",btnE);
			
			txtPassNew.requestFocus();

			return;

		}

		if(txtPassNewAgin.getText().isEmpty()==true) {

			System.out.println("please insert pass new again");

			Error("please insert pass new again",btnE);
			
			txtPassNewAgin.requestFocus();

			return;

		}
		if(txtPassNew.getText().equals(txtPassNewAgin.getText())==false) {

			System.out.println("passNew not equal passNewAgain");

			Error("passNew not equal passNewAgain",btnE);
			
			txtPassNew.requestFocus();

			return;

		}
		
		if(checkPassword(txtPassOld.getText().toString())==false){
			
			System.out.println("mật khẩu không đúng");

			Error("mật khẩu không đúng",btnE);
			
			txtPassOld.requestFocus();

			return;
		}
		
		
		savePassword(txtPassNewAgin.getText().toString());

		txtPassOld.setText("");

		txtPassNewAgin.setText("");

		txtPassNew.setText("");
		
		Success("success",btnE);
	}

	public boolean checkPassword(String password) {
		String line = "";
		try {
			FileReader reader = new FileReader("MyFile.txt");
			BufferedReader bufferedReader = new BufferedReader(reader);
			line = bufferedReader.readLine();
			reader.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return BCrypt.checkpw(password, line);
	}

	public void savePassword(String savePassword) throws IOException {
		FileWriter writer = new FileWriter("MyFile.txt", false);
		String hash = BCrypt.hashpw(savePassword, BCrypt.gensalt(12));
		writer.write(hash);
		writer.close();
	}

}
