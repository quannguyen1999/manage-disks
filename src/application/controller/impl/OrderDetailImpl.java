package application.controller.impl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import application.controller.DAO.Connect;
import application.controller.DAO.Repository;
import application.controller.services.OrderDetailService;
import application.entities.OrderDetail;

public class OrderDetailImpl  extends Repository implements OrderDetailService{

	static Connection conn;

	static Connect connect;

	@Override
	public boolean addOrderDetail(OrderDetail OrderDetail) {
		return add(OrderDetail);
	}

	@Override
	public boolean removeOrderDetail(String id) {
		return deleteById(id,new OrderDetail());
	}

	@Override
	public OrderDetail updateOrderDetail(OrderDetail OrderDetailUpdate, String id) {
//		OrderDetail OrderDetailFind=findOrderDetailById(id);
//		if(OrderDetailFind==null) {
//
//			return null;
//
//		}
//
//		OrderDetailUpdate.setOrderDetailId(id);

		return (OrderDetail) update(OrderDetailUpdate);
	}

	@Override
	public OrderDetail findOrderDetailById(String id) {
		// TODO Auto-generated method stub
		return (OrderDetail) findById(id,new OrderDetail());
	}

	@Override
	public List<OrderDetail> listOrderDetail() {
		List<Object> listObject=list(new OrderDetail(),OrderDetail.class.getSimpleName().toString());
		List<OrderDetail> listOrderDetail=new ArrayList<>();
		for(int i=0;i<listObject.size();i++) {
			listOrderDetail.add((OrderDetail)listObject.get(i));
		}
		return listOrderDetail;
	}

}