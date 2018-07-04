package psybergate.grad2018.javafnds.finance.resource;

import java.util.Collection;

import javax.enterprise.context.RequestScoped;

import psybergate.grad2018.javafnds.finance.entity.Bond;

@RequestScoped
public class BondResource extends AbstractResource<Bond> implements Resource<Bond> {

	@Override
	public Collection<Bond> getAll() {
		return getAll(Bond.class);
	}

	@Override
	public Bond getById(Long id) {
		return getEntityManager().find(Bond.class, id);
	}

}
