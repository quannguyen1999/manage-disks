package application.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import animatefx.animation.BounceInDown;
import animatefx.animation.BounceInLeft;
import animatefx.animation.FadeInRight;
import application.controller.impl.CustomerImpl;
import application.controller.impl.ProductImpl;
import application.controller.services.CustomerService;
import application.controller.services.ProductService;
import application.entities.Customer;
import application.entities.Product;
import application.entities.Title;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class FormEmployee extends DialogBox implements Initializable{
	
	@FXML JFXButton btnLogOut;

	@FXML JFXButton btnHelp;

	@FXML Pane pnlDiscs;

	@FXML Pane pnlCustomer;

	@FXML Label lblCustomer;
	
	@FXML FlowPane flowPane;
	
	@FXML JFXButton btnCustomer;
	
	@FXML JFXButton btnDisks;
	
	
	
	private TableView<Customer> tbl_view;

	TableColumn<Customer, String> colCustomerId;
	TableColumn<Customer, String> colName;
	TableColumn<Customer, String> colAddress;
	TableColumn<Customer, String> colPhone;
	TableColumn<Customer, LocalDate> colDateOfBirth;
	
	List<Customer> listCustomer=new ArrayList<>();

	
	@FXML BorderPane bd;
	
	private CustomerService customerService=new CustomerImpl();

	@FXML ComboBox<String> cbc=new ComboBox<String>();
	
	
	@FXML ComboBox<String> cbcPhone=new ComboBox<String>();
	
	
	
	private ProductService productService=new ProductImpl();
//	@FXML BorderPane bd;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//set icon for button
		btnLogOut.setGraphic(getImageView("Logout.png"));
		btnHelp.setGraphic(getImageView("IconHelp.png"));
		
		btnCustomer.setGraphic(getImageView("customers.png"));
		btnDisks.setGraphic(getImageView("product.png"));
		
		productService.listProduct().forEach(t->{
			
			try {
				btnClickAdd(new ActionEvent(),
						t);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			btnClickAdd(e, imageBig, name, quantity, status);
			
		});
		
		initTable();

		loadDataSearch();
		
		loadDataSearchPhone();

		cbc.setEditable(true);
		
		cbcPhone.setEditable(true);
		
		tbl_view.setOnMouseClicked(e->{
			if(e.getClickCount()==2) {
				int result=tbl_view.getSelectionModel().getSelectedIndex();
				if(result!=-1) {

					FXMLLoader loader= new FXMLLoader(getClass().getResource(loadFormAddCustomer));

					Parent root=null;
					try {
						root = loader.load();
					} catch (IOException e1) {
						e1.printStackTrace();
					}

					FormAddCustomer ctlMain=loader.getController();

					ctlMain.lblTitle.setText("Cập nhập khách hàng");

					ctlMain.txtMa.setText(tbl_view.getItems().get(result).getCustomerId());

					ctlMain.txtDiaChi.setText(tbl_view.getItems().get(result).getAddress());

					ctlMain.txtDienThoai.setText(tbl_view.getItems().get(result).getPhone());

					ctlMain.txtNgaySinh.setValue(tbl_view.getItems().get(result).getDateOfBirth());

					loadFXML(root,btnHelp).setOnHidden(ev->{

						handleRefersh(new ActionEvent());

					});;
				}
			}
		});
		
		

	}

	
	public void findItemInTable(ActionEvent e) throws IOException {
		String textFind=null;

		try {

			textFind=cbc.getSelectionModel().getSelectedItem().toString().trim();

		} catch (Exception e2) {

			Error("Bạn chưa nhập tìm kiếm", btnHelp);

			cbc.requestFocus();

		}

		if(textFind.isEmpty()) {

			Error("Bạn chưa nhập tìm kiếm", btnHelp);

			cbc.requestFocus();

			return;

		}

		tbl_view.getItems().clear();

		Customer customerFind=customerService.findCustomerById(textFind);

		if(customerFind==null) {

			Error("Không tìm thấy", btnHelp);

			cbc.requestFocus();

			return;

		}else {

			tbl_view.getItems().add(customerFind);

		}

	}
	
	public void findItemInTablePhone(ActionEvent e) throws IOException {
		String textFind=null;

		try {

			textFind=cbcPhone.getSelectionModel().getSelectedItem().toString().trim();

		} catch (Exception e2) {

			Error("Bạn chưa nhập tìm kiếm", btnHelp);

			cbcPhone.requestFocus();

		}

		if(textFind.isEmpty()) {

			Error("Bạn chưa nhập tìm kiếm", btnHelp);

			cbcPhone.requestFocus();

			return;

		}

//		tbl_view.getItems().clear();
//
//		Customer customerFind=customerService.findCustomerById(textFind);
//
//		if(customerFind==null) {
//
//			Error("Không tìm thấy", btnUser);
//
//			cbcPhone.requestFocus();
//
//			return;
//
//		}else {
//
//			tbl_view.getItems().add(customerFind);
//
//		}

	}
	private void loadDataSearch() {
		ObservableList<String> items = FXCollections.observableArrayList();
		List<Customer> accs=customerService.listCustomer();

		accs.forEach(t->{

			items.add(t.getCustomerId());

		});

		FilteredList<String> filteredItems = new FilteredList<String>(items);

		cbc.getEditor().textProperty().addListener(new InputFilter(cbc, filteredItems, false));

		cbc.setItems(filteredItems);

		cbc.setEditable(true);

	}
	
	private void loadDataSearchPhone() {
		ObservableList<String> items = FXCollections.observableArrayList();
		List<Customer> accs=customerService.listCustomer();

		accs.forEach(t->{

			items.add(t.getPhone());

		});

		FilteredList<String> filteredItems = new FilteredList<String>(items);

		cbcPhone.getEditor().textProperty().addListener(new InputFilter(cbcPhone, filteredItems, false));

		cbcPhone.setItems(filteredItems);

		cbcPhone.setEditable(true);

	}
	
	public void handleRefersh(ActionEvent e) {
		//		cbc.getItems().clear();
		cbc.setValue("");
		cbcPhone.setValue("");
		
		tbl_view.getItems().clear();
		uploadDuLieuLenBang();
	}
	
	public void initTable() {
		tbl_view=new TableView<Customer>();

		colCustomerId=new TableColumn<Customer, String>("mã");
		colName=new TableColumn<Customer, String>("Tên");
		colAddress=new TableColumn<Customer, String>("Địa chỉ");
		colPhone=new TableColumn<Customer, String>("Điện thoại");
		colDateOfBirth=new TableColumn<Customer, LocalDate>("ngày sinh");

		tbl_view.getColumns().addAll(colCustomerId,colName,colAddress,colPhone,colDateOfBirth);

		bd.setCenter(tbl_view);

		colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
		colName.setCellValueFactory(new PropertyValueFactory<>("name"));
		colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
		colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
		colDateOfBirth.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));

		colCustomerId.setMinWidth(100);// .setCellValueFactory(new PropertyValueFactory<>("maKH"));
		colName.setMinWidth(180);//.setCellValueFactory(new PropertyValueFactory<>("diaChi"));
		colAddress.setMinWidth(120);//.setCellValueFactory(new PropertyValueFactory<>("CMND"));
		colPhone.setMinWidth(100);//.setCellValueFactory(new PropertyValueFactory<>("soDT"));
		colDateOfBirth.setMinWidth(150);//.setCellValueFactory(new PropertyValueFactory<>("tenKH"));

		uploadDuLieuLenBang();
	}
	
	private void uploadDuLieuLenBang() {
		List<Customer> cuss=customerService.listCustomer();
		cuss.forEach(t->{
			tbl_view.getItems().add(t);
			listCustomer.add(t);
		});
	}

	public void btnClickChangePassword(ActionEvent e) throws IOException {

		changePassword(btnLogOut);

	}

	public void btnExit(ActionEvent e) throws IOException {

		areYouSure(btnLogOut);

	}

	public void clickBtnHelp(ActionEvent e) throws IOException {

		Help(btnLogOut);

	}

	public void btnHideWindow(ActionEvent e) {

		Stage stage=(Stage) ((Node)(e.getSource())).getScene().getWindow();  

		stage.setIconified(true);

	}
	
	public void btnClickAdd(ActionEvent e) throws IOException {
		FXMLLoader loader= new FXMLLoader(getClass().getResource(loadFormAddCustomer));

		Parent root=loader.load();

		FormAddCustomer ctlMain=loader.getController();

		String id=null;

		do {

			id="C"+ranDomNumber();

			ctlMain.txtMa.setText(id);

		} while (customerService.findCustomerById(id)!=null);

		loadFXML(root,btnHelp).setOnHidden(ev->{

			handleRefersh(e);

		});;


	}

	@FXML
	private void btnLogOut(ActionEvent e) throws MalformedURLException, IOException {

		logOut(btnLogOut,e);
		
	}

	public void btnCLickDiscs(ActionEvent e) {

		new BounceInLeft(pnlCustomer).play();

		pnlCustomer.toFront();

	}

	public void btnClickCustomer(ActionEvent e) throws IOException {
		
		
		pnlDiscs.toFront();
//		new BounceInDown(lblCustomer).play();
//
//		Parent root=(Parent) FXMLLoader.load(getClass().getResource("fxml/ManageCustomer.fxml"));
//		
//		bd.setCenter(root);

	}
	
	public void btnClickAdd(ActionEvent e,Product product) throws IOException, InterruptedException {
		BorderPane bd=new BorderPane();
		ImageView imgV=new ImageView();
		Image img = new Image("file:///"+product.getPicture());
		bd.setMaxHeight(154);
		imgV.setImage(img);
		imgV.setFitHeight(190);
		imgV.setFitWidth(180);
		Label lbl=new Label(product.getProductId());
		Label lbl1=new Label(product.getName());
		Label lbl2=new Label("SL:"+product.getQuantity());
		lbl.setAlignment(Pos.CENTER);

		JFXButton btn1=null;
		if(product.getStatus().equalsIgnoreCase("Hết hàng")) {
			btn1=new JFXButton("Hết hàng");
			
			btn1.setStyle("	-fx-background-color: #C3350B;\r\n" + 
					"	-fx-border-color: #C3350B;");
			
			btn1.setMinSize(174,25);
		}else {
			btn1=new JFXButton("Còn hàng");
			
			btn1.setStyle("	-fx-background-color:#FFC300 ;\r\n" + 
					"	-fx-border-color:#FFC300 ;");
			
			btn1.setMinSize(174,25);
		}
		
		JFXButton btn2=new JFXButton("Chi tiết");


		bd.setStyle("-fx-border-color: #73797F;\r\n" + 
				"		-fx-effect: dropshadow(three-pass-box, gray, 10,0, 0, 0);-fx-background-color:black");

		
		btn2.setStyle("	-fx-background-color: black;\r\n" + 
				"	-fx-border-color: #73797F;");
		btn2.setMinSize(174,25);

		lbl.setTextFill(Color.web("white"));
		lbl1.setTextFill(Color.web("white"));
		lbl2.setTextFill(Color.web("white"));
		
		lbl.setStyle("	-fx-background-color: black;\r\n" + 
				"	-fx-border-color: #73797F;");
		lbl1.setStyle("	-fx-background-color: black;\r\n" + 
				"	-fx-border-color: #73797F;");
		lbl2.setStyle("	-fx-background-color: black;\r\n" + 
				"	-fx-border-color: #73797F;");
		
		lbl.setMinSize(174,25);
		lbl1.setMinSize(174,25);
		lbl2.setMinSize(174,25);
		
		
		btn1.setTextFill(Color.web("white"));
		btn2.setTextFill(Color.web("white"));

		lbl.setPadding(new Insets(10, 0, 5, 0));
		VBox vb=new VBox(lbl,lbl1,lbl2,btn1,btn2);
		VBox.setMargin(btn1,new Insets(10,0,10,0));
		vb.setAlignment(Pos.CENTER);


		bd.setCenter(imgV);
		bd.setBottom(vb);

		flowPane.getChildren().add(bd);
	}





}
