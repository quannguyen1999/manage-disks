package application.controller.DAO;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityTransaction;

import application.entities.Customer;
public class Repository {
	static private Connect connect;
	static public <T> boolean add(T t) {
		EntityTransaction transaction = null;
		try {
			transaction = connect.getEntityManager().getTransaction();
			transaction.begin();
			connect.getEntityManager().persist(t);
			transaction.commit();
		} catch (Exception ex) {
			if (transaction != null) {
				transaction.rollback();
			}
			connect.getEntityManager().close();
			ex.printStackTrace();
			return false;
		}
		return true;
	}
	
	
	
	public <T> boolean deleteById(String id,T t) {
		EntityTransaction transaction = null;
		try {
			transaction = connect.getEntityManager().getTransaction();
			transaction.begin();
			Object tFind=Connect.getEntityManager().find(t.getClass(),id);
			connect.getEntityManager().remove(tFind);
			transaction.commit();
		} catch (Exception ex) {
			if (transaction != null) {
				transaction.rollback();
			}
			connect.getEntityManager().close();
			ex.printStackTrace();
			return false;
		}
		return true;
	}
	
	public <T> Object update(T tobject) {
		EntityTransaction transaction = null;
		Object tFind=null;
		try {
			transaction = connect.getEntityManager().getTransaction();
			transaction.begin();
			tFind=Connect.getEntityManager().merge(tobject);
			transaction.commit();
		} catch (Exception ex) {
			if (transaction != null) {
				transaction.rollback();
			}
			connect.getEntityManager().close();
			ex.printStackTrace();
			return null;
		}
		return tFind;
	}
	
	
	
	public static <T> Object findById(String id,T t) {
		EntityTransaction transaction = null;
		Object tFind=null;
		try {
			transaction = connect.getEntityManager().getTransaction();
			transaction.begin();
			tFind=connect.getEntityManager().find(t.getClass(), id);
			transaction.commit();
		} catch (Exception ex) {
			if (transaction != null) {
				transaction.rollback();
			}
			ex.printStackTrace();
			connect.getEntityManager().close();
			return null;
		}
		return tFind;
	}
	
	public static <T> List<Object> list(T t,String nameClass) {
		EntityTransaction transaction = null;
		List<Object> accs=new ArrayList<>();
		try {
			transaction =  connect.getEntityManager().getTransaction();
			transaction.begin();
			accs= (List<Object>) connect.getEntityManager().createQuery("select s from "+nameClass+" s",t.getClass()).getResultList();
			transaction.commit();
		} catch (Exception ex) {
			if (transaction != null) {
				transaction.rollback();
			}
			connect.getEntityManager().close();
		}
		
		return accs;
	}
	
	
}
