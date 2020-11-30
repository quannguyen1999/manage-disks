package application.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;

import application.controller.impl.TitleImpl;
import application.controller.impl.CategoryImpl;
import application.controller.impl.CustomerImpl;
import application.controller.services.TitleService;
import application.controller.services.CategoryService;
import application.controller.services.CustomerService;
import application.entities.Title;
import application.entities.Category;
import application.entities.Customer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
public class FormAddTitle extends DialogBox implements Initializable{

	@FXML public BorderPane mainBd;

	@FXML Label lblTitle;

	@FXML JFXTextField txtMa;

	@FXML ComboBox<String> cbc=new ComboBox<String>();

	@FXML JFXTextField txtNameTitle;

	@FXML JFXRadioButton rdTrue;

	@FXML JFXRadioButton rdFalse;

	//	@FXML JFXTextField txtNameCategory;

	//	@FXML JFXTextField txtDescriptionCategory;

	//	@FXML JFXTextField txtPriceCategory;

	//	@FXML JFXTextField txtTimeRent;

	@FXML JFXButton btn;

	public TitleService TitleService=new TitleImpl();

	public CategoryService categoryService=new CategoryImpl();

	public String maCategoryRemember="";

	public Title titleOld;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		//		txtNameCategory.setEditable(false);

		//		txtDescriptionCategory.setEditable(false);

		//		txtPriceCategory.setEditable(false);

		//		txtTimeRent.setEditable(false);

		//		txtMa.setEditable(false);

		//		cbc.setEditable(true);

		loadDataSearch();
	}

	private void loadDataSearch() {
		ObservableList<String> items = FXCollections.observableArrayList();

		List<Category> accs=categoryService.listCategory();

		accs.forEach(t->{

			items.add(t.getName());

		});

		FilteredList<String> filteredItems = new FilteredList<String>(items);

		cbc.getEditor().textProperty().addListener(new InputFilter(cbc, filteredItems, false));

		cbc.setItems(filteredItems);

		//		cbc.setEditable(true);

	}

	public void findItemCategoryId(ActionEvent e) throws IOException{
		Category listnv = null;
		List<Category> accs=categoryService.listCategory();
		String cbcTextFind=null;

		try {

			cbcTextFind=cbc.getSelectionModel().getSelectedItem().toString();

		} catch (Exception e2) {

			Error("Vui lòng không để trống", btn);
			xoaRongCategory();
			return;

		};

		if(cbcTextFind==null) {

			Error("Vui lòng không để trống", btn);

			cbc.requestFocus();
			xoaRongCategory();
			return;

		}
		try {
			for(int i=0;i<accs.size();i++) {
				if(accs.get(i).getCategoryId().equals(cbcTextFind)) {
					listnv=accs.get(i);
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		if(listnv!=null) {
			Category cate=listnv;
			if(cate!=null) {
				//				txtMaMH.setText(cate.getCategoryId());
			}
		}else {

			Error("Không tìm thấy mã này", btn);

			cbc.requestFocus();
			xoaRongCategory();
			return;

		}
	}

	public boolean kiemTraNameTitle(ActionEvent e,String ma) throws IOException {
		String MaKT=ma.trim();
		if(MaKT.isEmpty()==false) {
			return true;
		}else {
			Error("tên title không được để trống",btn);
			return false;
		}
	}

	public boolean kiemTraTenCategory(ActionEvent e,String ma) throws IOException {
		String MaKT=ma.trim();
		if(MaKT.isEmpty()==false) {
			Category categoryFind = categoryService.findCategoryByName(MaKT);

			if(categoryFind==null) {
				Error("tên category không được tồn tại",btn);
				return false;
			}

			return true;

		}else {
			Error("tên category không được để trống",btn);
			return false;
		}


	}


	public void CLickOK(ActionEvent e) throws IOException {
		String ma=txtMa.getText().toString();//.getText().toString();

		Category category = categoryService.findCategoryByName(cbc.getSelectionModel().getSelectedItem());

		String tenTitle=txtNameTitle.getText().toString();

		boolean stillContunite=false;

		if(kiemTraNameTitle(e,tenTitle)) {
			stillContunite=true;
		}else {
			txtNameTitle.requestFocus();
			stillContunite=false;
		}
		if(stillContunite==true) {

			Title title=null;

			if(rdFalse.isSelected()) {

				title=new Title(ma, tenTitle, CHUADAT, category);

			}else {

				title=new Title(ma, tenTitle, DAT, category);

			}

			if(lblTitle.getText().equals("Cập nhập title")==false) {

				List<Title> listTitleFind = TitleService.findTitleByName(tenTitle);

				if(listTitleFind != null) {

					Error("Lỗi trùng title", btn);

					return;

				}

				if(TitleService.addTitle(title)==false) {

					Error("Lỗi thêm không thành công", btn);

					return;

				}else{

					((Node)(e.getSource())).getScene().getWindow().hide();  

				};

			}else {

				if(tenTitle.equalsIgnoreCase(titleOld.getName()) == false) {

					List<Title> listTitleFind = TitleService.findTitleByName(tenTitle);

					if(listTitleFind != null) {

						Error("Lỗi trùng title", btn);

						return;

					}

				}

				if(TitleService.updateTitle(title,ma)==null) {

					Error("Lỗi cập nhập không thành công", btn);

				}else{

					((Node)(e.getSource())).getScene().getWindow().hide();  

				};

			}

		}
	}
	public void btnXoaRong(ActionEvent e) {

		if(lblTitle.getText().toString().equalsIgnoreCase("Cập nhập title")) {

			txtMa.setText(titleOld.getTitleId());

			cbc.setValue(titleOld.getCategory().getCategoryId());

			txtNameTitle.setText(titleOld.getName());

			if(titleOld.getStatus().equalsIgnoreCase(DAT)) {

				rdTrue.setSelected(true);

			}

		}else {
			txtNameTitle.setText("");

			rdTrue.setSelected(true);

			//			txtTimeRent.setText("");

			if(maCategoryRemember.isEmpty()==true) {

				cbc.setValue("");

				xoaRongCategory();

			}else {

				

				Category category=categoryService.findCategoryById(maCategoryRemember);
				cbc.setValue(category.getName());

			}
		}




	}

	public void xoaRongCategory() {

		//		txtNameCategory.setText("");
		//		txtPriceCategory.setText("");
		//		txtDescriptionCategory.setText("");

	}
	public void btnCLoseWindow(ActionEvent e) throws IOException {

		((Node)(e.getSource())).getScene().getWindow().hide();  

	}

}
