package application.controller.services;

import java.util.List;

import application.entities.LateFee;


public interface LateFeeService {
	public boolean addLateFee(LateFee LateFee);

	public boolean removeLateFee(String id);

	public LateFee updateLateFee(LateFee LateFeeUpdate,String id);

	public LateFee findLateFeeById(String id);

	public List<LateFee> listLateFee();
	
	public List<LateFee> findAllLteFeeByIdCustomer(String id);
	
	public List<LateFee> findAllLteFeeByPhoneCustomer(String phone);
	
	public List<LateFee> findAllLateFeeByBillId(String billId);
}
