package application.controller.services;

import java.util.List;

import application.entities.Product;

public interface ProductService {
	public boolean addProduct(Product product);
	
	public boolean removeProduct(String id);
	
	public Product updateProduct(Product productUpdate,String id);
	
	public Product findProductById(String id);
	
	public List<Product> listProduct();
	
}
