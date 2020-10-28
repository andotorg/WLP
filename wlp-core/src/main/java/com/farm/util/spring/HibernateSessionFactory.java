package com.farm.util.spring;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

 
public class HibernateSessionFactory {
	private static final SessionFactory sessionf = (SessionFactory) BeanFactory
			.getBean("sessionFactory");

	public static void closeSession(Session session) {
		session.close();
	}

	public static Session getSession() {
		return sessionf.openSession();
	}

	public static SessionFactory getFactory() {
		return sessionf;
	}
}
