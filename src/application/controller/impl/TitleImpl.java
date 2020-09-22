package application.controller.impl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import application.controller.DAO.Connect;
import application.controller.DAO.Repository;
import application.controller.services.CategoryService;
import application.controller.services.TitleService;
import application.entities.Category;
import application.entities.Title;

public class TitleImpl extends Repository implements TitleService{

	static Connection conn;

	static Connect connect;
	
	private CategoryService categoryService=new CategoryImpl();

	@Override
	public boolean addTitle(Title Title) {

		Category category=categoryService.findCategoryById(Title.getCategory().getCategoryId());
		
		if(category==null) {
			
			return false;
			
		}
		
		update(Title);

		return true;
		
	}

	@Override
	public boolean removeTitle(String id) {
		return deleteById(id,new Title());
	}

	@Override
	public Title updateTitle(Title TitleUpdate, String id) {
		Title TitleFind=findTitleById(id);
		if(TitleFind==null) {

			return null;

		}

		TitleUpdate.setTitleId(id);

		return (Title) update(TitleUpdate);
	}

	@Override
	public Title findTitleById(String id) {
		// TODO Auto-generated method stub
		return (Title) findById(id,new Title());
	}

	@Override
	public List<Title> listTitle() {
		List<Object> listObject=list(new Title(),Title.class.getSimpleName().toString());
		List<Title> listTitle=new ArrayList<>();
		for(int i=0;i<listObject.size();i++) {
			listTitle.add((Title)listObject.get(i));
		}
		return listTitle;
	}

}
