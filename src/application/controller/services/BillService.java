package application.controller.services;

import java.util.List;

import application.entities.Bill;

public interface BillService {
	public boolean addBill(Bill Bill);

	public boolean removeBill(String id);

	public Bill updateBill(Bill BillUpdate,String id);

	public Bill findBillById(String id);

	public List<Bill> listBill();
}
