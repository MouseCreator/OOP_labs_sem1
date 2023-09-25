package univ.lab.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public class CrudDaoManagerImpl<T> implements CrudDaoManager<T> {

    private final SessionFactory sessionFactory;
    private final Class<T> tClass;

    public CrudDaoManagerImpl(SessionFactory sessionFactory, Class<T> tClass) {
        this.sessionFactory = sessionFactory;
        this.tClass = tClass;
    }

    @Override
    public T save(T entity) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
            return entity;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Cannot create entity " + entity, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<T> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from " + tClass.getSimpleName(), tClass).getResultList();
        } catch (Exception e) {
            throw  new RuntimeException("Cannot get all entities: " + tClass.getSimpleName(), e);
        }
    }

    @Override
    public Optional<T> findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.get(tClass, id));
        } catch (Exception e) {
            throw new RuntimeException("Cannot get entity: " + tClass.getSimpleName() + " with id " + id, e);
        }
    }

    @Override
    public void delete(T entity) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.remove(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Cannot delete entity " + entity, e);
        } finally {
            if (session != null)
                session.close();
        }
    }

    @Override
    public void delete(Long id) {
        Optional<T> entityOptional = findById(id);
        entityOptional.ifPresent(this::delete);
    }

    @Override
    public T update(T entity) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            T merged = session.merge(entity);
            transaction.commit();
            return merged;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Cannot update entity " + entity, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
