package application.controller.impl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import application.controller.DAO.Connect;
import application.controller.DAO.Repository;
import application.controller.services.BillService;
import application.entities.Bill;
import application.entities.BillDetail;

public class BillImpl  extends Repository implements BillService{

	static Connection conn;

	static Connect connect;

	@Override
	public boolean addBill(Bill Bill) {
		return update(Bill)==null?false:true;
	}

	@Override
	public boolean removeBill(String id) {
		return true;
	}

	@Override
	public Bill updateBill(Bill BillUpdate, String id) {
		Bill BillFind=findBillById(id);
		if(BillFind==null) {

			return null;

		}

		BillUpdate.setBillId(id);

		return (Bill) update(BillUpdate);
	}

	@Override
	public Bill findBillById(String id) {
		// TODO Auto-generated method stub
		return (Bill) findById(id,new Bill());
	}

	@Override
	public List<Bill> listBill() {
		List<Object> listObject=list(new Bill(),Bill.class.getSimpleName().toString());
		List<Bill> listBill=new ArrayList<>();
		for(int i=0;i<listObject.size();i++) {
			listBill.add((Bill)listObject.get(i));
		}
		return listBill;
	}

	@Override
	public boolean addBillDetail(BillDetail BillDetail) {
		return update(BillDetail)==null?false:true;
	}

}

