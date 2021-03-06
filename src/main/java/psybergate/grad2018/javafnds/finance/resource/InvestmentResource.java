package psybergate.grad2018.javafnds.finance.resource;

import java.util.Collection;

import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;

import psybergate.grad2018.javafnds.finance.entity.Investment;

@Dependent
public class InvestmentResource extends AbstractResource<Investment> implements Resource<Investment> {

	@Override
	public Investment getById(Long id) {
		return getEntityManager().find(Investment.class, id);
	}

	@Override
	public Collection<Investment> getAll() {
		return getAll(Investment.class);
	}

}
