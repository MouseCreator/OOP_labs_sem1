package univ.lab.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import univ.lab.model.Derivative;

import java.util.List;
import java.util.Optional;

public class DerivativeDaoImpl implements DerivativeDao {

    private final CrudDaoManager<Derivative> crudDaoManager;

    private final SessionFactory sessionFactory;

    public DerivativeDaoImpl(SessionFactory sessionFactory, CrudDaoManager<Derivative> crudDaoManager) {
        this.sessionFactory = sessionFactory;
        this.crudDaoManager = crudDaoManager;
    }

    @Override
    public Derivative save(Derivative insurance) {
        return crudDaoManager.save(insurance);
    }

    @Override
    public List<Derivative> findAll() {
        try (Session session = sessionFactory.openSession()) {
            String hql = "SELECT d FROM Derivative d LEFT JOIN FETCH d.insurances";
            Query<Derivative> query = session.createQuery(hql, Derivative.class);
            return query.list();
        } catch (Exception e) {
            throw  new RuntimeException("Cannot get all entities: Derivative", e);
        }
    }

    @Override
    public Optional<Derivative> findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "from Derivative d left join fetch d.insurances where d.id = :id";
            Query<Derivative> query = session.createQuery(hql, Derivative.class);
            query.setParameter("id", id);
            Derivative singleResult = query.getSingleResult();
            return Optional.ofNullable(singleResult);
        } catch (Exception e) {
            throw new RuntimeException("Cannot get Derivative by Id: " + id, e);
        }
    }

    @Override
    public void delete(Long id) {
        crudDaoManager.delete(id);
    }

    @Override
    public void delete(Derivative entity) {
        crudDaoManager.delete(entity);
    }

    @Override
    public Derivative update(Derivative entity) {
        return crudDaoManager.update(entity);
    }
}
