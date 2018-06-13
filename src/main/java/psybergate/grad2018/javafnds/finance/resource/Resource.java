package psybergate.grad2018.javafnds.finance.resource;

import java.util.Collection;

public interface Resource<T> {

	T getById(Long id);

	void save(T entity);

	Collection<T> getAll();

	void removeById(Long id);

	void remove(T entity);
}
