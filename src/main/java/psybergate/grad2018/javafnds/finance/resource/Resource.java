package psybergate.grad2018.javafnds.finance.resource;

import java.util.Collection;

import psybergate.grad2018.javafnds.finance.entity.ForecastEntity;

public interface Resource<T extends ForecastEntity> extends Iterable<T> {

	Collection<T> getAll();

	T getById(Long id);

	void save(T entity);

	void removeById(Long id);

	void remove(T entity);

	boolean contains(T entity);

}
