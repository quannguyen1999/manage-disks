package application.controller.services;

import java.util.List;

import application.entities.Category;

public interface CategoryService {
	
	public boolean addCategory(Category category);

	public boolean removeCategory(String id);

	public Category updateCategory(Category categoryUpdate,String id);

	public Category findCategoryById(String id);

	public List<Category> listCategory();
	
	public Category findCategoryByName(String name);
	
	
}
