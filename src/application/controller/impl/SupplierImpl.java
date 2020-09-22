package application.controller.impl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import application.controller.DAO.Connect;
import application.controller.DAO.Repository;
import application.controller.services.SupplierService;
import application.entities.Customer;
import application.entities.Supplier;

public class SupplierImpl extends Repository implements SupplierService{

	static Connection conn;
	
	static Connect connect;
	
	@Override
	public boolean addSupplier(Supplier Supplier) {
		return add(Supplier);
	}

	@Override
	public boolean removeSupplier(String id) {
		return deleteById(id,new Supplier());
	}

	@Override
	public Supplier updateSupplier(Supplier SupplierUpdate, String id) {
		Supplier SupplierFind=findSupplierById(id);
		if(SupplierFind==null) {
			
			return null;
			
		}
		
		SupplierUpdate.setSupplierId(id);
		
		return (Supplier) update(SupplierUpdate);
	}

	@Override
	public Supplier findSupplierById(String id) {
		// TODO Auto-generated method stub
				return (Supplier) findById(id,new Supplier());
			}

	@Override
	public List<Supplier> listSupplier() {
		List<Object> listObject=list(new Supplier(),Supplier.class.getSimpleName().toString());
		List<Supplier> listSupplier=new ArrayList<>();
		for(int i=0;i<listObject.size();i++) {
			listSupplier.add((Supplier)listObject.get(i));
		}
		return listSupplier;
	}

}
