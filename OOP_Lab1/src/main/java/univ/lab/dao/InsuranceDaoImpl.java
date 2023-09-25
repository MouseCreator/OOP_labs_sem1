package univ.lab.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import univ.lab.model.Insurance;
import univ.lab.util.HibernateUtil;

public class InsuranceDaoImpl implements InsuranceDao {
    @Override
    public Insurance save(Insurance insurance) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.persist(insurance);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return insurance;
    }

    @Override
    public Insurance get(Long id) {
        SessionFactory sessionFactory = null;
        Session session = null;
        Insurance insurance = null;
        try {
            sessionFactory = HibernateUtil.getSessionFactory();
            session = sessionFactory.openSession();
            insurance = session.get(Insurance.class, id);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return insurance;
    }
}
