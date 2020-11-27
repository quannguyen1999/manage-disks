package application.controller.impl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityTransaction;

import application.controller.DAO.Connect;
import application.controller.DAO.Repository;
import application.controller.services.BillService;
import application.entities.Bill;
import application.entities.BillDetail;
import application.entities.Customer;

public class BillImpl  extends Repository implements BillService{

	static Connection conn;

	static Connect connect;

	@Override
	public boolean addBill(Bill Bill) {
		return update(Bill)==null?false:true;
	}

	@Override
	public boolean removeBill(String id) {
		return deleteById(id, new Bill());
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
	public List<BillDetail> listBillDetail() {
		List<Object> listObject=list(new BillDetail(),BillDetail.class.getSimpleName().toString());
		List<BillDetail> listBill=new ArrayList<>();
		for(int i=0;i<listObject.size();i++) {
			listBill.add((BillDetail)listObject.get(i));
		}
		return listBill;
	}

	@Override
	public boolean addBillDetail(BillDetail BillDetail) {
		return update(BillDetail)==null?false:true;
	}

	@Override
	public List<Bill> findAllBillByIdCustomer(String id) {
		EntityTransaction transaction = null;
		List<Bill> accs=new ArrayList<>();
		try {
			transaction =  connect.getEntityManager().getTransaction();
			transaction.begin();
			accs=  connect.getEntityManager().createQuery("from Bill where customerId='"+id+"'",Bill.class).getResultList();
			transaction.commit();
		} catch (Exception ex) {
			if (transaction != null) {
				transaction.rollback();
			}
			connect.getEntityManager().close();
		}
		return accs.size()==0?null:accs;
	}

	@Override
	public List<BillDetail> findAllBillDetailByIdBill(ArrayList<Bill> listIdBill){
		if(listIdBill.size() <= 0) {
			return null;
		}
		String name = "(";
		List<String> listBill=new ArrayList<>();
		for(int j=0;j<listIdBill.size();j++) {
			listBill.add(listIdBill.get(j).getBillId());
		}
		for(int i=0;i<listBill.size();i++) {
			if(i == listBill.size()-1) {
				name=name+"'"+listBill.get(i)+"'";
			}else {
				name=name+"'"+listBill.get(i)+"',";
			}
		}
		name += ")";
		
		EntityTransaction transaction = null;
		List<BillDetail> accs=new ArrayList<>();
		try {
			transaction =  connect.getEntityManager().getTransaction();
			transaction.begin();
			accs=  connect.getEntityManager().createQuery("from BillDetail where billId in "+name+"",BillDetail.class).getResultList();
			transaction.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			if (transaction != null) {
				transaction.rollback();
			}
			connect.getEntityManager().close();
		}
		return accs.size()==0?null:accs;
	}

	@Override
	public boolean removeBillDetail(String id) {
		return deleteById(id, new BillDetail());
	}

	@Override
	public List<BillDetail> findAllBillDetailByProductId(String id) {
		List<BillDetail> listBillDetailFind = new ArrayList<>();
		List<BillDetail> listBillDetail = listBillDetail();
		for(int i=0;i<listBillDetail.size();i++) {
			if(listBillDetail.get(i).getProduct().getProductId().equalsIgnoreCase(id)) {
				listBillDetailFind.add(listBillDetail.get(i));
			}
		}
		return listBillDetailFind.size()<=0?null:listBillDetailFind;
	}

	@Override
	public List<Bill> findAllBillByPhoneCustomer(String phone) {
		List<Bill> listBillDetailFind = new ArrayList<>();
		List<Bill> listBillDetail = listBill();
		for(int i=0;i<listBillDetail.size();i++) {
			if(listBillDetail.get(i).getCustomer().getPhone().equalsIgnoreCase(phone)) {
				listBillDetailFind.add(listBillDetail.get(i));
			}
		}
		return listBillDetailFind.size()<=0?null:listBillDetailFind;
	}

	@Override
	public BillDetail findBillDetailById(String id) {
		return (BillDetail) findById(id,new BillDetail());
	}
	
	

}

