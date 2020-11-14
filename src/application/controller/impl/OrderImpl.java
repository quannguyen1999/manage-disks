package application.controller.impl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityTransaction;

import application.controller.DAO.Connect;
import application.controller.DAO.Repository;
import application.controller.services.OrderService;
import application.entities.Bill;
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

	@Override
	public List<Order> findAllOrderByIdCustomer(String id) {
		List<Order> listOrderFind = new ArrayList<>();
		List<Order> listOrderX=listOrder();
		if(listOrderX!=null && listOrderX.size()>=1) {
			for(int i=0;i<listOrderX.size();i++) {
				if(listOrderX.get(i).getCustomer().getCustomerId().equalsIgnoreCase(id)) {
					listOrderFind.add(listOrderX.get(i));
				}
			}
		}
		return listOrderFind.size()==0?null:listOrderFind;
	}

	@Override
	public List<Order> findAllOrderByPhoneCustomer(String phone) {
		List<Order> listOrderFind = new ArrayList<>();
		List<Order> listOrderX=listOrder();
		if(listOrderX!=null && listOrderX.size()>=1) {
			for(int i=0;i<listOrderX.size();i++) {
				if(listOrderX.get(i).getCustomer().getPhone().equalsIgnoreCase(phone)) {
					listOrderFind.add(listOrderX.get(i));
				}
			}
		}
		return listOrderFind.size()==0?null:listOrderFind;
	}

}

