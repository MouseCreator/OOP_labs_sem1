package univ.lab.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private final static SessionFactory sessionFactory = initSessionFactory();

    private static SessionFactory initSessionFactory() {
        return new Configuration().configure().buildSessionFactory();
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
