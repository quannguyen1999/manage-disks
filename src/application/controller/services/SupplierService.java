package application.controller.services;

import java.util.List;

import application.entities.Supplier;

public interface SupplierService {
	public boolean addSupplier(Supplier supplier);

	public boolean removeSupplier(String id);

	public Supplier updateSupplier(Supplier supplierUpdate,String id);

	public Supplier findSupplierById(String id);

	public List<Supplier> listSupplier();
}
