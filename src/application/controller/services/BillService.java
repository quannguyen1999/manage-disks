package application.controller.services;

import java.util.ArrayList;
import java.util.List;

import application.entities.Bill;
import application.entities.BillDetail;

public interface BillService {
	public boolean addBill(Bill Bill);
	
	public boolean addBillDetail(BillDetail BillDetail);

	public boolean removeBill(String id);
	
	public boolean removeBillDetail(String id);

	public Bill updateBill(Bill BillUpdate,String id);

	public Bill findBillById(String id);
	
	public BillDetail findBillDetailById(String id);

	public List<Bill> listBill();
	
	public List<Bill> findAllBillByIdCustomer(String id);
	
	public List<Bill> findAllBillByPhoneCustomer(String phone);
	
	public List<BillDetail> findAllBillDetailByIdBill(ArrayList<Bill> listIdBill);
	
	public List<BillDetail> findAllBillDetailByProductId(String id);
	
	public List<BillDetail> listBillDetail();
}
