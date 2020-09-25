package application.controller.services;

import java.util.List;

import application.entities.Order;

public interface OrderService {
	public boolean addOrder(Order Order);

	public boolean removeOrder(String id);

	public Order updateOrder(Order OrderUpdate,String id);

	public Order findOrderById(String id);

	public List<Order> listOrder();
}
