package psybergate.grad2018.javafnds.finance.resource;

import java.util.Collection;

import javax.enterprise.context.Dependent;

import psybergate.grad2018.javafnds.finance.entity.Investment;

@Dependent
public class InvestmentResource extends AbstractResource<Investment> implements ForecastResource<Investment> {

	@Override
	public Investment getById(Long id) {
		return em.find(Investment.class, id);
	}

	@Override
	public Collection<Investment> getAll() {
		return getAll(Investment.class);
	}

	@Override
	public void save(Investment entity) {
		if (entity.getId() == null) {
			super.save(entity);
		} else {
			Investment investment = getById(entity.getId());
			investment.setInitialAmount(entity.getInitialAmount());
			investment.setMonths(entity.getMonths());
			investment.setName(entity.getName());
			investment.setRate(entity.getRate());
			super.save(investment);
		}
	}
	
	@Override
	public Investment getByName(String name) {
		// need modification
		if (name == null) return null;
		for (Investment investment : this) {
			if (investment.getName().equals(name)) return investment;
		}
		return null;
	}

}
