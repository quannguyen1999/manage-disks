package application.controller.impl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import application.controller.DAO.Connect;
import application.controller.DAO.Repository;
import application.controller.services.OrderService;
import application.entities.Order;

public class OrderImpl  extends Repository implements OrderService{

	static Connection conn;

	static Connect connect;

	@Override
	public boolean addOrder(Order Order) {
		return add(Order);
	}

	@Override
	public boolean removeOrder(String id) {
		return deleteById(id,new Order());
	}

	@Override
	public Order updateOrder(Order OrderUpdate, String id) {
//		Order OrderFind=findOrderById(id);
//		if(OrderFind==null) {
//
//			return null;
//
//		}
//
//		OrderUpdate.setOrderId(id);

		return (Order) update(OrderUpdate);
	}

	@Override
	public Order findOrderById(String id) {
		// TODO Auto-generated method stub
		return (Order) findById(id,new Order());
	}

	@Override
	public List<Order> listOrder() {
		List<Object> listObject=list(new Order(),Order.class.getSimpleName().toString());
		List<Order> listOrder=new ArrayList<>();
		for(int i=0;i<listObject.size();i++) {
			listOrder.add((Order)listObject.get(i));
		}
		return listOrder;
	}

}

