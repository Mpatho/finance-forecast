package psybergate.grad2018.javafnds.finance.resource;

import java.util.Collection;

public interface Resource<T> extends Iterable<T> {

	Collection<T> getAll();

	T getById(Long id);

	void save(T entity);

	void removeById(Long id);

	void remove(T entity);

}
