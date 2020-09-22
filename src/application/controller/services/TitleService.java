package application.controller.services;

import java.util.List;

import application.entities.Title;

public interface TitleService {
	
	public boolean addTitle(Title title);

	public boolean removeTitle(String id);

	public Title updateTitle(Title titleUpdate,String id);

	public Title findTitleById(String id);

	public List<Title> listTitle();
}
