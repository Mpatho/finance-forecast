package psybergate.grad2018.javafnds.finance.resource;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

public abstract class AbstractResource<T> implements Resource<T> {

	@PersistenceContext
	protected EntityManager em;

	public AbstractResource() {
		super();
	}

	@Override
	public void removeById(Long id) {
		T entity = getById(id);
		remove(entity);
	}

	@Override
	public void save(T entity) {
		em.persist(entity);
	}

	@Override
	public void remove(T entity) {
		em.remove(entity);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Collection<T> getAll(Class<T> clazz) {
		CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
		cq.select(cq.from(clazz));
		Query q = em.createQuery(cq);
		return q.getResultList();
	}

}