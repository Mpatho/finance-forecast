package psybergate.grad2018.javafnds.finance.resource;

import java.util.Collection;
import java.util.Iterator;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import psybergate.grad2018.javafnds.finance.entity.ForecastEntity;

public abstract class AbstractResource<T extends ForecastEntity> implements Resource<T> {

	@PersistenceContext(unitName = "financeDB")
	private EntityManager em;

	protected AbstractResource() {}

	@Override
	public void removeById(Long id) {
		T entity = getById(id);
		remove(entity);
	}

	@Override
	public void save(T entity) {
		if (contains(entity)) {
			entity = em.merge(entity);
		}
		em.persist(entity);
	}

	@Override
	public void remove(T entity) {
		if (!em.contains(entity)) {
			entity = em.merge(entity);
		}
		em.remove(entity);
	}

	protected EntityManager getEntityManager() {
		return em;
	}

	protected Collection<T> getAll(Class<T> clazz) {
		CriteriaQuery<T> cq = em.getCriteriaBuilder().createQuery(clazz);
		Root<T> entity = cq.from(clazz);
		TypedQuery<T> q = em.createQuery(cq.select(entity));
		return q.getResultList();
	}

	@Override
	public Iterator<T> iterator() {
		return getAll().iterator();
	}

	@Override
	@SuppressWarnings({
			"rawtypes",
			"unchecked"
	})
	public boolean contains(T entity) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery cq = cb.createQuery();
		Root e = cq.from(entity.getClass());
		Predicate predicate = cb.equal(e.get("id"), entity.getId());
		cq.select(e).where(predicate);
		return em.createQuery(cq).getResultList().size() > 0;
	}
}