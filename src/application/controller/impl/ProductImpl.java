package application.controller.impl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityTransaction;

import application.controller.DAO.Connect;
import application.controller.DAO.Repository;
import application.controller.services.ProductService;
import application.controller.services.SupplierService;
import application.controller.services.TitleService;
import application.entities.Customer;
import application.entities.Product;
import application.entities.Supplier;
import application.entities.Title;

public class ProductImpl extends Repository implements ProductService{
	static Connection conn;

	static Connect connect;

	
	private SupplierService supliService=new SupplierImpl();
	
	@Override
	public boolean addProduct(Product product) {
		
		TitleService titleService=new TitleImpl();
		
		Title title=titleService.findTitleById(product.getTitle().getTitleId());
		
		if(title==null) {
			
			return false;
			
		}
		
		Supplier supplier=supliService.findSupplierById(product.getSupplier().getSupplierId());
		
		if(supplier==null) {
			
			return false;
			
		}
		
		update(product);
		
		return true;
	}

	@Override
	public boolean removeProduct(String id) {
//		return deleteById(id,new Product());
		EntityTransaction transaction = null;
		try {
			transaction = connect.getEntityManager().getTransaction();
			transaction.begin();
			Object tFind=Connect.getEntityManager().find(Product.class,id);
			connect.getEntityManager().remove(tFind);
			transaction.commit();
		} catch (Exception ex) {
			if (transaction != null) {
				transaction.rollback();
			}
			connect.getEntityManager().close();
			ex.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public Product updateProduct(Product ProductUpdate, String id) {
		Product ProductFind=findProductById(id);
		if(ProductFind==null) {

			return null;

		}

		ProductUpdate.setProductId(id);

		return (Product) update(ProductUpdate);
	}

	@Override
	public Product findProductById(String id) {
		// TODO Auto-generated method stub
		return (Product) findById(id,new Product());
	}

	@Override
	public List<Product> listProduct() {
		List<Object> listObject=list(new Product(),Product.class.getSimpleName().toString());
		List<Product> listProduct=new ArrayList<>();
		for(int i=0;i<listObject.size();i++) {
			listProduct.add((Product)listObject.get(i));
		}
		return listProduct;
	}

	@Override
	public List<Product> listAllProductByTitleIdInStock(String titleId) {
		List<Product> listProductFind = new ArrayList<>();
		List<Product> listProductCurrent= listProduct();
		for(int i=0;i<listProductCurrent.size();i++) {
			if(listProductCurrent.get(i).getTitle().getTitleId().equalsIgnoreCase(titleId) && listProductCurrent.get(i).getQuantityOnShelf()>=1) {
				listProductFind.add(listProductCurrent.get(i));
			}
		}
		return listProductFind.size()<=0?null:listProductFind;
	}

}
