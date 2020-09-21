package application.controller.DAO;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class Connect {
	private static EntityManager entityManager=null;
	
	public static EntityManager getEntityManager() {
		if(entityManager==null) {
			entityManager = Persistence.createEntityManagerFactory("manage-disks").createEntityManager();
		}
		return entityManager;
	}
}
