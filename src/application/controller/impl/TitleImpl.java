package application.controller.impl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityTransaction;

import application.controller.DAO.Connect;
import application.controller.DAO.Repository;
import application.controller.services.BillService;
import application.controller.services.CategoryService;
import application.controller.services.OrderDetailService;
import application.controller.services.OrderService;
import application.controller.services.ProductService;
import application.controller.services.TitleService;
import application.entities.BillDetail;
import application.entities.Category;
import application.entities.Customer;
import application.entities.OrderDetail;
import application.entities.Product;
import application.entities.Title;

public class TitleImpl extends Repository implements TitleService{

	static Connection conn;

	static Connect connect;

	private CategoryService categoryService=new CategoryImpl();

	private ProductService productService=new ProductImpl();

	private BillService billService=new BillImpl();
	
	private OrderDetailService orderDetailService = new OrderDetailImpl();

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

	@Override
	public List<Title> findTitleByName(String name) {
		EntityTransaction transaction = null;
		List<Title> titles=new ArrayList<>();
		try {
			transaction =  connect.getEntityManager().getTransaction();
			transaction.begin();
			titles=  connect.getEntityManager().createQuery("from Title where name='"+name+"'",Title.class)
					.getResultList();
			transaction.commit();
		} catch (Exception ex) {
			if (transaction != null) {
				transaction.rollback();
			}
			connect.getEntityManager().close();
		}
		return titles.size()==0?null:titles;
	}

	@Override
	public int countProductByTitleId(String id) {
		int count = 0;
		List<Product> listProduct = productService.listProduct();
		if(listProduct!=null) {
			for(int i=0;i<listProduct.size();i++) {
				if(listProduct.get(i).getTitle().getTitleId().equalsIgnoreCase(id)) {
					count+=listProduct.get(i).getQuantity();
				}
			}
		}
		return count;
	}

	@Override
	public int countBillByTitleId(String id) {
		int count = 0;
		List<Product> listProduct = productService.listProduct();
		if(listProduct==null) {
			return 0;
		}
		for(int i=0;i<listProduct.size();i++) {
			if(listProduct.get(i).getTitle().getTitleId().equalsIgnoreCase(id)) {
				List<BillDetail> listBillDetail = billService.findAllBillDetailByProductId(listProduct.get(i).getProductId());
				if(listBillDetail!=null) {
					for(int j=0;j<listBillDetail.size();j++) {
						count+=listBillDetail.get(j).getQuantity();
					}
				}
			}
		}
		return count;
	}

	@Override
	public int countInStockProductByTitleId(String id) {
		return 0;
	}

	@Override
	public int countDebtCustomerByTitleId(String id) {
		return 0;
	}

	@Override
	public int countOrderByTitleId(String id) {
		int count = 0;
		
		Title title = findTitleById(id);
		
		List<OrderDetail> listOrderDetail = orderDetailService.findAllOrderDetailByTitleId(id);
		
		if(listOrderDetail==null) {
			return 0;
		}
		
		for(int i=0;i<listOrderDetail.size();i++) {
			System.out.println(listOrderDetail.get(i));
			count+=listOrderDetail.get(i).getQuantity();
		}

		return count;
	}



}
