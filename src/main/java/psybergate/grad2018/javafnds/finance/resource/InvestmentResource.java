package psybergate.grad2018.javafnds.finance.resource;

import java.util.Collection;

import psybergate.grad2018.javafnds.finance.entity.Investment;

public class InvestmentResource extends AbstractResource<Investment> {

	@Override
	public Investment getById(Long id) {
		return em.find(Investment.class, id);
	}

	@Override
	public Collection<Investment> getAll() {
		return getAll(Investment.class);
	}

}