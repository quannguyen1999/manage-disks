package application.controller.services;

import java.util.List;

import application.entities.OrderDetail;

public interface OrderDetailService {
	public boolean addOrderDetail(OrderDetail OrderDetail);

	public boolean removeOrderDetail(String id);
	
	public boolean removeOrderDetailByCustomerId(String customerId, String titleId);

	public OrderDetail updateOrderDetail(OrderDetail OrderDetailUpdate,String id);

	public OrderDetail findOrderDetailById(String id);
	
	public List<OrderDetail> findAllOrderDetailByTitleId(String id);

	public List<OrderDetail> listOrderDetail();
	
	public List<OrderDetail> findAllOrderDetailByOrderId(String id);
}
