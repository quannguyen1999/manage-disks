package application.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import animatefx.animation.BounceInDown;
import animatefx.animation.BounceInLeft;
import animatefx.animation.FadeInRight;
import application.controller.impl.BillImpl;
import application.controller.impl.CustomerImpl;
import application.controller.impl.LateFeeImpl;
import application.controller.impl.OrderDetailImpl;
import application.controller.impl.OrderImpl;
import application.controller.impl.ProductImpl;
import application.controller.impl.TitleImpl;
import application.controller.services.BillService;
import application.controller.services.CustomerService;
import application.controller.services.LateFeeService;
import application.controller.services.OrderDetailService;
import application.controller.services.OrderService;
import application.controller.services.ProductService;
import application.controller.services.TitleService;
import application.entities.Customer;
import application.entities.LateFee;
import application.entities.Product;
import application.entities.Title;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class FormEmployee extends DialogBox implements Initializable{

	@FXML JFXButton btnLogOut;

	@FXML JFXButton btnHelp;

	@FXML Pane pnlDiscs;

	@FXML Pane pnlCustomers;
	@FXML Pane pnlLateFees;
	@FXML Pane pnlOrders;
	@FXML Pane pnlDisks;
	@FXML Pane pnlTitles;

	@FXML FlowPane flowPane;

	@FXML JFXButton btnCustomer;

	@FXML JFXButton btnDisks;
	
	@FXML JFXButton btnTitles;
	@FXML JFXButton btnLateFees;
	@FXML JFXButton btnOrders;

	private TableView<Customer> tbl_view;

	TableColumn<Customer, String> colCustomerId;
	TableColumn<Customer, String> colName;
	TableColumn<Customer, String> colAddress;
	TableColumn<Customer, String> colPhone;
	TableColumn<Customer, LocalDate> colDateOfBirth;

	List<Customer> listCustomer=new ArrayList<>();


	@FXML BorderPane bdCustomer;
	
	@FXML BorderPane bdLateFee;
	
	@FXML StackPane sp;
	
	private BillService billService = new BillImpl();

	private OrderService orderService = new OrderImpl();

	private CustomerService customerService=new CustomerImpl();

	@FXML ComboBox<String> cbc=new ComboBox<String>();


	@FXML ComboBox<String> cbcPhone=new ComboBox<String>();



	private ProductService productService=new ProductImpl();
	//late fee
	private TableView<LateFee> tbl_view_latefee;

	TableColumn<LateFee, String> colLateFeeId;
	TableColumn<LateFee, String> colPrice;
	TableColumn<LateFee, String> colDatePay;
	TableColumn<LateFee, String> colCustomerIdLateFee;
	TableColumn<LateFee, String> colBillId;
	TableColumn<LateFee, String> colContent;
	TableColumn<LateFee, String> colNameCustomer;
	TableColumn<LateFee, String> colPhoneCustomer;


	public LateFeeService lateFeeService=new LateFeeImpl();

	@FXML ComboBox<String> cbcLateFee=new ComboBox<String>();
	@FXML ComboBox<String> cbcIdKh=new ComboBox<String>();
	@FXML ComboBox<String> cbcPhoneKh=new ComboBox<String>();

	@FXML RadioButton rdOne;
	@FXML RadioButton rdTwo;
	@FXML RadioButton rdThree;


//	@FXML JFXButton btnRefresh;

	List<LateFee> listFee=new ArrayList<>();
	
	
	//title 
	private TableView<Title> tbl_view_title;

	TableColumn<Title, String> colTitleId;
	TableColumn<Title, String> colNameTitle;
	TableColumn<Title, String> colStatus;
	TableColumn<Title, String> colcategoryId;

	DecimalFormat df = new DecimalFormat("#,###"); 

	@FXML BorderPane bdTitle;

	TitleService titleService=new TitleImpl();

	OrderDetailService orderDetailService = new OrderDetailImpl();

	@FXML ComboBox<String> cbcIdTitle=new ComboBox<String>();

	@FXML ComboBox<String> cbcNameTitle=new ComboBox<String>();

	@FXML JFXButton btnRefreshTitle;

	List<Title> listTitle=new ArrayList<>();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//set icon for button
		btnLogOut.setGraphic(getImageView("Logout.png"));
		btnHelp.setGraphic(getImageView("IconHelp.png"));

		btnCustomer.setGraphic(getImageView("customers.png"));
		btnDisks.setGraphic(getImageView("product.png"));
		btnTitles.setGraphic(getImageView("product.png"));
		

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
		
		
		initTableLateFee();
		initTableTitle();
		
		//load titie
		loadDataSearchTitle();
		
		cbcIdTitle.setEditable(true);

		cbcNameTitle.setEditable(true);
		
		//load late fee
		tbl_view_latefee.setOnMouseClicked(e->{
			if(e.getClickCount()==2) {
				int result=tbl_view_latefee.getSelectionModel().getSelectedIndex();
				if(result!=-1) {

					FXMLLoader loader= new FXMLLoader(getClass().getResource(loadFormPay));

					Parent root=null;
					try {
						root = loader.load();
					} catch (IOException e1) {
						e1.printStackTrace();
					}

					FormPay ctlMain=loader.getController();
						
					ctlMain.txtLateFee.setText(tbl_view_latefee.getItems().get(result).getLateFeetId());
					
					ctlMain.txtBillMa.setText(tbl_view_latefee.getItems().get(result).getBill().getBillId());
					
					ctlMain.txtBillDateOrder.setValue(tbl_view_latefee.getItems().get(result).getBill().getLocalDate());
					
					ctlMain.txtBillDatePay.setValue(tbl_view_latefee.getItems().get(result).getDatePay());
					
					ctlMain.rdBillDebtYes.setSelected(true);
					
					ctlMain.cbcCustomerId.setValue(tbl_view_latefee.getItems().get(result).getBill().getCustomer().getCustomerId());
					
					ctlMain.cbcCustomerPhone.setValue(tbl_view_latefee.getItems().get(result).getBill().getCustomer().getPhone());
					
					ctlMain.txtCustomerName.setText(tbl_view_latefee.getItems().get(result).getBill().getCustomer().getName());
					
					ctlMain.txtCustomerAddress.setText(tbl_view_latefee.getItems().get(result).getBill().getCustomer().getAddress());
					
					ctlMain.bill = tbl_view_latefee.getItems().get(result).getBill();
					
					ctlMain.lateFee = tbl_view_latefee.getItems().get(result);
					
					ctlMain.total = Math.round(tbl_view_latefee.getItems().get(result).getPrice());
					
					ctlMain.uploadDuLieuLenBang(tbl_view_latefee.getItems().get(result).getBill().getBillId());
					
					loadFXML(root,btnCustomer).setOnHidden(ev->{
						handleRefersh(new ActionEvent());
						if(ctlMain.total == 0) {
							try {
								Success("Thanh toán thành công", btnCustomer);
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							
							return;
						}
					});;
				}
			}
		});
		
		loadDataSearchManageLateFee();
		
		cbcLateFee.setEditable(false);
		cbcIdKh.setEditable(false);
		cbcPhoneKh.setEditable(true);

		cbcIdKh.setDisable(true);
		cbcLateFee.setDisable(true);
	}
	
	public void btnReturnDisk(ActionEvent e) throws IOException {
		FXMLLoader loader= new FXMLLoader(getClass().getResource(loadFormReturnDisk));

		Parent root=loader.load();

		loadFXML(root,btnCustomer).setOnHidden(ev->{

			handleRefersh(e);

		});;

	}
	
	public void btnRentDisk(ActionEvent e) throws IOException {
		FXMLLoader loader= new FXMLLoader(getClass().getResource(loadFormRentDisk));

		Parent root=loader.load();

		FormRentDisk ctlMain=loader.getController();

		String id=null;

		do {

			id="B"+ranDomNumber();

			ctlMain.txtIdBill.setText(id);

		} while (billService.findBillById(id)!=null);

		loadFXML(root,btnCustomer).setOnHidden(ev->{

			handleRefersh(e);

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
		List<Product> accs=productService.listProduct();

		accs.forEach(t->{

			items.add(t.getProductId());

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

		bdCustomer.setCenter(tbl_view);

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
//		ObservableList list = sp.getChildren();
//	
//		list.forEach(t->{
//			System.out.println(t);
//		});
		
		pnlDiscs.toFront();

		resetColor();

		btnDisks.setStyle("-fx-background-color:red");

	}
	
	public void btnCLickTitle(ActionEvent e) {
		pnlTitles.toFront();

		resetColor();

		btnTitles.setStyle("-fx-background-color:red");

	}
	
	

	public void btnClickCustomer(ActionEvent e) throws IOException {
		pnlCustomers.toFront();

		resetColor();

		btnCustomer.setStyle("-fx-background-color:red");
		//		new BounceInDown(lblCustomer).play();
		//
		//		Parent root=(Parent) FXMLLoader.load(getClass().getResource("fxml/ManageCustomer.fxml"));
		//		
		//		bd.setCenter(root);

	}
	
	public void btnClickLateFee(ActionEvent e) throws IOException {
		pnlLateFees.toFront();

		resetColor();

		btnLateFees.setStyle("-fx-background-color:red");
		
//
//		Parent root=(Parent) FXMLLoader.load(getClass().getResource(loadManageLateFee));
//
//		bdLateFee.setCenter(root);
//		//		new BounceInDown(lblCustomer).play();
//		//
//		//		Parent root=(Parent) FXMLLoader.load(getClass().getResource("fxml/ManageCustomer.fxml"));
//		//		
//		//		bd.setCenter(root);

	}
	
	public void btnClickOrders(ActionEvent e) throws IOException {
		pnlOrders.toFront();

		resetColor();

		btnOrders.setStyle("-fx-background-color:red");
		//		new BounceInDown(lblCustomer).play();
		//
		//		Parent root=(Parent) FXMLLoader.load(getClass().getResource("fxml/ManageCustomer.fxml"));
		//		
		//		bd.setCenter(root);

	}
	
	

	public void resetColor() {
		btnCustomer.setStyle("-fx-background-color:black");
		btnDisks.setStyle("-fx-background-color:black");
		btnTitles.setStyle("-fx-background-color:black");
		btnLateFees.setStyle("-fx-background-color:black");
		btnOrders.setStyle("-fx-background-color:black");
	}

	public void btnClickAdd(ActionEvent e,Product product) throws IOException, InterruptedException {
		BorderPane bd=new BorderPane();
		ImageView imgV=new ImageView();
		bd.setMaxHeight(154);
		imgV.setImage(getImage(product.getPicture()));
		imgV.setFitHeight(230);
		imgV.setFitWidth(230);
		Label lbl=new Label(product.getProductId());
		Label lbl1=new Label(product.getName());
		Label lbl2=new Label("SL:"+product.getQuantity());
		lbl.setAlignment(Pos.CENTER);

		JFXButton btn1=null;
		if(product.getStatus().equalsIgnoreCase(TRENKE)) {
			btn1=new JFXButton(TRENKE);

			btn1.setStyle("	-fx-background-color:#FFC300 ;\r\n" + 
					"	-fx-border-color:#FFC300 ;");

			btn1.setMinSize(250,25);
		}else if(product.getStatus().equalsIgnoreCase(CHOTHUE)){
			btn1=new JFXButton(CHOTHUE);

			btn1.setStyle("	-fx-background-color:#FFC300 ;\r\n" + 
					"	-fx-border-color:#FFC300 ;");

			btn1.setMinSize(250,25);
		}else {
			btn1=new JFXButton(GIULAI);

			btn1.setStyle("	-fx-background-color:#FFC300 ;\r\n" + 
					"	-fx-border-color:#FFC300 ;");

			btn1.setMinSize(250,25);
		}

		JFXButton btn2=new JFXButton("Chi tiết");


		bd.setStyle("-fx-border-color: #73797F;\r\n" + 
				"		-fx-effect: dropshadow(three-pass-box, gray, 10,0, 0, 0);-fx-background-color:black");


		btn2.setStyle("	-fx-background-color: black;\r\n" + 
				"	-fx-border-color: #73797F;");
		btn2.setMinSize(250,25);

		lbl.setTextFill(Color.web("white"));
		lbl1.setTextFill(Color.web("white"));
		lbl2.setTextFill(Color.web("white"));

		lbl.setStyle("	-fx-background-color: black;\r\n" + 
				"	-fx-border-color: #73797F;");
		lbl1.setStyle("	-fx-background-color: black;\r\n" + 
				"	-fx-border-color: #73797F;");
		lbl2.setStyle("	-fx-background-color: black;\r\n" + 
				"	-fx-border-color: #73797F;");

		lbl.setMinSize(250,25);
		lbl1.setMinSize(250,25);
		lbl2.setMinSize(250,25);


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

	public void handleKeyEvents(KeyEvent e) throws IOException {
		if(e.getCode()==KeyCode.ENTER) {

			String textFind = cbc.getValue();
			if(textFind.isEmpty()) {
				Error("Bạn chưa nhập mã cần tìm", btnCustomer);

				return;
			}

			Product product = productService.findProductById(textFind);

			if(product == null) {
				Error("Không tìm thấy", btnCustomer);

				return;
			}
			
			flowPane.getChildren().clear();
			
			BorderPane bd=new BorderPane();
			ImageView imgV=new ImageView();
			bd.setMaxHeight(154);
			imgV.setImage(getImage(product.getPicture()));
			imgV.setFitHeight(230);
			imgV.setFitWidth(230);
			Label lbl=new Label(product.getProductId());
			Label lbl1=new Label(product.getName());
			Label lbl2=new Label("SL:"+product.getQuantity());
			lbl.setAlignment(Pos.CENTER);

			JFXButton btn1=null;
			if(product.getStatus().equalsIgnoreCase(TRENKE)) {
				btn1=new JFXButton(TRENKE);

				//			btn1.setStyle("	-fx-background-color: #C3350B;\r\n" + 
				//					"	-fx-border-color: #C3350B;");

				btn1.setStyle("	-fx-background-color:#FFC300 ;\r\n" + 
						"	-fx-border-color:#FFC300 ;");

				btn1.setMinSize(250,25);
			}else if(product.getStatus().equalsIgnoreCase(CHOTHUE)){
				btn1=new JFXButton(CHOTHUE);

				btn1.setStyle("	-fx-background-color:#FFC300 ;\r\n" + 
						"	-fx-border-color:#FFC300 ;");

				btn1.setMinSize(250,25);
			}else {
				btn1=new JFXButton(GIULAI);

				btn1.setStyle("	-fx-background-color:#FFC300 ;\r\n" + 
						"	-fx-border-color:#FFC300 ;");

				btn1.setMinSize(250,25);
			}

			JFXButton btn2=new JFXButton("Chi tiết");


			bd.setStyle("-fx-border-color: #73797F;\r\n" + 
					"		-fx-effect: dropshadow(three-pass-box, gray, 10,0, 0, 0);-fx-background-color:black");


			btn2.setStyle("	-fx-background-color: black;\r\n" + 
					"	-fx-border-color: #73797F;");
			btn2.setMinSize(250,25);

			lbl.setTextFill(Color.web("white"));
			lbl1.setTextFill(Color.web("white"));
			lbl2.setTextFill(Color.web("white"));

			lbl.setStyle("	-fx-background-color: black;\r\n" + 
					"	-fx-border-color: #73797F;");
			lbl1.setStyle("	-fx-background-color: black;\r\n" + 
					"	-fx-border-color: #73797F;");
			lbl2.setStyle("	-fx-background-color: black;\r\n" + 
					"	-fx-border-color: #73797F;");

			lbl.setMinSize(250,25);
			lbl1.setMinSize(250,25);
			lbl2.setMinSize(250,25);


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
	
	public void resetListProduct(ActionEvent e) {
		flowPane.getChildren().clear();
		
		cbc.setValue(null);
		
		productService.listProduct().forEach(t->{

			try {
				btnClickAdd(new ActionEvent(),
						t);
			} catch (IOException ev) {
				ev.printStackTrace();
			} catch (InterruptedException ev) {
				ev.printStackTrace();
			}
		});
	}
	
	//manage late fee
	public void initTableLateFee() {
		tbl_view_latefee=new TableView<LateFee>();

		colLateFeeId=new TableColumn<LateFee, String>("Mã");
		colPrice=new TableColumn<LateFee, String>("Phí");
		colDatePay=new TableColumn<LateFee, String>("Ngày phải thanh toán");
		colBillId=new TableColumn<LateFee, String>("Mã bill");
		colContent=new TableColumn<LateFee, String>("Nội dung");
		colNameCustomer=new TableColumn<LateFee, String>("Tên Kh");
		colPhoneCustomer=new TableColumn<LateFee, String>("Phone kh");


		tbl_view_latefee.getColumns().addAll(colLateFeeId, 
				colPrice,
				colDatePay,
				colBillId,
				colContent,
				colNameCustomer,
				colPhoneCustomer);

		bdLateFee.setCenter(tbl_view_latefee);

		colLateFeeId.setCellValueFactory(new PropertyValueFactory<>("lateFeetId"));
		colPrice.setCellValueFactory(cellData-> new SimpleStringProperty(
				String.valueOf(cellData.getValue().getPrice())));
		colDatePay.setCellValueFactory(new PropertyValueFactory<>("datePay"));
		colContent.setCellValueFactory(new PropertyValueFactory<>("content"));

		colBillId.setCellValueFactory(cellData-> new SimpleStringProperty(
				String.valueOf(cellData.getValue().getBill().getBillId())));

		colNameCustomer.setCellValueFactory(cellData-> new SimpleStringProperty(
				String.valueOf(cellData.getValue().getBill().getCustomer().getName())));

		colPhoneCustomer.setCellValueFactory(cellData-> new SimpleStringProperty(
				String.valueOf(cellData.getValue().getBill().getCustomer().getPhone())));


		colDatePay.setMinWidth(200);
		
		colContent.setMinWidth(200);
		
		colNameCustomer.setMinWidth(150);
		
		colPhoneCustomer.setMinWidth(120);
		
		uploadDuLieuLenBangLateFee();
	}

	private void uploadDuLieuLenBangLateFee() {
		List<LateFee> cuss=lateFeeService.listLateFee();
		cuss.forEach(t->{
			tbl_view_latefee.getItems().add(t);
			listFee.add(t);
		});
	}
	
	private void loadDataSearchTitle() {
		ObservableList<String> items = FXCollections.observableArrayList();

		ObservableList<String> itemsNameTitle = FXCollections.observableArrayList();

		List<Title> accs=titleService.listTitle();

		accs.forEach(t->{
			items.add(t.getTitleId());

			itemsNameTitle.add(t.getName());
		});

		FilteredList<String> filteredItems = new FilteredList<String>(items);

		cbcIdTitle.getEditor().textProperty().addListener(new InputFilter(cbcIdTitle, filteredItems, false));

		cbcIdTitle.setItems(filteredItems);

		cbcIdTitle.setEditable(true);

		FilteredList<String> filteredNameItems = new FilteredList<String>(itemsNameTitle);

		cbcNameTitle.getEditor().textProperty().addListener(new InputFilter(cbcNameTitle, filteredNameItems, false));

		cbcNameTitle.setItems(filteredNameItems);

		cbcNameTitle.setEditable(true);
	}
	
	public void findItemInTableTitleId(ActionEvent e) throws IOException {
		String textFind=null;

		try {

			textFind=cbcIdTitle.getSelectionModel().getSelectedItem().toString().trim();

		} catch (Exception e2) {

			Error("Bạn chưa nhập tìm kiếm", btnCustomer);

			cbcIdTitle.requestFocus();

		}

		if(textFind.isEmpty()) {

			Error("Bạn chưa nhập tìm kiếm", btnCustomer);

			cbcIdTitle.requestFocus();

			return;

		}

		tbl_view_title.getItems().clear();

		Title TitleFind=titleService.findTitleById(textFind);

		if(TitleFind==null) {

			Error("Không tìm thấy", btnCustomer);

			cbcIdTitle.requestFocus();

			return;

		}else {

			tbl_view_title.getItems().add(TitleFind);

		}

	}
	
	public void findItemNameTitleInTable(ActionEvent e) throws IOException {
		String textFind=null;

		try {

			textFind=cbcNameTitle.getSelectionModel().getSelectedItem().toString().trim();

		} catch (Exception e2) {

			Error("Bạn chưa nhập tìm kiếm", btnCustomer);

			cbcNameTitle.requestFocus();

		}

		if(textFind.isEmpty()) {

			Error("Bạn chưa nhập tìm kiếm", btnCustomer);

			cbcNameTitle.requestFocus();

			return;

		}

		tbl_view_title.getItems().clear();
		
		List<Title> TitleFind=titleService.findTitleByName(textFind);

		if(TitleFind == null) {

			Error("Không tìm thấy", btnCustomer);

			cbcNameTitle.requestFocus();

			return;

		}else {

			tbl_view_title.getItems().clear();

			TitleFind.forEach(t->{

				tbl_view_title.getItems().add(t);

			});


		}

	}
	
	public void handleRefershTitle(ActionEvent e) {
		//		cbc.getItems().clear();
		cbcIdTitle.setValue("");
		cbcNameTitle.setValue("");
		tbl_view_title.getItems().clear();
		uploadDuLieuLenBangTitle();
	}
	
	public void clickRdOne(ActionEvent e) {

		cbcLateFee.setEditable(true);
		cbcIdKh.setEditable(false);
		cbcPhoneKh.setEditable(false);

		cbcLateFee.setDisable(false);
		cbcIdKh.setDisable(true);
		cbcPhoneKh.setDisable(true);
	}

	public void clickRdTwo(ActionEvent e) {

		cbcLateFee.setEditable(false);
		cbcIdKh.setEditable(false);
		cbcPhoneKh.setEditable(true);

		cbcLateFee.setDisable(true);
		cbcIdKh.setDisable(true);
		cbcPhoneKh.setDisable(false);
	}

	public void clickRdThree(ActionEvent e) {

		cbcLateFee.setEditable(false);
		cbcIdKh.setEditable(true);
		cbcPhoneKh.setEditable(false);

		cbcLateFee.setDisable(true);
		cbcIdKh.setDisable(false);
		cbcPhoneKh.setDisable(true);
	}
	
	private void loadDataSearchManageLateFee() {
		ObservableList<String> items = FXCollections.observableArrayList();

		List<LateFee> accs=lateFeeService.listLateFee();

		accs.forEach(t->{

			if(items.contains(t.getBill().getCustomer().getPhone())==false) {
				items.add(t.getBill().getCustomer().getPhone());
			}

		});

		FilteredList<String> filteredItems = new FilteredList<String>(items);

		cbcLateFee.getEditor().textProperty().addListener(new InputFilter(cbcLateFee, filteredItems, false));

		cbcLateFee.setItems(filteredItems);

		cbcLateFee.setEditable(true);

		ObservableList<String> itemsPhoneKH = FXCollections.observableArrayList();
		ObservableList<String> itemsIdKH = FXCollections.observableArrayList();
		List<Customer> listCusommer=customerService.listCustomer();

		listCusommer.forEach(t->{

			itemsPhoneKH.add(t.getPhone());
			itemsIdKH.add(t.getCustomerId());


		});

		FilteredList<String> filteredItemsPhoneKH = new FilteredList<String>(itemsPhoneKH);

		cbcPhoneKh.getEditor().textProperty().addListener(new InputFilter(cbcPhoneKh, filteredItemsPhoneKH, false));

		cbcPhoneKh.setItems(filteredItemsPhoneKH);

		cbcPhoneKh.setEditable(true);

		FilteredList<String> filteredItemsIdKH = new FilteredList<String>(itemsIdKH);

		cbcIdKh.getEditor().textProperty().addListener(new InputFilter(cbcIdKh, filteredItemsIdKH, false));

		cbcIdKh.setItems(filteredItemsIdKH);

		cbcIdKh.setEditable(true);



	}
	
	public void findItemPhoneCustomerInTable(ActionEvent e) throws IOException {
		String textFind=null;

		if(rdOne.isSelected()) {
			try {

				textFind=cbcLateFee.getSelectionModel().getSelectedItem().toString().trim();

			} catch (Exception e2) {

				Error("Bạn chưa nhập tìm kiếm", btnCustomer);

				cbcLateFee.requestFocus();

			}

			if(textFind.isEmpty()) {

				Error("Bạn chưa nhập tìm kiếm", btnCustomer);

				cbcLateFee.requestFocus();

				return;

			}
		}else if(rdThree.isSelected()) {


			try {

				textFind=cbcPhoneKh.getSelectionModel().getSelectedItem().toString().trim();

			} catch (Exception e2) {

				Error("Bạn chưa nhập tìm kiếm", btnCustomer);

				cbcPhoneKh.requestFocus();

			}

			if(textFind.isEmpty()) {

				Error("Bạn chưa nhập tìm kiếm", btnCustomer);

				cbcPhoneKh.requestFocus();

				return;

			}


		}else {

			try {

				textFind=cbcIdKh.getSelectionModel().getSelectedItem().toString().trim();

			} catch (Exception e2) {

				Error("Bạn chưa nhập tìm kiếm", btnCustomer);

				cbcIdKh.requestFocus();

			}

			if(textFind.isEmpty()) {

				Error("Bạn chưa nhập tìm kiếm", btnCustomer);

				cbcIdKh.requestFocus();

				return;

			}

		}


		tbl_view_latefee.getItems().clear();

		if(rdOne.isSelected()) {
			List<LateFee> listLateFeeSpecific = new ArrayList<>();

			for(int i=0;i<listFee.size();i++) {
				if(listFee.get(i).getBill().getCustomer().getPhone().equalsIgnoreCase(textFind)) {
					listLateFeeSpecific.add(listFee.get(i));
				}
			}

			if(listLateFeeSpecific.size() == 0) {

				Error("Không tìm thấy", btnCustomer);

				cbc.requestFocus();

				return;

			}else {

				tbl_view_latefee.getItems().clear();

				listLateFeeSpecific.forEach(t->{

					tbl_view_latefee.getItems().add(t);

				});



			}

		}else if(rdThree.isSelected()) {

			List<LateFee> listLateFeeFind = lateFeeService.findAllLteFeeByPhoneCustomer(textFind);

			if(listLateFeeFind == null) {

				Error("Không tìm thấy", btnCustomer);

				cbcPhoneKh.requestFocus();

				return;

			}else {
				tbl_view_latefee.getItems().clear();

				listLateFeeFind.forEach(t->{

					tbl_view_latefee.getItems().add(t);

				});
			}




		}else {
			List<LateFee> listLateFeeFind = lateFeeService.findAllLteFeeByIdCustomer(textFind);

			if(listLateFeeFind == null) {

				Error("Không tìm thấy", btnCustomer);

				cbcIdKh.requestFocus();

				return;

			}else {
				tbl_view_latefee.getItems().clear();

				listLateFeeFind.forEach(t->{

					tbl_view_latefee.getItems().add(t);

				});
			}


		}


	}
	
	public void handleRefershManageTitle(ActionEvent e) {
		rdThree.setSelected(true);
		
		cbcLateFee.setEditable(false);
		cbcIdKh.setEditable(false);
		cbcPhoneKh.setEditable(true);

		cbcLateFee.setDisable(true);
		cbcIdKh.setDisable(true);
		cbcPhoneKh.setDisable(false);
		
		cbcIdKh.setValue("");
		cbcPhoneKh.setValue("");
		cbcLateFee.setValue("");
		tbl_view_latefee.getItems().clear();
		uploadDuLieuLenBangLateFee();
	}

	
	//manage title
	public void initTableTitle() {
		tbl_view_title=new TableView<Title>();

		colTitleId=new TableColumn<Title, String>("mã");
		colNameTitle=new TableColumn<Title, String>("Tên");
		colStatus=new TableColumn<Title, String>("status");
		colcategoryId=new TableColumn<Title, String>("mã mặt hàng");

		tbl_view_title.getColumns().addAll(colTitleId,colNameTitle,colStatus,colcategoryId);

		bdTitle.setCenter(tbl_view_title);

		colTitleId.setCellValueFactory(new PropertyValueFactory<>("titleId"));
		colNameTitle.setCellValueFactory(new PropertyValueFactory<>("name"));
		colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
		colcategoryId.setCellValueFactory(cellData->new SimpleStringProperty(cellData.getValue().getCategory().getCategoryId()));

		colTitleId.setMinWidth(100);// .setCellValueFactory(new PropertyValueFactory<>("maKH"));
		colNameTitle.setMinWidth(180);//.setCellValueFactory(new PropertyValueFactory<>("diaChi"));
		colStatus.setMinWidth(120);//.setCellValueFactory(new PropertyValueFactory<>("CMND"));
		colcategoryId.setMinWidth(150);//.setCellValueFactory(new PropertyValueFactory<>("tenKH"));

		uploadDuLieuLenBangTitle();
	}

	private void uploadDuLieuLenBangTitle() {
		List<Title> cuss=titleService.listTitle();
		cuss.forEach(t->{
			tbl_view_title.getItems().add(t);
			listTitle.add(t);
		});
	}





}
