package application.controller.services;

import java.util.List;

import application.entities.Title;

public interface TitleService {
	
	public boolean addTitle(Title title);

	public boolean removeTitle(String id);

	public Title updateTitle(Title titleUpdate,String id);

	public Title findTitleById(String id);
	
	public List<Title> findTitleByName(String name);

	public List<Title> listTitle();
	
	public int countProductByTitleId(String id);
	
	public int countBillByTitleId(String id);
	
	public int countInStockProductByTitleId(String id);
	
	public int countDebtCustomerByTitleId(String id);
	
	public int countOrderByTitleId(String id);
	
	public List<Title> listAllTitleByCategoryId(String id);
	
	public List<Title> listAllTitleStillNotOrder();
	
	public List<Title> lisdtAllTitleHadOrder();
}
