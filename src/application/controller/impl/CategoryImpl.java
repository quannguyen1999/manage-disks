package application.controller.impl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import application.controller.DAO.Connect;
import application.controller.DAO.Repository;
import application.controller.services.CategoryService;
import application.entities.Category;
import application.entities.Customer;

public class CategoryImpl extends Repository implements CategoryService{

	static Connection conn;

	static Connect connect;

	@Override
	public boolean addCategory(Category category) {
		return add(category);
	}

	@Override
	public boolean removeCategory(String id) {
		return deleteById(id,new Category());
	}

	@Override
	public Category updateCategory(Category categoryUpdate, String id) {
		Category categoryFind=findCategoryById(id);
		if(categoryFind==null) {

			return null;

		}

		categoryUpdate.setCategoryId(id);

		return (Category) update(categoryUpdate);
	}

	@Override
	public Category findCategoryById(String id) {
		// TODO Auto-generated method stub
		return (Category) findById(id,new Category());
	}

	@Override
	public List<Category> listCategory() {
		List<Object> listObject=list(new Category(),Category.class.getSimpleName().toString());
		List<Category> listCategory=new ArrayList<>();
		for(int i=0;i<listObject.size();i++) {
			listCategory.add((Category)listObject.get(i));
		}
		return listCategory;
	}

}
