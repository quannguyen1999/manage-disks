package application.controller.services;

import java.util.List;

import application.entities.Bill;
import application.entities.BillDetail;

public interface BillService {
	public boolean addBill(Bill Bill);
	
	public boolean addBillDetail(BillDetail BillDetail);

	public boolean removeBill(String id);

	public Bill updateBill(Bill BillUpdate,String id);

	public Bill findBillById(String id);

	public List<Bill> listBill();
}
