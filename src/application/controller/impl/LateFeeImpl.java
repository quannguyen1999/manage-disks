package application.controller.impl;


import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import application.controller.DAO.Connect;
import application.controller.DAO.Repository;
import application.controller.services.LateFeeService;
import application.entities.LateFee;

public class LateFeeImpl  extends Repository implements LateFeeService{

	static Connection conn;

	static Connect connect;

	@Override
	public boolean addLateFee(LateFee LateFee) {
		return add(LateFee);
	}

	@Override
	public boolean removeLateFee(String id) {
		return deleteById(id,new LateFee());
	}

	@Override
	public LateFee updateLateFee(LateFee LateFeeUpdate, String id) {
		LateFee LateFeeFind=findLateFeeById(id);
		if(LateFeeFind==null) {

			return null;

		}

		LateFeeUpdate.setLateFeetId(id);//(id);

		return (LateFee) update(LateFeeUpdate);
	}

	@Override
	public LateFee findLateFeeById(String id) {
		// TODO Auto-generated method stub
		return (LateFee) findById(id,new LateFee());
	}

	@Override
	public List<LateFee> listLateFee() {
		List<Object> listObject=list(new LateFee(),LateFee.class.getSimpleName().toString());
		List<LateFee> listLateFee=new ArrayList<>();
		for(int i=0;i<listObject.size();i++) {
			listLateFee.add((LateFee)listObject.get(i));
		}
		return listLateFee;
	}



}


